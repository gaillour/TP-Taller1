package modelo.datos;

import org.junit.Assert;
import org.junit.Test;

import modeloDatos.Auto;
import modeloDatos.Cliente;
import modeloDatos.Pedido;


public class AutoConstructorTest {

	@Test
	public void testConstructorSinMascota() {
	        Auto auto= new Auto("ABC123", 4, false);
	        int CantidadPlazas = auto.getCantidadPlazas();
	        String pat = auto.getPatente();
            
	        Assert.assertEquals("La patente esperada no es correcto", "ABC123", pat);
	        Assert.assertEquals("La CantidadPlazas esperada no es correcta", 4, CantidadPlazas);
	        Assert.assertEquals("El valor de verdad esperado no es correcta", false, auto.isMascota());
	}
	
	@Test
	public void testConstructorConMascota() {
	        Auto auto= new Auto("ABC123", 4, true);
	        int CantidadPlazas = auto.getCantidadPlazas();
	        String pat = auto.getPatente();
            
	        Assert.assertEquals("La patente esperada no es correcto", "ABC123", pat);
	        Assert.assertEquals("La CantidadPlazas esperada no es correcta", 4, CantidadPlazas);
	        Assert.assertEquals("El valor de verdad esperado no es correcta", true, auto.isMascota());
	}
	  

}
