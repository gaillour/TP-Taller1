package modelo.negocio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import excepciones.ChoferRepetidoException;
import excepciones.ClienteConPedidoPendienteException;
import excepciones.ClienteConViajePendienteException;
import excepciones.ClienteNoExisteException;
import excepciones.SinVehiculoParaPedidoException;
import excepciones.UsuarioYaExisteException;
import excepciones.VehiculoRepetidoException;
import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Viaje;
import modeloNegocio.Empresa;

public class EmpresaTest {

    private Empresa empresa;
    @Before
    public void setUp() {
        empresa = Empresa.getInstance();
    }

  @Test
    public void testAgregarChofer() throws ChoferRepetidoException {
        ChoferPermanente chofer = new ChoferPermanente("45236014", "Fausto", 1999, 2);
        empresa.agregarChofer(chofer);

        Assert.assertEquals(chofer, empresa.getChoferes().get("45236014"));
    }

    
    @Test
    public void testAgregarCliente() throws UsuarioYaExisteException {
        empresa.agregarCliente("fausto77", "1234", "fausto");

        Cliente clienteGuardado = empresa.getClientes().get("fausto77");

        Assert.assertNotNull(clienteGuardado);
        Assert.assertEquals("fausto77", clienteGuardado.getNombreUsuario());
        Assert.assertEquals("fausto", clienteGuardado.getNombreReal());
    }

    @Test
    public void testAgregarVehiculo() throws VehiculoRepetidoException  {
    	Auto auto = new Auto("AER123", 4, false);
        empresa.agregarVehiculo(auto);

        Assert.assertEquals(auto, empresa.getVehiculos().get("AER123"));
    }
    
    @Test
    public void testAgregarPedido() throws SinVehiculoParaPedidoException,
            ClienteNoExisteException, ClienteConViajePendienteException,
            ClienteConPedidoPendienteException, UsuarioYaExisteException,
            VehiculoRepetidoException {

        empresa.agregarCliente("ivan8", "1234", "ivan");

        Auto auto = new Auto("AAA123", 4, false);
        empresa.agregarVehiculo(auto);

        Assert.assertNull("deberia ser null", empresa.getPedidoDeCliente(empresa.getClientes().get("ivan8")));
        Pedido pedido = new Pedido(
            empresa.getClientes().get("ivan8"),
            2, false, false, 20, "ZONA_PELIGROSA"
        );

        empresa.agregarPedido(pedido);
        
       

        Assert.assertEquals(pedido, empresa.getPedidoDeCliente(empresa.getClientes().get("ivan8")));
        Assert.assertEquals(pedido, empresa.getPedidos().get(empresa.getClientes().get("ivan8")));
    }
    
    
 
    @Test
    public void testFlujoCompletoDeViaje() throws Exception {
     empresa.agregarCliente("gonza58", "1234", "gonzalo");

     Cliente cliente = empresa.getClientes().get("gonza58");
     assertNotNull("El cliente no se agregó o recuperó correctamente", cliente);

     Auto auto = new Auto("EWR123", 4, false);
     empresa.agregarVehiculo(auto);

     Chofer chofer = new ChoferPermanente("1422566", "Fausto", 2022, 2);
     empresa.agregarChofer(chofer);

     Pedido pedido = new Pedido(
         cliente, 2, false, false, 2, "ZONA_PELIGROSA"
     );
     empresa.agregarPedido(pedido);
     
     empresa.crearViaje(pedido, chofer, auto);
     
     assertTrue("El cliente debería tener un viaje iniciado", empresa.getViajesIniciados().containsKey(cliente));
     assertEquals("No debería haber choferes desocupados", 0, empresa.getChoferesDesocupados().size());

     empresa.setUsuarioLogeado(cliente);
     
     empresa.pagarYFinalizarViaje(1); 

     assertEquals("La calificación esperada no es correcta", 1.0, empresa.calificacionDeChofer(chofer), 0.001);
     assertEquals("No debería quedar ningún viaje iniciado", 0, empresa.getViajesIniciados().size());
     assertEquals("Debería haber 1 viaje terminado", 1, empresa.getViajesTerminados().size());
     assertEquals("El chofer debería estar desocupado nuevamente", 1, empresa.getChoferesDesocupados().size());
 }
    
