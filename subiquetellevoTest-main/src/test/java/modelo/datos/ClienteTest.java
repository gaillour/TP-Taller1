package modelo.datos;


import org.junit.Assert;
import org.junit.Test;

import modeloDatos.Cliente;

public class ClienteTest {

	 @Test
	 public void testConstructor() {
	        Cliente cliente = new Cliente("Ivancito77", "1234", "Ivan");
	        String nombreUsuario = cliente.getNombreUsuario();
	        String pass = cliente.getPass();
	        String nombreReal = cliente.getNombreReal();

	        Assert.assertEquals("El nombre de usuario esperado no es correcto", "Ivancito77", nombreUsuario);
	        Assert.assertEquals("La contraseña esperada no es correcta", "1234", pass);
	        Assert.assertEquals("El nombre real esperado no es correcto", "Ivan", nombreReal);
	    }

	   
}
