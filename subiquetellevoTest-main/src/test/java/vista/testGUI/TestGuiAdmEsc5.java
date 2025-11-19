package vista.testGUI;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextField;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import excepciones.SinViajesException;
import junit.framework.Assert;
import modelo.negocio.Escenario5;
import modeloDatos.Chofer;
import modeloDatos.Cliente;
import modeloDatos.Vehiculo;
import modeloDatos.Viaje;
import util.Constantes;
import vista.TestUtils;
import vista.Ventana;

public class TestGuiAdmEsc5 {
	Robot robot;
    Controlador controlador;
    private Escenario5 escenario = new Escenario5(); 

    public TestGuiAdmEsc5()
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
 
  //PANEL ADMINISTRADOR: 
    
    @Test
    public void testPanelAdm_SueldosAPagar()  
    {
        robot.delay(TestUtils.getDelay());
        JTextField campoSueldosAPagar = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.TOTAL_SUELDOS_A_PAGAR);

        robot.delay(TestUtils.getDelay());
        Assert.assertFalse("El campo de Sueldos a Pagar no deberia estar vacio", campoSueldosAPagar.getText().isEmpty());
        
        double sueldosAPagar = escenario.empresa.getTotalSalarios();
        double sueldosEnPantalla = Double.parseDouble(campoSueldosAPagar.getText().replace(",", "."));
		Assert.assertEquals("El total de Sueldos a Pagar y el valor que figura en pantalla no iguales", sueldosAPagar,sueldosEnPantalla, 0.0001);
    }
    
//PANEL ADMINISTRADOR: Gestion de Choferes
      
    @Test
    public void testPanelAdm_GestionChoferes_TextFieldCalificacion()  
    {
        robot.delay(TestUtils.getDelay());
        JList<?> listaChoferesTotales = (JList<?>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_TOTALES);
        listaChoferesTotales.setSelectedIndex(0);
        TestUtils.clickComponent(listaChoferesTotales, robot);
        
        JTextField campoCalificacionChofer = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICACION_CHOFER);

        robot.delay(TestUtils.getDelay());
        Assert.assertFalse("El campo de Calificacion del Chofer no deberia estar vacio", campoCalificacionChofer.getText().isEmpty());
        
        Chofer primerChofer = (Chofer) listaChoferesTotales.getModel().getElementAt(0);
        double calificacion = 0;
		try {
			calificacion = escenario.empresa.calificacionDeChofer(primerChofer);
		} catch (SinViajesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertEquals("La calificacion promedio del chofer y la que figura en pantalla no son la misma", calificacion, Double.parseDouble(campoCalificacionChofer.getText()), 0.0001);
    }
    
    @Test
    public void testPanelAdm_GestionChoferes_TextFieldSueldo()  
    {
        robot.delay(TestUtils.getDelay());
        JList<?> listaChoferesTotales = (JList<?>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_TOTALES);
        listaChoferesTotales.setSelectedIndex(0);
        TestUtils.clickComponent(listaChoferesTotales, robot);
        
        JTextField campoSueldoChofer = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.SUELDO_DE_CHOFER);

        robot.delay(TestUtils.getDelay());
        Assert.assertFalse("El campo del Sueldo del Chofer no deberia estar vacio", campoSueldoChofer.getText().isEmpty());
        
        Chofer primerChofer = (Chofer) listaChoferesTotales.getModel().getElementAt(0);
        double sueldo = primerChofer.getSueldoNeto();
        double sueldoEnPantalla = Double.parseDouble(campoSueldoChofer.getText().replace(",", "."));
		Assert.assertEquals("La cifra del Sueldo del chofer y el que figura en pantalla, no coinciden", sueldo, sueldoEnPantalla, 0.0001);
    }
    
    @Test
    public void testPanelAdm_GestionChoferes_TextFieldSueldosTotales()  
    {
        robot.delay(TestUtils.getDelay());
        
        JTextField campoSueldosTotales = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.TOTAL_SUELDOS_A_PAGAR);

        robot.delay(TestUtils.getDelay());
        Assert.assertFalse("El campo de Sueldos a Pagar no deberia estar vacio", campoSueldosTotales.getText().isEmpty());
        
        double sueldos = escenario.empresa.getTotalSalarios();
        double sueldosEnPantalla = Double.parseDouble(campoSueldosTotales.getText().replace(",", "."));
		Assert.assertEquals("La cifra del Sueldo del chofer y el que figura en pantalla, no coinciden", sueldos, sueldosEnPantalla, 0.0001);
    }
    
    @Test
    public void testPanelAdm_GestionChoferes_ListaViajes()  
    {
        robot.delay(TestUtils.getDelay());
        JList<?> listaChoferesTotales = (JList<?>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_TOTALES);
        listaChoferesTotales.setSelectedIndex(0);
        TestUtils.clickComponent(listaChoferesTotales, robot);
        
        robot.delay(TestUtils.getDelay());
        JList<?> listaViajesHistoricos = (JList<?>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VIAJES_DE_CHOFER);
        Assert.assertTrue("El listado de Viajes Históricos del Chofer no deberia estar vacio", listaViajesHistoricos.getModel().getSize() > 0);
        
        Chofer primerChofer = (Chofer) listaChoferesTotales.getModel().getElementAt(0);
        ArrayList<Viaje> arrayViajes = escenario.empresa.getHistorialViajeChofer(primerChofer);
        List<Viaje> listaViajes = new ArrayList<>();
        for (int i = 0; i < listaViajesHistoricos.getModel().getSize(); i++) {
            listaViajes.add((Viaje) listaViajesHistoricos.getModel().getElementAt(i));
        }

        Assert.assertEquals("Los viajes historicos del chofer y los viajes en el JList no son iguales", arrayViajes, listaViajes);
    }
      
    @Test
    public void testPanelAdm_GestionChoferes_CalificacionSinSeleccionarChofer()  
    {
        robot.delay(TestUtils.getDelay());
        JTextField campoCalificacionChofer = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICACION_CHOFER);
        Assert.assertTrue("El campo de Calificacion del Chofer deberia estar vacio", campoCalificacionChofer.getText().isEmpty());
    }
    
    @Test
    public void testPanelAdm_GestionChoferes_SueldoSinSeleccionarChofer()  
    {
        robot.delay(TestUtils.getDelay());
        JTextField campoSueldo = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.SUELDO_DE_CHOFER);
        Assert.assertTrue("El campo de Sueldo del Chofer deberia estar vacio", campoSueldo.getText().isEmpty());
    }
    
    @Test
    public void testPanelAdm_GestionChoferes_ListaViajesSinSeleccionarChofer()  
    {
    	robot.delay(TestUtils.getDelay());
    	JList<?> listaViajesHistoricos = (JList<?>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VIAJES_DE_CHOFER);
    	Assert.assertTrue("La lista de Viajes Historicos del Chofer deberia estar vacia", listaViajesHistoricos.getModel().getSize() == 0);
    }
      
      
