package modelo.datos;

import org.junit.Assert;

import org.junit.Test;

import modeloDatos.Administrador;

public class AdministradorTest {

	// Test 1: Probar que siempre devuelve la MISMA instancia
    @Test
    public void testSingletonInstanciaUnica() {
        // 1. Arrange
        // Obtenemos la instancia dos veces
        Administrador admin1 = Administrador.getInstance();
        Administrador admin2 = Administrador.getInstance();

        // Verificamos que ambas variables apuntan al MISMO objeto en memoria
        Assert.assertNotNull("La instancia no puede ser nula", admin1);
        Assert.assertSame("admin1 y admin2 deben ser la MISMA instancia", admin1, admin2);
    }
 // Test 2: Probar que los datos son correctos
    @Test
    public void testSingletonDatosCorrectos() {
        // 1. Arrange
        Administrador admin = Administrador.getInstance();

        Assert.assertEquals("El nombre de usuario debe ser 'admin'", "admin", admin.getNombreUsuario());
        Assert.assertEquals("La contraseña debe ser 'admin'", "admin", admin.getPass());
    }

}
