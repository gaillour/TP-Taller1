package modelo.negocio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import excepciones.ChoferRepetidoException;
import excepciones.ClienteConPedidoPendienteException;
import excepciones.ClienteConViajePendienteException;
import excepciones.ClienteNoExisteException;
import excepciones.PasswordErroneaException;
import excepciones.SinVehiculoParaPedidoException;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioYaExisteException;
import excepciones.VehiculoRepetidoException;
import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import util.Mensajes;

public class EmpresaTestEscenarioVacio {

	private EscenarioBase escenarioVacio = new EscenarioBase(); 
    
    @Before
    public void setUp() throws Exception {
        escenarioVacio.setUp();
    }

    @After
    public void tearDown() throws Exception {
        escenarioVacio.tearDown();
    }
    
//----- Metodo void agregarChofer(Chofer chofer)				   
    
    // Clase cubierta: 1, 3
    @Test
    public void testAgregarChofer_ClaseCorrecta() {
        try {
            // Creamos un chofer válido
            ChoferPermanente choferP = new ChoferPermanente("12345678", "Gonzalo", 1999, 2);
            escenarioVacio.empresa.agregarChofer(choferP);
            
            try {
                // Verificamos si el chofer fue añadido correctamente en la estructura de datos
                HashMap<String, Chofer> choferesRegistrados = escenarioVacio.empresa.getChoferes();
                
                // Comprobamos que el chofer fue agregado
                assertTrue("El chofer no fue registrado correctamente en la empresa", 
                           choferesRegistrados.containsKey(choferP.getDni()));
                
                // Verificamos que el chofer registrado es efectivamente el mismo que se agregó
                assertEquals("El chofer registrado no coincide con el chofer ingresado", 
                             choferP, choferesRegistrados.get(choferP.getDni()));
                
            } catch (Exception ex) {
                // Si ocurre alguna excepción en la verificación, fallamos el test y mostramos el mensaje
                fail("Error inesperado al verificar el chofer registrado: " + ex.getMessage());
            }
        } catch (ChoferRepetidoException e) {
            // Fallamos el test si se lanza ChoferRepetidoException, se supone inicialmente Map vacio
            fail("No se esperaba una excepción de ChoferRepetidoException: " + e.getMessage());
        } catch (Exception e) {
            fail("Excepción inesperada: " + e.getMessage());
        }
    }
 
    // Clase cubierta: 1, 4
    @Test
    public void testAgregarChofer_ChoferRepetido() {
        try {
            ChoferPermanente choferP = new ChoferPermanente("12345678", "Gonzalo", 1999, 2);
            ChoferPermanente choferP2 = new ChoferPermanente("12345678", "Gonzalo", 1999, 2);

            // Agregamos el chofer por primera vez, lo cual debería funcionar sin problemas
            escenarioVacio.empresa.agregarChofer(choferP);
            
            // Intentamos agregar el mismo chofer nuevamente (con el mismo DNI), lo que debería lanzar la excepción
            escenarioVacio.empresa.agregarChofer(choferP2);
            
            // Si llegamos a esta línea, es un error, ya que se esperaba la excepción
            fail("Se esperaba una ChoferRepetidoException al agregar un chofer con un DNI repetido");
            
        } catch (ChoferRepetidoException e) {
            // Éxito: Se lanzó ChoferRepetidoException como se esperaba
        	assertEquals("El mensaje no corresponde con la excepcion adecuada.", Mensajes.CHOFER_YA_REGISTRADO.getValor(), e.getMessage());
        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName() + " - " + e.getMessage());
        }
    }
 
