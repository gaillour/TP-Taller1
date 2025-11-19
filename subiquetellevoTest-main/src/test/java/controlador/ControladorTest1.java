package controlador;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;


import org.junit.Before;
import org.junit.Test;

import modeloNegocio.Empresa;
import util.Constantes;
import util.Mensajes;
import vista.IOptionPane;
import vista.IVista;
import vista.Ventana;


/**
 * Controlador con escenario empresa vacia
 * lista choferes vacio
 * lista choferes disponible vacio
 * lista vehiculos vacio
 * lista vehiculos disponibles vacio
 * lista clientes vacio
 * lista pedidos vacio
 * lista viajes iniciados vacio
 * lista viajes terminados vacio
 */
public class ControladorTest1 {
	
	Empresa empresa;
	Controlador control;
	IVista vista = mock(Ventana.class);
	IOptionPane panel ;

	
	@Before
	public void setUp() {
		control = new Controlador();
		panel = new OptionPane();
		empresa = Empresa.getInstance();
		control.setVista(vista);
		when(control.getVista().getOptionPane()).thenReturn(panel);
	}
	
	@Test
	public void testConstructor() {
		assertNotNull(control.getVista());
		assertNotNull(control.getPersistencia());
		assertEquals(control.getFileName(),"empresa.bin");
	}
	
	/**
	 * Test Usuario inexistente
	 */
	@Test
	public void testLogin() {
		when(control.getVista().getUsserName()).thenReturn("usuario");
		when(control.getVista().getPassword()).thenReturn("password");
		control.login();
		assertEquals(((OptionPane)panel).getMsj(),Mensajes.USUARIO_DESCONOCIDO.getValor());
	}
	
	
	/**
	 * Test registrar. los campos de contrasena no coinciden
	 */
	@Test
	public void testRegistrar() {
		when(vista.getRegNombreReal()).thenReturn("Pepe Lopez");
		when(vista.getRegUsserName()).thenReturn("usuario");
		when(vista.getRegPassword()).thenReturn("password");
		when(vista.getRegConfirmPassword()).thenReturn("password1");
		
		control.registrar();
		assertEquals(((OptionPane)panel).getMsj(),Mensajes.PASS_NO_COINCIDE.getValor());
	}
	
	/**
	 * Agrega un nuevo usuario. Se agrega un usuario no existente
	 * a lista de empresa vacia.
	 */
	@Test
	public void testRegistrar2() {
		when(vista.getRegNombreReal()).thenReturn("Pepe Lopez");
		when(vista.getRegUsserName()).thenReturn("usuario");
		when(vista.getRegPassword()).thenReturn("password");
		when(vista.getRegConfirmPassword()).thenReturn("password");
		
		control.registrar();
		assertTrue(empresa.getClientes().containsKey(control.getVista().getRegUsserName()));
		
	}
	
	
	/**
	 * Agrega nuevo chofer Temporario no existente a la lista de empresa vacia.
	 */
	@Test
	public void testNuevoChofer() {
		when(vista.getTipoChofer()).thenReturn(Constantes.TEMPORARIO);
		when(vista.getNombreChofer()).thenReturn("Raul Perez");
		when(vista.getDNIChofer()).thenReturn("1234");
		
		control.nuevoChofer();
		assertTrue(empresa.getChoferes().containsKey(control.getVista().getDNIChofer()));
	}

	/**
	 * Agrega nuevo chofer Permanente no existente a la lista de empresa vacia.
	 */
	@Test
	public void testNuevoChofer2() {
		when(vista.getTipoChofer()).thenReturn(Constantes.PERMANENTE);
		when(vista.getNombreChofer()).thenReturn("Raul Perez");
		when(vista.getDNIChofer()).thenReturn("12345");
		when(vista.getAnioChofer()).thenReturn(2000);
		when(vista.getHijosChofer()).thenReturn(2);
		
		control.nuevoChofer();
		assertTrue(empresa.getChoferes().containsKey(control.getVista().getDNIChofer()));
	}
	
	/**
	 * Agrega un nuevo vehiculo no existente a la lista de empresa vacia.
	 */
	@Test
	public void testNuevoVehiculo() {
		when(vista.getTipoVehiculo()).thenReturn(Constantes.AUTO);
		when(vista.getPatente()).thenReturn("123 asd");
		when(vista.getPlazas()).thenReturn(3);
		when(vista.isVehiculoAptoMascota()).thenReturn(true);
		
		assertNull(((OptionPane)(control.getVista().getOptionPane())).getMsj());
	}
		
	/**
	 * Para verificar que se ejecuto el metodo escribir() corroboro
	 * que se genere un archivo con el nombre almacenado en el atributo
	 * fileName.
	 */
	@Test
	public void testEscribir() {
		File arch = new File(control.getFileName());
		if(arch.exists())
			arch.delete();
		
		control.escribir();
		
		assertNotNull(arch.exists());
	}
	
}