    /**
     * Prueba que el método getHistorialViajeChofer devuelve una lista
     * solo con los viajes terminados de un chofer específico.
     */
    @Test
    public void testGetHistorialViajeChofer() throws Exception {
        // --- ARRANGE ---
        // 1. Crear los actores
        empresa.agregarCliente("Rami54", "1234", "Ramiro");
        Cliente cliente1 = empresa.getClientes().get("Rami54");
        
        Chofer chofer1 = new ChoferPermanente("41253698", "Matias", 2020, 0);
        Chofer chofer2 = new ChoferPermanente("44785123", "Miguel", 2020, 0);
        empresa.agregarChofer(chofer1);
        empresa.agregarChofer(chofer2);
        
        Auto auto1 = new Auto("VAS121", 4, false);
        Auto auto2 = new Auto("KVS982", 4, false);
        empresa.agregarVehiculo(auto1);
        empresa.agregarVehiculo(auto2);
        
        // 2. Crear y finalizar 2 viajes para el Chofer 1
        // Viaje 1
        Pedido p1 = new Pedido(cliente1, 1, false, false, 10, "ZONA_STANDARD");
        empresa.agregarPedido(p1);
        empresa.crearViaje(p1, chofer1, auto1);
        empresa.setUsuarioLogeado(cliente1);
        empresa.pagarYFinalizarViaje(1); // Viaje terminado
        
        // Viaje 2
        Pedido p2 = new Pedido(cliente1, 1, false, false, 20, "ZONA_STANDARD");
        empresa.agregarPedido(p2);
        empresa.crearViaje(p2, chofer1, auto2);
        empresa.setUsuarioLogeado(cliente1);
        empresa.pagarYFinalizarViaje(2); // Viaje terminado
        
        // 3. Crear y finalizar 1 viaje para el Chofer 2 (para asegurar que no se mezclen)
        Pedido p3 = new Pedido(cliente1, 1, false, false, 30, "ZONA_STANDARD");
        empresa.agregarPedido(p3);
        empresa.crearViaje(p3, chofer2, auto1);
        empresa.setUsuarioLogeado(cliente1);
        empresa.pagarYFinalizarViaje(3); // Viaje terminado
        
        // 4. Crear 1 viaje INICIADO para el Chofer 1 (no debe aparecer en el historial)
        Pedido p4 = new Pedido(cliente1, 1, false, false, 40, "ZONA_STANDARD");
        empresa.agregarPedido(p4);
        empresa.crearViaje(p4, chofer1, auto1);
        
        // --- ACT ---
        // Consultamos los historiales
        ArrayList<Viaje> historialChofer1 = empresa.getHistorialViajeChofer(chofer1);
        ArrayList<Viaje> historialChofer2 = empresa.getHistorialViajeChofer(chofer2);
        ArrayList<Viaje> historialVacio = empresa.getHistorialViajeChofer(new ChoferPermanente("NADA", "Inexistente", 2000, 0));

        // --- ASSERT ---
        // Verificamos el historial del Chofer 1
        assertNotNull("El historial no debería ser nulo", historialChofer1);
        assertEquals("El historial del Chofer 1 debería tener 2 viajes terminados", 2, historialChofer1.size());
        
        // Verificamos los costos para asegurarnos de que son los viajes correctos
        // (Asumiendo que el historial los devuelve en orden de finalización)
        assertEquals(1.0, historialChofer1.get(0).getCalificacion(), 0.01);
        assertEquals(2.0, historialChofer1.get(1).getCalificacion(), 0.01);
        
        // Verificamos el historial del Chofer 2
        assertNotNull("El historial no debería ser nulo", historialChofer2);
        assertEquals("El historial del Chofer 2 debería tener 1 viaje terminado", 1, historialChofer2.size());
        assertEquals(3.0, historialChofer2.get(0).getCalificacion(), 0.01);
        
        // Verificamos un chofer sin viajes
        assertNotNull("El historial de un chofer sin viajes no debe ser nulo", historialVacio);
        assertTrue("El historial de un chofer sin viajes debe estar vacío", historialVacio.isEmpty());
    }
 