//----- Metodo void agregarCliente(String usuario, String pass, String nombreReal)				    
    
    // Clase cubierta: 1, 3, 5, 7
    @Test
    public void testAgregarCliente_ClaseCorrecta() {
        try {
            escenarioVacio.empresa.agregarCliente("user1", "pass123", "Gonzalo");
            
            // Verificamos si el cliente fue añadido correctamente en la estructura de datos
            HashMap<String, Cliente> clientesRegistrados = escenarioVacio.empresa.getClientes();
            
            // Comprobamos que el cliente fue agregado usando su nombre de usuario como clave
            assertTrue("El cliente no fue registrado correctamente en la empresa", 
                       clientesRegistrados.containsKey("user1"));
            
            // Verificamos que el cliente registrado es efectivamente el mismo que se agregó
            Cliente cliente = clientesRegistrados.get("user1");
            assertEquals("El nombre del cliente registrado no coincide", "Gonzalo", cliente.getNombreReal());
            assertEquals("La contraseña del cliente registrado no coincide", "pass123", cliente.getPass());
            
        } catch (UsuarioYaExisteException e) {
            // Fallamos el test si se lanza UsuarioYaExisteException, porque se supone que es el primer registro
            fail("No se esperaba UsuarioYaExisteException, el HashMap debe estar vacío: " + e.getMessage());
        } catch (Exception e) {
            fail("Excepción inesperada: " + e.getMessage());
        }
    }
    
    // Clases cubiertas: 1, 3, 5, 8
    @Test
    public void testAgregarCliente_UsuarioRepetido() {
        try {
            // Primero, agregamos un usuario válido
            escenarioVacio.empresa.agregarCliente("user1", "pass123", "Gonzalo");

            // Intentamos agregar el mismo usuario nuevamente (con el mismo nombre de usuario)
            escenarioVacio.empresa.agregarCliente("user1", "pass123", "Gonzalo");
            fail("Se esperaba una UsuarioYaExisteException al agregar un usuario repetido");

        } catch (UsuarioYaExisteException e) {
            // Éxito: Se lanzó UsuarioYaExisteException como se esperaba
        	assertEquals("El mensaje no corresponde con la excepcion adecuada.", Mensajes.USUARIO_REPETIDO.getValor(), e.getMessage());

        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName() + " - " + e.getMessage());
        }
    }
    

    @Test
    public void testAgregarPedido_ClienteNoRegistrado() {
        try {
            Cliente clienteNoRegistrado = new Cliente("userNotRegistered", "pass123", "Juan Perez");

            // Creamos un pedido con ese cliente
            Pedido pedido = new Pedido(clienteNoRegistrado, 2, true, false, 10, "ZONA_STANDARD");

            // Intentamos agregar el pedido
            escenarioVacio.empresa.agregarPedido(pedido);

            // Si llegamos aquí, el test debe fallar, ya que se esperaba una excepción
            fail("Se esperaba una ClienteNoExisteException al agregar un pedido con un cliente no registrado");

        } catch (ClienteNoExisteException e) {
            // Éxito: Se lanzó ClienteNoExisteException como se esperaba
        	assertEquals("El mensaje no corresponde con la excepcion adecuada.", Mensajes.CLIENTE_NO_EXISTE.getValor(), e.getMessage());

        } catch (SinVehiculoParaPedidoException e) {
            // Fallamos si se lanza SinVehiculoParaPedidoException
            fail("No se esperaba una SinVehiculoParaPedidoException: " + e.getMessage());

        } catch (ClienteConViajePendienteException e) {
            // Fallamos si se lanza ClienteConViajePendienteException
            fail("No se esperaba una ClienteConViajePendienteException: " + e.getMessage());

        } catch (ClienteConPedidoPendienteException e) {
            // Fallamos si se lanza ClienteConPedidoPendienteException
            fail("No se esperaba una ClienteConPedidoPendienteException: " + e.getMessage());

        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName() + " - " + e.getMessage());
        }
    }

    // Clases cubiertas: 1, 3, 6 
    // testAgregarPedido_ClienteConViajePendiente Escenario5

    // Clases cubiertas: 1, 3, 5, 8 
    // testAgregarPedido_ClienteConPedidoPendiente() Escenario4

    // Clases cubiertas: 1, 3, 5, 7, 10 
    // testAgregarPedido_SinVehiculoDisponible() Escenario3

