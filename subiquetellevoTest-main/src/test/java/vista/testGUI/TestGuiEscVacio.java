package vista.testGUI;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Robot;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import modelo.negocio.EscenarioBase;
import util.Constantes;
import util.Mensajes;
import vista.FalseOptionPanel;
import vista.TestUtils;
import vista.Ventana;

public class TestGuiEscVacio {
	Robot robot;
    Controlador controlador;
    FalseOptionPanel op = new FalseOptionPanel();
    private EscenarioBase escenario = new EscenarioBase(); 

    public TestGuiEscVacio()
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

        escenario.empresa.getClientes().clear(); 
        escenario.empresa.getChoferes().clear();
        escenario.empresa.getVehiculos().clear();
        escenario.empresa.logout();

        escenario.setUp(); 
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
    public void testPanelLogin_PasswordIncorrectaLogueo()
    {
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
        Assert.assertNull("El Usuario actual deberia ser null", escenario.empresa.getUsuarioLogeado());
    }
    
    @Test
    public void testPanelLogin_UsuarioDesconocidoLogueo()
    {
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
        Assert.assertNull("El Usuario actual deberia ser null", escenario.empresa.getUsuarioLogeado());
    }

//TEST PANEL REGISTRO
    @Test
  	public void testPanelRegistro_CorrectoColeccion()
  	{
  		robot.delay(TestUtils.getDelay());
  		JButton botonIrReg = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REGISTRAR);
  		TestUtils.clickComponent(botonIrReg, robot);
  		
  		robot.delay(TestUtils.getDelay());
  		
  		JPanel panelRegistro = (JPanel) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PANEL_REGISTRO);
  	 Assert.assertNotNull("El panel de registro no se encontró", panelRegistro);
   Assert.assertTrue("El panel de registro debería estar visible", panelRegistro.isVisible());
  	
		JTextField nombreUsuario = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_USSER_NAME);
	 JTextField nombreReal = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_REAL_NAME);
	 JTextField password = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_PASSWORD);
	JTextField confirmarPassword = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_CONFIRM_PASSWORD);
	 JButton botonRegistrar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_BUTTON_REGISTRAR);
		
	 int cantidadClientes = escenario.empresa.getClientes().size();
 	
	 TestUtils.clickComponent(nombreUsuario, robot);
	 TestUtils.tipeaTexto("a", robot); 

	 TestUtils.clickComponent(password, robot);
	 TestUtils.tipeaTexto("a", robot); 

	 TestUtils.clickComponent(confirmarPassword, robot);
	 TestUtils.tipeaTexto("a", robot); 

	 TestUtils.clickComponent(nombreReal, robot);
	 TestUtils.tipeaTexto("a", robot);
  		
  		TestUtils.clickComponent(botonRegistrar, robot);
  		
  		robot.delay(TestUtils.getDelay());
  		
  		JPanel panelLogin = (JPanel) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PANEL_LOGIN);
  		Assert.assertNotNull("El panel de Login no se encontró, el registro debe haber fallado", panelLogin);
  		Assert.assertTrue("El panel de Login deberia ser visible despues del registro exitoso", panelLogin.isVisible());
  		Assert.assertEquals("La cantidad de clientes deberia haber aumentado en 1 ", cantidadClientes + 1, escenario.empresa.getClientes().size());
  	}
  	@Test
  	public void testPanelRegistro_SegundoAdm()
  	{
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
  		TestUtils.tipeaTexto("admin", robot);
  	
  		TestUtils.clickComponent(password, robot);
  		TestUtils.tipeaTexto("a", robot);
  		
  		TestUtils.clickComponent(confirmarPassword, robot);
  		TestUtils.tipeaTexto("a", robot);
  		
  		TestUtils.clickComponent(nombreReal, robot);
  		TestUtils.tipeaTexto("a", robot);
  		
  		TestUtils.clickComponent(botonRegistrar, robot);
  		
  		robot.delay(TestUtils.getDelay());
  		Assert.assertEquals("Mensaje incorrecto, deberia decir: "+Mensajes.USUARIO_REPETIDO.getValor(), Mensajes.USUARIO_REPETIDO.getValor(),op.getMensaje());
  		Assert.assertEquals("La cantidad de clientes NO deberia haber aumentado, se registro un segundo Adm", cantidadClientes, escenario.empresa.getClientes().size());
  	}
  	
  	@Test
	public void testPanelRegistro_MovimientoPaneles()
	{
		robot.delay(TestUtils.getDelay());
		JPanel panelLogin = (JPanel) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PANEL_LOGIN);
		Assert.assertTrue("El panel de Login deberia ser el panel actual", panelLogin.isVisible());
		
		JButton botonIrReg = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REGISTRAR);
		TestUtils.clickComponent(botonIrReg, robot);
		
		robot.delay(TestUtils.getDelay());
		JPanel panelRegistro= (JPanel) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PANEL_REGISTRO);
		Assert.assertTrue("El panel de Registro deberia ser el panel actual", panelRegistro.isVisible());
		
		JButton botonCancelar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_BUTTON_CANCELAR);
		TestUtils.clickComponent(botonCancelar, robot);
		
		robot.delay(TestUtils.getDelay());
		panelLogin = (JPanel) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PANEL_LOGIN);
		Assert.assertTrue("El panel de Login deberia ser el panel actual", panelLogin.isVisible());
	}
  	
  	@Test
  	public void testPanelRegistro_PasswordNoCoincide()
  	{
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
  		TestUtils.tipeaTexto("a", robot);
  	
  		TestUtils.clickComponent(password, robot);
  		TestUtils.tipeaTexto("a", robot);
  		
  		TestUtils.clickComponent(confirmarPassword, robot);
  		TestUtils.tipeaTexto("b", robot);
  		
  		TestUtils.clickComponent(nombreReal, robot);
  		TestUtils.tipeaTexto("a", robot);
  		
  		TestUtils.clickComponent(botonRegistrar, robot);
  		
  		robot.delay(TestUtils.getDelay());
  		Assert.assertEquals("La cantidad de clientes no deberia haber aumentado", cantidadClientes, escenario.empresa.getClientes().size());
  		Assert.assertEquals("Mensaje incorrecto, deberia decir: "+Mensajes.PASS_NO_COINCIDE.getValor(), Mensajes.PASS_NO_COINCIDE.getValor(),op.getMensaje());
  	}

