package vista.testGUI;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Robot;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import junit.framework.Assert;
import modelo.negocio.EscenarioBase;
import util.Constantes;
import vista.TestUtils;
import vista.Ventana;

public class TestEnabledDisabledEscVacio {
	Robot robot;
    Controlador controlador;
    private EscenarioBase escenario = new EscenarioBase(); 

    public TestEnabledDisabledEscVacio()
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
        escenario.setUp();
    }

    @After
    public void tearDown() throws Exception
    {
        Ventana ventana = (Ventana) controlador.getVista();
    	ventana.setVisible(false);
    	escenario.tearDown();
    }

// TEST PANEL LOGIN
    
    @Test
    public void testPanelLogin_Completo()
    {
        robot.delay(TestUtils.getDelay());

        JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_USUARIO);
        JTextField password = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PASSWORD);
        JButton botonIrReg = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REGISTRAR);
        JButton botonLogin = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LOGIN);

        TestUtils.clickComponent(nombre, robot);
        TestUtils.tipeaTexto("random", robot);
        
        TestUtils.clickComponent(password, robot);
        TestUtils.tipeaTexto("random", robot);

        Assert.assertTrue("El boton de IR a registro deberia estar hablitado", botonIrReg.isEnabled());
        Assert.assertTrue("El boton de login deberia estar hablitado", botonLogin.isEnabled());
    }
    
    @Test
    public void testPanelLogin_CamposVacios()
    {
        robot.delay(TestUtils.getDelay());

        JButton botonIrReg = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REGISTRAR);
        JButton botonLogin = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LOGIN);

        Assert.assertTrue("El boton de IR a registro deberia estar hablitado", botonIrReg.isEnabled());
        Assert.assertFalse("El boton de login deberia estar deshablitado", botonLogin.isEnabled());
    }
    
    @Test
    public void testPanelLogin_SoloNombre()
    {
        robot.delay(TestUtils.getDelay());

        JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_USUARIO);
        JButton botonIrReg = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REGISTRAR);
        JButton botonLogin = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LOGIN);

        TestUtils.clickComponent(nombre, robot);
        TestUtils.tipeaTexto("random", robot);

        Assert.assertTrue("El boton de IR a registro deberia estar hablitado", botonIrReg.isEnabled());
        Assert.assertFalse("El boton de login deberia estar deshablitado", botonLogin.isEnabled());
    }
    
    @Test
    public void testPanelLogin_SoloPassword()
    {
        robot.delay(TestUtils.getDelay());

        JTextField password = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PASSWORD);
        JButton botonIrReg = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REGISTRAR);
        JButton botonLogin = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LOGIN);

        TestUtils.clickComponent(password, robot);
        TestUtils.tipeaTexto("random", robot);

        Assert.assertTrue("El boton de IR a registro deberia estar hablitado", botonIrReg.isEnabled());
        Assert.assertFalse("El boton de login deberia estar deshablitado", botonLogin.isEnabled());
    }
    
