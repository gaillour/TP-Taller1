package modelo.datos;


import org.junit.Assert;
import org.junit.Test;

import modeloDatos.Cliente;
import modeloDatos.Combi;
import modeloDatos.Pedido;

public class CombiTest {

	@Test
	public void testConstructorConMascota() {
	        Combi combi= new Combi("ABC123", 10, true);
	        int CantidadPlazas = combi.getCantidadPlazas();
	        String pat = combi.getPatente();
        
	        Assert.assertEquals("La patente esperada no es correcto", "ABC123", pat);
	        Assert.assertEquals("La CantidadPlazas esperada no es correcta", 10, CantidadPlazas);
	        Assert.assertEquals("El valor de verdad esperado no es correcta", true, combi.isMascota());
}
	@Test
	public void testConstructoSinMascota() {
	        Combi combi= new Combi("ABC123", 10, false);
	        int CantidadPlazas = combi.getCantidadPlazas();
	        String pat = combi.getPatente();
        
	        Assert.assertEquals("La patente esperada no es correcto", "ABC123", pat);
	        Assert.assertEquals("La CantidadPlazas esperada no es correcta", 10, CantidadPlazas);
	        Assert.assertEquals("El valor de verdad esperado no es correcta", false, combi.isMascota());
}
}