//PANEL ADMINISTRADOR: Listados
    
    @Test
    public void testPanelAdm_Listados_VerificoClientes()  
    {
        robot.delay(TestUtils.getDelay());
        JList<?> listadoClientes = (JList<?>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTADO_DE_CLIENTES);
        
        HashMap<String, Cliente> clientes = escenario.empresa.getClientes();
        
        List<Cliente> listaClientesJList = new ArrayList<>();
        for (int i = 0; i < listadoClientes.getModel().getSize(); i++) {
            listaClientesJList.add((Cliente) listadoClientes.getModel().getElementAt(i));
        }
        
        Assert.assertEquals("La lista de clientes en el JList y en el sistema no son iguales", new ArrayList<>(clientes.values()), listaClientesJList);
    }
    
    @Test
    public void testPanelAdm_Listados_VerificoVehiculos()  
    {
        robot.delay(TestUtils.getDelay());
        JList<?> listadoVehiculos = (JList<?>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_TOTALES);
        
        HashMap<String, Vehiculo> vehiculos = escenario.empresa.getVehiculos();
        
        List<Vehiculo> listaVehiculosJList = new ArrayList<>();
        for (int i = 0; i < listadoVehiculos.getModel().getSize(); i++) {
        	listaVehiculosJList.add((Vehiculo) listadoVehiculos.getModel().getElementAt(i));
        }
        
        Assert.assertEquals("La lista de Vehiculos en el JList y en el sistema no son iguales", new ArrayList<>(vehiculos.values()), listaVehiculosJList);
    }
    
    @Test
    public void testPanelAdm_Listados_VerificoViajesHistoricos()  
    {
        robot.delay(TestUtils.getDelay());
        JList<?> listadoViajesHistoricos = (JList<?>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VIAJES_HISTORICOS);
        
        ArrayList<Viaje> viajesHistoricos = escenario.empresa.getViajesTerminados();
        
        List<Viaje> listaViajesHistoricosJList = new ArrayList<>();
        for (int i = 0; i < listadoViajesHistoricos.getModel().getSize(); i++) {
        	listaViajesHistoricosJList.add((Viaje) listadoViajesHistoricos.getModel().getElementAt(i));
        }
        
        Assert.assertEquals("La lista de Vehiculos en el JList y en el sistema no son iguales", viajesHistoricos, listaViajesHistoricosJList);
    }

}
