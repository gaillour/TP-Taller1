package vista.testGUI;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Robot;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import junit.framework.Assert;
import modelo.negocio.Escenario4;
import modeloDatos.Cliente;
import util.Constantes;
import vista.TestUtils;
import vista.Ventana;

public class TestGuiEsc4 {
	Robot robot;
    Controlador controlador;
    private Escenario4 escenario = new Escenario4(); 

    public TestGuiEsc4()
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
 
// TEST PANEL CLIENTE : Pendiente Actual
    
	@Test
    public void testPanelCliente_PagarViaje_TextAreaPedidosViajes()  
    {
        robot.delay(TestUtils.getDelay());
        JTextArea textAreaPedidosViajes = (JTextArea) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PEDIDO_O_VIAJE_ACTUAL);
        
        JTextField campoCalificacion = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICACION_DE_VIAJE);
        JButton botonCalificarPagar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICAR_PAGAR);
        
        TestUtils.clickComponent(campoCalificacion, robot);
        TestUtils.tipeaTexto("3", robot);
        TestUtils.clickComponent(botonCalificarPagar, robot);
        
        robot.delay(TestUtils.getDelay());
        Assert.assertTrue("El TextArea Pedido Pendiente o Viaje Actual deberia estar vacio", textAreaPedidosViajes.getText().isEmpty());
    }
	
	@Test
    public void testPanelCliente_PagarViaje_TextAreaHistorialViajes()  
    {
        robot.delay(TestUtils.getDelay());
        JList<?> textAreaHistorialViajes = (JList<?>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VIAJES_CLIENTE);
        int cantListaViajesCliente = escenario.empresa.getHistorialViajeCliente((Cliente) escenario.empresa.getUsuarioLogeado()).size(); 
        
        JTextField campoCalificacion = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICACION_DE_VIAJE);
        JTextField campoValor = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.VALOR_VIAJE);
        JButton botonCalificarPagar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICAR_PAGAR);
        
        TestUtils.clickComponent(campoCalificacion, robot);
        TestUtils.tipeaTexto("3", robot);
        TestUtils.clickComponent(botonCalificarPagar, robot);
        
        robot.delay(TestUtils.getDelay());
        Assert.assertEquals("El Viaje Actual finalizado no se visualiza en la lista de historicos",cantListaViajesCliente + 1, escenario.empresa.getHistorialViajeCliente((Cliente) escenario.empresa.getUsuarioLogeado()).size());
    }
	
	@Test
    public void testPanelCliente_PagarViaje_TextFieldCalificacion()  
    {
        robot.delay(TestUtils.getDelay());
        JTextField campoCalificacion = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICACION_DE_VIAJE);
        JButton botonCalificarPagar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICAR_PAGAR);
        
        TestUtils.clickComponent(campoCalificacion, robot);
        TestUtils.tipeaTexto("3", robot);
        TestUtils.clickComponent(botonCalificarPagar, robot);

        robot.delay(TestUtils.getDelay());
        Assert.assertTrue("El campo Calificacion deberia estar vacio", campoCalificacion.getText().isEmpty());
    }
	
	@Test
    public void testPanelCliente_PagarViaje_TextFieldValor()  
    {
        robot.delay(TestUtils.getDelay());
        JTextField campoCalificacion = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICACION_DE_VIAJE);
        JTextField campoValor = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.VALOR_VIAJE);
        JButton botonCalificarPagar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICAR_PAGAR);
        
        TestUtils.clickComponent(campoCalificacion, robot);
        TestUtils.tipeaTexto("3", robot);
        TestUtils.clickComponent(botonCalificarPagar, robot);

        robot.delay(TestUtils.getDelay());
        Assert.assertTrue("El campo Valor deberia estar vacio", campoValor.getText().isEmpty());
    }
}
