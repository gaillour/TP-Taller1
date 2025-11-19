package modelo.negocio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
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
import util.Mensajes;

public class EmpresaTestEscenario2 {

	private Escenario2 escenario = new Escenario2(); 
    
    @Before
    public void setUp() throws Exception {
    	escenario.setUp();
    }

    @After
    public void tearDown() throws Exception {
    	escenario.tearDown();
    }
    
//----- Metodo void agregarPedido(Pedido pedido)				   
    
    // Clases cubiertas: 1, 2, 4, 6, 8
    @Test
    public void testPedido_ClaseCorrecta() {
        try {
        	// Obtenemos el cliente registrado
            Cliente cliente = escenario.empresa.getClientes().get("user1");

            // Creamos un pedido válido (2 pasajeros, sin mascotas, sin baúl)
            Pedido pedido = new Pedido(cliente, 2, false, false, 10, "ZONA_STANDARD");
            escenario.empresa.agregarPedido(pedido);

            HashMap<Cliente, Pedido> pedidosRegistrados = escenario.empresa.getPedidos();
            
            // Verificamos que el pedido haya sido agregado correctamente al HashMap de pedidos
            assertTrue("El cliente no tiene un pedido registrado", pedidosRegistrados.containsKey(cliente));  
            
            // Verificación de que el pedido asociado al cliente sea el correcto
            //assertEquals("El pedido asociado al cliente no es el correcto", pedido, pedidosRegistrados.get(cliente));
            assertSame("El pedido asociado al cliente no es el correcto", pedido, pedidosRegistrados.get(cliente));
        } catch (SinVehiculoParaPedidoException e) {
            fail("No se esperaba una SinVehiculoParaPedidoException: " + e.getMessage());

        } catch (ClienteNoExisteException e) {
            fail("No se esperaba una ClienteNoExisteException: " + e.getMessage());

        } catch (ClienteConViajePendienteException e) {
            fail("No se esperaba una ClienteConViajePendienteException: " + e.getMessage());

        } catch (ClienteConPedidoPendienteException e) {
            fail("No se esperaba una ClienteConPedidoPendienteException: " + e.getMessage());

        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName() + " - " + e.getMessage());
        }
    }

 // Clases cubiertas: 1, 2, 4, 6, 9
    @Test
    public void testAgregarPedido_SinVehiculoDisponible() {
        try {
        	// Obtenemos el cliente registrado
            Cliente cliente = escenario.empresa.getClientes().get("user1");

            // Creamos un pedido que no puede ser satisfecho por los vehículos registrados
            // El pedido requiere 10 plazas, pero la mayor combi tiene solo 9 plazas
            Pedido pedido = new Pedido(cliente, 10, true, false, 10, "ZONA_STANDARD");

            // Intentamos agregar el pedido
            escenario.empresa.agregarPedido(pedido);

            fail("Se esperaba una SinVehiculoParaPedidoException al agregar un pedido que no puede ser satisfecho por los vehículos disponibles");

        } catch (SinVehiculoParaPedidoException e) {
            // Éxito: Se lanzó SinVehiculoParaPedidoException como se esperaba
            assertEquals("El mensaje no corresponde con la excepcion adecuada.", Mensajes.SIN_VEHICULO_PARA_PEDIDO.getValor(), e.getMessage());

        } catch (ClienteNoExisteException e) {
            fail("No se esperaba una ClienteNoExisteException: " + e.getMessage());

        } catch (ClienteConViajePendienteException e) {
            fail("No se esperaba una ClienteConViajePendienteException: " + e.getMessage());

        } catch (ClienteConPedidoPendienteException e) {
            fail("No se esperaba una ClienteConPedidoPendienteException: " + e.getMessage());

        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName() + " - " + e.getMessage());
        }
    }

    
