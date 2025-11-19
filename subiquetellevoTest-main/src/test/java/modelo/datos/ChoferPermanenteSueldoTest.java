package modelo.datos;

import static org.junit.Assert.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;

public class ChoferPermanenteSueldoTest {

    private final double SUELDO_BASICO = 10000.0;
    private final double DELTA = 0.01;

    @Before
    public void setUp() {
        Chofer.setSueldoBasico(SUELDO_BASICO);
    }

    @Test
    public void testSueldoBasico() {
        ChoferPermanente chofer = new ChoferPermanente("43521612", "Gonzalo", 2006, 2);
        Assert.assertEquals("No es el sueldo bruto esperado", 10000, chofer.getSueldoBasico(), DELTA);
    }

    @Test
    public void testSueldoNeto() {
        ChoferPermanente chofer = new ChoferPermanente("43521612", "Gonzalo", 2015, 2);
        Assert.assertEquals("No es el sueldo bruto esperado", 14104.0, chofer.getSueldoNeto(), DELTA);
    }

    @Test
    public void testSueldoBrutoCeroAntiguedadCeroHijos() {
        ChoferPermanente chofer = new ChoferPermanente("43521612", "Gonzalo", 2025, 0);
        Assert.assertEquals("No es el sueldo bruto esperado", 10000, chofer.getSueldoBruto(), DELTA);
    }

    @Test
    public void testSueldoBrutoMediaAntiguedadConHijos() {
        ChoferPermanente chofer = new ChoferPermanente("43521612", "Gonzalo", 2015, 2);
        Assert.assertEquals(16400, chofer.getSueldoBruto(), DELTA);
    }

}
