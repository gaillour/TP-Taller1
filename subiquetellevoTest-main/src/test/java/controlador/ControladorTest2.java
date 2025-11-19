package controlador;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Combi;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;
import util.Constantes;
import util.Mensajes;
import vista.IOptionPane;
import vista.IVista;
import vista.Ventana;

/**
 * Escenario con empresa con elementos en las listas
 * lista chofer con dos choferes:
 * 				1.Permanente
 * 				2.Temporario (viaje iniciado) 
 * lista chofer disponible con el chofer Permanente, el temporario tiene viaje iniciado
 * lista vehiculo con :
 * 				1. auto 4 lugares admite mascota 
 * 				2.combi 6 lugares no permite mascota  
 * 				3.moto (moto viaje iniciado)
 * lista vehiculo disponible inicialmente con los 2 vehiculos
 * lista clientes con 3 clientes
 * 				1-con pedido pendiente 
 * 				2-sin pedido ni viaje
 * 				3-con viaje iniciado
 * lista pedidos con 1 pedido hecho por 1 de los clientes
 * lista viajes iniciados con el viaje de uno de los clientes
 * lista viajes terminado vacia
 */
public class ControladorTest2 {

	Empresa empresa;
	Controlador control;
	IVista vista = mock(Ventana.class);
	IOptionPane panel;
	
	Cliente c1 ;
	Cliente c2 ;
	Cliente c3 ;
	Vehiculo a1 = new Auto("asd 123",4,true);
	Chofer ch1 = new ChoferPermanente("1234","Juan Pepe",2000, 1);
	Chofer ch2 = new ChoferTemporario("8549","Omar Quintana");
	Vehiculo a2 = new Combi("qwe 123",6,false);
	Vehiculo a3 = new Moto("zxc 789");
	Pedido p;
	Pedido p2;
	
	@Before
	public void setUp() throws Exception{
		control = new Controlador();
		panel = new OptionPane();
		empresa = Empresa.getInstance();
		control.setVista(vista);
		when(control.getVista().getOptionPane()).thenReturn(panel);
		
		empresa.agregarChofer(ch1);
		empresa.agregarChofer(ch2);
		
		empresa.agregarVehiculo(a1);
		empresa.agregarVehiculo(a2);
		empresa.agregarVehiculo(a3);
		
		empresa.agregarCliente("usuario1", "password","Raul Perez");
		empresa.agregarCliente("usuario2", "password","Maria Fernandez");
		empresa.agregarCliente("usuario3", "password", "Eustacio Gomez");
		
		c1 = empresa.getClientes().get("usuario1");
		c2 = empresa.getClientes().get("usuario2");
		c3 = empresa.getClientes().get("usuario3");
		empresa.setUsuarioLogeado(c1);
		
		p = new Pedido(c1,3,true,true,10,Constantes.ZONA_PELIGROSA);
		empresa.agregarPedido(p);
		
		//Viaje del cliente 3 con el chofer 2 y vehiculo 3(moto)
		p2 = new Pedido(c3,1,false,false,10,Constantes.ZONA_STANDARD);
		empresa.agregarPedido(p2);
		empresa.crearViaje(p2, ch2, a3);
	}
	
	@After
	public void tearDown() {
		empresa.getChoferes().clear();
		empresa.getChoferesDesocupados().clear();
		empresa.getClientes().clear();
		empresa.getVehiculos().clear();
		empresa.getVehiculosDesocupados().clear();
		empresa.setUsuarioLogeado(null);
		empresa.getPedidos().clear();
		empresa.getViajesIniciados().clear();
		empresa.getViajesTerminados().clear();
	}
	
	/**
	 * Test login con contrasena erronea.
	 */
	@Test
	public void testLogin() {
		when(control.getVista().getUsserName()).thenReturn("usuario1");
		when(control.getVista().getPassword()).thenReturn("1234");
		
		control.login();
		
		assertEquals(((OptionPane)panel).getMsj(),Mensajes.PASS_ERRONEO.getValor());
	}
	
