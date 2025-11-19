package persistencia;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Usuario;
import modeloDatos.Vehiculo;
import modeloDatos.Viaje;
import persistencia.EmpresaDTO;
import persistencia.IPersistencia;
import persistencia.PersistenciaBIN;

public class testPersistencia {

	private PersistenciaBIN persistencia; 
    private EmpresaDTO dto; 
    private String nombreArchivo = "empresa.bin";
    private File archivo; 

    /**
     * Test de Persitencia. La persitencia consistirá en probar la escritura 
     * y lectura del archivo persistido cuando existe el archivo y cuando no. 
     * Pueden persistir todo o elegir alguna parte de los datos, esto lo debe justificar.
     */
    
    @Before
    public void setUp() {
        this.persistencia = new PersistenciaBIN(); 
        this.dto = new EmpresaDTO(); 
        this.archivo = new File(this.nombreArchivo); 
        
        if (this.archivo.exists()) {
	        this.archivo.delete();
	    }
    }
    
    /**
     * Prueba que se puede crear un archivo al escribir un DTO vacío.
     */
    @Test
    public void testCrearArchivo() {
        try {
            this.persistencia.abrirOutput(this.nombreArchivo);
            this.persistencia.escribir(this.dto);
            this.persistencia.cerrarOutput();
            
            assertTrue("Debería existir el archivo binario", this.archivo.exists());
        
        } catch (Exception e) {
            fail("No deberia lanzar excepcion: " + e.getMessage());
        }
    }
  
   
	//funcion auxiliar 
    private EmpresaDTO escribirYLeer(EmpresaDTO dto) throws IOException, ClassNotFoundException {
        persistencia.abrirOutput(nombreArchivo);
        persistencia.escribir(dto);
        persistencia.cerrarOutput();
        
        persistencia.abrirInput(nombreArchivo);
        EmpresaDTO resultado = (EmpresaDTO) persistencia.leer();
        persistencia.cerrarInput();
        
        return resultado;
    }

    /**
     * Prueba la escritura y lectura de un DTO vacío.
     * Verifica que la serialización y deserialización no corrompen un objeto en su estado inicial.
     */
    @Test
    public void testDtoVacioArchivo() {
        try {
            // 1. Escribir y leer
        	EmpresaDTO dtoRecuperado = escribirYLeer(this.dto);

            
            // 2. Verificación de atributos
            assertNotNull("El DTO recuperado no puede ser nulo.", dtoRecuperado);
            
            // Verificamos que las colecciones internas no sean nulas y estén vacías
            assertNotNull("La lista de clientes no debería ser nula.", dtoRecuperado.getClientes());
            assertTrue("La lista de clientes debería estar vacía.", dtoRecuperado.getClientes().isEmpty());

            assertNotNull("La lista de choferes no debería ser nula.", dtoRecuperado.getChoferes());
            assertTrue("La lista de choferes debería estar vacía.", dtoRecuperado.getChoferes().isEmpty());
            
            assertNotNull("La lista de pedidos no debería ser nula.", dtoRecuperado.getPedidos());
            assertTrue("La lista de pedidos debería estar vacía.", dtoRecuperado.getPedidos().isEmpty());
            
            assertNotNull("La lista de vehiculos no debería ser nula.", dtoRecuperado.getVehiculos());
            assertTrue("La lista de vehiculos debería estar vacía.", dtoRecuperado.getVehiculos().isEmpty()); 
            
            assertNotNull("La lista de choferes desocupados no debería ser nula.", dtoRecuperado.getChoferesDesocupados());
            assertTrue("La lista de choferes desocupados debería estar vacía.", dtoRecuperado.getChoferesDesocupados().isEmpty());
            
            assertNotNull("La lista de viajes iniciados desocupados no debería ser nula.", dtoRecuperado.getViajesIniciados());
            assertTrue("La lista de de viajes iniciados debería estar vacía.", dtoRecuperado.getViajesIniciados().isEmpty());
            
            assertNotNull("La lista de viajes terminados desocupados no debería ser nula.", dtoRecuperado.getViajesTerminados());
            assertTrue("La lista de de viajes terminados debería estar vacía.", dtoRecuperado.getViajesTerminados().isEmpty());
            
            // Verificamos que los atributos que son objetos simples estén nulos
            assertNull("El usuario logueado debería ser nulo en un DTO vacío.", dtoRecuperado.getUsuarioLogeado()); 
            
        } catch (IOException | ClassNotFoundException e) {
            fail("No debería lanzar excepcion: " + e.getMessage());
        }
    }
	 /**
     * Prueba que el sistema maneja correctamente el caso en que se intenta
     * leer un archivo que no existe. Se espera que se lance una FileNotFoundException.
     * La anotación `@Test(expected = ...)` le dice a JUnit que esta prueba es exitosa
     * SI Y SOLO SI se lanza la excepción especificada.
     */
	@Test(expected = FileNotFoundException.class)
    public void testDespersitirSinArchivo() throws IOException {
            // El método que debe fallar es abrirInput
            this.persistencia.abrirInput(this.nombreArchivo);
            fail("Debería haber lanzado una excepcion de tipo FileNotFoundException"); 
    }
    
 