//TEST PANEL CLIENTE

    @Test
    public void testPanelCliente_NuevoPedido_SinVehiculoParaPedido()
    {
    	//REGISTRO USER
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
  		TestUtils.tipeaTexto("a", robot);
  		TestUtils.clickComponent(password, robot);
  		TestUtils.tipeaTexto("a", robot);
  		TestUtils.clickComponent(confirmarPassword, robot);
  		TestUtils.tipeaTexto("a", robot);
  		TestUtils.clickComponent(nombreReal, robot);
  		TestUtils.tipeaTexto("a", robot);
  		TestUtils.clickComponent(botonRegistrar, robot);
  		
  		//LOGIN USER
  		robot.delay(TestUtils.getDelay());
        JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_USUARIO);
        JTextField passwordL = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PASSWORD);
        JButton botonLogin = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LOGIN);

        TestUtils.clickComponent(nombre, robot);
        TestUtils.tipeaTexto("a", robot);
        
        TestUtils.clickComponent(passwordL, robot);
        TestUtils.tipeaTexto("a", robot);
        
        TestUtils.clickComponent(botonLogin, robot);
    	
        //PEDIDO USER
    	robot.delay(TestUtils.getDelay());
        JTextField campoCantPax = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CANT_PAX);
        JTextField campoKM = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CANT_KM);
        JRadioButton radioStandard = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.ZONA_STANDARD);
        JCheckBox checkBoxBaul = (JCheckBox) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CHECK_BAUL);
        JCheckBox checkBoxMascota = (JCheckBox) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CHECK_MASCOTA);
        JButton botonNuevoPedido = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_PEDIDO);
        
        TestUtils.clickComponent(campoCantPax, robot);
        TestUtils.tipeaTexto("5", robot);
        TestUtils.clickComponent(campoKM, robot);
        TestUtils.tipeaTexto("5", robot);
        TestUtils.clickComponent(radioStandard, robot);
        TestUtils.clickComponent(checkBoxMascota, robot);
        TestUtils.clickComponent(checkBoxBaul, robot);
        
        TestUtils.clickComponent(botonNuevoPedido, robot);
        
        robot.delay(TestUtils.getDelay());
        Assert.assertEquals("Mensaje incorrecto, deberia decir: "+Mensajes.SIN_VEHICULO_PARA_PEDIDO.getValor(), Mensajes.SIN_VEHICULO_PARA_PEDIDO.getValor(),op.getMensaje());
        
    }






}