    /**
     * Prueba que el método getHistorialViajeCliente devuelve una lista
     * solo con los viajes terminados de un cliente específico.
     */
    @Test
    public void testGetHistorialViajeCliente() throws Exception {
        empresa.agregarCliente("MATI66", "1234", "Matias");
        Cliente cliente1 = empresa.getClientes().get("MATI66");
        
        empresa.agregarCliente("Rodri06", "1225", "Rodrigo");
        Cliente cliente2 = empresa.getClientes().get("Rodri06");
        
        Chofer chofer = new ChoferPermanente("45261326", "Juan", 2020, 0);
        empresa.agregarChofer(chofer);
        
        Auto auto = new Auto("ASA153", 4, false);
        empresa.agregarVehiculo(auto);

        Pedido p1 = new Pedido(cliente1, 1, false, false, 10, "ZONA_STANDARD");
        empresa.agregarPedido(p1);
        empresa.crearViaje(p1, chofer, auto);
        empresa.setUsuarioLogeado(cliente1);
        empresa.pagarYFinalizarViaje(1); // Costo 150
        
        Pedido p2 = new Pedido(cliente1, 1, false, false, 20, "ZONA_STANDARD");
        empresa.agregarPedido(p2);
        empresa.crearViaje(p2, chofer, auto);
        empresa.setUsuarioLogeado(cliente1);
        empresa.pagarYFinalizarViaje(2); // Costo 250
        
        Pedido p3 = new Pedido(cliente2, 1, false, false, 30, "ZONA_STANDARD");
        empresa.agregarPedido(p3);
        empresa.crearViaje(p3, chofer, auto);
        empresa.setUsuarioLogeado(cliente2);
        empresa.pagarYFinalizarViaje(3); // Costo 300
        
        Pedido p4 = new Pedido(cliente1, 1, false, false, 40, "ZONA_STANDARD");
        empresa.agregarPedido(p4);
        empresa.crearViaje(p4, chofer, auto);
        
        ArrayList<Viaje> historialCliente1 = empresa.getHistorialViajeCliente(cliente1);
        ArrayList<Viaje> historialCliente2 = empresa.getHistorialViajeCliente(cliente2);
        ArrayList<Viaje> historialVacio = empresa.getHistorialViajeCliente(new Cliente("noexiste", "1", "1"));

        assertNotNull("El historial no debería ser nulo", historialCliente1);
        assertEquals("El historial del Cliente 1 debería tener 2 viajes terminados", 2, historialCliente1.size());

        assertEquals(1.0, historialCliente1.get(0).getCalificacion(), 0.01);
        assertEquals(2.0, historialCliente1.get(1).getCalificacion(), 0.01);
        
        assertNotNull("El historial no debería ser nulo", historialCliente2);
        assertEquals("El historial del Cliente 2 debería tener 1 viaje terminado", 1, historialCliente2.size());
        assertEquals(3.0, historialCliente2.get(0).getCalificacion(), 0.01);
        
        assertNotNull("El historial de un cliente sin viajes no debe ser nulo", historialVacio);
        assertTrue("El historial de un cliente sin viajes debe estar vacío", historialVacio.isEmpty());
    }
    
    /**
     * Prueba que el método getTotalSalarios calcula correctamente la suma
     * de los salarios de los choferes registrados.
     * Verifica el caso base (0 choferes) y el caso acumulativo (múltiples choferes).
     *
     */
    @Test
    public void testGetTotalSalarios() throws ChoferRepetidoException {
        
        double totalCero = empresa.getTotalSalarios();
        assertEquals("El total de salarios con 0 choferes debe ser 0.0", 0.0, totalCero, 0.001);

        Chofer.setSueldoBasico(1000);
        ChoferPermanente chofer1 = new ChoferPermanente("45236014", "Fausto", 1999, 2);
        empresa.agregarChofer(chofer1);

        double totalConUno = empresa.getTotalSalarios();
        assertEquals("El total con un chofer debe ser igual a su sueldo neto", 
                     chofer1.getSueldoNeto(), totalConUno, 0.001);

        ChoferPermanente chofer2 = new ChoferPermanente("1422566", "García", 2010, 0);
        empresa.agregarChofer(chofer2);

        double totalConDos = empresa.getTotalSalarios();
        assertEquals("El total con dos choferes debe ser la suma de los sueldos netos de ambos",
                     chofer1.getSueldoNeto() + chofer2.getSueldoNeto(),
                     totalConDos,
                     0.001);
    }