//----- Metodo Metodo ArrayList<Vehiculo> vehiculosOrdenadosPorPedido(Pedido pedido)	 
    
    // Clases cubiertas: 1, 2, 4 (Pedido valido con vehiculos habilitados y puntajes distintos)
    @Test
    public void testVehiculosOrdenadosPorPedido_ClaseCorrecta() {
            // Obtenemos el cliente registrado
            Cliente cliente = escenario.empresa.getClientes().get("user1");

            // Creamos un pedido válido (2 pasajeros, sin mascotas, sin baúl)
            Pedido pedido = new Pedido(cliente, 2, false, false, 10, "ZONA_STANDARD");

            // Llamamos al método vehiculosOrdenadosPorPedido
            ArrayList<Vehiculo> vehiculosOrdenados = escenario.empresa.vehiculosOrdenadosPorPedido(pedido);

            // Verificamos que el ArrayList no sea null y contenga los vehículos habilitados
            assertNotNull("El ArrayList de vehículos no debería ser null", vehiculosOrdenados);
            assertEquals("El número de vehículos habilitados es incorrecto", 6, vehiculosOrdenados.size());

            // Puntajes esperados:
            // puntajeEsperadoAuto = 30 * 2
            // puntajeEsperadoCombi = 10 * 2 
            // puntajeEsperadoMoto = null 

            int[] puntajesEsperados = {60, 60, 60, 20, 20, 20};

            // Verificamos que los vehículos estén ordenados en orden descendente de puntaje
            for (int i = 0; i < vehiculosOrdenados.size(); i++) {
                Vehiculo vehiculo = vehiculosOrdenados.get(i);
                Integer puntaje = vehiculo.getPuntajePedido(pedido);
                assertEquals("El puntaje del vehículo en la posición " + i + " no es el esperado",
                             (Integer) puntajesEsperados[i], puntaje);
            }    
    }

    // Clases cubiertas: 1, 3
    @Test
    public void testVehiculosOrdenadosPorPedido_SinVehiculosHabilitados() {  //esta bien asumir que el metodo se comporta asi en este caso????
        	// Obtenemos el cliente registrado
            Cliente cliente = escenario.empresa.getClientes().get("user1");

            // Creamos un pedido válido pero con más pasajeros que la capacidad de los vehículos (10 pasajeros)
            Pedido pedido = new Pedido(cliente, 10, false, true, 10, "ZONA_STANDARD");

            // Llamamos al método vehiculosOrdenadosPorPedido
            ArrayList<Vehiculo> vehiculosOrdenados = escenario.empresa.vehiculosOrdenadosPorPedido(pedido);

            // Verificamos que el ArrayList esté vacío, ya que ningún vehículo puede satisfacer el pedido
            assertNotNull("El ArrayList de vehículos no debería ser null", vehiculosOrdenados);
            assertTrue("El ArrayList debería estar vacío ya que ningún vehículo está habilitado", vehiculosOrdenados.isEmpty());
    }

//----- Metodo boolean validarPedido(Pedido pedido)				
    
    // Clases cubiertas: 1, 2
    @Test
    public void testValidarPedido_ClaseCorrecta() {
        	// Obtenemos el cliente registrado
            Cliente cliente = escenario.empresa.getClientes().get("user1");

            // Creamos un pedido válido que puede ser satisfecho 
            Pedido pedido = new Pedido(cliente, 2, true, false, 10, "ZONA_STANDARD");

            // Validamos el pedido
            boolean resultado = escenario.empresa.validarPedido(pedido);

            // Verificamos que el pedido sea válido
            assertTrue("El pedido debería ser válido ya que hay vehículos habilitados", resultado);

    }

//----- Metodo void crearViaje(Pedido pedido, Chofer chofer, Vehiculo vehiculo)		
    
    // Clases cubiertas: 1, 4, 5, 9 
    @Test
    public void testCrearViaje_PedidoNoRegistrado() {
        try {
        	Cliente cliente = escenario.empresa.getClientes().get("user1");
    		
    		ArrayList<Chofer> choferes = escenario.empresa.getChoferesDesocupados();
    		Chofer chofer = choferes.get(0);
    		
    		HashMap<String, Vehiculo> vehiculos = escenario.empresa.getVehiculos() ;
    		Vehiculo vehiculo = vehiculos.get("AAA111");
            
            Pedido pedidoNoRegistrado = new Pedido(cliente, 2, true, false, 10, "ZONA_STANDARD");

            // Intentamos crear el viaje con el pedido no registrado
            escenario.empresa.crearViaje(pedidoNoRegistrado, chofer, vehiculo);
            fail("Se esperaba una PedidoInexistenteException al crear un viaje con un pedido no registrado");

        } catch (PedidoInexistenteException e) {
            // Éxito: Se lanzó PedidoInexistenteException como se esperaba
            assertEquals("El mensaje no corresponde con la excepcion adecuada.", Mensajes.PEDIDO_INEXISTENTE.getValor(), e.getMessage());

        } catch (ChoferNoDisponibleException e) {
            fail("Se lanzó ChoferNoDisponibleException en lugar de PedidoInexistenteException: " + e.getMessage());

        } catch (VehiculoNoDisponibleException e) {
            fail("Se lanzó VehiculoNoDisponibleException en lugar de PedidoInexistenteException: " + e.getMessage());

        } catch (VehiculoNoValidoException e) {
            fail("Se lanzó VehiculoNoValidoException en lugar de PedidoInexistenteException: " + e.getMessage());

        } catch (ClienteConViajePendienteException e) {
            fail("Se lanzó ClienteConViajePendienteException en lugar de PedidoInexistenteException: " + e.getMessage());

        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName() + " - " + e.getMessage());
        }
    }

    
}
