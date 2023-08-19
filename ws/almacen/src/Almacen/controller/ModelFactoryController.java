package Almacen.controller;

import java.util.List;

import Almacen.exception.ClienteException;
import Almacen.model.Almacen;
import Almacen.model.Cliente;
import Almacen.model.ClienteJuridico;
import Almacen.model.ClienteNatural;

public class ModelFactoryController {

	Almacen almacen = null;
	private static class SingletonHolder {
		// El constructor de Singleton puede ser llamado desde aqu� al ser protected
		private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
	}

	// M�todo para obtener la instancia de nuestra clase
	public static ModelFactoryController getInstance() {
		return SingletonHolder.eINSTANCE;
	}

	public ModelFactoryController() {
		System.out.println("invocaci�n clase singleton");
		inicializarDatos();
	}

	private void inicializarDatos() {

		almacen = new Almacen("1234");

		Cliente cliente1 = new ClienteNatural("Camilo", "Garcia", "1112728156", "3213792877",
				"calle", "04/01/2004", "gar");

		Cliente cliente2 = new ClienteJuridico("Miguel", "Marin", "nn", "nn",
				"calle", "04");

		almacen.getListaClientes().add(cliente1);
		almacen.getListaClientes().add(cliente2);


	}

	public Almacen getAlmacen() {
		return almacen;
	}

	public void setAlmacen(Almacen almacen) {
		this.almacen = almacen;
	}

	//--------------------------COMUNICACION DEL MODEL FACTORY CON EL ALMACEN--------------------------------------
	/**
	 * Me retorna la lista de clientes del almacen
	 * @return
	 */
	public List<Cliente> getListaClientes() {
		return almacen.getListaClientes();
	}

	/**
	 * Es el encargado de llamar el metodo que elimina un cliente
	 * @param clienteSeleccionado
	 * @return
	 * @throws ClienteException
	 */
	public boolean eliminarCliente(Cliente clienteSeleccionado) throws ClienteException {

		if(almacen.EliminarCliente(clienteSeleccionado.getIdentificacion())){
			return true;
		}

		return false;
	}

	/**
	 * Es el encargado de llamar el metodo de crear un cliente juridico
	 * @param nombre
	 * @param apellidos
	 * @param identificacion
	 * @param telefono
	 * @param direccion
	 * @param nit
	 * @return
	 * @throws ClienteException
	 */
	public boolean crearClienteJuridico(String nombre, String apellidos, String identificacion, String telefono,
			String direccion, String nit) throws ClienteException {
		return almacen.crearClienteJuridico(nombre, apellidos, identificacion, telefono, direccion, nit);
	}

	/**
	 * Es el encargado de llamar el metodo de crear un cliente natural
	 * @param nombre
	 * @param apellidos
	 * @param identificacion
	 * @param telefono
	 * @param direccion
	 * @param email
	 * @param fechaNacimiento
	 * @return
	 * @throws ClienteException
	 */
	public boolean crearClienteNatural(String nombre, String apellidos, String identificacion, String telefono,
			String direccion, String email, String fechaNacimiento) throws ClienteException {

		return almacen.crearClienteNatural(nombre, apellidos, identificacion, telefono, direccion, fechaNacimiento, email);
	}

	/**
	 * Es el encargado de llamar el metodo para actualizar un cliente natural
	 * @param nombre
	 * @param apellidos
	 * @param identificacion
	 * @param telefono
	 * @param direccion
	 * @param fechaNacimiento
	 * @param email
	 * @throws ClienteException
	 */
	public void actualizarClienteNatural(String nombre, String apellidos, String identificacion, String telefono, String direccion,
			String fechaNacimiento, String email) throws ClienteException{
		almacen.actualizarClienteNatural(nombre, apellidos, identificacion, telefono,
				direccion, fechaNacimiento, email);
	}

	/**
	 * Es el encargado de llamar el metodo para actualizar un cliente juridico
	 * @param nombre
	 * @param apellidos
	 * @param identificacion
	 * @param telefono
	 * @param direccion
	 * @param nit
	 * @throws ClienteException
	 */
	public void actualizarClienteJuridico(String nombre, String apellidos, String identificacion, String telefono, String direccion,
			String nit) throws ClienteException{
		almacen.actualizarClienteJuridico(nombre, apellidos, identificacion, telefono, direccion, nit);
	}

}
