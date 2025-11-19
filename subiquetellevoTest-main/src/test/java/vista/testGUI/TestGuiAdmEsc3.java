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
import junit.framework.Assert;
import modelo.negocio.Escenario3;
import modeloDatos.Chofer;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import util.Constantes;
import vista.FalseOptionPanel;
import vista.TestUtils;
import vista.Ventana;

public class TestGuiAdmEsc3 {
	Robot robot;
    Controlador controlador;
    FalseOptionPanel op = new FalseOptionPanel();
    private Escenario3 escenario = new Escenario3(); 

    public TestGuiAdmEsc3()
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

//TEST PANEL ADMINISTRADOR: Gestion de Pedidos
    
    @Test
    public void testPanelAdm_GestionPedidos_ListaPedidosPendientes()
    {
    	robot.delay(TestUtils.getDelay());
        JList<?> pedidosPendientesJList = (JList<?>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_PEDIDOS_PENDIENTES);
        Assert.assertTrue("El Listado de Pedidos Pendientes no deberia estar vacio", pedidosPendientesJList.getModel().getSize() > 0);
        
        HashMap<Cliente, Pedido>  listaPedidos = escenario.empresa.getPedidos();
        List<Pedido> listaPedidosJList = new ArrayList<>();
        for (int i = 0; i < pedidosPendientesJList.getModel().getSize(); i++) {
        	listaPedidosJList.add((Pedido) pedidosPendientesJList.getModel().getElementAt(i));
        }
        
        robot.delay(TestUtils.getDelay());
        Assert.assertEquals("La Lista de Pedidos Pendientes en el JList y en el sistema no son iguales", new ArrayList<>(listaPedidos.values()), listaPedidosJList);
    }
    
    @Test
    public void testPanelAdm_GestionPedidos_ListaChoferesLibres()
    {
    	robot.delay(TestUtils.getDelay());
        JList<?> listaChoferesLibresJList = (JList<?>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_LIBRES);
        Assert.assertTrue("El Listado de Choferes Libres no deberia estar vacio", listaChoferesLibresJList.getModel().getSize() > 0);
        
        ArrayList<Chofer> choferesLibres = escenario.empresa.getChoferesDesocupados();
        List<Chofer> choferesLibresJList = new ArrayList<>();
        for (int i = 0; i < listaChoferesLibresJList.getModel().getSize(); i++) {
        	choferesLibresJList.add((Chofer) listaChoferesLibresJList.getModel().getElementAt(i));
        }
        
        robot.delay(TestUtils.getDelay());
        Assert.assertEquals("La Lista de Choferes Libres en el JList y en el sistema no son iguales", choferesLibres, choferesLibresJList);
    }

    //APARECEN VEHICULOS QUE NO SATISFACEN EL PEDIDO
    @Test
    public void testPanelAdm_GestionPedidos_VehiculosDisponibles()
    {
    	robot.delay(TestUtils.getDelay());
    	JList<?> pedidosPendientesJList = (JList<?>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_PEDIDOS_PENDIENTES);
        JList<?> vehiculosDisponiblesJList = (JList<?>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_DISPONIBLES);
        Assert.assertTrue("El Listado de Vehiculos Disponibles deberia estar vacio", vehiculosDisponiblesJList.getModel().getSize() == 0);
        
        pedidosPendientesJList.setSelectedIndex(0);
        TestUtils.clickComponent(pedidosPendientesJList, robot);
        
        robot.delay(TestUtils.getDelay());
        ArrayList<Vehiculo>  listaVehiculosDesocupados = escenario.empresa.vehiculosOrdenadosPorPedido((Pedido) pedidosPendientesJList.getModel().getElementAt(0));
        
        List<Vehiculo> listaVehiculosJList = new ArrayList<>();
        for (int i = 0; i < vehiculosDisponiblesJList.getModel().getSize(); i++) {
        	listaVehiculosJList.add((Vehiculo) vehiculosDisponiblesJList.getModel().getElementAt(i));
        }
        
        robot.delay(TestUtils.getDelay());
        Assert.assertEquals("La Lista de Vehiculos Disponibles en el JList y en el sistema no son iguales", listaVehiculosDesocupados, listaVehiculosJList);
    }
    
    @Test
    public void testPanelAdm_GestionPedidos_ListaPedidosPendientes_After()
    {
    	robot.delay(TestUtils.getDelay());
        JList<?> pedidosPendientesJList = (JList<?>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_PEDIDOS_PENDIENTES);
        JList<?> choferesLibresJList = (JList<?>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_LIBRES);
        JList<?> vehiculosDisponiblesJList = (JList<?>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_DISPONIBLES);
        JButton nuevoViaje = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VIAJE);

