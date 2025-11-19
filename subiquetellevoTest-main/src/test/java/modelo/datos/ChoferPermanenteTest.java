package modelo.datos;

import org.junit.Assert;
import org.junit.Test;

import modeloDatos.ChoferPermanente;
import modeloDatos.Cliente;

public class ChoferPermanenteTest {

	 @Test
	 public void testConstructor() {
	        ChoferPermanente chofer = new  ChoferPermanente("43521612", "Gonzalo", 2020, 2);
	        String nombre = chofer.getNombre();
	        String dni = chofer.getDni();
	        int anioAnt = chofer.getAnioIngreso();
	        int cantHijos = chofer.getCantidadHijos();
           
	        Assert.assertEquals("El nombre esperado no es correcto", "Gonzalo", nombre);
	        Assert.assertEquals("La dni esperada no es correcta", "43521612", dni);
	        Assert.assertEquals("El año esperado no es correcto", 2020 , anioAnt);
	        Assert.assertEquals("La cantidad de hijos esperados no es correcto", 2 , cantHijos);
	        Assert.assertEquals("La antiguedad esperada no es correcto", 5 , chofer.getAntiguedad());
	    }

	 @Test
	 public void testSetCantHijos() {
		 ChoferPermanente chofer = new  ChoferPermanente("43521612", "Gonzalo", 2020, 2);
		 chofer.setCantidadHijos(1); 
		 Assert.assertEquals("La cantidad de hijos esperados no es correcto", 1 , chofer.getCantidadHijos());
	 }
}
