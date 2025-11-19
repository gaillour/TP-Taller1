package modelo.negocio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import excepciones.ChoferNoDisponibleException;
import excepciones.ClienteConPedidoPendienteException;
import excepciones.ClienteConViajePendienteException;
import excepciones.ClienteNoExisteException;
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

public class EmpresaTestEscenario3 {
	
	private Escenario3 escenario = new Escenario3(); 
    
    @Before
    public void setUp() throws Exception {
    	escenario.setUp();
    }

    @After
    public void tearDown() throws Exception {
    	escenario.tearDown();
    }
	
//----- Metodo void agregarPedido(Pedido pedido)
	
	// Clases cubiertas: 1, 2, 4, 7
    @Test
    public void testAgregarPedido_ClienteConPedidoPendiente() {
        try {
        	// Obtenemos el cliente registrado que ya realizo un pedido (sin terminar)
            Cliente cliente = escenario.empresa.getClientes().get("user4");
            assertNotNull("El cliente no fue registrado correctamente", cliente);

            // Verificamos si el cliente tiene un pedido pendiente en el HashMap de Pedidos
            HashMap<Cliente, Pedido> pedidosIniciados = escenario.empresa.getPedidos();
            assertTrue("El cliente debería tener un pedido iniciado", pedidosIniciados.containsKey(cliente));

            // Intentamos agregar un segundo pedido para el mismo cliente, lo que debería fallar
            Pedido pedido2 = new Pedido(cliente, 2, true, false, 10, "ZONA_STANDARD");
            escenario.empresa.agregarPedido(pedido2);

            fail("Se esperaba una ClienteConPedidoPendienteException al intentar agregar un segundo pedido con un cliente que ya tiene un pedido pendiente");

        } catch (ClienteConPedidoPendienteException e) {
            // Éxito: Se lanzó ClienteConPedidoPendienteException como se esperaba
            assertEquals("El mensaje no corresponde con la excepcion adecuada.", Mensajes.CLIENTE_CON_PEDIDO_PENDIENTE.getValor(), e.getMessage());
            
        } catch (SinVehiculoParaPedidoException e) {
            fail("No se esperaba una SinVehiculoParaPedidoException: " + e.getMessage());

        } catch (ClienteNoExisteException e) {
            fail("No se esperaba una ClienteNoExisteException: " + e.getMessage());

        } catch (ClienteConViajePendienteException e) {
            fail("No se esperaba una ClienteConViajePendienteException: " + e.getMessage());

        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName() + " - " + e.getMessage());
        }
    }