    /**
     * Prueba que el usuario logueado es 'null' por defecto,
     * justo después de que la instancia de Empresa es inicializada.
     */
    @Test
    public void testGetUsuarioLogueadoEsNuloPorDefecto() {
        Assert.assertNull("El usuario logueado debería ser null al inicio", empresa.getUsuarioLogeado());
    }

    /**
     * Prueba el flujo simple de loguear un usuario (set) y luego recuperarlo
     */
    @Test
    public void testSetYGetUsuarioLogueado() throws UsuarioYaExisteException {
        empresa.agregarCliente("fausto77", "1234", "fausto");
        Cliente clienteALoguear = empresa.getClientes().get("fausto77");
        
        empresa.setUsuarioLogeado(clienteALoguear);
        
        Cliente usuarioRecuperado = (Cliente) empresa.getUsuarioLogeado();
        
        Assert.assertNotNull("El usuario recuperado no debería ser nulo", usuarioRecuperado);
        Assert.assertEquals("El usuario recuperado debe ser el mismo que se logueó", 
                            clienteALoguear, usuarioRecuperado);
        Assert.assertEquals("El nombre de usuario no coincide", 
                            "fausto77", usuarioRecuperado.getNombreUsuario());
    }

    /**
     * Prueba que se puede cambiar el usuario logueado
     * (simula un "cerrar sesión" y "abrir sesión" con otro usuario).
     */
    @Test
    public void testCambiarUsuarioLogueado() throws UsuarioYaExisteException {
        // --- ARRANGE ---
        empresa.agregarCliente("usuario1", "111", "Usuario Uno");
        Cliente cliente1 = empresa.getClientes().get("usuario1");
        
        empresa.agregarCliente("usuario2", "222", "Usuario Dos");
        Cliente cliente2 = empresa.getClientes().get("usuario2");
        
        // 1. Logueamos al primer usuario
        empresa.setUsuarioLogeado(cliente1);
        Assert.assertEquals("El usuario 1 debería estar logueado", 
                            cliente1, empresa.getUsuarioLogeado());
        
        // --- ACT ---
        // 2. Logueamos al segundo usuario
        empresa.setUsuarioLogeado(cliente2);
        Cliente usuarioRecuperado = (Cliente) empresa.getUsuarioLogeado();

        // --- ASSERT ---
        Assert.assertNotNull(usuarioRecuperado);
        Assert.assertEquals("El usuario 2 debería estar logueado ahora", 
                            cliente2, usuarioRecuperado);
        Assert.assertNotSame("El usuario 1 ya no debería estar logueado", 
                             cliente1, usuarioRecuperado);
    }

    /**
     * Prueba que se puede "cerrar sesión" estableciendo el
     * usuario logueado a 'null'.
     */
    @Test
    public void testSetUsuarioLogueadoANull() throws UsuarioYaExisteException {
        empresa.agregarCliente("fausto77", "1234", "fausto");
        Cliente clienteALoguear = empresa.getClientes().get("fausto77");
        empresa.setUsuarioLogeado(clienteALoguear);
        
        // Verificamos que sí se logueó
        Assert.assertNotNull("El usuario debería estar logueado", empresa.getUsuarioLogeado());
        
        // Simulamos el "logout"
        empresa.setUsuarioLogeado(null);
        
        // --- ASSERT ---
        Assert.assertNull("El usuario logueado debería ser null después del logout", 
                          empresa.getUsuarioLogeado());
    }
    
    
    @After
    public void tearDown() {
        empresa.getChoferes().clear();
        empresa.getClientes().clear();
        empresa.getVehiculos().clear();
        empresa.getPedidos().clear();
        
        empresa.getChoferesDesocupados().clear();
        empresa.getVehiculosDesocupados().clear();
        empresa.getViajesIniciados().clear();
        empresa.getViajesTerminados().clear();
        empresa.setUsuarioLogeado(null);     

}


}

