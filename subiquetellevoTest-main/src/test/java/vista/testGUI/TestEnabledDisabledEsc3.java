package vista.testGUI;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Robot;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import junit.framework.Assert;
import modelo.negocio.Escenario3;
import util.Constantes;
import vista.TestUtils;
import vista.Ventana;

//ESCENARIO 3 TIENE PEDIDOS
public class TestEnabledDisabledEsc3 {
	Robot robot;
    Controlador controlador;
    private Escenario3 escenario = new Escenario3(); 

    public TestEnabledDisabledEsc3()
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
    }
 
// TEST PANEL CLIENTE : Pendiente Actual
    
    @Test
    public void testPanelCliente_PendienteActual_PedidoPendiente()
    {
    	robot.delay(TestUtils.getDelay());
        JTextField campoCalificacion = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICACION_DE_VIAJE);
        JTextField campoValor = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.VALOR_VIAJE);
        JButton botonCalificarPagar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICAR_PAGAR);
        
        Assert.assertFalse("El campo de Calificar Viaje deberia estar deshablitado", campoCalificacion.isEnabled());
        Assert.assertTrue("El campo de Valor del Viaje deberia estar vacio", campoValor.getText().isEmpty());
        Assert.assertFalse("El boton de Pagar deberia estar deshablitado", botonCalificarPagar.isEnabled());
    }
    
// TEST PANEL CLIENTE : NUEVO PEDIDO
    
    @Test
    public void testPanelCliente_NuevoPedido_ConPendientes()
    {
    	robot.delay(TestUtils.getDelay());
        JTextField campoCantPax = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CANT_PAX);
        JTextField campoKM = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CANT_KM);
        JRadioButton radioStandard = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.ZONA_STANDARD);
        JRadioButton radioSinAsfaltar = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.ZONA_SIN_ASFALTAR);
        JRadioButton radioPeligrosa = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.ZONA_PELIGROSA);
        JCheckBox checkBoxBaul = (JCheckBox) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CHECK_BAUL);
        JCheckBox checkBoxMascota = (JCheckBox) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CHECK_MASCOTA);
        JButton botonNuevoPedido = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_PEDIDO);

        Assert.assertFalse("El campo de Cantidad de Pasajeros deberia estar deshablitado", campoCantPax.isEnabled());
        Assert.assertFalse("El campo de Cantidad de Kilometros deberia estar deshablitado", campoKM.isEnabled());
        Assert.assertFalse("La opcion de Topo de Zona Standard deberia estar deshablitado", radioStandard.isEnabled());
        Assert.assertFalse("La opcion de Topo de Zona Sin Asfaltar deberia estar deshablitado", radioSinAsfaltar.isEnabled());
        Assert.assertFalse("La opcion de Topo de Zona Peligrosa deberia estar deshablitado", radioPeligrosa.isEnabled());
        Assert.assertFalse("El checkBox de Baul deberia estar deshablitado", checkBoxBaul.isEnabled());
        Assert.assertFalse("El checkBox de Mascota Pedido deberia estar deshablitado", checkBoxMascota.isEnabled());
        Assert.assertFalse("El boton de Nuevo Pedido deberia estar deshablitado", botonNuevoPedido.isEnabled());
    }
    
}
