package vista.testGUI;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import junit.framework.Assert;
import modelo.negocio.Escenario2;
import modeloDatos.Chofer;
import modeloDatos.Vehiculo;
import util.Constantes;
import util.Mensajes;
import vista.FalseOptionPanel;
import vista.TestUtils;
import vista.Ventana;

public class TestGuiAdmEsc2 {
	Robot robot;
    Controlador controlador;
    FalseOptionPanel op = new FalseOptionPanel();
    private Escenario2 escenario = new Escenario2(); 

    public TestGuiAdmEsc2()
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
        TestUtils.tipeaTexto("admin", robot);
        
        TestUtils.clickComponent(password, robot);
        TestUtils.tipeaTexto("admin", robot);

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

//TEST PANEL ADMINISTRADOR: Alta Chofer
    
    @Test
    public void testPanelAdm_AltaChofer_YaRegistrado()
    {
  		robot.delay(TestUtils.getDelay());
        JTextField campoDNI = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.DNI_CHOFER);
        JTextField campoNombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_CHOFER);
        JRadioButton radioPermanente = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PERMANENTE);
        JTextField campoCantHijos = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CH_CANT_HIJOS);
        JTextField campoAnio = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CH_ANIO);
        JButton altaChofer = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER);
        
        TestUtils.clickComponent(campoDNI, robot);
        TestUtils.tipeaTexto("11111111", robot);
        
        TestUtils.clickComponent(campoNombre, robot);
        TestUtils.tipeaTexto("ChoferP", robot);
        
        TestUtils.clickComponent(radioPermanente, robot);
        
        TestUtils.clickComponent(campoCantHijos, robot);
        TestUtils.tipeaTexto("3", robot);
        
        TestUtils.clickComponent(campoAnio, robot);
        TestUtils.tipeaTexto("2000", robot);
        
        TestUtils.clickComponent(altaChofer, robot);
        
        robot.delay(TestUtils.getDelay());
        Assert.assertEquals("Mensaje incorrecto, deberia decir: "+Mensajes.CHOFER_YA_REGISTRADO.getValor(), Mensajes.CHOFER_YA_REGISTRADO.getValor(),op.getMensaje());
    }
    
    @Test
    public void testPanelAdm_AltaChofer_YaRegistradoListaChoferesTotales()
    {
  		robot.delay(TestUtils.getDelay());
        JTextField campoDNI = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.DNI_CHOFER);
        JTextField campoNombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_CHOFER);
        JRadioButton radioPermanente = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PERMANENTE);
        JTextField campoCantHijos = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CH_CANT_HIJOS);
        JTextField campoAnio = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CH_ANIO);
        JButton altaChofer = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER);
        
        JList<?> choferesTotalesJList = (JList<?>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_TOTALES);
        Assert.assertTrue("El Listado de Choferes Totales no deberia estar vacio", choferesTotalesJList.getModel().getSize() > 0);
        int cantidadInicialJlist = choferesTotalesJList.getModel().getSize();
        
        TestUtils.clickComponent(campoDNI, robot);
        TestUtils.tipeaTexto("11111111", robot);
        
        TestUtils.clickComponent(campoNombre, robot);
        TestUtils.tipeaTexto("ChoferP", robot);
        
        TestUtils.clickComponent(radioPermanente, robot);
        
        TestUtils.clickComponent(campoCantHijos, robot);
        TestUtils.tipeaTexto("3", robot);
        
        TestUtils.clickComponent(campoAnio, robot);
        TestUtils.tipeaTexto("2000", robot);
        
        TestUtils.clickComponent(altaChofer, robot);
        
        robot.delay(TestUtils.getDelay());
        int cantidadFinalJlist = choferesTotalesJList.getModel().getSize();
        HashMap<String, Chofer>  listaChoferes = escenario.empresa.getChoferes();
        List<Chofer> listaChoferesJList = new ArrayList<>();
        for (int i = 0; i < choferesTotalesJList.getModel().getSize(); i++) {
        	listaChoferesJList.add((Chofer) choferesTotalesJList.getModel().getElementAt(i));
        }
        
        robot.delay(TestUtils.getDelay());
        Assert.assertEquals("La Lista de Choferes Totales en el JList y en el sistema no son iguales", new ArrayList<>(listaChoferes.values()), listaChoferesJList);
        Assert.assertEquals("La Lista de Choferes Totales en el JList ha aumentado, se ha registrado un Chofer REPETIDO", cantidadInicialJlist, cantidadFinalJlist);
    }
    
    @Test
    public void testPanelAdm_AltaChofer_YaRegistradoListaChoferesLibres()
    {
  		robot.delay(TestUtils.getDelay());
        JTextField campoDNI = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.DNI_CHOFER);
        JTextField campoNombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_CHOFER);
        JRadioButton radioPermanente = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PERMANENTE);
        JTextField campoCantHijos = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CH_CANT_HIJOS);
        JTextField campoAnio = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CH_ANIO);
        JButton altaChofer = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER);
        
        JList<?> choferesLibresJList = (JList<?>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_LIBRES);
        Assert.assertTrue("El Listado de Choferes Libres no deberia estar vacio", choferesLibresJList.getModel().getSize() > 0);
        int cantidadInicialJlist = choferesLibresJList.getModel().getSize();
        
        TestUtils.clickComponent(campoDNI, robot);
        TestUtils.tipeaTexto("11111111", robot);
        
        TestUtils.clickComponent(campoNombre, robot);
        TestUtils.tipeaTexto("ChoferP", robot);
        
        TestUtils.clickComponent(radioPermanente, robot);
        
        TestUtils.clickComponent(campoCantHijos, robot);
        TestUtils.tipeaTexto("3", robot);
        
        TestUtils.clickComponent(campoAnio, robot);
        TestUtils.tipeaTexto("2000", robot);
        
        TestUtils.clickComponent(altaChofer, robot);

        robot.delay(TestUtils.getDelay());
        int cantidadFinalJlist = choferesLibresJList.getModel().getSize();
        List<Chofer>  listaChoferes = escenario.empresa.getChoferesDesocupados();
        List<Chofer> listaChoferesJList = new ArrayList<>();
        for (int i = 0; i < choferesLibresJList.getModel().getSize(); i++) {
        	listaChoferesJList.add((Chofer) choferesLibresJList.getModel().getElementAt(i));
        }
        
        robot.delay(TestUtils.getDelay());
        Assert.assertEquals("La Lista de Choferes Libres en el JList y en el sistema no son iguales", listaChoferes, listaChoferesJList);
        Assert.assertEquals("La Lista de Choferes Libres en el JList ha aumentado, se ha registrado un Chofer REPETIDO", cantidadInicialJlist, cantidadFinalJlist);
    }
    