	/**
	 * Test login con parametros correctos.
	 */
	@Test
	public void testLogin2() {
		when(control.getVista().getUsserName()).thenReturn("usuario1");
		when(control.getVista().getPassword()).thenReturn("password");
		
		control.login();
		assertNull(((OptionPane)panel).getMsj());
		assertEquals(empresa.getUsuarioLogeado(),c1);
	}
	
	/**
	 * Test logout: se verifica que el usuario logueado pase a ser nulo
	 */
	@Test
	public void testLogout() {
		control.logout();
		assertNull(empresa.getUsuarioLogeado());
	}
	
	/**
	 * Test registrar usuario existente en la lista de empresa.
	 */
	@Test
	public void testRegistrar() {
		when(control.getVista().getRegUsserName()).thenReturn("usuario1");
		when(control.getVista().getRegNombreReal()).thenReturn("Raul Peres");
		when(control.getVista().getRegPassword()).thenReturn("password");
		when(control.getVista().getRegConfirmPassword()).thenReturn("password");
		
		control.registrar();
		assertEquals(Mensajes.USUARIO_REPETIDO.getValor(),((OptionPane)panel).getMsj());
		assertEquals(empresa.getClientes().size(),3);
	}
	
	/**
	 * Test registro de vehiculo con patente repetida en la lista de empresa.
	 */
	@Test
	public void testNuevoVehiculo() {
		when(control.getVista().getTipoVehiculo()).thenReturn(Constantes.AUTO);
		when(control.getVista().getPatente()).thenReturn("asd 123");
		when(control.getVista().getPlazas()).thenReturn(4);
		when(control.getVista().isVehiculoAptoMascota()).thenReturn(true);
		control.nuevoVehiculo();
		assertEquals(Mensajes.VEHICULO_YA_REGISTRADO.getValor(),((OptionPane)panel).getMsj());
	}
	
	/**
	 * Test metodo registro de chofer existente en la lista de empresa.
	 * se espera la excepcion ChoferYaRegistradoException
	 */
	@Test
	public void testNuevoChofer() {
		when(vista.getTipoChofer()).thenReturn(Constantes.PERMANENTE);
		when(vista.getNombreChofer()).thenReturn("Juan Pepe");
		when(vista.getDNIChofer()).thenReturn("1234");
		when(vista.getAnioChofer()).thenReturn(2000);
		when(vista.getHijosChofer()).thenReturn(2);	
		
		control.nuevoChofer();
		assertEquals(empresa.getChoferes().size(),2);
		assertEquals(Mensajes.CHOFER_YA_REGISTRADO,((OptionPane)panel).getMsj());
	}
	
	/**
	 * Test metodo nuevoPedido con cliente valido logeado en empresa.
	 * No se espera que lance excepcion 
	 */
	@Test
	public void testNuevoPedido() {
		empresa.setUsuarioLogeado(c2);
		when(control.getVista().getCantidadPax()).thenReturn(3);
		when(control.getVista().isPedidoConMascota()).thenReturn(false);
		when(control.getVista().isPedidoConBaul()).thenReturn(true);
		when(control.getVista().getCantKm()).thenReturn(10);
		when(control.getVista().getTipoZona()).thenReturn(Constantes.ZONA_PELIGROSA);
		
		control.nuevoPedido();
		assertNull(((OptionPane)panel).getMsj());
		assertEquals(2,empresa.getPedidos().size());
	}
	
	/**
	 *  Test metodo nuevoPedido donde no hay vehiculo que lo satisfaga
	 *  El pedido require 8 pasajeros y no hay vehiculo para tal
	 *  se espera excepcion SinVehiculoParaPedidoException
	 */
	@Test
	public void testNuevoPedido2() {
		empresa.setUsuarioLogeado(c2);
		when(control.getVista().getCantidadPax()).thenReturn(8);
		when(control.getVista().isPedidoConMascota()).thenReturn(false);
		when(control.getVista().isPedidoConBaul()).thenReturn(false);
		when(control.getVista().getCantKm()).thenReturn(10);
		when(control.getVista().getTipoZona()).thenReturn(Constantes.ZONA_PELIGROSA);
		
		control.nuevoPedido();
		assertEquals(Mensajes.SIN_VEHICULO_PARA_PEDIDO.getValor(),((OptionPane)panel).getMsj());
	}
	
