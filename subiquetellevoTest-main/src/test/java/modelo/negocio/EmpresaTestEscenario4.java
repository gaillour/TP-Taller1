package modelo.negocio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import excepciones.ChoferNoDisponibleException;
import excepciones.ClienteConPedidoPendienteException;
import excepciones.ClienteConViajePendienteException;
import excepciones.ClienteNoExisteException;
import excepciones.ClienteSinViajePendienteException;
import excepciones.PedidoInexistenteException;
import excepciones.SinVehiculoParaPedidoException;
import excepciones.VehiculoNoDisponibleException;
import excepciones.VehiculoNoValidoException;
import modeloDatos.Chofer;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloDatos.Viaje;
import util.Mensajes;

public class EmpresaTestEscenario4 {
	
	private Escenario4 escenario = new Escenario4(); 
    
    @Before
    public void setUp() throws Exception {
    	escenario.setUp();
    }

    @After
    public void tearDown() throws Exception {
    	escenario.tearDown();
    }
    
//----- Metodo void agregarPedido(Pedido pedido)
    
    // Clases cubiertas: 1, 2, 5
    @Test
    public void testAgregarPedido_ClienteConViajePendiente() {
        try {

            // Obtenemos el cliente registrado que ya se encuentra en un viaje (sin terminar)
            Cliente cliente = escenario.empresa.getClientes().get("user4");

            // Verificamos si el cliente tiene un viaje pendiente en el HashMap de viajes iniciados
            HashMap<Cliente, Viaje> viajesIniciados = escenario.empresa.getViajesIniciados();
            assertTrue("El cliente debería tener un viaje iniciado", viajesIniciados.containsKey(cliente));

            // Ahora intentamos crear un segundo pedido para el mismo cliente, que debería fallar
            Pedido pedido2 = new Pedido(cliente, 2, true, false, 10, "ZONA_STANDARD");
            escenario.empresa.agregarPedido(pedido2);

            fail("Se esperaba una ClienteConViajePendienteException al agregar un segundo pedido con un cliente que tiene un viaje pendiente");

        } catch (ClienteConViajePendienteException e) {
            // Éxito: Se lanzó ClienteConViajePendienteException como se esperaba
        	assertEquals("El mensaje no corresponde con la excepcion adecuada.", Mensajes.CLIENTE_CON_VIAJE_PENDIENTE.getValor(), e.getMessage());

        } catch (SinVehiculoParaPedidoException e) {
            fail("No se esperaba una SinVehiculoParaPedidoException: " + e.getMessage());

        } catch (ClienteNoExisteException e) {
            fail("No se esperaba una ClienteNoExisteException: " + e.getMessage());

        } catch (ClienteConPedidoPendienteException e) {
            fail("No se esperaba una ClienteConPedidoPendienteException: " + e.getMessage());

        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName() + " - " + e.getMessage());
        }
    }
    