        pedidosPendientesJList.setSelectedIndex(0);
        choferesLibresJList.setSelectedIndex(0);
        vehiculosDisponiblesJList.setSelectedIndex(0);
        
        Pedido pedido = (Pedido) pedidosPendientesJList.getModel().getElementAt(0);
        
        TestUtils.clickComponent(pedidosPendientesJList, robot);
        TestUtils.clickComponent(choferesLibresJList, robot);
        TestUtils.clickComponent(vehiculosDisponiblesJList, robot);
        TestUtils.clickComponent(nuevoViaje, robot);
        
        robot.delay(TestUtils.getDelay());
        HashMap<Cliente, Pedido>  listaPedidos = escenario.empresa.getPedidos();
        List<Pedido> listaPedidosJList = new ArrayList<>();
        for (int i = 0; i < pedidosPendientesJList.getModel().getSize(); i++) {
        	listaPedidosJList.add((Pedido) pedidosPendientesJList.getModel().getElementAt(i));
        }
        
        robot.delay(TestUtils.getDelay());
        Assert.assertEquals("Despues de crear el Nuevo Viaje, la Lista de Pedidos Pendientes en el JList y en el sistema no son iguales", new ArrayList<>(listaPedidos.values()), listaPedidosJList);
        Assert.assertFalse("El Pedido no deberia seguir figurando en el JList",listaPedidosJList.contains(pedido));
    }
    
    @Test
    public void testPanelAdm_GestionPedidos_ListaChoferesLibres_After()
    {
    	robot.delay(TestUtils.getDelay());
        JList<?> pedidosPendientesJList = (JList<?>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_PEDIDOS_PENDIENTES);
        JList<?> choferesLibresJList = (JList<?>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_LIBRES);
        JList<?> vehiculosDisponiblesJList = (JList<?>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_DISPONIBLES);
        JButton nuevoViaje = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VIAJE);

        pedidosPendientesJList.setSelectedIndex(0);
        choferesLibresJList.setSelectedIndex(0);
        vehiculosDisponiblesJList.setSelectedIndex(0);
        
        Chofer chofer = (Chofer) choferesLibresJList.getModel().getElementAt(0);
        
        TestUtils.clickComponent(pedidosPendientesJList, robot);
        TestUtils.clickComponent(choferesLibresJList, robot);
        TestUtils.clickComponent(vehiculosDisponiblesJList, robot);
        TestUtils.clickComponent(nuevoViaje, robot);
        
        robot.delay(TestUtils.getDelay());
        ArrayList<Chofer> choferesLibres = escenario.empresa.getChoferesDesocupados();
        List<Chofer> listaChoferesLibresJList = new ArrayList<>();
        for (int i = 0; i < choferesLibresJList.getModel().getSize(); i++) {
        	listaChoferesLibresJList.add((Chofer) choferesLibresJList.getModel().getElementAt(i));
        }
        
        robot.delay(TestUtils.getDelay());
        Assert.assertEquals("Despues de crear el Nuevo Viaje, la Lista de Choferes Libres en el JList y en el sistema no son iguales", choferesLibres, listaChoferesLibresJList);
        Assert.assertFalse("El Chofer no deberia seguir figurando en el JList",listaChoferesLibresJList.contains(chofer));
    }
    
    @Test
    public void testPanelAdm_GestionPedidos_VehiculosDisponibles_After()
    {
    	robot.delay(TestUtils.getDelay());
        JList<?> pedidosPendientesJList = (JList<?>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_PEDIDOS_PENDIENTES);
        JList<?> choferesLibresJList = (JList<?>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_LIBRES);
        JList<?> vehiculosDisponiblesJList = (JList<?>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_DISPONIBLES);
        JButton nuevoViaje = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VIAJE);

        pedidosPendientesJList.setSelectedIndex(0);
        choferesLibresJList.setSelectedIndex(0);
        vehiculosDisponiblesJList.setSelectedIndex(0);
        
        TestUtils.clickComponent(pedidosPendientesJList, robot);
        TestUtils.clickComponent(choferesLibresJList, robot);
        TestUtils.clickComponent(vehiculosDisponiblesJList, robot);
        TestUtils.clickComponent(nuevoViaje, robot);
        
        robot.delay(TestUtils.getDelay());
        Assert.assertEquals("La lista de vehiculos disponibles deberia estar vacia", 0, vehiculosDisponiblesJList.getModel().getSize());
    }
    
}