	@Test
	public void testNuevoPedido3() {
		empresa.setUsuarioLogeado(c3);
		when(control.getVista().getCantidadPax()).thenReturn(3);
		when(control.getVista().isPedidoConMascota()).thenReturn(false);
		when(control.getVista().isPedidoConBaul()).thenReturn(true);
		when(control.getVista().getCantKm()).thenReturn(10);
		when(control.getVista().getTipoZona()).thenReturn(Constantes.ZONA_PELIGROSA);
		
		control.nuevoPedido();
		assertEquals(Mensajes.CLIENTE_CON_VIAJE_PENDIENTE.getValor(),((OptionPane)panel).getMsj());
		assertEquals(2,empresa.getPedidos().size());
	}
	
	@Test
	public void testNuevoPedido4() {
		empresa.setUsuarioLogeado(c1);
		when(control.getVista().getCantidadPax()).thenReturn(3);
		when(control.getVista().isPedidoConMascota()).thenReturn(false);
		when(control.getVista().isPedidoConBaul()).thenReturn(true);
		when(control.getVista().getCantKm()).thenReturn(10);
		when(control.getVista().getTipoZona()).thenReturn(Constantes.ZONA_PELIGROSA);
		
		control.nuevoPedido();
		assertEquals(Mensajes.CLIENTE_CON_PEDIDO_PENDIENTE.getValor(),((OptionPane)panel).getMsj());
		assertEquals(2,empresa.getPedidos().size());
	}
	
	/**
	 * Test metodo nuevoViaje donde el pedido no existe en la lista pedido de empresa.
	 * se espera excepcion PedidoInexistenteException
	 */
	@Test
	public void testNuevoViaje() {
		when(control.getVista().getPedidoSeleccionado()).thenReturn(new Pedido(c1,3,false,true,10,Constantes.ZONA_SIN_ASFALTAR));
		when(control.getVista().getChoferDisponibleSeleccionado()).thenReturn(ch1);
		when(control.getVista().getVehiculoDisponibleSeleccionado()).thenReturn(a1);
		
		control.nuevoViaje();
		assertEquals(Mensajes.PEDIDO_INEXISTENTE.getValor(),((OptionPane)panel).getMsj());
	}
	
	/**
	 * Test metodo nuevoViaje() donde el chofer seleccionado no corresponde a los choferes de la empresa
	 * se espera excepcion ChoferNoDisponibleException
	 */
	@Test
	public void testNuevoViaje2() {	
		empresa.setUsuarioLogeado(c1);
		when(control.getVista().getPedidoSeleccionado()).thenReturn(p);
		when(control.getVista().getChoferDisponibleSeleccionado()).thenReturn(ch2);
		when(control.getVista().getVehiculoDisponibleSeleccionado()).thenReturn(a1);

		control.nuevoViaje();
		assertEquals(Mensajes.CHOFER_NO_DISPONIBLE.getValor(),((OptionPane)panel).getMsj());
	}
	
	/**
	 * Test metodo nuevoViaje() donde el vehiculo seleccionado no pertenece a los vehiculos de la empresa
	 * se espera excepcion VehiculoNoDisponibleException
	 */
	@Test
	public void testNuevoViaje3() {
		empresa.setUsuarioLogeado(c1);
		when(control.getVista().getPedidoSeleccionado()).thenReturn(p);
		when(control.getVista().getChoferDisponibleSeleccionado()).thenReturn(ch1);
		when(control.getVista().getVehiculoDisponibleSeleccionado()).thenReturn(a3);
		
		control.nuevoViaje();
		assertEquals(Mensajes.VEHICULO_NO_DISPONIBLE.getValor(),((OptionPane)panel).getMsj());
		assertNull(empresa.getViajesIniciados().get(c1));
	}
	
