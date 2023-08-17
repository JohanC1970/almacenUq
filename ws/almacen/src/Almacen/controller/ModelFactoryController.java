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
				"calle", "04", "gar");

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

	public List<Cliente> getListaClientes() {
		return almacen.getListaClientes();
	}

	public boolean eliminarCliente(Cliente clienteSeleccionado) throws ClienteException {

		if(almacen.EliminarCliente(clienteSeleccionado.getIdentificacion())){
			return true;
		}

		return false;
	}


}
