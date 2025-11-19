package modelo.negocio;

import modeloNegocio.Empresa;

//  Este escenario configura varios clientes, choferes y vehículos en el sistema, sin pedidos ni viajes
public class Escenario2 extends EscenarioBase {

    
    public Escenario2() {
    }

    public void setUp() 
    {
    	empresa = Empresa.getInstance();
    	elementosBaseEmpresa();
    }

    public void tearDown() {
        // Llama al tearDown de EscenarioBase para limpiar los datos compartidos
        super.tearDown();
    }
}
