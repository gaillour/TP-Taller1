package modelo.negocio;

import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloNegocio.Empresa;

//  Este escenario configura clientes con pedidos que aún no han sido asignados a choferes o vehículos
public class Escenario3 extends EscenarioBase {
    
    
    public Escenario3() {
    }

    public void setUp() 
    {
    	empresa = Empresa.getInstance();
		elementosBaseEmpresa();
		
    	try {
    		Cliente cliente1 = empresa.getClientes().get("user1");
    		Cliente cliente2 = empresa.getClientes().get("user2");
    		Cliente cliente3 = empresa.getClientes().get("user3");
    		Cliente cliente4 = empresa.getClientes().get("user4");
    		Cliente cliente5 = empresa.getClientes().get("user5");
        
    		// Crear y realizar viajes completos para cada cliente
            realizarPedido(cliente1, 3, true, false, 10, "ZONA_PELIGROSA");
            realizarPedido(cliente2, 5, false, true, 20, "ZONA_SIN_ASFALTAR");
            realizarPedido(cliente3, 1, false, false, 15, "ZONA_STANDARD");
            realizarPedido(cliente4, 4, false, false, 15, "ZONA_STANDARD");
        
        } catch (Exception e) {
            System.out.println("Ocurrió un error al configurar el escenario: " + e.getMessage());
        }
    }

    private void realizarPedido(Cliente cliente, int cantidadPasajeros, boolean mascota, boolean baul, int km, String zona) {
        try {
            Pedido pedido = new Pedido(cliente, cantidadPasajeros, mascota, baul, km, zona);
            empresa.agregarPedido(pedido);
        } catch (Exception e) {
            System.out.println("Ocurrió un error al realizar el pedido completo: " + e.getMessage());
        }
    }
    
    public void tearDown() {
        // Llama al tearDown de EscenarioBase para limpiar los datos compartidos
        super.tearDown();
    }
}
