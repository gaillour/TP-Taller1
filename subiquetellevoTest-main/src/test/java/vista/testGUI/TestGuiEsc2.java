package vista.testGUI;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Robot;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import junit.framework.Assert;
import modelo.negocio.Escenario2;
import util.Constantes;
import util.Mensajes;
import vista.FalseOptionPanel;
import vista.TestUtils;
import vista.Ventana;

public class TestGuiEsc2 {
	Robot robot;
    Controlador controlador;
    FalseOptionPanel op = new FalseOptionPanel();
    private Escenario2 escenario = new Escenario2(); 

    public TestGuiEsc2()
    {
        try{
            robot = new Robot();
        } catch (AWTException e){
        }
    }

    @Before
    public void setUp() throws Exception
    {
        controlador = new Controlador();
        controlador.getVista().setOptionPane(op);
        escenario.setUp();
        
        robot.delay(TestUtils.getDelay());

        JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_USUARIO);
        JTextField password = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PASSWORD);
        JButton botonLogin = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LOGIN);

        TestUtils.clickComponent(nombre, robot);
        TestUtils.tipeaTexto("user1", robot);
        
        TestUtils.clickComponent(password, robot);
        TestUtils.tipeaTexto("pass1", robot);

        TestUtils.clickComponent(botonLogin, robot);
    }

    @After
    public void tearDown() throws Exception
    {
        Ventana ventana = (Ventana) controlador.getVista();
    	ventana.setVisible(false);
    	escenario.tearDown();
    	escenario.empresa.logout();
    }

// TEST PANEL LOGIN
    
    @Test
    public void testPanelLogin_CorrectoCliente()
    {
    	robot.delay(TestUtils.getDelay());
        JPanel panelCliente = (JPanel) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PANEL_CLIENTE);
        Assert.assertTrue("El panel de Cliente deberia estar visible despues de un login exitoso", panelCliente.isVisible());
        Assert.assertEquals("Deberia coincidir el nombre de usuario con el nombre ingresado", "user1", escenario.empresa.getUsuarioLogeado().getNombreUsuario()) ;
    }
    
    @Test
    public void testPanelLogin_CerrarSesionCliente()
    {
    	robot.delay(TestUtils.getDelay());
    	JButton cerrarSesion = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CERRAR_SESION_CLIENTE);
    	TestUtils.clickComponent(cerrarSesion, robot);
    	
    	JPanel panelLogin = (JPanel) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PANEL_LOGIN);
        Assert.assertTrue("El panel de Login deberia estar visible despues de cerrar sesion", panelLogin.isVisible());
        Assert.assertEquals("No deberia haber ningun usuario logueado", null, escenario.empresa.getUsuarioLogeado()) ;
    }
    
    @Test
    public void testPanelLogin_AdmPasswordIncorrecta()
    {
        robot.delay(TestUtils.getDelay());
        JButton cerrarSesion = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CERRAR_SESION_CLIENTE);
    	TestUtils.clickComponent(cerrarSesion, robot);
    	
    	robot.delay(TestUtils.getDelay());
        JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_USUARIO);
        JTextField password = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PASSWORD);
        JButton botonLogin = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LOGIN);

        TestUtils.clickComponent(nombre, robot);
        TestUtils.tipeaTexto("admin", robot);
        
        TestUtils.clickComponent(password, robot);
        TestUtils.tipeaTexto("random", robot);
        
        TestUtils.clickComponent(botonLogin, robot);

        robot.delay(TestUtils.getDelay());
        Assert.assertEquals("Mensaje incorrecto, deberia decir: "+Mensajes.PASS_ERRONEO.getValor(), Mensajes.PASS_ERRONEO.getValor(),op.getMensaje());
    }
    
    @Test
    public void testPanelLogin_ClientePasswordIncorrecta()
    {
        robot.delay(TestUtils.getDelay());
        JButton cerrarSesion = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CERRAR_SESION_CLIENTE);
    	TestUtils.clickComponent(cerrarSesion, robot);
    	
    	robot.delay(TestUtils.getDelay());
        JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_USUARIO);
        JTextField password = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PASSWORD);
        JButton botonLogin = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LOGIN);

        TestUtils.clickComponent(nombre, robot);
        TestUtils.tipeaTexto("user1", robot);
        
        TestUtils.clickComponent(password, robot);
        TestUtils.tipeaTexto("1", robot);
        
        TestUtils.clickComponent(botonLogin, robot);

        robot.delay(TestUtils.getDelay());
        Assert.assertEquals("Mensaje incorrecto, deberia decir: "+Mensajes.PASS_ERRONEO.getValor(), Mensajes.PASS_ERRONEO.getValor(),op.getMensaje());
    }
   
    @Test
    public void testPanelLogin_UsuarioDesconocido()
    {
    	robot.delay(TestUtils.getDelay());
        JButton cerrarSesion = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CERRAR_SESION_CLIENTE);
    	TestUtils.clickComponent(cerrarSesion, robot);
    	
    	robot.delay(TestUtils.getDelay());
        JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_USUARIO);
        JTextField password = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PASSWORD);
        JButton botonLogin = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LOGIN);

        TestUtils.clickComponent(nombre, robot);
        TestUtils.tipeaTexto("a", robot);
        
        TestUtils.clickComponent(password, robot);
        TestUtils.tipeaTexto("a", robot);
        
        TestUtils.clickComponent(botonLogin, robot);

        robot.delay(TestUtils.getDelay());
        Assert.assertEquals("Mensaje incorrecto, deberia decir: "+Mensajes.USUARIO_DESCONOCIDO.getValor(), Mensajes.USUARIO_DESCONOCIDO.getValor(),op.getMensaje());
        Assert.assertNull("El Usuario actual deberia ser null", escenario.empresa.getUsuarioLogeado());
    }
    
