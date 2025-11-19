package modelo.datos;

import org.junit.Assert;
import org.junit.Test;

import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;


public class ChoferTemporarioTest {

	 @Test
	 public void testConstructor() {
	        ChoferTemporario chofer = new  ChoferTemporario("43521612", "Gonzalo Agurto");
	        String nombre = chofer.getNombre();
	        String dni = chofer.getDni();
	    
	        Assert.assertEquals("El nombre esperado no es correcto", "Gonzalo Agurto", nombre);
	        Assert.assertEquals("La dni esperada no es correcta", "43521612", dni);
	    }
	 

	 @Test
	 public void testSueldos() {
		 Chofer.setSueldoBasico(10000);
		 ChoferTemporario chofer = new  ChoferTemporario("43521612", "Gonzalo Agurto");
		 

		 Assert.assertEquals("El sueldo bruto calculado no es correcto", 10000, chofer.getSueldoBruto(), 0.01);
	 }


}