	/**
	 * Test metodo nuevoViaje() donde el vehiculo seleccionado no satisface el pedido seleccionado
	 * se espera VehiculoNoValidoException
	 */
	@Test
	public void testNuevoViaje4() {
		empresa.setUsuarioLogeado(c1);
		when(control.getVista().getPedidoSeleccionado()).thenReturn(p);
		when(control.getVista().getChoferDisponibleSeleccionado()).thenReturn(ch1);
		when(control.getVista().getVehiculoDisponibleSeleccionado()).thenReturn(a2);
		
		control.nuevoViaje();
		assertEquals(Mensajes.VEHICULO_NO_VALIDO.getValor(),((OptionPane)panel).getMsj());
		assertNull(empresa.getViajesIniciados().get(c1));
	}
	
	
	/**
	 * Test motodo nuevoViaje() donde el cliente logueado tiene un viaje pendiente
	 * se espera que el viaje se cree correctamente
	 */
	@Test
	public void testNuevoViaje6() {
		empresa.setUsuarioLogeado(c1);
		when(control.getVista().getPedidoSeleccionado()).thenReturn(p);
		when(control.getVista().getChoferDisponibleSeleccionado()).thenReturn(ch1);
		when(control.getVista().getVehiculoDisponibleSeleccionado()).thenReturn(a1);

		control.nuevoViaje();
		assertNotNull(empresa.getViajesIniciados().get(empresa.getUsuarioLogeado()));
		assertNull(((OptionPane)panel).getMsj());
	}
	
	/**
	 * Test metodo CalificarPagar() donde el usuario a pagar no esta realizando un viaje
	 * se espera una exception de tipo ClienteSinViajePendienteException
	 */
	@Test
	public void testCalificarPagar() {
		when(control.getVista().getCalificacion()).thenReturn(3);
		
		control.calificarPagar();
		assertEquals(Mensajes.CLIENTE_SIN_VIAJE_PENDIENTE.getValor(),((OptionPane)panel).getMsj());
	}
	
	/**
	 * Test metodo CalificarPagar() donde el usuario a pagar tiene el viaje iniciado
	 * No se espera que lance excepciones.
	 */
	@Test
	public void testCalificarPagar2() {
		empresa.setUsuarioLogeado(c3);
		when(control.getVista().getCalificacion()).thenReturn(3);
		
		control.calificarPagar();
		assertNull(((OptionPane)panel).getMsj());
		assertEquals(1,empresa.getViajesTerminados().size());
	}
	
	/**
	 * Test metodo actionPerformed donde el evento invoca al metodo registrar
	 */
	@Test
	public void testActionPerformedRegister() {
		ActionEvent event = new ActionEvent(new JButton(), 0, Constantes.REG_BUTTON_REGISTRAR);
		
		when(control.getVista().getRegUsserName()).thenReturn("elnestor");
		when(control.getVista().getRegNombreReal()).thenReturn("Nestor Bloque");
		when(control.getVista().getRegPassword()).thenReturn("password");
		when(control.getVista().getRegConfirmPassword()).thenReturn("password");
		
		control.actionPerformed(event);
		
		assertNotNull(empresa.getClientes().get("elnestor"));
	}
	
	/**
	 * Test metodo actionPerformed donde el evento invoca al metodo login
	 */
	@Test
	public void testActionPerformedLogin() {
		ActionEvent event = new ActionEvent(new JButton(), 0, Constantes.LOGIN);
		empresa.setUsuarioLogeado(null);
		when(control.getVista().getUsserName()).thenReturn("usuario2");
		when(control.getVista().getPassword()).thenReturn("password");
		
		control.actionPerformed(event);
		assertEquals(empresa.getUsuarioLogeado(),c2);
	}
	
