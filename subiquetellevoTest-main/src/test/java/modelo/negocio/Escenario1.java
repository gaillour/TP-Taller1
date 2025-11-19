package modelo.negocio;


//  Este escenario configura varios clientes en el sistema sin pedidos ni viajes
public class Escenario1 extends EscenarioBase{
    
    
    public Escenario1() {
    	super();
    }

    public void setUp() 
    {

        // Agregamos varios clientes
        try {
            empresa.agregarCliente("user1", "pass1", "Cliente 1");
            empresa.agregarCliente("user2", "pass2", "Cliente 2");
            empresa.agregarCliente("user3", "pass3", "Cliente 3");
            empresa.agregarCliente("user4", "pass3", "Cliente 4");
        } catch (Exception e) {
        	System.out.println("Ocurrió un error al configurar el escenario: " + e.getMessage());
        }
    }
    
    public void tearDown() {
        // Llama al tearDown de EscenarioBase para limpiar los datos compartidos
        super.tearDown();
    }
}