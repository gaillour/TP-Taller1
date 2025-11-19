package modelo.datos;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import modeloDatos.Moto;
import modeloDatos.Pedido;

public class MotoMetodosTest {
	
    private Pedido pedido;
    private Moto moto;
    @Before
    public void setUp() {
    	moto= new Moto("ABC123");
        pedido = mock(Pedido.class);
    }

    @Test
    public void testGetPuntajeValido() {
        // Mock: 1 pax, NO mascota, NO baul
        when(pedido.getCantidadPasajeros()).thenReturn(1);
        when(pedido.isMascota()).thenReturn(false);
        when(pedido.isBaul()).thenReturn(false);

        Integer puntaje = moto.getPuntajePedido(pedido);

        // Verificamos que el puntaje no sea nulo
        Assert.assertNotNull("El puntaje no debería ser nulo", puntaje);
        // Verificamos el valor exacto
        Assert.assertEquals("El puntaje debe ser 1000", 1000, (int) puntaje);
    }
    
    @Test
    public void testGetPuntajeInvalidoConBaul() {
        when(pedido.getCantidadPasajeros()).thenReturn(1);
        when(pedido.isMascota()).thenReturn(false);
        when(pedido.isBaul()).thenReturn(true);

        Integer puntaje = moto.getPuntajePedido(pedido);

        Assert.assertNull("El puntaje debería ser nulo", puntaje);

    }
    @Test
    public void testGetPuntajeInvalidoConMascota() {
        when(pedido.getCantidadPasajeros()).thenReturn(1);
        when(pedido.isMascota()).thenReturn(true);
        when(pedido.isBaul()).thenReturn(false);

        Integer puntaje = moto.getPuntajePedido(pedido);

        Assert.assertNull("El puntaje debería ser nulo", puntaje);

    }
    
    @Test
    public void testGetPuntajeInvalidoCantidadDePlaza() {
        when(pedido.getCantidadPasajeros()).thenReturn(2);
        when(pedido.isMascota()).thenReturn(false);
        when(pedido.isBaul()).thenReturn(false);

        Integer puntaje = moto.getPuntajePedido(pedido);

        Assert.assertNull("El puntaje debería ser nulo", puntaje);

    }
    

}
