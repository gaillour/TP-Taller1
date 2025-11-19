package vista.testGUI;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import junit.framework.Assert;
import modelo.negocio.EscenarioBase;
import modeloDatos.Chofer;
import modeloDatos.Vehiculo;
import util.Constantes;
import vista.FalseOptionPanel;
import vista.TestUtils;
import vista.Ventana;

public class TestGuiAdmEscVacio {
	Robot robot;
    Controlador controlador;
    FalseOptionPanel op = new FalseOptionPanel();
    private EscenarioBase escenario = new EscenarioBase(); 

    public TestGuiAdmEscVacio()
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

//TEST PANEL LOGIN:
    
    @Test
    public void testPanelLogin_CorrectoAdm()
    {
        robot.delay(TestUtils.getDelay());
        JPanel panelAdm = (JPanel) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PANEL_ADMINISTRADOR);
        Assert.assertTrue("El panel de Administrador deberia estar visible despues de un login exitoso", panelAdm.isVisible());
        Assert.assertEquals("Deberia coincidir el nombre de usuario con el nombre ingresado", "admin", escenario.empresa.getUsuarioLogeado().getNombreUsuario()) ;
    }
    
    @Test
    public void testPanelLogin_CerrarSesionAdm()
    {
    	robot.delay(TestUtils.getDelay());
    	JButton cerrarSesion = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CERRAR_SESION_ADMIN);
    	TestUtils.clickComponent(cerrarSesion, robot);
    	
    	JPanel panelLogin = (JPanel) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PANEL_LOGIN);
        Assert.assertTrue("El panel de Login deberia estar visible despues de cerrar sesion", panelLogin.isVisible());
        Assert.assertEquals("No deberia haber ningun usuario logueado", null, escenario.empresa.getUsuarioLogeado()) ;
    }
    
