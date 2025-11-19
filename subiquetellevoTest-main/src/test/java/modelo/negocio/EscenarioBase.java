package modelo.negocio;

import modeloDatos.Auto;
import modeloDatos.ChoferPermanente;
import modeloDatos.ChoferTemporario;
import modeloDatos.Combi;
import modeloDatos.Moto;
import modeloNegocio.Empresa;

//ESTE SERIA EL ESCENARIO VACIO
public class EscenarioBase {
    
    public Empresa empresa;
    
    public EscenarioBase() 
    {
    	empresa = Empresa.getInstance();
    }

    public void addChoferPermanente(String dni, String nombre, int anioIngreso, int cantidadHijos) {
        try {
            // Crear y agregar chofer permanente a la empresa
            ChoferPermanente chofer = new ChoferPermanente(dni, nombre, anioIngreso, cantidadHijos);
            empresa.agregarChofer(chofer);
        } catch (Exception e) {
            System.out.println("Ocurrió un error al agregar el chofer: " + e.getMessage());
        }
    }

    public void addChoferTemporario(String dni, String nombre) {
        try {
            // Crear y agregar chofer temporario a la empresa
            ChoferTemporario chofer = new ChoferTemporario(dni, nombre);
            empresa.agregarChofer(chofer);
        } catch (Exception e) {
            System.out.println("Ocurrió un error al agregar el chofer temporario: " + e.getMessage());
        }
    }
 
    public void elementosBaseEmpresa() {
    	try {
            // Crear y agregar choferes permanentes usando el método addChoferPermanente
    		addChoferPermanente("11111111", "Chofer Permanente 1", 2010, 2);
            addChoferPermanente("22222222", "Chofer Permanente 2", 2015, 1);
            addChoferPermanente("33333333", "Chofer Permanente 3", 2005, 3);
        
            // Crear y agregar choferes temporarios usando el método addChoferTemporario
            addChoferTemporario("44444444", "Chofer Temporario 1");
            addChoferTemporario("55555555", "Chofer Temporario 2");
            addChoferTemporario("66666666", "Chofer Temporario 3");
            addChoferTemporario("77777777", "Chofer Temporario 4");
            
            // Crear y agregar clientes usando el método addCliente
            empresa.agregarCliente("user1", "pass1", "Cliente 1");
            empresa.agregarCliente("user2", "pass2", "Cliente 2");
            empresa.agregarCliente("user3", "pass3", "Cliente 3");
            empresa.agregarCliente("user4", "pass4", "Cliente 4");
            empresa.agregarCliente("user5", "pass5", "Cliente 5");
            
            // Crear y agregar vehículos (3 autos)
            Auto auto1 = new Auto("AAA111", 4, true);
            Auto auto2 = new Auto("BBB222", 3, false);
            Auto auto3 = new Auto("CCC333", 2, true);
        
            empresa.agregarVehiculo(auto1);
            empresa.agregarVehiculo(auto2);
            empresa.agregarVehiculo(auto3);
        
            // Crear y agregar vehículos (3 combis)
            Combi combi1 = new Combi("DDD444", 9, true);
            Combi combi2 = new Combi("EEE555", 8, true);
            Combi combi3 = new Combi("FFF666", 6, false);
        
            empresa.agregarVehiculo(combi1);
            empresa.agregarVehiculo(combi2);
            empresa.agregarVehiculo(combi3);
        
            // Crear y agregar vehículos (3 motos)
            Moto moto1 = new Moto("GGG777");
            Moto moto2 = new Moto("HHH888");
            Moto moto3 = new Moto("III999");
        
            empresa.agregarVehiculo(moto1);
            empresa.agregarVehiculo(moto2);
            empresa.agregarVehiculo(moto3);
        
        } catch (Exception e) {
            System.out.println("Ocurrió un error al configurar el escenario: " + e.getMessage());
        }
    }
    
    public void setUp() 
    {
    	empresa = Empresa.getInstance();
    }

    public void tearDown() 
    {
    	
    	empresa.getChoferes().clear();     
        empresa.getChoferesDesocupados().clear();   
        empresa.getClientes().clear();           
        empresa.getVehiculos().clear();        
        empresa.getVehiculosDesocupados().clear();  
        empresa.getPedidos().clear();           
        empresa.getViajesIniciados().clear();       
        empresa.getViajesTerminados().clear();
        
    }
}