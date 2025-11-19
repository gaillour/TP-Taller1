package modelo.datos;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import modeloDatos.Auto;
import modeloDatos.Pedido;

public class AutoMetodosTest {

	private Auto autoQueAceptaMascotas;
    private Auto autoQueNoAceptaMascotas;
    private Pedido pedido;

    @Before
    public void setUp() {
        // 1. Creamos nuestros autos para las pruebas
        autoQueAceptaMascotas = new Auto("AD123BC", 4, true); // 4 plazas, acepta mascota
        autoQueNoAceptaMascotas = new Auto("AE456FG", 3, false); // 3 plazas, NO acepta mascota

        // 2. Creamos el Mock de Pedido
        pedido = mock(Pedido.class);
    }

    @Test
    public void testGetPuntajeFallaPorPlazas() {
        //mosk simula ser un pedido de 4 pasajeros
        when(pedido.getCantidadPasajeros()).thenReturn(4);
        when(pedido.isMascota()).thenReturn(false); // Pedido pide mascota
        when(pedido.isBaul()).thenReturn(false); 
        
        Assert.assertNull("El puntaje debe ser null si las plazas no alcanzan", autoQueNoAceptaMascotas.getPuntajePedido(pedido));
    }

    @Test
    public void testGetPuntajeFallaPorMascota() {
        //mosk simula un pedido de 2 pasajero y que tiene mascota
        when(pedido.getCantidadPasajeros()).thenReturn(2); 
        when(pedido.isMascota()).thenReturn(true); // Pedido pide mascota
        when(pedido.isBaul()).thenReturn(true); 
        
        Assert.assertNull("El puntaje debe ser null si el auto no acepta mascotas", autoQueNoAceptaMascotas.getPuntajePedido(pedido));
    }

    @Test
    public void testGetPuntajeExitoConBaul() {
        //mosk simula un auto de 3 pasajeros, mascota y baul
        when(pedido.getCantidadPasajeros()).thenReturn(3); 
        when(pedido.isMascota()).thenReturn(true); 
        when(pedido.isBaul()).thenReturn(true); 
        // Ejecución y Verificación
        // Fórmula: 40 * 3 = 120
        int puntaje = autoQueAceptaMascotas.getPuntajePedido(pedido);
        Assert.assertNotNull("El puntaje no debería ser null", puntaje);
        Assert.assertEquals("El cálculo del puntaje con baúl es incorrecto", 120, puntaje);
    }

    @Test
    public void testGetPuntajeExitoSinBaul() {
    	//mosk simula un auto de 2 pasajeros, sin mascota y sin baul
        when(pedido.getCantidadPasajeros()).thenReturn(2); 
        when(pedido.isMascota()).thenReturn(false); 
        when(pedido.isBaul()).thenReturn(false); 

        // Fórmula: 30 * 2 = 60
        int puntaje = autoQueAceptaMascotas.getPuntajePedido(pedido);
        Assert.assertNotNull("El puntaje no debería ser null", puntaje);
        Assert.assertEquals("El cálculo del puntaje sin baúl es incorrecto", 60, puntaje);
    }
    
}
