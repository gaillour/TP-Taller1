package modelo.negocio;

import modeloDatos.Chofer;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;

//  Este escenario configura varios clientes, choferes y vehículos en el sistema, 
//  y realiza viajes completos para cada cliente
public class Escenario5 extends EscenarioBase {

    
    public Escenario5() {
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
    		Chofer choferP3 = empresa.getChoferes().get("33333333");
    		Chofer choferT1 = empresa.getChoferes().get("44444444");
    		Chofer choferT2 = empresa.getChoferes().get("55555555");
    		Chofer choferT3 = empresa.getChoferes().get("66666666");
            
            Vehiculo auto1 = empresa.getVehiculos().get("AAA111");
            Vehiculo auto2 = empresa.getVehiculos().get("BBB222");
            Vehiculo auto3 = empresa.getVehiculos().get("CCC333");
        
            Vehiculo combi1 = empresa.getVehiculos().get("DDD444");
            Vehiculo combi2 = empresa.getVehiculos().get("EEE555");
            Vehiculo combi3 = empresa.getVehiculos().get("FFF666");
        
            Vehiculo moto1 = empresa.getVehiculos().get("GGG777");
            Vehiculo moto2 = empresa.getVehiculos().get("HHH888");
            Vehiculo moto3 = empresa.getVehiculos().get("III999");
        
         // Crear y realizar viajes completos para cada cliente
            realizarViajeCompleto(cliente1, choferP1, auto1, 3, true, false, 10, "ZONA_PELIGROSA", 5);
            realizarViajeCompleto(cliente1, choferP2, combi1, 5, false, true, 20, "ZONA_SIN_ASFALTAR", 4);
            realizarViajeCompleto(cliente1, choferT1, moto1, 1, false, false, 15, "ZONA_STANDARD", 3);
        
            realizarViajeCompleto(cliente2, choferP2, auto2, 3, false, false, 25, "ZONA_PELIGROSA", 5);
            realizarViajeCompleto(cliente2, choferP3, combi2, 6, false, true, 30, "ZONA_SIN_ASFALTAR", 4);
            realizarViajeCompleto(cliente2, choferT2, moto2, 1, false, false, 18, "ZONA_STANDARD", 5);
        
            realizarViajeCompleto(cliente3, choferP3, auto3, 2, true, true, 8, "ZONA_PELIGROSA", 3);
            realizarViajeCompleto(cliente3, choferP1, combi3, 7, false, false, 22, "ZONA_SIN_ASFALTAR", 4);
            realizarViajeCompleto(cliente3, choferT3, moto3, 1, false, false, 12, "ZONA_STANDARD", 5);
        
            realizarViajeCompleto(cliente4, choferP1, auto1, 3, false, true, 20, "ZONA_PELIGROSA", 5);
            realizarViajeCompleto(cliente4, choferP2, combi1, 5, true, false, 28, "ZONA_SIN_ASFALTAR", 4);
            realizarViajeCompleto(cliente4, choferT1, moto1, 1, false, false, 14, "ZONA_STANDARD", 5);
        } catch (Exception e) {
            System.out.println("Ocurrió un error al configurar el escenario: " + e.getMessage());
        }
    }

    private void realizarViajeCompleto(Cliente cliente, Chofer chofer, Vehiculo vehiculo, int cantidadPasajeros, boolean mascota, boolean baul, int km, String zona, int calificacion) {
        try {
            // Crear un pedido
            Pedido pedido = new Pedido(cliente, cantidadPasajeros, mascota, baul, km, zona);
            empresa.agregarPedido(pedido);
            
            // Crear un viaje
            empresa.crearViaje(pedido, chofer, vehiculo);
            
            // Loguear al cliente para indicar que es el cliente activo
            empresa.setUsuarioLogeado(cliente);
            
            // Pagar y finalizar el viaje con la calificación
            empresa.pagarYFinalizarViaje(calificacion);
        } catch (Exception e) {
            System.out.println("Ocurrió un error al realizar el viaje completo: " + e.getMessage() + cliente.toString() + vehiculo.toString() + chofer.toString());
        }
    }
    
    public void tearDown() {
        // Llama al tearDown de EscenarioBase para limpiar los datos compartidos
        super.tearDown();
    }
}