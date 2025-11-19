package modelo.negocio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import excepciones.PasswordErroneaException;
import excepciones.UsuarioNoExisteException;
import modeloDatos.Cliente;
import modeloDatos.Usuario;
import modeloNegocio.Empresa;
import util.Mensajes;

public class EmpresaTestEscenario1 {

	private Escenario1 escenario = new Escenario1(); 
    
    @Before
    public void setUp() throws Exception {
    	escenario.setUp();
    	
    }

    @After
    public void tearDown() throws Exception {
    	escenario.tearDown();
    }
	
//----- Metodo static Empresa getInstance()
    
    // Clases cubiertas: 1
    @Test
    public void testgetInstance_PrimeraLlamada() {
        Empresa primeraInstancia = Empresa.getInstance();
        assertNotNull("La primera llamada a getInstance() debe retornar una instancia de Empresa",primeraInstancia);
    }

    // Clases cubiertas: 2
    @Test
    public void testgetInstance_LlamadasSucesivas() {
        Empresa primeraInstancia = Empresa.getInstance();
        Empresa segundaInstancia = Empresa.getInstance();
        Empresa terceraInstancia = Empresa.getInstance();

        assertSame("Las llamadas sucesivas deben devolver la misma instancia",primeraInstancia, segundaInstancia);
        assertSame("Las llamadas sucesivas deben devolver la misma instancia",primeraInstancia, terceraInstancia);
    }
    
//----- Metodo Usuario login(String usserName, String pass)				
    
    // Clases cubiertas: 1, 2, 3, 5 
    @Test
    public void testLogin_ClaseCorrecta() {
        try {
        	Cliente usuario = escenario.empresa.getClientes().get("user1");
        	
            // Intentamos realizar el login con credenciales correctas
            Usuario usuarioLogueado = escenario.empresa.login(usuario.getNombreUsuario(), usuario.getPass());
            
            // Verificamos que el login fue exitoso y que el usuario retornado es correcto
            assertNotNull("El usuario debería haber sido logueado correctamente", usuarioLogueado);
            assertEquals("El nombre de usuario no coincide", usuario.getNombreUsuario(), usuarioLogueado.getNombreUsuario());
            assertEquals("La contraseña no coincide", usuario.getPass(), usuarioLogueado.getPass());

        } catch (UsuarioNoExisteException e) {
            fail("No se esperaba una UsuarioNoExisteException para un usuario registrado: " + e.getMessage());

        } catch (PasswordErroneaException e) {
            fail("No se esperaba una PasswordErroneaException para una contraseña correcta: " + e.getMessage());

        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName() + " - " + e.getMessage());
        }
    }
    
    // Clases cubiertas: 1, 2, 3, 6 
    @Test
    public void testLogin_PasswordIncorrecta() {
        try {
        	Cliente usuario = escenario.empresa.getClientes().get("user1");
        	
            // Intentamos realizar el login con una contraseña incorrecta
        	Usuario usuarioLogueado = escenario.empresa.login(usuario.getNombreUsuario(), "wrongpass");
        	
            fail("Se esperaba una PasswordErroneaException al intentar loguearse con una contraseña incorrecta");

        } catch (PasswordErroneaException e) {
            // Éxito: Se lanzó PasswordErroneaException como se esperaba
        	Cliente usuarioPret = escenario.empresa.getClientes().get("user1");
        	assertEquals("El nombre del usuario pretendido dado por la excepcion no es el correcto.", usuarioPret.getNombreUsuario(), e.getUsuarioPretendido());
        	assertEquals("La contrasenia pretendida dada por la excepcion no es la correcta.", "wrongpass", e.getPasswordPretendida());
            assertEquals("El mensaje no corresponde con la excepcion adecuada.", Mensajes.PASS_ERRONEO.getValor(), e.getMessage());

        } catch (UsuarioNoExisteException e) {
            fail("Se lanzó UsuarioNoExisteException en lugar de PasswordErroneaException: " + e.getMessage());

        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName() + " - " + e.getMessage());
        }
    }

//----- Metodo logout()

    // Clases cubiertas: 1
    @Test
    public void testLogout_ClaseCorrecta() {
    	Cliente usuario = escenario.empresa.getClientes().get("user1");
		
    	try {
    		escenario.empresa.login(usuario.getNombreUsuario(), usuario.getPass());
    		escenario.empresa.logout();
    		Usuario usuarioLogueado = escenario.empresa.getUsuarioLogeado();

        	// Verificamos que el logout fue exitoso y que retorna null
        	assertNull("El usuario continua logueado despues del Logout", usuarioLogueado);
		} catch (UsuarioNoExisteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PasswordErroneaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
 // Clases cubiertas: 2
    @Test
    public void testLogout_SinLogin() {
    		escenario.empresa.logout();
    		Usuario usuarioLogueado = escenario.empresa.getUsuarioLogeado();

        	// Verificamos que el logout fue exitoso y que retorna null
        	assertNull("Figura usuario logueado a pesar de no hacer Login", usuarioLogueado);
    }


}