//TEST PANEL REGISTRO

	@Test
	public void testPanelRegistro_Completo()
	{
		robot.delay(TestUtils.getDelay());
		JButton botonIrReg = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REGISTRAR);
		TestUtils.clickComponent(botonIrReg, robot);
	
		JTextField nombreUsuario = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_USSER_NAME);
		JTextField nombreReal = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_REAL_NAME);
		JTextField password = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_PASSWORD);
		JTextField confirmarPassword = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_CONFIRM_PASSWORD);
		
		JButton botonRegistrar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_BUTTON_REGISTRAR);
		JButton botonCancelar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_BUTTON_CANCELAR);
	
		TestUtils.clickComponent(nombreUsuario, robot);
		TestUtils.tipeaTexto("qq", robot);
	
		TestUtils.clickComponent(password, robot);
		TestUtils.tipeaTexto("qq", robot);
		
		TestUtils.clickComponent(confirmarPassword, robot);
		TestUtils.tipeaTexto("qq", robot);
		
		TestUtils.clickComponent(nombreReal, robot);
		TestUtils.tipeaTexto("qq", robot);
	
		Assert.assertTrue("El boton de registro deberia estar hablitado", botonRegistrar.isEnabled());
		Assert.assertTrue("El boton de Cancelar registro deberia estar hablitado", botonCancelar.isEnabled());
	}
	
	@Test
	public void testPanelRegistro_CamposVacios()
	{
		robot.delay(TestUtils.getDelay());
		JButton botonIrReg = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REGISTRAR);
		TestUtils.clickComponent(botonIrReg, robot);
	
		JButton botonRegistrar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_BUTTON_REGISTRAR);
		JButton botonCancelar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_BUTTON_CANCELAR);
	
		Assert.assertFalse("El boton de registro deberia estar deshablitado", botonRegistrar.isEnabled());
		Assert.assertTrue("El boton de Cancelar registro deberia estar hablitado", botonCancelar.isEnabled());
	}
	
	@Test
	public void testPanelRegistro_SoloNombreUsuario()
	{
		robot.delay(TestUtils.getDelay());
		JButton botonIrReg = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REGISTRAR);
		TestUtils.clickComponent(botonIrReg, robot);
	
		JTextField nombreUsuario = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_USSER_NAME);
		JButton botonRegistrar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_BUTTON_REGISTRAR);
		JButton botonCancelar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_BUTTON_CANCELAR);
	
		TestUtils.clickComponent(nombreUsuario, robot);
		TestUtils.tipeaTexto("randomNombreReal", robot);
	
		Assert.assertFalse("El boton de registro deberia estar deshablitado", botonRegistrar.isEnabled());
		Assert.assertTrue("El boton de Cancelar registro deberia estar hablitado", botonCancelar.isEnabled());
	}
	
	@Test
	public void testPanelRegistro_SoloPassword()
	{
		robot.delay(TestUtils.getDelay());
		JButton botonIrReg = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REGISTRAR);
		TestUtils.clickComponent(botonIrReg, robot);
	
		JTextField password = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_PASSWORD);
		JButton botonRegistrar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_BUTTON_REGISTRAR);
		JButton botonCancelar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_BUTTON_CANCELAR);
	
		TestUtils.clickComponent(password, robot);
		TestUtils.tipeaTexto("randomContrasenia", robot);
	
		Assert.assertFalse("El boton de registro deberia estar deshablitado", botonRegistrar.isEnabled());
		Assert.assertTrue("El boton de Cancelar registro deberia estar hablitado", botonCancelar.isEnabled());
	}
	
	@Test
	public void testPanelRegistro_SoloConfirmarPassword()
	{
		robot.delay(TestUtils.getDelay());
		JButton botonIrReg = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REGISTRAR);
		TestUtils.clickComponent(botonIrReg, robot);
	
		JTextField confirmarPassword = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_CONFIRM_PASSWORD);
		JButton botonRegistrar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_BUTTON_REGISTRAR);
		JButton botonCancelar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_BUTTON_CANCELAR);
	
		TestUtils.clickComponent(confirmarPassword, robot);
		TestUtils.tipeaTexto("randomContrasenia", robot);
	
		Assert.assertFalse("El boton de registro deberia estar deshablitado", botonRegistrar.isEnabled());
		Assert.assertTrue("El boton de Cancelar registro deberia estar hablitado", botonCancelar.isEnabled());
	}
	
	@Test
	public void testPanelRegistro_SoloNombreReal()
	{
		robot.delay(TestUtils.getDelay());
		JButton botonIrReg = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REGISTRAR);
		TestUtils.clickComponent(botonIrReg, robot);
	
		JTextField nombreReal = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_REAL_NAME);
		JButton botonRegistrar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_BUTTON_REGISTRAR);
		JButton botonCancelar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_BUTTON_CANCELAR);
	
		TestUtils.clickComponent(nombreReal, robot);
		TestUtils.tipeaTexto("randomNombreReal", robot);
	
		Assert.assertFalse("El boton de registro deberia estar deshablitado", botonRegistrar.isEnabled());
		Assert.assertTrue("El boton de Cancelar registro deberia estar hablitado", botonCancelar.isEnabled());
	}

	@Test
	public void testPanelRegistro_CompletoDosCampos()
	{
		robot.delay(TestUtils.getDelay());
		JButton botonIrReg = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REGISTRAR);
		TestUtils.clickComponent(botonIrReg, robot);
	
		JTextField nombreReal = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_REAL_NAME);
		JTextField password = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_PASSWORD);
		JButton botonRegistrar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_BUTTON_REGISTRAR);
		JButton botonCancelar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_BUTTON_CANCELAR);
	
		TestUtils.clickComponent(nombreReal, robot);
		TestUtils.tipeaTexto("randomNombreReal", robot);
		
		TestUtils.clickComponent(password, robot);
		TestUtils.tipeaTexto("randomContrasenia", robot);
	
		Assert.assertFalse("El boton de registro deberia estar deshablitado", botonRegistrar.isEnabled());
		Assert.assertTrue("El boton de Cancelar registro deberia estar hablitado", botonCancelar.isEnabled());
	}
	
	@Test
	public void testPanelRegistro_CompletoTresCampos()
	{
		robot.delay(TestUtils.getDelay());
		JButton botonIrReg = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REGISTRAR);
		TestUtils.clickComponent(botonIrReg, robot);
	
		JTextField nombreReal = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_REAL_NAME);
		JTextField password = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_PASSWORD);
		JTextField confirmarPassword = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_CONFIRM_PASSWORD);
		JButton botonRegistrar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_BUTTON_REGISTRAR);
		JButton botonCancelar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.REG_BUTTON_CANCELAR);
	
		TestUtils.clickComponent(nombreReal, robot);
		TestUtils.tipeaTexto("randomNombreReal", robot);
		
		TestUtils.clickComponent(password, robot);
		TestUtils.tipeaTexto("randomContrasenia", robot);
		
		TestUtils.clickComponent(confirmarPassword, robot);
		TestUtils.tipeaTexto("randomContrasenia", robot);
	
		Assert.assertFalse("El boton de registro deberia estar deshablitado", botonRegistrar.isEnabled());
		Assert.assertTrue("El boton de Cancelar registro deberia estar hablitado", botonCancelar.isEnabled());
	}

}