//TEST PANEL REGISTRO
    
    @Test
  	public void testPanelRegistro_UsuarioRepetido()
  	{
    	JButton cerrarSesion = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CERRAR_SESION_CLIENTE);
    	TestUtils.clickComponent(cerrarSesion, robot);
    	
    	robot.delay(TestUtils.getDelay());
  		JButton botonIrReg = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REGISTRAR);
  		TestUtils.clickComponent(botonIrReg, robot);
  		
  		robot.delay(TestUtils.getDelay());
  		JTextField nombreUsuario = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_USSER_NAME);
  		JTextField nombreReal = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_REAL_NAME);
  		JTextField password = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_PASSWORD);
  		JTextField confirmarPassword = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_CONFIRM_PASSWORD);
  		JButton botonRegistrar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_BUTTON_REGISTRAR);
  	
  		TestUtils.clickComponent(nombreUsuario, robot);
  		TestUtils.tipeaTexto("user1", robot);
  	
  		TestUtils.clickComponent(password, robot);
  		TestUtils.tipeaTexto("a", robot);
  		
  		TestUtils.clickComponent(confirmarPassword, robot);
  		TestUtils.tipeaTexto("a", robot);
  		
  		TestUtils.clickComponent(nombreReal, robot);
  		TestUtils.tipeaTexto("a", robot);
  		
  		TestUtils.clickComponent(botonRegistrar, robot);
  		Assert.assertEquals("Mensaje incorrecto, deberia decir: "+Mensajes.USUARIO_REPETIDO.getValor(), Mensajes.USUARIO_REPETIDO.getValor(),op.getMensaje());
  	}
    
    @Test
  	public void testPanelRegistro_UsuarioRepetidoColeccion()
  	{
    	JButton cerrarSesion = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CERRAR_SESION_CLIENTE);
    	TestUtils.clickComponent(cerrarSesion, robot);
    	
    	robot.delay(TestUtils.getDelay());
  		JButton botonIrReg = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REGISTRAR);
  		TestUtils.clickComponent(botonIrReg, robot);
  		
  		robot.delay(TestUtils.getDelay());
  		JTextField nombreUsuario = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_USSER_NAME);
  		JTextField nombreReal = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_REAL_NAME);
  		JTextField password = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_PASSWORD);
  		JTextField confirmarPassword = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_CONFIRM_PASSWORD);
  		JButton botonRegistrar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_BUTTON_REGISTRAR);
  		
  		int cantidadClientes = escenario.empresa.getClientes().size();
  	
  		TestUtils.clickComponent(nombreUsuario, robot);
  		TestUtils.tipeaTexto("user1", robot);
  	
  		TestUtils.clickComponent(password, robot);
  		TestUtils.tipeaTexto("a", robot);
  		
  		TestUtils.clickComponent(confirmarPassword, robot);
  		TestUtils.tipeaTexto("a", robot);
  		
  		TestUtils.clickComponent(nombreReal, robot);
  		TestUtils.tipeaTexto("a", robot);
  		
  		TestUtils.clickComponent(botonRegistrar, robot);
  		
  		robot.delay(TestUtils.getDelay());
  		Assert.assertEquals("La cantidad de clientes no deberia haber aumentado", cantidadClientes, escenario.empresa.getClientes().size());
  	}
    
