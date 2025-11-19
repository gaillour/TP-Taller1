package modelo.datos;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import modeloDatos.Cliente;
import modeloDatos.Pedido;

public class PedidoTest {

	@Test
	public void testConstructorZona_Standard() {
		Cliente cliente = new Cliente("Ivancito77", "1234", "Ivan");
        Pedido pedido = new Pedido(cliente, 2, true, false, 10, "ZONA_STANDARD");

        Assert.assertEquals("El cliente esperado no es correcto", cliente , pedido.getCliente()); 
        Assert.assertEquals("La cantidad de pasajeros esperada no es correcta", 2, pedido.getCantidadPasajeros());
        Assert.assertEquals("Los km esperados no son correctos", 10 , pedido.getKm());
        Assert.assertEquals("La zona esperada no es correcta", "ZONA_STANDARD" , pedido.getZona());
        Assert.assertEquals("El valor de verdad baul esperada no es correcta", false , pedido.isBaul());
        Assert.assertEquals("El valor de verdad mascota esperada no es correcta", true , pedido.isMascota());
	}
	
	@Test
	public void testConstructorZona_SIN_ASFALTAR() {
		Cliente cliente = new Cliente("Ivancito77", "1234", "Ivan");
        Pedido pedido = new Pedido(cliente, 5, false, false, 10, "ZONA_SIN_ASFALTAR");

        Assert.assertEquals("El cliente esperado no es correcto", cliente , pedido.getCliente()); 
        Assert.assertEquals("La cantidad de pasajeros esperada no es correcta", 5, pedido.getCantidadPasajeros());
        Assert.assertEquals("Los km esperados no son correctos", 10 , pedido.getKm());
        Assert.assertEquals("La zona esperada no es correcta", "ZONA_SIN_ASFALTAR" , pedido.getZona());
        Assert.assertEquals("El valor de verdad baul esperada no es correcta", false , pedido.isBaul());
        Assert.assertEquals("El valor de verdad mascota esperada no es correcta", false , pedido.isMascota());
	}
	
	@Test
	public void testConstructorZona_PELIGROSA() {
		Cliente cliente = new Cliente("Ivancito77", "1234", "Ivan");
        Pedido pedido = new Pedido(cliente, 2, true, false, 10, "ZONA_PELIGROSA");

        Assert.assertEquals("El cliente esperado no es correcto", cliente , pedido.getCliente()); 
        Assert.assertEquals("La cantidad de pasajeros esperada no es correcta", 2, pedido.getCantidadPasajeros());
        Assert.assertEquals("Los km esperados no son correctos", 10 , pedido.getKm());
        Assert.assertEquals("La zona esperada no es correcta", "ZONA_PELIGROSA" , pedido.getZona());
        Assert.assertEquals("El valor de verdad baul esperada no es correcta", false , pedido.isBaul());
        Assert.assertEquals("El valor de verdad mascota esperada no es correcta", true , pedido.isMascota());
	}

}