//----- Metodo void agregarVehiculo(Vehiculo vehiculo)				
    
    // Clases cubiertas: 1, 3
    @Test
    public void testAgregarVehiculo_ClaseCorrecta() {
        try {
            Vehiculo auto = new Auto("ABC123", 4, true);

            // Agregamos el vehículo a la empresa
            escenarioVacio.empresa.agregarVehiculo(auto);

            // Verificamos si el vehículo fue añadido correctamente al HashMap
            HashMap<String, Vehiculo> vehiculosRegistrados = escenarioVacio.empresa.getVehiculos();
            
            // Verificamos que el vehículo se ha agregado correctamente
            assertTrue("El vehículo no fue registrado correctamente en la empresa", 
                       vehiculosRegistrados.containsKey("ABC123"));
            
            // Verificamos que el vehículo registrado es el mismo que agregamos
            assertEquals("El vehículo registrado no coincide con el vehículo ingresado", 
                         auto, vehiculosRegistrados.get("ABC123"));

        } catch (VehiculoRepetidoException e) {
            fail("No se esperaba una VehiculoRepetidoException: " + e.getMessage());

        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName() + " - " + e.getMessage());
        }
    }

    // Clases cubiertas: 1, 4 (Patente ya registrada)
    @Test
    public void testAgregarVehiculo_VehiculoRepetido() {
        try {
            // Creamos y agregamos un vehículo válido por primera vez
            Vehiculo vehiculo = new Auto("ABC123", 4, true);
            escenarioVacio.empresa.agregarVehiculo(vehiculo);

            // Intentamos agregar el mismo vehículo nuevamente (con la misma patente)
            escenarioVacio.empresa.agregarVehiculo(vehiculo);

            // Si llegamos aquí, el test debe fallar, ya que se esperaba una excepción
            fail("Se esperaba una VehiculoRepetidoException al agregar un vehículo con patente repetida");

        } catch (VehiculoRepetidoException e) {
            // Éxito: Se lanzó VehiculoRepetidoException como se esperaba
        	assertEquals("El mensaje no corresponde con la excepcion adecuada.", Mensajes.VEHICULO_YA_REGISTRADO.getValor(), e.getMessage());

        } catch (Exception e) {
            // Capturamos cualquier otra excepción inesperada
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName() + " - " + e.getMessage());
        }
    }


    @Test
    public void testVehiculosOrdenadosPorPedido_VehiculosConMismoPuntaje() {
        try {
            // Creamos y registramos un cliente en la empresa
            String usuario = "userMismoPuntaje";
            escenarioVacio.empresa.agregarCliente(usuario, "pass123", "Cliente Con Mismo Puntaje");

            // Obtenemos el cliente registrado
            Cliente cliente = escenarioVacio.empresa.getClientes().get(usuario);
            assertNotNull("El cliente no fue registrado correctamente", cliente);

            // Creamos y agregamos vehículos con la misma capacidad y características que les darán el mismo puntaje
            Vehiculo auto1 = new Auto("ABC123", 3, true);  // Capacidad para 3 pasajeros, admite mascota
            Vehiculo auto2 = new Auto("DEF456", 3, true);  // Capacidad para 3 pasajeros, admite mascota
            Vehiculo auto3 = new Auto("GHI789", 3, true);  // Capacidad para 3 pasajeros, admite mascota

            escenarioVacio.empresa.agregarVehiculo(auto1);
            escenarioVacio.empresa.agregarVehiculo(auto2);
            escenarioVacio.empresa.agregarVehiculo(auto3);

            // Creamos un pedido que generará el mismo puntaje para todos los vehículos
            Pedido pedido = new Pedido(cliente, 3, true, false, 10, "ZONA_STANDARD"); // No se usa baúl, 3 pasajeros

            // Llamamos al método vehiculosOrdenadosPorPedido
            ArrayList<Vehiculo> vehiculosOrdenados = escenarioVacio.empresa.vehiculosOrdenadosPorPedido(pedido);
            
            // Verificamos que el ArrayList contiene exactamente los 3 vehículos
            assertNotNull("El ArrayList de vehículos no debería ser null", vehiculosOrdenados);
            assertEquals("El número de vehículos habilitados es incorrecto", 3, vehiculosOrdenados.size());

            // Verificamos que los 3 vehículos estén en el ArrayList, sin importar el orden
            assertTrue("El ArrayList debería contener el auto1", vehiculosOrdenados.contains(auto1));
            assertTrue("El ArrayList debería contener el auto2", vehiculosOrdenados.contains(auto2));
            assertTrue("El ArrayList debería contener el auto3", vehiculosOrdenados.contains(auto3));

        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName() + " - " + e.getMessage());
        }
    }

//----- Metodo boolean validarPedido(Pedido pedido)				
    
    // Clases cubiertas: 1, 3 
    // testValidarPedido_ClaseCorrecta() Escenario3

    // Clases cubiertas: 1, 4 
    @Test
    public void testValidarPedido_SinVehiculosHabilitados() {
        try {
            // Registramos un cliente
            String usuario = "userNoVehiculos";
            escenarioVacio.empresa.agregarCliente(usuario, "pass123", "Cliente Sin Vehiculos");

            // Obtenemos el cliente registrado
            Cliente cliente = escenarioVacio.empresa.getClientes().get(usuario);
            assertNotNull("El cliente no fue registrado correctamente", cliente);

            // Creamos y agregamos vehículos que no cumplen con los requisitos del pedido
            Vehiculo auto = new Auto("ABC123", 4, false);  	// Vehículo no acepta mascotas
            Vehiculo moto = new Moto("GHI789");  			// Moto solo acepta 1 pasajero y sin baúl ni mascota

            escenarioVacio.empresa.agregarVehiculo(auto);
            escenarioVacio.empresa.agregarVehiculo(moto);

            // Creamos un pedido que requiere mascotas y baúl (ningún vehículo puede satisfacerlo)
            Pedido pedido = new Pedido(cliente, 2, true, true, 10, "ZONA_STANDARD");

            // Validamos el pedido
            boolean resultado = escenarioVacio.empresa.validarPedido(pedido);

            // Verificamos que el resultado sea false, ya que ningún vehículo puede satisfacer el pedido
            assertFalse("El pedido debería ser inválido ya que no hay vehículos habilitados", resultado);

        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName() + " - " + e.getMessage());
        }
    }
  
    @Test
    public void testLogin_UsuarioNoRegistrado() {
        try {
            escenarioVacio.empresa.login("nonexistent", "password");
            fail("Se esperaba una UsuarioNoExisteException al intentar loguearse con un usuario no registrado");

        } catch (UsuarioNoExisteException e) {
            // Éxito: Se lanzó UsuarioNoExisteException como se esperaba
        	assertEquals("El mensaje no corresponde con la excepcion adecuada.", Mensajes.USUARIO_DESCONOCIDO.getValor(), e.getMessage());

        } catch (PasswordErroneaException e) {
            fail("Se lanzó PasswordErroneaException en lugar de UsuarioNoExisteException: " + e.getMessage());

        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName() + " - " + e.getMessage());
        }
    }

    // Clases cubiertas: 1, 3, 5, 8 
    // testLogin_PasswordIncorrecta() Escenario2

