package persistencia;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import persistencia.EmpresaDTO;

public class EmpresaDTOTest {
	
	EmpresaDTO empresa;
	Escenario escenario;

	@Before
	public void setUp() throws Exception {
		empresa = new EmpresaDTO();
		escenario = new Escenario();
		escenario.getEscenario(empresa);
	}
	
	@After
	public void tearDown() throws Exception{
		escenario.limpiarEscenario();
	}


	@Test
	public void testGetChofer() {
		assertEquals("NO SE ASIGNA LA MISMA LIST DE CHOFERES.",empresa.getChoferes(),escenario.choferes);
	}
	@Test
	public void testGetChoferDisponible() {
		assertEquals("NO SE ASIGNA LA MISMA LIST DE CHOFERES DISPONIBLES", empresa.getChoferesDesocupados(), escenario.choferDisponible);
	}
	@Test
	public void testGetVehiculos() {
		assertEquals("NO SE ASIGNA LA MISMA LIST DE VEHICULOS", empresa.getVehiculos(), escenario.vehiculos);
	}
	@Test
	public void testGetVehiculosDesocupados() {
		assertEquals("NO SE ASIGNA LA MISMA LIST DE VEHICULOS DESOCUPADOS", empresa.getVehiculosDesocupados(), escenario.vehiculosDesocupados);
	}
	@Test
	public void testGetCliente() {
		assertEquals("NO SE ASIGNA LA MISMA LIST DE CLIENTES", empresa.getClientes(), escenario.clientes);
	}
	@Test
	public void testGetUsuarioLogueado() {
		assertEquals("NO SE ASIGNA EL MISMO USUARIO LOGUEADO", empresa.getUsuarioLogeado(), escenario.usuarioLogueado);
	}
	@Test
	public void testGetPedidos() {
		assertEquals("NO SE ASIGNA LA MISMA LIST DE PEDIDOS", empresa.getPedidos(), escenario.pedidos);
	}
	@Test
	public void testGetViajesTerminados() {
		assertEquals("NO SE ASIGNA LA MISMA LIST DE VIAJES INICIADOS", empresa.getViajesIniciados(), escenario.viajesIniciados);
	}
	@Test
	public void testGetViajesIniciados() {
		assertEquals("NO SE ASIGNA LA MISMA LIST DE VIAJES TERMINADOS", empresa.getViajesTerminados(), escenario.viajesTerminados);
	}

}