//TEST PANEL CLIENTE
    
    @Test
    public void testPanelLogin_Titulo() 
    {
        robot.delay(TestUtils.getDelay());
        JPanel panelCliente = (JPanel) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PANEL_CLIENTE);
        Ventana ventana = (Ventana) controlador.getVista(); 
        
        robot.delay(TestUtils.getDelay());
        Assert.assertTrue("El panel de Cliente deberia estar visible despues de un login exitoso", panelCliente.isVisible());
        Assert.assertEquals("Deberia coincidir el nombre de usuario con el nombre ingresado", "user1", ventana.getTitle());
    }
 
    @Test
    public void testPanelCliente_NuevoPedido_TextFieldCantPax()
    {
        robot.delay(TestUtils.getDelay());
        JTextField campoCantPax = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CANT_PAX);
        JTextField campoCantKM = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CANT_KM);
        JRadioButton radioStandard = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.ZONA_STANDARD);
        JCheckBox checkBoxBaul = (JCheckBox) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CHECK_BAUL);
        JCheckBox checkBoxMascota = (JCheckBox) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CHECK_MASCOTA);
        JButton botonNuevoPedido = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_PEDIDO);
        
        TestUtils.clickComponent(campoCantPax, robot);
        TestUtils.tipeaTexto("4", robot);
        TestUtils.clickComponent(campoCantKM, robot);
        TestUtils.tipeaTexto("10", robot);
        TestUtils.clickComponent(radioStandard, robot);
        TestUtils.clickComponent(checkBoxMascota, robot);
        TestUtils.clickComponent(checkBoxBaul, robot);
        
        robot.delay(TestUtils.getDelay());
        TestUtils.clickComponent(botonNuevoPedido, robot);
        
        robot.delay(TestUtils.getDelay());
        Assert.assertTrue("El campo CantPax deberia estar vacio", campoCantPax.getText().isEmpty());
    }
    
    @Test
    public void testPanelCliente_NuevoPedido_TextFieldCantKM()
    {
        robot.delay(TestUtils.getDelay());
        JTextField campoCantPax = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CANT_PAX);
        JTextField campoCantKM = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CANT_KM);
        JRadioButton radioStandard = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.ZONA_STANDARD);
        JCheckBox checkBoxBaul = (JCheckBox) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CHECK_BAUL);
        JCheckBox checkBoxMascota = (JCheckBox) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CHECK_MASCOTA);
        JButton botonNuevoPedido = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_PEDIDO);
        
        TestUtils.clickComponent(campoCantPax, robot);
        TestUtils.tipeaTexto("4", robot);
        TestUtils.clickComponent(campoCantKM, robot);
        TestUtils.tipeaTexto("10", robot);
        TestUtils.clickComponent(radioStandard, robot);
        TestUtils.clickComponent(checkBoxMascota, robot);
        TestUtils.clickComponent(checkBoxBaul, robot);
        
        robot.delay(TestUtils.getDelay());
        TestUtils.clickComponent(botonNuevoPedido, robot);
        
        robot.delay(TestUtils.getDelay());
        Assert.assertTrue("El campo CantKM deberia estar vacio", campoCantKM.getText().isEmpty());
    }
    
    @Test
    public void testPanelCliente_NuevoPedido_VerificoTextArea()
    {
        robot.delay(TestUtils.getDelay());
        JTextField campoCantPax = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CANT_PAX);
        JTextField campoKM = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CANT_KM);
        JRadioButton radioStandard = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.ZONA_STANDARD);
        JCheckBox checkBoxBaul = (JCheckBox) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CHECK_BAUL);
        JCheckBox checkBoxMascota = (JCheckBox) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CHECK_MASCOTA);
        JButton botonNuevoPedido = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_PEDIDO);
        JTextArea textAreaPedidoOViajeActual = (JTextArea) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PEDIDO_O_VIAJE_ACTUAL);
        
        Assert.assertTrue("El JTextArea de pedido o viaje actual deberia estar vacio", textAreaPedidoOViajeActual.getText().isEmpty());
        
        TestUtils.clickComponent(campoCantPax, robot);
        TestUtils.tipeaTexto("4", robot);
        TestUtils.clickComponent(campoKM, robot);
        TestUtils.tipeaTexto("10", robot);
        TestUtils.clickComponent(radioStandard, robot);
        TestUtils.clickComponent(checkBoxMascota, robot);
        TestUtils.clickComponent(checkBoxBaul, robot);
        
        robot.delay(TestUtils.getDelay());
        TestUtils.clickComponent(botonNuevoPedido, robot);
        
        robot.delay(TestUtils.getDelay());
        Assert.assertFalse("El JTextArea de pedido o viaje actual no deberia estar vacio", textAreaPedidoOViajeActual.getText().isEmpty());
    }

    //Este test falla porque el metodo de la empresa de OrdenarVehiculos para un pedido pone vehiculos que NO satisfacen dicho pedido
    @Test
    public void testPanelCliente_NuevoPedido_SinVehiculoParaPedido()
    {
        robot.delay(TestUtils.getDelay());
        JTextField campoCantPax = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CANT_PAX);
        JTextField campoKM = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CANT_KM);
        JRadioButton radioStandard = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.ZONA_STANDARD);
        JCheckBox checkBoxBaul = (JCheckBox) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CHECK_BAUL);
        JCheckBox checkBoxMascota = (JCheckBox) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CHECK_MASCOTA);
        JButton botonNuevoPedido = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_PEDIDO);
        
        TestUtils.clickComponent(campoCantPax, robot);
        TestUtils.tipeaTexto("10", robot);
        TestUtils.clickComponent(campoKM, robot);
        TestUtils.tipeaTexto("10", robot);
        TestUtils.clickComponent(radioStandard, robot);
        TestUtils.clickComponent(checkBoxMascota, robot);
        TestUtils.clickComponent(checkBoxBaul, robot);
        
        robot.delay(TestUtils.getDelay());
        TestUtils.clickComponent(botonNuevoPedido, robot);
        
        robot.delay(TestUtils.getDelay());
        Assert.assertEquals("Mensaje incorrecto, deberia decir: "+Mensajes.SIN_VEHICULO_PARA_PEDIDO.getValor(), Mensajes.SIN_VEHICULO_PARA_PEDIDO.getValor(),op.getMensaje());
    }
    
}