//TEST PANEL ADMINISTRADOR: Alta Chofer

    @Test
    public void testPanelAdm_AltaChofer_TexFieldDNI()
    {
  		robot.delay(TestUtils.getDelay());
        JTextField campoDNI = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.DNI_CHOFER);
        JTextField campoNombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_CHOFER);
        JRadioButton radioPermanente = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PERMANENTE);
        JTextField campoCantHijos = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CH_CANT_HIJOS);
        JTextField campoAnio = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CH_ANIO);
        JButton altaChofer = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER);
        
        TestUtils.clickComponent(campoDNI, robot);
        TestUtils.tipeaTexto("123456789", robot);
        
        TestUtils.clickComponent(campoNombre, robot);
        TestUtils.tipeaTexto("ChoferP", robot);
        
        TestUtils.clickComponent(radioPermanente, robot);
        
        TestUtils.clickComponent(campoCantHijos, robot);
        TestUtils.tipeaTexto("3", robot);
        
        TestUtils.clickComponent(campoAnio, robot);
        TestUtils.tipeaTexto("2000", robot);
        
        TestUtils.clickComponent(altaChofer, robot);
        
        robot.delay(TestUtils.getDelay());
        Assert.assertTrue("El campo DNI del Chofer deberia vaciarse", campoDNI.getText().isEmpty());
    }
    
    @Test
    public void testPanelAdm_AltaChofer_TexFieldNombreChofer()
    {
  		robot.delay(TestUtils.getDelay());
        JTextField campoDNI = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.DNI_CHOFER);
        JTextField campoNombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_CHOFER);
        JRadioButton radioPermanente = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PERMANENTE);
        JTextField campoCantHijos = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CH_CANT_HIJOS);
        JTextField campoAnio = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CH_ANIO);
        JButton altaChofer = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER);
        
        TestUtils.clickComponent(campoDNI, robot);
        TestUtils.tipeaTexto("123456789", robot);
        
        TestUtils.clickComponent(campoNombre, robot);
        TestUtils.tipeaTexto("ChoferP", robot);
        
        TestUtils.clickComponent(radioPermanente, robot);
        
        TestUtils.clickComponent(campoCantHijos, robot);
        TestUtils.tipeaTexto("3", robot);
        
        TestUtils.clickComponent(campoAnio, robot);
        TestUtils.tipeaTexto("2000", robot);
        
        TestUtils.clickComponent(altaChofer, robot);
        
        robot.delay(TestUtils.getDelay());
        Assert.assertTrue("El campo del Nombre del Chofer deberia vaciarse", campoNombre.getText().isEmpty());
    }
    
    @Test
    public void testPanelAdm_AltaChofer_TexFieldCantHijos()
    {
  		robot.delay(TestUtils.getDelay());
        JTextField campoDNI = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.DNI_CHOFER);
        JTextField campoNombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_CHOFER);
        JRadioButton radioPermanente = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PERMANENTE);
        JTextField campoCantHijos = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CH_CANT_HIJOS);
        JTextField campoAnio = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CH_ANIO);
        JButton altaChofer = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER);
        
        TestUtils.clickComponent(campoDNI, robot);
        TestUtils.tipeaTexto("123456789", robot);
        
        TestUtils.clickComponent(campoNombre, robot);
        TestUtils.tipeaTexto("ChoferP", robot);
        
        TestUtils.clickComponent(radioPermanente, robot);
        
        TestUtils.clickComponent(campoCantHijos, robot);
        TestUtils.tipeaTexto("3", robot);
        
        TestUtils.clickComponent(campoAnio, robot);
        TestUtils.tipeaTexto("2000", robot);
        
        TestUtils.clickComponent(altaChofer, robot);
        
        robot.delay(TestUtils.getDelay());
        Assert.assertTrue("El campo Cantidad de Hijos del Chofer deberia vaciarse", campoNombre.getText().isEmpty());
    }
    
    @Test
    public void testPanelAdm_AltaChofer_TexFieldAnioDeIngreso()
    {
  		robot.delay(TestUtils.getDelay());
        JTextField campoDNI = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.DNI_CHOFER);
        JTextField campoNombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_CHOFER);
        JRadioButton radioPermanente = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PERMANENTE);
        JTextField campoCantHijos = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CH_CANT_HIJOS);
        JTextField campoAnio = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CH_ANIO);
        JButton altaChofer = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER);
        
        TestUtils.clickComponent(campoDNI, robot);
        TestUtils.tipeaTexto("123456789", robot);
        
        TestUtils.clickComponent(campoNombre, robot);
        TestUtils.tipeaTexto("ChoferP", robot);
        
        TestUtils.clickComponent(radioPermanente, robot);
        
        TestUtils.clickComponent(campoCantHijos, robot);
        TestUtils.tipeaTexto("3", robot);
        
        TestUtils.clickComponent(campoAnio, robot);
        TestUtils.tipeaTexto("2000", robot);
        
        TestUtils.clickComponent(altaChofer, robot);
        
        robot.delay(TestUtils.getDelay());
        Assert.assertTrue("El campo del Año de Ingreso del Chofer deberia vaciarse", campoNombre.getText().isEmpty());
    }
    
    @Test
    public void testPanelAdm_AltaChofer_ListaChoferesTotales()
    {
  		robot.delay(TestUtils.getDelay());
        JTextField campoDNI = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.DNI_CHOFER);
        JTextField campoNombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_CHOFER);
        JRadioButton radioPermanente = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PERMANENTE);
        JTextField campoCantHijos = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CH_CANT_HIJOS);
        JTextField campoAnio = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CH_ANIO);
        JButton altaChofer = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER);
        
        JList<?> choferesTotalesJList = (JList<?>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_TOTALES);
        Assert.assertTrue("El Listado de Choferes Totales deberia estar vacio", choferesTotalesJList.getModel().getSize() == 0);
        
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
        HashMap<String, Chofer>  listaChoferes = escenario.empresa.getChoferes();
        List<Chofer> listaChoferesJList = new ArrayList<>();
        for (int i = 0; i < choferesTotalesJList.getModel().getSize(); i++) {
        	listaChoferesJList.add((Chofer) choferesTotalesJList.getModel().getElementAt(i));
        }
        
        robot.delay(TestUtils.getDelay());
        Assert.assertEquals("La Lista de Choferes Totales en el JList y en el sistema no son iguales", new ArrayList<>(listaChoferes.values()), listaChoferesJList);
    }
    
    @Test
    public void testPanelAdm_AltaChofer_ListaChoferesLibres()
    {
  		robot.delay(TestUtils.getDelay());
        JTextField campoDNI = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.DNI_CHOFER);
        JTextField campoNombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_CHOFER);
        JRadioButton radioPermanente = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PERMANENTE);
        JTextField campoCantHijos = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CH_CANT_HIJOS);
        JTextField campoAnio = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CH_ANIO);
        JButton altaChofer = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER);
        
        JList<?> choferesLibresJList = (JList<?>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_LIBRES);
        Assert.assertTrue("El Listado de Choferes Libres deberia estar vacio", choferesLibresJList.getModel().getSize() == 0);
        
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
        List<Chofer>  listaChoferes = escenario.empresa.getChoferesDesocupados();
        List<Chofer> listaChoferesJList = new ArrayList<>();
        for (int i = 0; i < choferesLibresJList.getModel().getSize(); i++) {
        	listaChoferesJList.add((Chofer) choferesLibresJList.getModel().getElementAt(i));
        }
        
        robot.delay(TestUtils.getDelay());
        Assert.assertEquals("La Lista de Choferes Libres en el JList y en el sistema no son iguales", listaChoferes, listaChoferesJList);
    }
  
