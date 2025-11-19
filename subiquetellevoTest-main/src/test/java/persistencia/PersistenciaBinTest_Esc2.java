package persistencia;

import static org.junit.Assert.*;

import java.io.File;
import java.io.Serializable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import persistencia.EmpresaDTO;
import persistencia.IPersistencia;
import persistencia.PersistenciaBIN;
import java.io.Serializable;
/**
 * Escenario donde se persiste todas las colleciones de empresa con null
 */
public class PersistenciaBinTest_Esc2 {

	IPersistencia<Serializable> persistencia = new PersistenciaBIN();
	String nombreArchivo = "datos.xml";
	File archivo = new File(nombreArchivo);
	EmpresaDTO empresa, serializado;
	
	@Before
	public void setUp() throws Exception {
		if(!archivo.exists())
			archivo.createNewFile();
		empresa = new EmpresaDTO();
		
		persistencia.abrirOutput(nombreArchivo);
		persistencia.escribir(empresa);
		persistencia.cerrarOutput();
		
		persistencia.abrirInput(nombreArchivo);
		serializado = (EmpresaDTO) persistencia.leer();
		persistencia.cerrarInput();
		
	}

	@After
	public void tearDown() {
		if(archivo.exists())
			archivo.delete();
	}
	
	@Test
	public void testEscrituraLecturaChoferes() {		
		assertTrue("CHOFERES DEBERIA SER VACIO", serializado.getChoferes().isEmpty());
	}

	
	@Test
	public void testEscrituraLecturaChoferesDesocupados() {
		assertTrue("CHOFERES DESOCUPADOS DEBERIA SER VACIO",serializado.getChoferesDesocupados().isEmpty());
	}
	
	@Test
	public void testEscrituraLecturaVehiculos() {
		assertTrue("VEHICULOS DEBERIA ESTAS VACIO.", serializado.getVehiculos().isEmpty());
	}
	
	@Test
	public void testEscrituraLecturaVehiculosDesocupados() {
		assertTrue("EL VEHICULO DISPONIBLE DEBRIA SER VACIO.", serializado.getVehiculosDesocupados().isEmpty());
	}
	
	@Test
	public void testEscrituraLecturaClientes() {
		assertTrue("EL CLIENTE DEBERIA SER VACIO.",serializado.getClientes().isEmpty());
	}

	
	@Test
	public void testEscrituraLecturaViajesTerminados() {
		assertTrue("EL VIAJE TERMINADO DEBERIA SER VACIO.",serializado.getViajesTerminados().isEmpty());
	}
	
	@Test
	public void testEscrituraLecturaUsuarioLogueado() {
		assertNull("EL USUARIO LOGUEADO BEBERIA SER NULL.",serializado.getUsuarioLogeado());
	}

	//HashMap<Cliente,Pedido> pedidos;
	@Test
	public void testEscrituraLecturaPedidos() {
		assertTrue("DEBERIA SER TRUE",serializado.getPedidos().isEmpty());
	}
	
	
	// HashMap<Cliente,Viaje> viajesTerminados
	@Test
	public void testEscrituraLecturaViajesIniciados() {
		assertTrue("DEBERIA SER TRUE",serializado.getViajesIniciados().isEmpty());
	}
}