//----- Metodo void crearViaje(Pedido pedido, Chofer chofer, Vehiculo vehiculo)					    
    
    // Clases cubiertas: 1, 2, 4, 5, 7, 8, 10, 12
    @Test
    public void testCrearViaje_ClaseCorrecta() {
        try {
        	Cliente cliente = escenario.empresa.getClientes().get("user1");
    		Pedido pedido = escenario.empresa.getPedidoDeCliente(cliente);
    		
    		ArrayList<Chofer> choferes = escenario.empresa.getChoferesDesocupados();
    		Chofer chofer = choferes.get(0);
    		
    		HashMap<String, Vehiculo> vehiculos = escenario.empresa.getVehiculos() ;
    		Vehiculo auto = vehiculos.get("AAA111");
    		
            // Crear el viaje
        	escenario.empresa.crearViaje(pedido, chofer, auto);
            
            // Verificar que el viaje haya sido creado correctamente
            HashMap<Cliente, Viaje> viajesIniciados = escenario.empresa.getViajesIniciados();
            assertTrue("El viaje no fue registrado correctamente", viajesIniciados.containsKey(cliente));

        } catch (PedidoInexistenteException e) {
            fail("No se esperaba una PedidoInexistenteException: " + e.getMessage());

        } catch (ChoferNoDisponibleException e) {
            fail("No se esperaba una ChoferNoDisponibleException: " + e.getMessage());

        } catch (VehiculoNoDisponibleException e) {
            fail("No se esperaba una VehiculoNoDisponibleException: " + e.getMessage());

        } catch (VehiculoNoValidoException e) {
            fail("No se esperaba una VehiculoNoValidoException: " + e.getMessage());

        } catch (ClienteConViajePendienteException e) {
            fail("No se esperaba una ClienteConViajePendienteException: " + e.getMessage());

        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName() + " - " + e.getMessage());
        }
    }

    // Clases cubiertas: 1, 2, 4, 5
    @Test
    public void testCrearViaje_ChoferNoDisponible() {
        try {
            // Elimino los choferes de la lista de choferes desocupados
            ArrayList<Chofer> choferesDesocupados = escenario.empresa.getChoferesDesocupados();
            choferesDesocupados.clear();

            Cliente cliente = escenario.empresa.getClientes().get("user1");
    		Pedido pedido = escenario.empresa.getPedidoDeCliente(cliente);
    		
    		HashMap<String, Chofer> choferes = escenario.empresa.getChoferes();
    		Chofer chofer = choferes.get("11111111");
    		
    		HashMap<String, Vehiculo> vehiculos = escenario.empresa.getVehiculos() ;
    		Vehiculo vehiculo = vehiculos.get("AAA111");

            // Intentamos crear el viaje con un chofer no disponible
            escenario.empresa.crearViaje(pedido, chofer, vehiculo);

            // Si llegamos a esta línea, el test debe fallar, ya que se esperaba una excepción
            fail("Se esperaba una ChoferNoDisponibleException al crear un viaje con un chofer no disponible");

        } catch (ChoferNoDisponibleException e) {
            // Éxito: Se lanzó ChoferNoDisponibleException como se esperaba
            assertEquals("El mensaje no corresponde a la excepcion adecuada.", Mensajes.CHOFER_NO_DISPONIBLE.getValor(), e.getMessage());

        } catch (PedidoInexistenteException e) {
            fail("Se lanzó PedidoInexistenteException en lugar de ChoferNoDisponibleException: " + e.getMessage());

        } catch (VehiculoNoDisponibleException e) {
            fail("Se lanzó VehiculoNoDisponibleException en lugar de ChoferNoDisponibleException: " + e.getMessage());

        } catch (VehiculoNoValidoException e) {
            fail("Se lanzó VehiculoNoValidoException en lugar de ChoferNoDisponibleException: " + e.getMessage());

        } catch (ClienteConViajePendienteException e) {
            fail("Se lanzó ClienteConViajePendienteException en lugar de ChoferNoDisponibleException: " + e.getMessage());

        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName() + " - " + e.getMessage());
        }
    }

    // Clases cubiertas: 1, 2, 4, 5, 7, 9
    @Test
    public void testCrearViaje_VehiculoNoDisponible() {
        try {
        	// Elimino los vehículos de la lista de vehículos desocupados para simular que están todos ocupados
            ArrayList<Vehiculo> vehiculosD = escenario.empresa.getVehiculosDesocupados();
            vehiculosD.clear();
            
            HashMap<String, Vehiculo> vehiculos = escenario.empresa.getVehiculos() ;
    		Vehiculo vehiculo = vehiculos.get("AAA111");
            
            HashMap<String, Chofer> choferes = escenario.empresa.getChoferes();
    		Chofer chofer = choferes.get("11111111");

            Cliente cliente = escenario.empresa.getClientes().get("user1");
    		Pedido pedido = escenario.empresa.getPedidoDeCliente(cliente);

            // Intentamos crear el viaje con un vehículo no disponible
            escenario.empresa.crearViaje(pedido, chofer, vehiculo);

            // Si llegamos a esta línea, el test debe fallar, ya que se esperaba una excepción
            fail("Se esperaba una VehiculoNoDisponibleException al crear un viaje con un vehículo no disponible");

        } catch (VehiculoNoDisponibleException e) {
            // Éxito: Se lanzó VehiculoNoDisponibleException como se esperaba
        	assertEquals("El mensaje no corresponde a la excepcion adecuada.", Mensajes.VEHICULO_NO_DISPONIBLE.getValor(), e.getMessage());

        } catch (PedidoInexistenteException e) {
            fail("Se lanzó PedidoInexistenteException en lugar de VehiculoNoDisponibleException: " + e.getMessage());

        } catch (ChoferNoDisponibleException e) {
            fail("Se lanzó ChoferNoDisponibleException en lugar de VehiculoNoDisponibleException: " + e.getMessage());

        } catch (VehiculoNoValidoException e) {
            fail("Se lanzó VehiculoNoValidoException en lugar de VehiculoNoDisponibleException: " + e.getMessage());

        } catch (ClienteConViajePendienteException e) {
            fail("Se lanzó ClienteConViajePendienteException en lugar de VehiculoNoDisponibleException: " + e.getMessage());

        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName() + " - " + e.getMessage());
        }
    }

    // Clases cubiertas: 1, 2, 4, 5, 7, 8, 11
    @Test
    public void testCrearViaje_VehiculoNoValido() {
        try {
        	// vehículo que no permite mascotas
        	HashMap<String, Vehiculo> vehiculos = escenario.empresa.getVehiculos() ;
    		Vehiculo vehiculoNoValido = vehiculos.get("BBB222");
            
            HashMap<String, Chofer> choferes = escenario.empresa.getChoferes();
    		Chofer chofer = choferes.get("11111111");

    		// Pedido que solicita poder viajar con mascotas
            Cliente cliente = escenario.empresa.getClientes().get("user1");
    		Pedido pedido = escenario.empresa.getPedidoDeCliente(cliente);

            // Intentamos crear el viaje con un vehículo NO válido 
            escenario.empresa.crearViaje(pedido, chofer, vehiculoNoValido);

            // Si llegamos a esta línea, el test debe fallar, ya que se esperaba una excepción
            fail("Se esperaba una VehiculoNoValidoException al crear un viaje con un vehículo no válido para el pedido");

        } catch (VehiculoNoValidoException e) {
            // Éxito: Se lanzó VehiculoNoValidoException como se esperaba
        	assertEquals("El mensaje no corresponde a la excepcion adecuada.", Mensajes.VEHICULO_NO_VALIDO.getValor(), e.getMessage());

        } catch (PedidoInexistenteException e) {
            fail("Se lanzó PedidoInexistenteException en lugar de VehiculoNoValidoException: " + e.getMessage());

        } catch (ChoferNoDisponibleException e) {
            fail("Se lanzó ChoferNoDisponibleException en lugar de VehiculoNoValidoException: " + e.getMessage());

        } catch (VehiculoNoDisponibleException e) {
            fail("Se lanzó VehiculoNoDisponibleException en lugar de VehiculoNoValidoException: " + e.getMessage());

        } catch (ClienteConViajePendienteException e) {
            fail("Se lanzó ClienteConViajePendienteException en lugar de VehiculoNoValidoException: " + e.getMessage());

        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName() + " - " + e.getMessage());
        }
    }