//----- Metodo void crearViaje(Pedido pedido, Chofer chofer, Vehiculo vehiculo)		
    
    // Clases cubiertas: 1, 2, 4, 5, 7, 8, 10, 13
    @Test
    public void testCrearViaje_ClienteConViajePendiente() {
        try {
        	// Este cliente ya se encuentra en un viaje iniciado
            Cliente cliente = escenario.empresa.getClientes().get("user1");

            // Verificamos que el cliente tiene un viaje iniciado  //esta bien verificar esto aca????
            assertTrue("El cliente debería tener un viaje pendiente", escenario.empresa.getViajesIniciados().containsKey(cliente));

            // Creamos un segundo pedido para el mismo cliente
            Pedido segundoPedido = new Pedido(cliente, 1, false, false, 15, "ZONA_STANDARD");
            escenario.empresa.agregarPedido(segundoPedido);

            HashMap<String, Vehiculo> vehiculos = escenario.empresa.getVehiculos() ;
    		Vehiculo vehiculo = vehiculos.get("III999");
            
            HashMap<String, Chofer> choferes = escenario.empresa.getChoferes();
    		Chofer chofer = choferes.get("66666666");
            
            // Intentamos crear el viaje con el cliente que ya tiene un viaje pendiente
            escenario.empresa.crearViaje(segundoPedido, chofer, vehiculo);

            // Si llegamos a esta línea, el test debe fallar, ya que se esperaba una excepción
            fail("Se esperaba una ClienteConViajePendienteException al crear un viaje con un cliente que ya tiene un viaje pendiente");

        } catch (ClienteConViajePendienteException e) {
            // Éxito: Se lanzó ClienteConViajePendienteException como se esperaba
        	assertEquals("El mensaje no corresponde con la excepcion adecuada.", Mensajes.CLIENTE_CON_VIAJE_PENDIENTE.getValor(), e.getMessage());

        } catch (PedidoInexistenteException e) {
            fail("Se lanzó PedidoInexistenteException en lugar de ClienteConViajePendienteException: " + e.getMessage());

        } catch (ChoferNoDisponibleException e) {
            fail("Se lanzó ChoferNoDisponibleException en lugar de ClienteConViajePendienteException: " + e.getMessage());

        } catch (VehiculoNoDisponibleException e) {
            fail("Se lanzó VehiculoNoDisponibleException en lugar de ClienteConViajePendienteException: " + e.getMessage());

        } catch (VehiculoNoValidoException e) {
            fail("Se lanzó VehiculoNoValidoException en lugar de ClienteConViajePendienteException: " + e.getMessage());

        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName() + " - " + e.getMessage());
        }
    }
    
