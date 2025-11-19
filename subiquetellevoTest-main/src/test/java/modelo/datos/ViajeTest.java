package modelo.datos;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import modeloDatos.Auto;
import modeloDatos.ChoferPermanente;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Viaje;


import org.junit.Before;
import org.junit.BeforeClass;



public class ViajeTest {

    private Cliente cliente;
    private ChoferPermanente choferP;
    private Auto auto;

    @BeforeClass
    public static void setUpClass() {
        // Establecemos el valor base estático una sola vez para todos los tests.
        Viaje.setValorBase(5000.0);
    }

    @Before
    public void setUp() {
        this.cliente = new Cliente("Ivancito77", "1234", "Ivan");
        this.choferP = new ChoferPermanente("43521612", "Gonzalo", 2020, 2);
        this.auto = new Auto("ABC123", 4, true);
    }

    @Test
    public void testConstructorYFinalizacion() {
        Pedido pedido = new Pedido(cliente, 3, true, true, 150, "ZONA_PELIGROSA");
        Viaje viaje = new Viaje(pedido, this.choferP, this.auto);

        Assert.assertEquals("El puntaje esperado no es correcto", 0, viaje.getCalificacion());
        Assert.assertEquals("El valor de verdad no es correcto", false, viaje.isFinalizado());

        viaje.finalizarViaje(5);

        Assert.assertEquals("El valor de verdad no es correcto", true, viaje.isFinalizado());
        Assert.assertEquals("El puntaje esperado no es correcto", 5, viaje.getCalificacion());
        Assert.assertEquals("El chofer esperado no es correcto", this.choferP, viaje.getChofer());
        Assert.assertEquals("El pedido esperado no es correcto", pedido, viaje.getPedido());
        Assert.assertEquals("El vehiculo esperado no es correcto", this.auto, viaje.getVehiculo());
    }
    
    @Test
    public void testGetValorBaseStatic() {
        Assert.assertEquals("El valor base esperado no es correcto", 5000.0, Viaje.getValorBase(), 0.001);
    }

    @Test
    public void testGetValorZonaStandardSinMascotaNiBaul() {
        Pedido pedido = new Pedido(this.cliente, 3, false, false, 10, "ZONA_STANDARD");
        Viaje viaje = new Viaje(pedido, this.choferP, this.auto);
        double esperado = 11500.0; // 5000 * 2.30

        Assert.assertEquals("Valor incorrecto (standard, sin mascota ni baul)", esperado, viaje.getValor(), 0.001);
    }

    @Test
    public void testGetValorZonaStandardConMascota() {
        Pedido pedido = new Pedido(this.cliente, 3, true, false, 10, "ZONA_STANDARD");
        Viaje viaje = new Viaje(pedido, this.choferP, this.auto);
        double esperado = 23000.0 ; // 5000 * (2.30 + 2.30)

        Assert.assertEquals("Valor incorrecto (standard, con mascota)", esperado, viaje.getValor(), 0.001);
    }
   
    @Test
    public void testGetValorZonaStandardConBaul() {
        Pedido pedido = new Pedido(this.cliente, 3, false, true, 10, "ZONA_STANDARD");
        Viaje viaje = new Viaje(pedido, this.choferP, this.auto);
        double esperado = 15500.0; // 5000 * (2.30 + 0.80)

        Assert.assertEquals("Valor incorrecto (standard, con baul)", esperado, viaje.getValor(), 0.001);
    }
   
    @Test
    public void testGetValorZonaStandardConMascotaYBaul() {
        Pedido pedido = new Pedido(this.cliente, 3, true, true, 10, "ZONA_STANDARD");
        Viaje viaje = new Viaje(pedido, this.choferP, this.auto);
        double esperado = 27000.0; // 5000 * (2.30 + 2.30 + 0.80)

        Assert.assertEquals("Valor incorrecto (standard, con mascota y baul)", esperado, viaje.getValor(), 0.001);
    }
    
