package persistencia;

import java.util.ArrayList;
import java.util.HashMap;

import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Usuario;
import modeloDatos.Vehiculo;
import modeloDatos.Viaje;
import modeloNegocio.Empresa;
import persistencia.EmpresaDTO;
import util.Constantes;

/**
 * Clase escenario donde se iniciliza la Empresa y EmpresaDTO con:
 * 	1 chofer Temporario
 * 	1 Cliente
 * 	1 Vehiculo de tipo Auto
 * 	1 Pedido del cliente 1
 * 	1 Viaje del pedido realizado por cliente 1
 * el unico cliente es el usuario logueado
 */
public class Escenario {

	HashMap<String,Chofer> choferes = new HashMap<String, Chofer>();
	ArrayList<Chofer> choferDisponible = new ArrayList<Chofer>();
	HashMap<String,Cliente> clientes = new HashMap<String, Cliente>();
	HashMap<Cliente,Pedido> pedidos = new HashMap<Cliente, Pedido>();
	Usuario usuarioLogueado;
	HashMap<String,Vehiculo> vehiculos = new HashMap<String, Vehiculo>();
	ArrayList<Vehiculo> vehiculosDesocupados = new ArrayList<Vehiculo>();
	HashMap<Cliente,Viaje> viajesIniciados = new HashMap<Cliente, Viaje>();
	ArrayList<Viaje> viajesTerminados = new ArrayList<Viaje>();
	
	Chofer chofer1 = new ChoferTemporario("1234","primer chofer");
	Cliente cliente1 = new Cliente("primer usuario","1234","nombre real");
	Vehiculo auto1 = new Auto("asd 123",4,true);
	Pedido pedido1 = new Pedido(cliente1,3,false,true,10,Constantes.ZONA_SIN_ASFALTAR);
	Viaje viaje1 = new Viaje(pedido1, chofer1, auto1);
	
	
	public void getEscenario(EmpresaDTO empresaDTO) {
		
		choferes.put(chofer1.getDni(),chofer1);
		clientes.put(cliente1.getNombreUsuario(), cliente1);
		vehiculos.put(auto1.getPatente(),auto1);
		usuarioLogueado = cliente1;
		pedidos.put(cliente1, pedido1);
		viajesIniciados.put(cliente1,viaje1);
		
		Empresa.getInstance().setChoferes(choferes);
		Empresa.getInstance().setChoferesDesocupados(choferDisponible);
		Empresa.getInstance().setClientes(clientes);
		Empresa.getInstance().setPedidos(pedidos);
		Empresa.getInstance().setUsuarioLogeado(usuarioLogueado);
		Empresa.getInstance().setVehiculos(vehiculos);
		Empresa.getInstance().setVehiculosDesocupados(vehiculosDesocupados);
		Empresa.getInstance().setViajesIniciados(viajesIniciados);
		Empresa.getInstance().setViajesTerminados(viajesTerminados);
	
		empresaDTO.setChoferes(choferes);
		empresaDTO.setChoferesDesocupados(choferDisponible);
		empresaDTO.setClientes(clientes);
		empresaDTO.setPedidos(pedidos);
		empresaDTO.setUsuarioLogeado(usuarioLogueado);
		empresaDTO.setVehiculos(vehiculos);
		empresaDTO.setVehiculosDesocupados(vehiculosDesocupados);
		empresaDTO.setViajesIniciados(viajesIniciados);
		empresaDTO.setViajesTerminados(viajesTerminados);
	}
	

	public void limpiarEscenario() {
		
		
		Empresa.getInstance().getChoferes().clear();
		Empresa.getInstance().getChoferesDesocupados();
		Empresa.getInstance().getClientes().clear();
		Empresa.getInstance().getPedidos().clear();
		Empresa.getInstance().setUsuarioLogeado(null);
		Empresa.getInstance().getVehiculos().clear();
		Empresa.getInstance().getVehiculosDesocupados().clear();
		Empresa.getInstance().getViajesIniciados().clear();
		Empresa.getInstance().getViajesTerminados().clear();
	}
}