//TEST PANEL ADMINISTRADOR: Alta Vehiculo
    
    @Test
    public void testPanelAdm_AltaVehiculo_TextFieldPatente()
    {
        robot.delay(TestUtils.getDelay());
        JTextField campoPatente = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PATENTE);
        JTextField campoCantPlazas = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CANTIDAD_PLAZAS);
        JButton altaVehiculo = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VEHICULO);
        
        TestUtils.clickComponent(campoPatente, robot);
        TestUtils.tipeaTexto("a", robot);
        
        TestUtils.clickComponent(campoCantPlazas, robot);
        TestUtils.tipeaTexto("2", robot);
        
        TestUtils.clickComponent(altaVehiculo, robot);
        
        robot.delay(10000);
        Assert.assertTrue("El campo Patente deberia estar vacio", campoPatente.getText().isEmpty());
    }
    
    @Test
    public void testPanelAdm_AltaVehiculo_TextFieldCantPlazas()
    {
        robot.delay(TestUtils.getDelay());
        JTextField campoPatente = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PATENTE);
        JTextField campoCantPlazas = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CANTIDAD_PLAZAS);
        JButton altaVehiculo = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VEHICULO);
        
        TestUtils.clickComponent(campoPatente, robot);
        TestUtils.tipeaTexto("a", robot);
        
        TestUtils.clickComponent(campoCantPlazas, robot);
        TestUtils.tipeaTexto("2", robot);
        
        TestUtils.clickComponent(altaVehiculo, robot);
        
        robot.delay(TestUtils.getDelay());
        Assert.assertTrue("El campo Cantidad de Plazas deberia estar vacio", campoCantPlazas.getText().isEmpty());
    }
    
    @Test
    public void testPanelAdm_AltaVehiculo_ListaVehiculosTotales()
    {
        robot.delay(TestUtils.getDelay());
        JTextField campoPatente = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PATENTE);
        JTextField campoCantPlazas = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CANTIDAD_PLAZAS);
        JButton altaVehiculo = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VEHICULO);
        
        JList<?> vehiculosTotalesJList = (JList<?>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_TOTALES);
        Assert.assertTrue("El Listado de Vehiculos Totales deberia estar vacio", vehiculosTotalesJList.getModel().getSize() == 0);
        
        TestUtils.clickComponent(campoPatente, robot);
        TestUtils.tipeaTexto("a", robot);
        
        TestUtils.clickComponent(campoCantPlazas, robot);
        TestUtils.tipeaTexto("2", robot);
        
        TestUtils.clickComponent(altaVehiculo, robot);
        
        robot.delay(TestUtils.getDelay());
        HashMap<String, Vehiculo>  listaVehiculos = escenario.empresa.getVehiculos();
        List<Vehiculo> listaVehiculosJList = new ArrayList<>();
        for (int i = 0; i < vehiculosTotalesJList.getModel().getSize(); i++) {
        	listaVehiculosJList.add((Vehiculo) vehiculosTotalesJList.getModel().getElementAt(i));
        }
        
        robot.delay(TestUtils.getDelay());
        Assert.assertEquals("La Lista de Vehiculos Totales en el JList y en el sistema no son iguales", new ArrayList<>(listaVehiculos.values()), listaVehiculosJList);
    }

}