    @Test
    public void testZONA_SIN_ASFALTAR_SinMascotaNiBaul() {
        Pedido pedido = new Pedido(this.cliente, 3, false, false, 10, "ZONA_SIN_ASFALTAR");
        Viaje viaje = new Viaje(pedido, this.choferP, this.auto);
        double esperado = 15500.0; // 5000 * 3.10

        Assert.assertEquals("El valor SIN_ASFALTAR (sin mascota ni baul) no es correcto", esperado, viaje.getValor(), 0.001);
    }

    @Test
    public void testZONA_SIN_ASFALTAR_ConMascota() {
        Pedido pedido = new Pedido(this.cliente, 3, true, false, 10, "ZONA_SIN_ASFALTAR");
        Viaje viaje = new Viaje(pedido, this.choferP, this.auto);
        double esperado = 27000.0; // 5000 * (3.10 + 2.30)

        Assert.assertEquals("El valor SIN_ASFALTAR (con mascota) no es correcto", esperado, viaje.getValor(), 0.001);
    }

    @Test
    public void testZONA_SIN_ASFALTAR_ConBaul() {
        Pedido pedido = new Pedido(this.cliente, 3, false, true, 10, "ZONA_SIN_ASFALTAR");
        Viaje viaje = new Viaje(pedido, this.choferP, this.auto);
        double esperado = 19500.0; // 5000 * (3.10 + 0.80)

        Assert.assertEquals("El valor SIN_ASFALTAR (con baul) no es correcto", esperado, viaje.getValor(), 0.001);
    }

    @Test
    public void testZONA_SIN_ASFALTAR_ConMascotaYBaul() {
        Pedido pedido = new Pedido(this.cliente, 3, true, true, 10, "ZONA_SIN_ASFALTAR");
        Viaje viaje = new Viaje(pedido, this.choferP, this.auto);
        double esperado = 31000.0; // 5000 * (3.10 + 2.30 + 0.80)

        Assert.assertEquals("El valor SIN_ASFALTAR (con mascota y baul) no es correcto", esperado, viaje.getValor(), 0.001);
    }


    @Test
    public void testZONA_PELIGROSA_SinMascotaNiBaul() {
        Pedido pedido = new Pedido(this.cliente, 3, false, false, 10, "ZONA_PELIGROSA");
        Viaje viaje = new Viaje(pedido, this.choferP, this.auto);
        double esperado = 16500.0; // 5000 * 3.30

        Assert.assertEquals("El valor PELIGROSA (sin mascota ni baul) no es correcto", esperado, viaje.getValor(), 0.001);
    }
  
    @Test
    public void testZONA_PELIGROSA_ConMascota() {
        Pedido pedido = new Pedido(this.cliente, 3, true, false, 10, "ZONA_PELIGROSA");
        Viaje viaje = new Viaje(pedido, this.choferP, this.auto);
        double esperado = 28000.0; // 5000 * (3.30 + 2.30)

        Assert.assertEquals("El valor PELIGROSA (con mascota) no es correcto", esperado, viaje.getValor(), 0.001);
    }
  
    @Test
    public void testZONA_PELIGROSA_ConBaul() {
        Pedido pedido = new Pedido(this.cliente, 3, false, true, 10, "ZONA_PELIGROSA");
        Viaje viaje = new Viaje(pedido, this.choferP, this.auto);
        double esperado = 20500.0; // 5000 * (3.30 + 0.80)

        Assert.assertEquals("El valor PELIGROSA (con baul) no es correcto", esperado, viaje.getValor(), 0.001);
    }
    @Test
    public void testZONA_PELIGROSA_ConMascotaYBaul() {
        Pedido pedido = new Pedido(this.cliente, 3, true, true, 10, "ZONA_PELIGROSA");
        Viaje viaje = new Viaje(pedido, this.choferP, this.auto);
        double esperado = 32000.0; // 5000 * (3.30 + 2.30 + 0.80)

        Assert.assertEquals("El valor PELIGROSA (con mascota y baul) no es correcto", esperado, viaje.getValor(), 0.001);
    }
    
}  


 