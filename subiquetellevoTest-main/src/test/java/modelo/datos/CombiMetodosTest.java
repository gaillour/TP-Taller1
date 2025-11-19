package modelo.datos;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import modeloDatos.Combi;
import modeloDatos.Pedido;

public class CombiMetodosTest {

	private Combi combiQueAceptaMascotas;
    private Combi combiQueNoAceptaMascotas;
    private Pedido pedido;

    @Before
    public void setUp() {
        // 1. Creamos nuestros autos para las pruebas
        combiQueAceptaMascotas = new Combi("AD123BC", 5, true); // 5 plazas, acepta mascota
        combiQueNoAceptaMascotas = new Combi("AE456FG", 10, false); // 8 plazas, NO acepta mascota

        // 2. Creamos el Mock de Pedido
        pedido = mock(Pedido.class);
    }

  
    // Test 2: Falla por cantidad de plazas
    @Test
    public void testGetPuntajeFallaPorPlazas() {
        //mosk simula ser un pedido de 10 pasajeros
        when(pedido.getCantidadPasajeros()).thenReturn(10);
        when(pedido.isMascota()).thenReturn(false); 
        when(pedido.isBaul()).thenReturn(false);

        Assert.assertNull("El puntaje debe ser null si las plazas no alcanzan", combiQueAceptaMascotas.getPuntajePedido(pedido));
    }

    // Test 3: Falla por mascota
    @Test
    public void testGetPuntajeFallaPorMascota() {
        //mosk simula un pedido de 2 pasajero y que tiene mascota
        when(pedido.getCantidadPasajeros()).thenReturn(5); 
        when(pedido.isMascota()).thenReturn(true); // Pedido pide mascota
        when(pedido.isBaul()).thenReturn(true); 
        
        Assert.assertNull("El puntaje debe ser null si el auto no acepta mascotas", combiQueNoAceptaMascotas.getPuntajePedido(pedido));
    }

    // Test 4: Éxito CON baúl (10 * cantPax)
 @Test
    public void testGetPuntajeExitoConBaul() {
        //mosk simula un auto de 3 pasajeros, mascota y baul
        when(pedido.getCantidadPasajeros()).thenReturn(5); 
        when(pedido.isMascota()).thenReturn(true); 
        when(pedido.isBaul()).thenReturn(true); 
        // Ejecución y Verificación
        // Fórmula: 40 * 3 = 120
        int puntaje = combiQueAceptaMascotas.getPuntajePedido(pedido);
        Assert.assertNotNull("El puntaje no debería ser null", puntaje);
        Assert.assertEquals("El cálculo del puntaje con baúl es incorrecto",150, puntaje);
    }

    @Test
    public void testGetPuntajeExitoSinBaul() {
    	//mosk simula un auto de 2 pasajeros, sin mascota y sin baul
        when(pedido.getCantidadPasajeros()).thenReturn(5); 
        when(pedido.isMascota()).thenReturn(false); 
        when(pedido.isBaul()).thenReturn(false); 

        int puntaje = combiQueAceptaMascotas.getPuntajePedido(pedido);
        Assert.assertNotNull("El puntaje no debería ser null", puntaje);
        Assert.assertEquals("El cálculo del puntaje sin baúl es incorrecto", 50, puntaje);
    }
    

}
