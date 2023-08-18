package Almacen.controller;

import java.net.URL;
import java.util.ResourceBundle;

import Almacen.application.Aplicacion;
import Almacen.exception.ClienteException;
import Almacen.model.Almacen;
import Almacen.model.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class ClienteController implements Initializable{

	ModelFactoryController mfm = ModelFactoryController.getInstance();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TableColumn<Cliente, String> columnaTelefono;

    @FXML
    private ComboBox<String> comboxTipoCliente;

    @FXML
    private TableView<Cliente> tablaClientes;

    @FXML
    private DatePicker dateFechaNacimiento;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNit;

    @FXML
    private TableColumn<Cliente, String> columnaIdentificacion;

    @FXML
    private TextField txtIdentificacion;

    @FXML
    private TextField txtNombre;

    @FXML
    private TableColumn<Cliente, String> columnaApellido;

    @FXML
    private Button btnEliminar;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtApellido;

    @FXML
    private TableColumn<Cliente, String> columnaNombre;

    @FXML
    private Button btnRegistrar;

    @FXML
    private Button btnActualizar;

    private Aplicacion aplicacion;

    private Cliente clienteSeleccionado;

    private Stage stage;

    private Almacen almacen;

    ObservableList<Cliente>listadoClientes = FXCollections.observableArrayList();

    @Override
	public void initialize(URL location, ResourceBundle resources) {

    	//Inicializo algunos datos que ya tengo guardados previamente
    	this.columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    	this.columnaIdentificacion.setCellValueFactory(new PropertyValueFactory<>("identificacion"));
    	this.columnaApellido.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
    	this.columnaTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

    	//Este evento es para saber que cuenta es seleccionada en la tabla
    	tablaClientes.getSelectionModel().selectedItemProperty().addListener((obs,oldselection,newSelection) ->{
			if(newSelection != null){
				clienteSeleccionado = newSelection;
			}
		});

    	comboxTipoCliente.getItems().add("Natural");
    	comboxTipoCliente.getItems().add("Juridico");

    	txtNit.setDisable(true);
    	txtEmail.setDisable(true);
    	dateFechaNacimiento.setDisable(true);

    	//Se crea un oyente al combobox para asegurarnos que cada vez que seleccionen una opcion
    	//se habiliten los campos de texto correspondientes
    	comboxTipoCliente.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {

				String seleccion = comboxTipoCliente.getValue();
				if(seleccion.equals("Natural")){
					txtNit.setDisable(true);
			    	txtEmail.setDisable(false);
			    	dateFechaNacimiento.setDisable(false);
				}
				if(seleccion.equals("Juridico")){
					txtNit.setDisable(false);
			    	txtEmail.setDisable(true);
			    	dateFechaNacimiento.setDisable(true);
				}
			}

    	});
	}

	public Aplicacion getAplicacion() {
		return aplicacion;
	}


	public void setAplicacion(Aplicacion aplicacion) {
		this.aplicacion = aplicacion;
		this.almacen = mfm.getAlmacen();

		tablaClientes.getItems().clear();
		tablaClientes.setItems(getClientes());
	}

	public Stage getStage() {
		return stage;
	}


	public void setStage(Stage stage) {
		this.stage = stage;
	}


	//-------------------------------------METODOS CRUD--------------------------------

	/**
	 * Este evento es el encargado de llamar a los metodos necesarios para crear los diferentes tipos de clientes
	 * @param event
	 */
	@FXML
    void registrarCliente(ActionEvent event) {
    	String nombre = txtNombre.getText();
    	String apellidos = txtApellido.getText();
    	String identificacion = txtIdentificacion.getText();
    	String telefono = txtTelefono.getText();
    	String direccion = txtDireccion.getText();

    	if(comboxTipoCliente.getValue()!=null){
    		String tipoCliente = comboxTipoCliente.getValue();

    		if(tipoCliente.equals("Natural")){
        		String email = txtEmail.getText();
            	String fechaNacimiento = ""+dateFechaNacimiento.getValue();
            	if(validarCamposTextoClienteNatural(nombre, apellidos, identificacion, telefono,
            			direccion, email, fechaNacimiento)){
            		crearClienteNatural(nombre, apellidos, identificacion, telefono,
            			direccion, email, fechaNacimiento);
            	}
        	}

        	if(tipoCliente.equals("Juridico")){
        		String nit = txtNit.getText();
        		if(validarCamposTextoClienteJuridico(nombre, apellidos, identificacion, telefono, direccion, nit)){
            		crearClienteJuridico(nombre, apellidos, identificacion, telefono, direccion, nit);
        		}
        	}
    	}else{
    		mostrarMensaje("Gesti�n cliente", "Proceso incompleto", "Por favor llenar correctamente el formulario", AlertType.INFORMATION);
    	}

    }

	@FXML
    void actualizarCliente(ActionEvent event) {

    }

	/**
	 * Este evento me elimina un cliente seleccionado
	 * @param event
	 * @throws ClienteException
	 */
    @FXML
    void eliminarCliente(ActionEvent event) throws ClienteException {

    	if(clienteSeleccionado!=null){
    		if(mfm.eliminarCliente(clienteSeleccionado)){
    			actualizarTabla();
    			mostrarMensaje("Gesti�n clientes", "Proceso exitoso", "Se elimino correctamente el cliente", AlertType.CONFIRMATION);
    		}
    	}else{
			mostrarMensaje("Gesti�n clientes", "Proceso incompleto", "Tiene que seleccionar un cliente para poderlo eliminar", AlertType.INFORMATION);

    	}

    }

    //-------------------------------------METODOS NECESARIOS--------------------------

    /**
     * Este evento me verifica que en algunos campos de texto solo se ingresen numeros
     * @param event
     */
    @FXML
    void validarIngresoNumeros(KeyEvent event) {
    	if(event.getTarget() == txtTelefono){
			if(!Character.isDigit(event.getCharacter().charAt(0))){
				event.consume();
			}
		}
    	if(event.getTarget() == txtIdentificacion){
			if(!Character.isDigit(event.getCharacter().charAt(0))){
				event.consume();
			}
		}
    }

	/**
	 * Este metodo es para pedir la lista de clientes del almacen
	 * @return
	 */
	private ObservableList<Cliente> getClientes() {
		listadoClientes.addAll(mfm.getListaClientes());
		return listadoClientes;
	}

	/**
	 * Este metodo me actualiza los datos de la tabla
	 */
	private void actualizarTabla(){
		tablaClientes.getItems().clear();
		tablaClientes.setItems(getClientes());
	}

    /**
     * Muestra un mensaje dependiendo con el tipo de alerta seleccionado
     * @param title
     * @param header
     * @param content
     * @param alertType
     */
    public void mostrarMensaje(String title, String header, String content, AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
    }

    /**
     * Este metodo es para verificar que los campos de texto para registrar un cliente natural sean correctos
     * @param nombre
     * @param apellidos
     * @param identificacion
     * @param telefono
     * @param direccion
     * @param email
     * @param fechaNacimiento
     * @return
     */
    public boolean validarCamposTextoClienteNatural(String nombre, String apellidos, String identificacion, String telefono, String direccion,
			String email, String fechaNacimiento){

    	String msj = "Debe completar los siguientes campos de texto para poder registrar al cliente:\n";
    	if(nombre.equals("") || nombre == null){
    		msj+="El nombre\n";
    	}
    	if(apellidos.equals("") || apellidos == null){
    		msj+="El apellido\n";
    	}
    	if(identificacion.equals("") || identificacion == null){
    		msj+="La identificacion\n";
    	}
    	if(telefono.equals("") || telefono==null){
    		msj+="El telefono\n";
    	}
    	if(direccion.equals("") || direccion == null){
    		msj+="La direccion\n";
    	}
    	if(email.equals("") || email==null){
    		msj+="El email\n";
    	}
    	if(fechaNacimiento.equals("") || fechaNacimiento.equals("null")){
    		msj+="La fecha de nacimiento";
    	}
    	if(msj.equals("Debe completar los siguientes campos de texto para poder registrar al cliente:\n")){
    		return true;
    	}
    	mostrarMensaje("Gesti�n clientes", "Proceso incorrecto", msj, AlertType.INFORMATION);
    	return false;
    }

    /**
     * Este metodo es para verificar que los campos de texto para registrar un cliente juridico sean correctos
     * @param nombre
     * @param apellidos
     * @param identificacion
     * @param telefono
     * @param direccion
     * @param email
     * @param fechaNacimiento
     * @return
     */
    public boolean validarCamposTextoClienteJuridico(String nombre, String apellidos, String identificacion, String telefono, String direccion,
			String nit){

    	String msj ="Debe completar los siguientes campos de texto para poder registrar al cliente:\n";
    	if(nombre.equals("") || nombre == null){
    		msj+="El nombre\n";
    	}
    	if(apellidos.equals("") || apellidos == null){
    		msj+="El apellido\n";
    	}
    	if(identificacion.equals("") || identificacion == null){
    		msj+="La identificacion\n";
    	}
    	if(telefono.equals("") || telefono==null){
    		msj+="El telefono\n";
    	}
    	if(direccion.equals("") || direccion == null){
    		msj+="La direccion\n";
    	}

    	if(nit.equals("") || nit==null){
    		msj+="El nit";
    	}
    	if(msj.equals("Debe completar los siguientes campos de texto para poder registrar al cliente:\n")){
    		return true;
    	}
    	mostrarMensaje("Gesti�n clientes", "Proceso incorrecto", msj, AlertType.INFORMATION);
    	return false;
    }

    /**
     * Este metodo me crea un cliente juridico
     * @param nombre
     * @param apellidos
     * @param identificacion
     * @param telefono
     * @param direccion
     * @param nit
     */
    private void crearClienteJuridico(String nombre, String apellidos, String identificacion, String telefono,
			String direccion, String nit) {
		boolean fueCreado;
    	try {
    		fueCreado = mfm.crearClienteJuridico(nombre,apellidos,identificacion,telefono,direccion,nit);
    		if(fueCreado){
    			mostrarMensaje("Gesti�n clientes","Proceso exitoso", "Se creo correctamente el cliente", AlertType.CONFIRMATION);
    			actualizarTabla();
    			limpiarCamposTexto();
    		}
		} catch (ClienteException e) {
			mostrarMensaje("Gesti�n clientes", "Proceso incorrecto", "El cliente ya existe", AlertType.ERROR);
		}

	}

    /**
     * Este metodo me crea un cliente natural
     * @param nombre
     * @param apellidos
     * @param identificacion
     * @param telefono
     * @param direccion
     * @param email
     * @param fechaNacimiento
     */
	private void crearClienteNatural(String nombre, String apellidos, String identificacion, String telefono,
			String direccion, String email, String fechaNacimiento) {
		boolean fueCreado;
    	try {
    		fueCreado = mfm.crearClienteNatural(nombre,apellidos,identificacion,telefono,direccion,
    				email,fechaNacimiento);
    		if(fueCreado){
    			mostrarMensaje("Gesti�n clientes","Proceso exitoso", "Se creo correctamente el cliente", AlertType.CONFIRMATION);
    			actualizarTabla();
    			limpiarCamposTexto();
    		}
		} catch (ClienteException e) {
			mostrarMensaje("Gesti�n clientes", "Proceso incorrecto", "El cliente ya existe", AlertType.ERROR);
		}

	}

	/**
	 * Este metodo es para limpiar todos los campos de texto al momento de registrar un cliente
	 */
	private void limpiarCamposTexto(){
		txtNombre.setText("");
		txtApellido.setText("");
		txtIdentificacion.setText("");
		txtTelefono.setText("");
		txtNit.setText("");
		txtDireccion.setText("");
		txtEmail.setText("");
		dateFechaNacimiento.setValue(null);

	}
}