//TEST PANEL ADMINISTRADOR: Alta Vehiculo
   
    @Test
    public void testPanelAdm_AltaAuto_YaRegistrado()
    {
        robot.delay(TestUtils.getDelay());
        JTextField campoPatente = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PATENTE);
        JCheckBox checkBoxMascota = (JCheckBox) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CHECK_VEHICULO_ACEPTA_MASCOTA);
        JTextField campoCantPlazas = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CANTIDAD_PLAZAS);
        JButton altaVehiculo = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VEHICULO);
        
        TestUtils.clickComponent(campoPatente, robot);
        TestUtils.tipeaTexto("AAA111", robot);
        
        TestUtils.clickComponent(campoCantPlazas, robot);
        TestUtils.tipeaTexto("2", robot);
        
        TestUtils.clickComponent(altaVehiculo, robot);
        
        robot.delay(TestUtils.getDelay());
        Assert.assertEquals("Mensaje incorrecto, deberia decir: "+Mensajes.VEHICULO_YA_REGISTRADO.getValor(), Mensajes.VEHICULO_YA_REGISTRADO.getValor(),op.getMensaje());
    }
    
    @Test
    public void testPanelAdm_AltaAuto_YaRegistradoListaVehiculosTotales()
    {
        robot.delay(TestUtils.getDelay());
        JTextField campoPatente = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PATENTE);
        JTextField campoCantPlazas = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CANTIDAD_PLAZAS);
        JButton altaVehiculo = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VEHICULO);
        
        JList<?> vehiculosTotalesJList = (JList<?>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_TOTALES);
        Assert.assertTrue("El Listado de Vehiculos Totales no deberia estar vacio", vehiculosTotalesJList.getModel().getSize() > 0);
        int cantidadInicialJlist = vehiculosTotalesJList.getModel().getSize();
        
        TestUtils.clickComponent(campoPatente, robot);
        TestUtils.tipeaTexto("AAA111", robot);
        
        TestUtils.clickComponent(campoCantPlazas, robot);
        TestUtils.tipeaTexto("2", robot);
        
        TestUtils.clickComponent(altaVehiculo, robot);
        
        robot.delay(TestUtils.getDelay());
        int cantidadFinalJlist = vehiculosTotalesJList.getModel().getSize();
        HashMap<String, Vehiculo>  listaVehiculos = escenario.empresa.getVehiculos();
        List<Vehiculo> listaVehiculosJList = new ArrayList<>();
        for (int i = 0; i < vehiculosTotalesJList.getModel().getSize(); i++) {
        	listaVehiculosJList.add((Vehiculo) vehiculosTotalesJList.getModel().getElementAt(i));
        }
        
        robot.delay(TestUtils.getDelay());
        Assert.assertEquals("La Lista de Vehiculos Totales en el JList y en el sistema no son iguales", new ArrayList<>(listaVehiculos.values()), listaVehiculosJList);
        Assert.assertEquals("La Lista de Vehiculos Totales en el JList ha aumentado, se ha registrado un Vehiculo REPETIDO", cantidadInicialJlist, cantidadFinalJlist);
    }

}