//----- Metodo double getTotalSalarios()				
    
    // Clases cubiertas: 1, 3 
    @Test
    public void testGetTotalSalarios_MultiplesChoferes() {
        try {
            // Registramos un chofer permanente con un salario basado en su antigüedad y cantidad de hijos
        	// Antigüedad de 14 años, 2 hijos
            ChoferPermanente choferPermanente = new ChoferPermanente("12345678", "Juan", 2010, 2); 
            escenarioVacio.empresa.agregarChofer(choferPermanente);
            
            // El sueldo básico del chofer permanente será calculado con un 5% por cada año de antigüedad, 
            // y 7% por cada hijo.
            double sueldoBrutoPermanente = choferPermanente.getSueldoBruto();
            //System.out.println("Sueldo bruto del chofer permanente: " + sueldoBrutoPermanente);

            // Registramos un chofer temporario con su sueldo igual al sueldo básico
            ChoferTemporario choferTemporario = new ChoferTemporario("87654321", "Pedro");
            escenarioVacio.empresa.agregarChofer(choferTemporario);
            
            double sueldoBrutoTemporario = choferTemporario.getSueldoBruto();
            //System.out.println("Sueldo bruto del chofer temporario: " + sueldoBrutoTemporario);

            // Verificamos que el salario total es la suma de ambos salarios brutos
            double totalSalarios = escenarioVacio.empresa.getTotalSalarios();
            double salarioEsperado = sueldoBrutoPermanente + sueldoBrutoTemporario;

            assertEquals("El total de salarios es incorrecto", salarioEsperado, totalSalarios, 0.01);

        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName() + " - " + e.getMessage());
        }
    }


    // Clases cubiertas: 1, 3 Parece que se calculan mal los salarios o este metodo hace algo raro en la suma
    @Test
    public void testGetTotalSalarios_UnChofer() {
        try {
            // Definimos el sueldo básico y otros parámetros del chofer
            double sueldoBasico = 2000.0;
            int anioIngreso = 2010;  // Antigüedad de 14 años (asumiendo que estamos en 2024)
            int cantidadHijos = 1;  // Un hijo

            // Registramos un chofer permanente con estos datos
            ChoferPermanente choferPermanente = new ChoferPermanente("12345678", "Juan", anioIngreso, cantidadHijos);
            Chofer.setSueldoBasico(sueldoBasico);  // Establecemos sueldo básico como 2000
            escenarioVacio.empresa.agregarChofer(choferPermanente);

            // Calculamos el salario esperado:
            int antiguedad = LocalDate.now().getYear() - anioIngreso;
            double incrementoAntiguedad = Math.min(antiguedad * 0.05, 1.0) * sueldoBasico;  // Máximo de 100% del básico
            double incrementoPorHijos = cantidadHijos * 0.07 * sueldoBasico;
            double salarioEsperado = sueldoBasico + incrementoAntiguedad + incrementoPorHijos;

            // Verificamos que el salario total es correcto
            double totalSalarios = escenarioVacio.empresa.getTotalSalarios();

            assertEquals("El total de salarios es incorrecto", salarioEsperado, totalSalarios, 0.01);

        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName() + " - " + e.getMessage());
        }
    }

    // Clases cubiertas: 2 
    @Test
    public void testGetTotalSalarios_SinChoferes() {
        try {
            // Verificamos que no haya choferes registrados
            assertTrue("No debería haber choferes registrados", escenarioVacio.empresa.getChoferes().isEmpty());

            // Obtenemos el total de salarios
            double totalSalarios = escenarioVacio.empresa.getTotalSalarios();

            // Verificamos que el total de salarios sea 0.0
            assertEquals("El total de salarios debería ser 0.0 cuando no hay choferes registrados", 0.0, totalSalarios, 0.0);

        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName() + " - " + e.getMessage());
        }
    }

}
