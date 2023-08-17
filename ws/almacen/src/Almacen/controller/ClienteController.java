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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
    private Label lblNit;

    @FXML
    private Label lblFechaNacimiento;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtEmail;

    @FXML
    private TableColumn<Cliente,String> columnApellido;

    @FXML
    private Label lblEmail;

    @FXML
    private TextField txtNit;

    @FXML
    private TableColumn<Cliente,String> columnNombre;

    @FXML
    private DatePicker dateFecha;

    @FXML
    private TextField txtIdentificacion;

    @FXML
    private TableColumn<Cliente, String> columnTipoCliente;

    @FXML
    private TableView<Cliente> tableClientes;

    @FXML
    private TableColumn<Cliente,String> columnTelefono;

    @FXML
    private TextField txtNombre;

    @FXML
    private TableColumn<Cliente,String> columnIdentificacion;

    @FXML
    private Button btnEliminar;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtApellido;

    @FXML
    private ComboBox<String> comBoxTipoCliente;

    @FXML
    private Button btnRegistrar;

    @FXML
    private Button btnActualizar;

    private Stage stage;

    private Aplicacion aplicacion;

    private Cliente clienteSeleccionado;

    private Almacen almacen;

    ObservableList<Cliente> listadoClientes = FXCollections.observableArrayList();


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		//Inicializacion de la tabla de clientes
		this.columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		this.columnApellido.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
		this.columnIdentificacion.setCellValueFactory(new PropertyValueFactory<>("identificacion"));
		this.columnTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
		this.columnTipoCliente.setCellValueFactory(new PropertyValueFactory<>("tipoCliente"));

		//Inicializacion del combobox
		comBoxTipoCliente.getItems().addAll("Natural");
		comBoxTipoCliente.getItems().addAll("Juridica");

		//SELECCION EN LA TABLA DE CLIENTES
		tableClientes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if(newSelection != null) {
				clienteSeleccionado = newSelection;
				mostrarInformacionCliente();
				}
			});


	}


    public Stage getStage() {
		return stage;
	}


	public void setStage(Stage stage) {
		this.stage = stage;
	}


	public Aplicacion getAplicacion() {
		return aplicacion;
	}


	public void setAplicacion(Aplicacion aplicacion) {
		this.aplicacion = aplicacion;
		this.almacen = mfm.almacen;

		tableClientes.getItems().clear();
		tableClientes.setItems(getClientes());
	}


	private ObservableList<Cliente> getClientes() {
		listadoClientes.addAll(mfm.getListaClientes());
		return listadoClientes;
	}


	private void mostrarInformacionCliente() {
		// TODO Auto-generated method stub

	}

	/**
     * Este evento es para validar que solo ingresen numeros en los respectivos campos de texto
     * @param event
     */
    @FXML
    void validarIngresoNumeros(KeyEvent event) {
    	if(event.getTarget() == txtIdentificacion){
			if(!Character.isDigit(event.getCharacter().charAt(0))){
				event.consume();
			}
		}
    	if(event.getTarget() == txtTelefono){
			if(!Character.isDigit(event.getCharacter().charAt(0))){
				event.consume();
			}
		}
    }

    /**
     * Este metodo me elimina un cliente seleccionado de la tabla
     * @param event
     * @throws ClienteException
     */
    @FXML
    void eliminarCliente(ActionEvent event) throws ClienteException {

    	if(clienteSeleccionado != null){
    		if(mfm.eliminarCliente(clienteSeleccionado)){
    			mostrarMensaje("Informacion cliente", "Proceso exitoso", "Se elimino correctamente el cliente", AlertType.CONFIRMATION);
    			actualizarTabla();
    		}
    	}else{
			mostrarMensaje("Informacion cliente", "Proceso incorrecto", "Debe selecccionar un cliente para poderlo eliminar", AlertType.ERROR);

    	}
    }

    /**
     * Este metodo me actualiza los datos de la tabla
     */
    private void actualizarTabla() {
    	tableClientes.getItems().clear();
    	tableClientes.setItems(getClientes());

	}


	@FXML
    void actualizarCliente(ActionEvent event) {

    }

    @FXML
    void registrarCliente(ActionEvent event) {

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
}