//----- Metodo void pagarYFinalizarViaje(int calificacion)	//son necesarios tantos test de este método con distinttas calificaciones???
    
 // Clases cubiertas: 1, 2, 4
    @Test
    public void testPagarYFinalizarViaje_Calificacion5() {
        try {
        	// Este cliente ya se encuentra en un viaje iniciado
            Cliente cliente = escenario.empresa.getClientes().get("user1");

            // Logueamos al cliente en la empresa
            escenario.empresa.setUsuarioLogeado(cliente);

            // Pagamos y finalizamos el viaje con una calificación de 5
            escenario.empresa.pagarYFinalizarViaje(5);

            // Verificamos que el viaje ha sido movido de "viajesIniciados" a "viajesTerminados"
            assertFalse("El cliente ya no debería tener un viaje pendiente", 
            		escenario.empresa.getViajesIniciados().containsKey(cliente));
            assertTrue("El viaje debería estar en la lista de viajes terminados", 
            		escenario.empresa.getViajesTerminados().size() > 0); //esta bien que lo verifique asi o deberia verificar que el viaje del cliente esté en el ArrayList???
            		//o tambien se podria verificar si aumento en uno el tamaño del ArrayList
        } catch (ClienteSinViajePendienteException e) {
            // Fallamos si se lanza ClienteSinViajePendienteException, ya que el cliente tiene un viaje pendiente
            fail("No se esperaba una ClienteSinViajePendienteException: " + e.getMessage());

        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName() + " - " + e.getMessage());
        }
    }
    
    // Clases cubiertas: 1, 2, 4
    @Test
    public void testPagarYFinalizarViaje_Calificacion0() {
        try {
        	// Este cliente ya se encuentra en un viaje iniciado
            Cliente cliente = escenario.empresa.getClientes().get("user2");

            // Logueamos al cliente en la empresa
            escenario.empresa.setUsuarioLogeado(cliente);

            // Pagamos y finalizamos el viaje con una calificación de 0
            escenario.empresa.pagarYFinalizarViaje(0);

            // Verificamos que el viaje ha sido movido de "viajesIniciados" a "viajesTerminados"
            assertFalse("El cliente ya no debería tener un viaje pendiente", 
            		escenario.empresa.getViajesIniciados().containsKey(cliente));
            assertTrue("El viaje debería estar en la lista de viajes terminados", 
            		escenario.empresa.getViajesTerminados().size() > 0);

        } catch (ClienteSinViajePendienteException e) {
            // Fallamos si se lanza ClienteSinViajePendienteException, ya que el cliente tiene un viaje pendiente
            fail("No se esperaba una ClienteSinViajePendienteException: " + e.getMessage());

        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName() + " - " + e.getMessage());
        }
    }

    // Clases cubiertas: 1, 2, 4
    @Test
    public void testPagarYFinalizarViaje_Calificacion3() {
        try {
        	// Este cliente ya se encuentra en un viaje iniciado
            Cliente cliente = escenario.empresa.getClientes().get("user2");

            // Logueamos al cliente en la empresa
            escenario.empresa.setUsuarioLogeado(cliente);

            // Pagamos y finalizamos el viaje con una calificación de 3
            escenario.empresa.pagarYFinalizarViaje(3);

            // Verificamos que el viaje ha sido movido de "viajesIniciados" a "viajesTerminados"
            assertFalse("El cliente ya no debería tener un viaje pendiente", 
            		escenario.empresa.getViajesIniciados().containsKey(cliente));
            assertTrue("El viaje debería estar en la lista de viajes terminados", 
            		escenario.empresa.getViajesTerminados().size() > 0);

        } catch (ClienteSinViajePendienteException e) {
            // Fallamos si se lanza ClienteSinViajePendienteException, ya que el cliente tiene un viaje pendiente
            fail("No se esperaba una ClienteSinViajePendienteException: " + e.getMessage());

        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName() + " - " + e.getMessage());
        }
    }
    
    // Clases cubiertas: 1, 3 
    @Test
    public void testPagarYFinalizarViaje_SinViajePendiente() {
        try {
        	// Este cliente ya se encuentra en un viaje iniciado
            Cliente cliente = escenario.empresa.getClientes().get("user1");

            // Logueamos al cliente en la empresa
            escenario.empresa.setUsuarioLogeado(cliente);

            // Pagamos y finalizamos el viaje con una calificación de 3
            escenario.empresa.pagarYFinalizarViaje(3);

            // Intentamos finalizar nuevamente viaje
            escenario.empresa.pagarYFinalizarViaje(3);

            // Si llegamos aquí, el test debe fallar porque no se lanzó la excepción esperada
            fail("Se esperaba una ClienteSinViajePendienteException al intentar finalizar un viaje sin tener uno pendiente");

        } catch (ClienteSinViajePendienteException e) {
            // Éxito: se lanzó ClienteSinViajePendienteException como se esperaba
        	assertEquals("El mensaje no corresponde con la excepcion adecuada.", Mensajes.CLIENTE_SIN_VIAJE_PENDIENTE.getValor(), e.getMessage());

        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName() + " - " + e.getMessage());
        }
    }
    
//----- Metodo  Viaje getViajeDeCliente(Cliente cliente)					
    
    // Clases cubiertas: 1, 2
    @Test
    public void testGetViajeDeCliente_ClienteConViajeEnCurso() {
        	// Este cliente tiene un viaje en curso
            Cliente cliente = escenario.empresa.getClientes().get("user1");

            // Verificamos que el cliente tiene un viaje en curso
            Viaje viaje = escenario.empresa.getViajeDeCliente(cliente);
            assertNotNull("El cliente debería tener un viaje en curso", viaje);

    }
 
    // Clases cubiertas: 1, 3
    @Test
    public void testGetViajeDeCliente_ClienteSinViajeEnCurso() {
        	// Este cliente NO tiene un viaje en curso
            Cliente cliente = escenario.empresa.getClientes().get("user5");

            // Intentamos obtener el viaje del cliente, que no debería tener un viaje en curso
            Viaje viaje = escenario.empresa.getViajeDeCliente(cliente);
            
            // Verificamos que el resultado es null, ya que el cliente no tiene un viaje en curso
            assertNull("El cliente no debería tener un viaje en curso", viaje);

    }
  
    
    
    
    
}
