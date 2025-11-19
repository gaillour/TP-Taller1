package persistencia;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.print.attribute.standard.PresentationDirection;

import org.junit.Before;
import org.junit.Test;

import persistencia.EmpresaDTO;
import persistencia.PersistenciaBIN;

public class PersistenciaBin_Esc3 {
	
	PersistenciaBIN persistencia;
	
	@Before
	public void setUp() {
		persistencia = new PersistenciaBIN();
	}
	

	@Test
	public void testAbrirInput() {
		try {
			persistencia.abrirInput("datos.bin");
		}
		catch(Exception e) {
			fail("No deberia lanzar excepcion");
		}
	}
	
	@Test
	public void testAbrirInput2() {
		try {
			persistencia.abrirInput(null);
			fail("Tiene que lanzar exepcion");
		}
		catch(Exception e) {
		}
	}
	
	
	@Test
	public void testAbrirOutput() {
		try {
			persistencia.abrirOutput("datos.bin");
		}
		catch(IOException e) {
			fail("No deberia lanzar excepcion");
		}
	}
	
	@Test
	public void testAbrirOutput2() {
		try {
			persistencia.abrirOutput(null);
			fail("Tiene que lanzar excepcion");
		}
		catch(Exception e) {
		}
	}
	
	@Test
	public void testCerrarInput() {
		try {
			persistencia.abrirInput("datos.bin");
			persistencia.cerrarInput();
		}
		catch(IOException e) {
			fail("No deberia lanzar excepcion");
		}
	}

	@Test
	public void testCerrarOutput() {
		try {
			persistencia.abrirOutput("datos.bin");
			persistencia.cerrarOutput();
		}
		catch(IOException e) {
			fail("NO deberia lanzar excepcion");
		}
	}
	
	@Test
	public void testEscribir() {
		EmpresaDTO empresa = new EmpresaDTO();
		try {
			persistencia.abrirOutput("archivo.bin");
			persistencia.escribir(empresa);
		}
		catch(Exception e) {
			fail("No deberia lanzar excepcion");
		}
	}
	
	@Test
	public void testLeer() {
		try {
			EmpresaDTO empresa;
			empresa = (EmpresaDTO) persistencia.leer();
		}
		catch(Exception e) {
			fail("No deberia lanzar excepcion");
		}
	}
}
