package modelo.negocio;

import modeloDatos.Chofer;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;

//  Este escenario configura un cliente con un viaje en curso, es decir, 
//  un pedido asignado a un chofer y vehículo

public class Escenario4 extends EscenarioBase {

    
    public Escenario4() {
        
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
    		
    		Chofer choferP1 = empresa.getChoferes().get("11111111");
    		Chofer choferP2 = empresa.getChoferes().get("22222222");
    		Chofer choferT1 = empresa.getChoferes().get("44444444");
    		Chofer choferT2 = empresa.getChoferes().get("55555555");
    		
    		Vehiculo auto1 = empresa.getVehiculos().get("AAA111");
    		Vehiculo auto2 = empresa.getVehiculos().get("BBB222");
    		Vehiculo combi1 = empresa.getVehiculos().get("DDD444");
    		Vehiculo moto1 = empresa.getVehiculos().get("GGG777");
        
            // Crear y realizar viajes completos para cada cliente
            realizarViaje(cliente1, choferP1, auto1, 3, true, false, 10, "ZONA_PELIGROSA");
            realizarViaje(cliente2, choferP2, combi1, 5, false, true, 20, "ZONA_SIN_ASFALTAR");
            realizarViaje(cliente3, choferT1, moto1, 1, false, false, 15, "ZONA_STANDARD");
            realizarViaje(cliente4, choferT2, auto2, 3, false, false, 15, "ZONA_STANDARD");
        
        } catch (Exception e) {
            System.out.println("Ocurrió un error al configurar el escenario: " + e.getMessage());
        }
    }

    private void realizarViaje(Cliente cliente, Chofer chofer, Vehiculo vehiculo, int cantidadPasajeros, boolean mascota, boolean baul, int km, String zona) {
        try {
            // Crear un pedido
            Pedido pedido = new Pedido(cliente, cantidadPasajeros, mascota, baul, km, zona);
            empresa.agregarPedido(pedido);
            
            // Crear un viaje
            empresa.crearViaje(pedido, chofer, vehiculo);
        } catch (Exception e) {
            System.out.println("Ocurrió un error al crear el viaje completo: " + e.getMessage());
        }
    }
    
    public void tearDown() {
        // Llama al tearDown de EscenarioBase para limpiar los datos compartidos
        super.tearDown();
    }
}