//----- Metodo Pedido getPedidoDeCliente(Cliente cliente)
    
    // Clases cubiertas: 1, 2
    @Test
    public void testGetPedidoDeCliente_ClienteConPedidoEnCurso() {
        try {
            Cliente cliente = escenario.empresa.getClientes().get("user5");
            
            // Creamos un pedido y lo asignamos
            Pedido pedido = new Pedido(cliente, 2, true, false, 10, "ZONA_STANDARD");
            escenario.empresa.agregarPedido(pedido);

            // Verificamos que el cliente tiene un pedido en curso
            Pedido pedidoEnCurso = escenario.empresa.getPedidoDeCliente(cliente);
            assertNotNull("El cliente debería tener un pedido en curso", pedidoEnCurso);
            assertEquals("El pedido en curso debería coincidir con el pedido realizado", pedido, pedidoEnCurso);

        } catch (Exception e) { //por la excepcion que se puede generar a la hora de agregar el pedido
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName() + " - " + e.getMessage());
        }
    }
    
    // Clases cubiertas: 1, 3
    @Test
    public void testGetPedidoDeCliente_ClienteSinPedidoPendiente() {
        	// El cliente no tiene ningún pedido pendiente, no agregamos ningún pedido
            Cliente cliente = escenario.empresa.getClientes().get("user5");

            // Llamamos al método para obtener el pedido
            Pedido pedidoPendiente = escenario.empresa.getPedidoDeCliente(cliente);

            // Verificamos que el método retorne null, ya que no tiene pedidos pendientes
            assertNull("El cliente no debería tener ningún pedido pendiente", pedidoPendiente);
    }

    
}