    /**
     * Prueba el ciclo completo de escritura y lectura con un DTO que contiene
     * un conjunto complejo de datos. Esta es la prueba de integración más importante.
     */
    @Test
    public void testDtoConDatosCompleto() {
        try {
            // 1. Llenar el DTO original con un estado de negocio realista
            this.llenaEmpresaDTO(this.dto);

            EmpresaDTO dtoRecuperado = escribirYLeer(this.dto);
            
            assertNotNull("El DTO recuperado no debería ser nulo", dtoRecuperado);

            assertNotNull("El usuario logueado no debería ser nulo", dtoRecuperado.getUsuarioLogeado());
            assertEquals("El nombre de usuario logueado no coincide",  "Martin90", dtoRecuperado.getUsuarioLogeado().getNombreUsuario());

            // 4.2. Verificar Tamaños de todas las colecciones
            assertEquals("La cantidad de clientes no coincide", 3, dtoRecuperado.getClientes().size());
            assertEquals("La cantidad de choferes no coincide", 3, dtoRecuperado.getChoferes().size());
            assertEquals("La cantidad de vehículos no coincide", 3, dtoRecuperado.getVehiculos().size());
            assertEquals("La cantidad de pedidos no coincide", 3, dtoRecuperado.getPedidos().size());
            assertEquals("La cantidad de viajes iniciados no coincide", 2, dtoRecuperado.getViajesIniciados().size());
            assertEquals("La cantidad de viajes terminados no coincide", 1, dtoRecuperado.getViajesTerminados().size());
            assertEquals("La cantidad de choferes desocupados no coincide", 3, dtoRecuperado.getChoferesDesocupados().size());
            assertEquals("La cantidad de vehículos desocupados no coincide", 3, dtoRecuperado.getVehiculosDesocupados().size());

            Chofer choferRecuperado = dtoRecuperado.getChoferes().get("87654321B"); 
            assertNotNull("El chofer 'María Gómez' no fue encontrado", choferRecuperado);
            assertEquals("El nombre del chofer no coincide", "María Gómez", choferRecuperado.getNombre());

            Vehiculo vehiculoRecuperado = dtoRecuperado.getVehiculos().get("BBB456"); 
            assertNotNull("El vehículo 'BBB456' no fue encontrado", vehiculoRecuperado);

            Cliente clienteClave = null;
            for (Cliente c : dtoRecuperado.getPedidos().keySet()) {
                if (c.getNombreUsuario().equals("Laura33")) {
                    clienteClave = c;
                    break;
                }
            }
            
            assertNotNull("La clave Cliente 'Laura33' no se encontró en el mapa de pedidos", clienteClave);
            Pedido pedidoRecuperado = dtoRecuperado.getPedidos().get(clienteClave);

            Viaje viajeTerminadoRecuperado = dtoRecuperado.getViajesTerminados().get(0); 
            assertNotNull("El viaje terminado no debería ser nulo", viajeTerminadoRecuperado);
            assertEquals("El chofer del viaje terminado no coincide", "Juan López", viajeTerminadoRecuperado.getChofer().getNombre());
            
        } catch (Exception e) {
            // Si algo falla, la prueba se detiene y muestra este mensaje.
            fail("No debería lanzar excepción durante la lectoescritura con datos complejos: " + e.getMessage());
        }
    }

