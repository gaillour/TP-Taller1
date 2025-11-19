package persistencia;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modeloNegocio.Empresa;
import persistencia.EmpresaDTO;
import persistencia.UtilPersistencia;

public class UtilPersistenciaTest {
	
	EmpresaDTO empresaDTO;
	Escenario escenario;
	
	@Before
	public void setUp()throws Exception{
		empresaDTO = new EmpresaDTO();
		escenario = new Escenario();

		escenario.getEscenario(empresaDTO);
	}
	
	@After
	public void tearDown() throws Exception{
		escenario.limpiarEscenario();
	}

	
	@Test
	public void testEmpresaDTOfromEmpresa() {
		
		empresaDTO = UtilPersistencia.EmpresaDtoFromEmpresa();
		
		assertEquals("LOS CHOFERES NO COINCIDEN",empresaDTO.getChoferes(),Empresa.getInstance().getChoferes());
		assertEquals("LOS CHOFERES DESOCUPADOS NO COINCIDEN",empresaDTO.getChoferesDesocupados(),Empresa.getInstance().getChoferesDesocupados());
		assertEquals("LOS CLIENTES NO COINCIDEN",empresaDTO.getClientes(),Empresa.getInstance().getClientes());
		assertEquals("LOS PEDIDOS NO COINCIDEN",empresaDTO.getPedidos(),Empresa.getInstance().getPedidos());
		assertEquals("EL USUARIO LOGUEADO NO COINCIDE",empresaDTO.getUsuarioLogeado(),Empresa.getInstance().getUsuarioLogeado());
		assertEquals("LOS VEHICULOS NO COINCIDEN",empresaDTO.getVehiculos(),Empresa.getInstance().getVehiculos());
		assertEquals("LOS VEHICULOS DESOCUPADOS NO COINCIDEN",empresaDTO.getVehiculosDesocupados(),Empresa.getInstance().getVehiculosDesocupados());
		assertEquals("LOS VIAJES INICIADOS NO COINCIDEN",empresaDTO.getViajesIniciados(),Empresa.getInstance().getViajesIniciados());
		assertEquals("LOS VIAJES TERMINADOS NO COINCIDEN",empresaDTO.getViajesTerminados(),Empresa.getInstance().getViajesTerminados());
	}

	@Test
	public void testEmpresafromEmpresaDTO() {
		
		UtilPersistencia.empresaFromEmpresaDTO(empresaDTO);
		
		assertEquals("LOS CHOFERES NO COINCIDEN",Empresa.getInstance().getChoferes(),empresaDTO.getChoferes());
		assertEquals("LOS CHOFERES DISPONIBLES NO COINCIDEN",Empresa.getInstance().getChoferesDesocupados(),empresaDTO.getChoferesDesocupados());
		assertEquals("LOS CLIENTES NO COINCIDEN",Empresa.getInstance().getClientes(),empresaDTO.getClientes());
		assertEquals("LOS PEDIDOS NO COINCIDEN",Empresa.getInstance().getPedidos(),empresaDTO.getPedidos());
		assertEquals("EL USUARIO LOGUEADO NO COINCIDE",Empresa.getInstance().getUsuarioLogeado(),empresaDTO.getUsuarioLogeado());
		assertEquals("LOS VEHICULOS NO COINCIDEN",Empresa.getInstance().getVehiculos(),empresaDTO.getVehiculos());
		assertEquals("LOS VEHICULOS DISPONIBLES NO COINCIDEN",Empresa.getInstance().getVehiculosDesocupados(),empresaDTO.getVehiculosDesocupados());
		assertEquals("LOS VIAJES INICIADOS NO COINCIDEN",Empresa.getInstance().getViajesIniciados(),empresaDTO.getViajesIniciados());
		assertEquals("LOS VIAJES TERMINADOS NO COINCIDEN",Empresa.getInstance().getViajesTerminados(),empresaDTO.getViajesTerminados());
	}

	@Test
	public void testEmpresafromEmpresaDTO2() {
		try {
			UtilPersistencia.empresaFromEmpresaDTO(null);
			fail("deberia lanzar excepcion. empresaDTO es null");
		}
		catch(Exception e) {
			
		}
	}
	
}