	/**
	 * Test metodo actionPerformed donde el evento invoca al metodo nuevoPedido()
	 * se espera que se agregue el nuevo pedido valido en la lista de pedidos
	 */
	@Test
	public void testActionPerformedNuevoPedido() {
		ActionEvent event = new ActionEvent(new JButton(),0,Constantes.NUEVO_PEDIDO);
		
		empresa.setUsuarioLogeado(c2);
		when(control.getVista().getCantidadPax()).thenReturn(3);
		when(control.getVista().isPedidoConMascota()).thenReturn(false);
		when(control.getVista().isPedidoConBaul()).thenReturn(true);
		when(control.getVista().getCantKm()).thenReturn(10);
		when(control.getVista().getTipoZona()).thenReturn(Constantes.ZONA_PELIGROSA);
		
		control.actionPerformed(event);
		assertNotNull(empresa.getPedidos().get(empresa.getUsuarioLogeado()));
		assertEquals(2,empresa.getPedidos().size());
	}
	
	/**
	 * Test metodo actionPerformed donde el evento invoca al metodo nuevoChofer()
	 * se espera que se agregue un nuevo chofer en la lista de choferes y choferes disponibles
	 */
	@Test
	public void testActionPerformedNuevoChofer() {
		ActionEvent event = new ActionEvent(new JButton(),0,Constantes.NUEVO_CHOFER);
		
		when(vista.getTipoChofer()).thenReturn(Constantes.PERMANENTE);
		when(vista.getNombreChofer()).thenReturn("Eliseo Ramos");
		when(vista.getDNIChofer()).thenReturn("58436");
		when(vista.getAnioChofer()).thenReturn(2000);
		when(vista.getHijosChofer()).thenReturn(2);
		
		control.actionPerformed(event);
		assertEquals(3,empresa.getChoferes().size());
		assertEquals(2,empresa.getChoferesDesocupados().size());
	}
	
	/**
	 * TEst metodo actionPerformed donde el evento invoca al metodo nuevoVehiculo()
	 * se espera que se agregue un nuevo vehiculo en la lista de vehiculos y vehiculos disponibles
	 */
	@Test
	public void testActionPerformedNuevoVehiculo() {
		ActionEvent event = new ActionEvent(new JButton(),0,Constantes.NUEVO_VEHICULO);	
		
		when(control.getVista().getTipoVehiculo()).thenReturn(Constantes.MOTO);
		when(control.getVista().getPatente()).thenReturn("skt 008");
		
		control.actionPerformed(event);
		assertNotNull(empresa.getVehiculos().get("skt 008"));
	}
	
	/**
	 * Test metodo actionPerformed donde el evento invoca al metodo CalificarPagar()
	 * se espera que se termine el viaje iniciado
	 */
	@Test
	public void testActionPerformedCalificarPagar() {
		ActionEvent event = new ActionEvent(new JButton(),0,Constantes.CALIFICAR_PAGAR);	
		
		empresa.setUsuarioLogeado(c3);
		when(control.getVista().getCalificacion()).thenReturn(3);
		
		control.actionPerformed(event);
		assertEquals(1,empresa.getViajesTerminados().size());
	}
	
	/**
	 * Test metodo actionPerformed donde el evento invoca al metodo NuevoViaje()
	 * se espera que se inicie un nuevo viaje sobre un pedido existente
	 */
	@Test
	public void testActionPerformedNuevoViaje() {
		ActionEvent event = new ActionEvent(new JButton(),0,Constantes.NUEVO_VIAJE);	
		
		empresa.setUsuarioLogeado(c1);
		when(control.getVista().getPedidoSeleccionado()).thenReturn(p);
		when(control.getVista().getChoferDisponibleSeleccionado()).thenReturn(ch1);
		when(control.getVista().getVehiculoDisponibleSeleccionado()).thenReturn(a1);

		control.actionPerformed(event);
		assertNotNull(empresa.getViajesIniciados().get(c1));
	}
	
	
}