    /**
     * Tu método para poblar el DTO.
     */
    private void llenaEmpresaDTO(EmpresaDTO dto) {
        try {
            Chofer chofer1 = new ChoferPermanente("12345678A", "Carlos Perez", 2010, 0);
            Chofer chofer2 = new ChoferPermanente("87654321B", "María Gómez", 2015, 0);
            Chofer chofer3 = new ChoferPermanente("45678912C", "Juan López", 2018, 0);

            HashMap<String, Chofer> choferes = new HashMap<>();
            choferes.put(chofer1.getDni(), chofer1);
            choferes.put(chofer2.getDni(), chofer2);
            choferes.put(chofer3.getDni(), chofer3);
            dto.setChoferes(choferes);

            ArrayList<Chofer> choferesDesocupados = new ArrayList<>();
            choferesDesocupados.add(chofer1);
            choferesDesocupados.add(chofer2);
            choferesDesocupados.add(chofer3);
            dto.setChoferesDesocupados(choferesDesocupados);

            Cliente cliente1 = new Cliente("Gonzalo77", "1234", "Gonzalo");
            Cliente cliente2 = new Cliente("Laura33", "abcd", "Laura");
            Cliente cliente3 = new Cliente("Martin90", "pass", "Martín");

            HashMap<String, Cliente> clientes = new HashMap<>();
            clientes.put(cliente1.getNombreUsuario(), cliente1);
            clientes.put(cliente2.getNombreUsuario(), cliente2);
            clientes.put(cliente3.getNombreUsuario(), cliente3);
            dto.setClientes(clientes);

            dto.setUsuarioLogeado(cliente3);

            Vehiculo veh1 = new Auto("AAA123", 4, false);
            Vehiculo veh2 = new Auto("BBB456", 2, true);
            Vehiculo veh3 = new Auto("CCC789", 3, false);

            HashMap<String, Vehiculo> vehiculos = new HashMap<>();
            vehiculos.put(veh1.getPatente(), veh1);
            vehiculos.put(veh2.getPatente(), veh2);
            vehiculos.put(veh3.getPatente(), veh3);
            dto.setVehiculos(vehiculos);

            ArrayList<Vehiculo> vehiculosDesocupados = new ArrayList<>();
            vehiculosDesocupados.add(veh1);
            vehiculosDesocupados.add(veh2);
            vehiculosDesocupados.add(veh3);
            dto.setVehiculosDesocupados(vehiculosDesocupados);

            // --- PEDIDOS ---
            Pedido pedido1 = new Pedido(cliente1, 2, false, true, 5, "ZONA_STANDARD");
            Pedido pedido2 = new Pedido(cliente2, 1, true, false, 12, "ZONA_STANDARD");
            Pedido pedido3 = new Pedido(cliente3, 3, false, true, 8, "ZONA_STANDARD");

            HashMap<Cliente, Pedido> pedidos = new HashMap<>();
            pedidos.put(cliente1, pedido1);
            pedidos.put(cliente2, pedido2);
            pedidos.put(cliente3, pedido3);
            dto.setPedidos(pedidos);

            Viaje viaje1 = new Viaje(pedido1, chofer1, veh1);
            Viaje viaje2 = new Viaje(pedido2, chofer2, veh2);

            HashMap<Cliente, Viaje> viajesIniciados = new HashMap<>();
            viajesIniciados.put(pedido1.getCliente(), viaje1);
            viajesIniciados.put(pedido2.getCliente(), viaje2);
            dto.setViajesIniciados(viajesIniciados);
            
            Viaje viaje3 = new Viaje(pedido3, chofer3, veh3);
            ArrayList<Viaje> viajesTerminados = new ArrayList<>();
            viajesTerminados.add(viaje3);
            dto.setViajesTerminados(viajesTerminados);

        } catch (Exception e) {
            fail("No debería lanzar excepcion al llenar el DTO: " + e.getMessage());
        }
    }

	
	@After
	public void tearDown() {
	    if (this.archivo.exists()) {
	        this.archivo.delete();
	    }
	}


}
