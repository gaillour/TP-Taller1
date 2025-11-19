package modelo.datos;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import modeloDatos.Cliente;
import modeloDatos.Combi;
import modeloDatos.Moto;
import modeloDatos.Pedido;

public class MotoTest {

	@Test
	public void testConstructor() {
	        Moto moto= new Moto("ABC123");
	        int CantidadPlazas = moto.getCantidadPlazas();
	        String pat = moto.getPatente();
           
	        Assert.assertEquals("La patente esperada no es correcto", "ABC123", pat);
	        Assert.assertEquals("La CantidadPlazas esperada no es correcta", 1, CantidadPlazas);
	        Assert.assertEquals("El false esperado no es correcta", false, moto.isMascota());
}

}
