package Almacen.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

import Almacen.application.Aplicacion;
import Almacen.exception.ClienteException;
import Almacen.exception.ProductoException;
import Almacen.model.Almacen;
import Almacen.model.Cliente;
import Almacen.model.PaisOrigen;
import Almacen.model.Producto;
import Almacen.model.ProductoEnvasado;
import Almacen.model.ProductoPerecedero;
import Almacen.model.ProductoRefrigerado;
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

public class ProductoController implements Initializable{

	ModelFactoryController mfm = ModelFactoryController.getInstance();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtCantidadExistencia;

    @FXML
    private ComboBox<PaisOrigen> comboxPaisOrigen;

    @FXML
    private TableColumn<Producto, Integer> columnaCantidadExistencia;

    @FXML
    private TextField txtCodigoAprobacion;

    @FXML
    private TextField txtValorUnitario;

    @FXML
    private DatePicker dateFechaVencimiento;

    @FXML
    private DatePicker dateFechaEnvasado;

    @FXML
    private TextField txtPesoEnvase;

    @FXML
    private TextField txtTemperaturaRecomendada;

    @FXML
    private TextField txtNombre;

    @FXML
    private ComboBox<String> comboxTipoProducto;

    @FXML
    private Button btnRegresar;

    @FXML
    private TableColumn<Producto, Double> columnaValorUnitario;

    @FXML
    private Button btnEliminarProducto;

    @FXML
    private TextField txtCodigoProducto;

    @FXML
    private TableColumn<Producto, String> columnaCodigo;

    @FXML
    private TableView<Producto> tablaProductos;

    @FXML
    private TableColumn<Producto, String> columnaNombre;

    @FXML
    private Button btnRegistrarProducto;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private Button btnActualizarProducto;


    private Producto productoSeleccionado;

    private Almacen almacen;

    private Aplicacion aplicacion;

    private Stage stage;

    ObservableList<Producto>listadoProductos = FXCollections.observableArrayList();



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub


    	//Inicializo algunos datos que ya tengo guardados previamente
    	this.columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    	this.columnaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
    	this.columnaValorUnitario.setCellValueFactory(new PropertyValueFactory<>("valorUnitario"));
    	this.columnaCantidadExistencia.setCellValueFactory(new PropertyValueFactory<>("cantidadExistencia"));

    	//Este evento es para saber que cuenta es seleccionada en la tabla
    	tablaProductos.getSelectionModel().selectedItemProperty().addListener((obs,oldselection,newSelection) ->{
			if(newSelection != null){
				productoSeleccionado = newSelection;
				//mostrarInformacionProducto();
			}
		});

    	comboxTipoProducto.getItems().add("Perecedero");
    	comboxTipoProducto.getItems().add("Envasado");
    	comboxTipoProducto.getItems().add("Refrigerado");

    	comboxPaisOrigen.getItems().add(PaisOrigen.ARGENTINA);
    	comboxPaisOrigen.getItems().add(PaisOrigen.CHILE);
    	comboxPaisOrigen.getItems().add(PaisOrigen.COLOMBIA);
    	comboxPaisOrigen.getItems().add(PaisOrigen.ECUADOR);
    	comboxPaisOrigen.getItems().add(PaisOrigen.PERU);

    	txtCodigoAprobacion.setDisable(true);
    	txtPesoEnvase.setDisable(true);
    	txtTemperaturaRecomendada.setDisable(true);
    	dateFechaEnvasado.setDisable(true);
    	dateFechaVencimiento.setDisable(true);
    	comboxPaisOrigen.setDisable(true);

    	//Se crea un oyente al combobox para asegurarnos que cada vez que seleccionen una opcion
    	//se habiliten los campos de texto correspondientes
    	comboxTipoProducto.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				String seleccion = comboxTipoProducto.getValue();
				if(seleccion.equals("Perecedero")){

					txtCodigoAprobacion.setDisable(true);
			    	txtPesoEnvase.setDisable(true);
			    	txtTemperaturaRecomendada.setDisable(true);
			    	dateFechaEnvasado.setDisable(true);
			    	dateFechaVencimiento.setDisable(false);
			    	comboxPaisOrigen.setDisable(true);
				}

				if (seleccion.equals("Refrigerado")){

					txtCodigoAprobacion.setDisable(false);
			    	txtPesoEnvase.setDisable(true);
			    	txtTemperaturaRecomendada.setDisable(false);
			    	dateFechaEnvasado.setDisable(true);
			    	dateFechaVencimiento.setDisable(true);
			    	comboxPaisOrigen.setDisable(true);
				}

				if(seleccion.equals("Envasado")){

					txtCodigoAprobacion.setDisable(true);
			    	txtPesoEnvase.setDisable(false);
			    	txtTemperaturaRecomendada.setDisable(true);
			    	dateFechaEnvasado.setDisable(false);
			    	dateFechaVencimiento.setDisable(true);
			    	comboxPaisOrigen.setDisable(false);
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

		tablaProductos.getItems().clear();
		tablaProductos.setItems(getProductos());
	}


	private ObservableList<Producto> getProductos() {
		listadoProductos.addAll(mfm.getListaProductos());
		return listadoProductos;
	}


	public Stage getStage() {
		return stage;
	}


	public void setStage(Stage stage) {
		this.stage = stage;
	}

//-------------------------------------------------Metodos crud-------------------------------------------------------------------------
/**
 * Esta funcion sirve para registrar un producto y lanza mensaje de confirmacin
 * @param event
 * @throws ProductoException
 */
    @FXML
    void registrarProducto(ActionEvent event) throws ProductoException {

    	String codigo = txtCodigoProducto.getText();
    	String nombre = txtNombre.getText();
    	String cantidadExistencia2 = txtCantidadExistencia.getText();
    	String valorUnitario = txtValorUnitario.getText();
    	String descripcion = txtDescripcion.getText();
    	int cantidadExistencia = Integer.parseInt(cantidadExistencia2);

    	if(comboxTipoProducto.getValue()!=null){

    		if(comboxTipoProducto.getValue().equals("Perecedero")){
    			if(dateFechaVencimiento.getValue()!=null)
    			{

    			String fechaVencimiento = obtenerFechaComoString(dateFechaVencimiento, "dd/MM/yyyy");
    			if(validarCamposTextoProductoPerecedero(nombre, codigo, cantidadExistencia2, valorUnitario, descripcion, fechaVencimiento))
    				{
    				crearProductoPerecedero(codigo, nombre, cantidadExistencia2, valorUnitario, descripcion, fechaVencimiento);
    				}
    			}else
    			{
    				mostrarMensaje("Gestion de productos", "Proceso incorrecto", "La fecha no esta ingresada correctamente", AlertType.INFORMATION);
    			}
    		}

    		if(comboxTipoProducto.getValue().equals("Refrigerado"))
    		{

    			String codigoAprobacion = txtCodigoAprobacion.getText();
    			String temperaturaRefrigeracion = txtTemperaturaRecomendada.getText();

    			if(validarCamposTextoProductoRefrigerado(nombre, codigo, cantidadExistencia2, valorUnitario, descripcion, codigoAprobacion, temperaturaRefrigeracion))
    			{
    			crearProductoRefrigerado(codigo, nombre, cantidadExistencia, valorUnitario, descripcion, temperaturaRefrigeracion, codigoAprobacion);
    			}
    		}
    		if(comboxTipoProducto.getValue().equals("Envasado")){

    			if(dateFechaEnvasado.getValue()!=null){
    			String pesoEnvasado = txtPesoEnvase.getText();
    			PaisOrigen paisOrigen = comboxPaisOrigen.getValue();
    			String fechaEnvasado = obtenerFechaComoString(dateFechaEnvasado, "dd/MM/yyyy");
    			if(validarCamposTextoProductoEnvasado(nombre, codigo, cantidadExistencia2, valorUnitario, descripcion, paisOrigen, pesoEnvasado, fechaEnvasado)){
    				crearProductoEnvasado(nombre, codigo, cantidadExistencia, valorUnitario, descripcion, paisOrigen, pesoEnvasado, fechaEnvasado);


    					}
    				}
    			}
    		}
    	}

/**
 * Esta funcion me actualiza los datos de un producto y lanza un mensaje de confirmacin
 * @param event
 * @throws ProductoException
 */
	@FXML
    void actualizarProducto(ActionEvent event) throws ProductoException {

		if(productoSeleccionado!= null){

	    	String codigo = txtCodigoProducto.getText();
	    	String nombre = txtNombre.getText();
	    	String cantidadExistencia2 = txtCantidadExistencia.getText();
	    	String valorUnitario2 = txtValorUnitario.getText();
	    	String descripcion = txtDescripcion.getText();
	    	int cantidadExistencia = Integer.parseInt(cantidadExistencia2);
	    	Double valorUnitario = Double.parseDouble(valorUnitario2);

	    	if(productoSeleccionado instanceof ProductoPerecedero){
	    		if(dateFechaVencimiento!=null){
	    			String fechaVencimiento = obtenerFechaComoString(dateFechaVencimiento, "dd/MM/yyyy");

	    			if(validarCamposTextoProductoPerecedero(nombre, codigo, cantidadExistencia2, valorUnitario2, descripcion, fechaVencimiento)){
	    				actualizarProductoPerecedero(nombre, codigo, cantidadExistencia, valorUnitario, descripcion, fechaVencimiento);
	    			}

	    		}
	    	}
	    	if(productoSeleccionado instanceof ProductoRefrigerado)
	    	{
	    		String codigoAprobacion = txtCodigoAprobacion.getText();
	    		String temperaturaRecomendada = txtTemperaturaRecomendada.getText();
	    		if(validarCamposTextoProductoRefrigerado(nombre, codigo, cantidadExistencia2, valorUnitario2, descripcion, codigoAprobacion, temperaturaRecomendada))
	    		{
	    			actualizarProductoRefrigerado(nombre, codigo, cantidadExistencia, valorUnitario, descripcion, temperaturaRecomendada, codigoAprobacion);
	    		}
	    	}
	    	if(productoSeleccionado instanceof ProductoEnvasado){
	    		if(dateFechaEnvasado!=null)
	    		{
	    			String fechaEnvasado = obtenerFechaComoString(dateFechaEnvasado, "dd/MM/yyyy");
	    			String pesoEnvase2 = txtPesoEnvase.getText();
	    			double pesoEnvasado = Double.parseDouble(pesoEnvase2);
	    			PaisOrigen paisOrigen = comboxPaisOrigen.getValue();
	    			if(validarCamposTextoProductoEnvasado(nombre, codigo, cantidadExistencia2, valorUnitario2, descripcion, paisOrigen, pesoEnvase2, fechaEnvasado)){
	    				actualizarProductoEnvasado(nombre, codigo, cantidadExistencia, valorUnitario, descripcion, paisOrigen, pesoEnvasado, fechaEnvasado);
	    			}
	    		}

	    	}


    }
}

/**
 * esta funcion elimina un producto y lanza mensaje de confirmacin
 * @param event
 * @throws ProductoException
 */
    @FXML
    void eliminarProducto(ActionEvent event) throws ProductoException {

    	if(productoSeleccionado!=null){
    		if(mfm.eliminarProducto(productoSeleccionado)){
    			actualizarTabla();
    			limpiarCamposTexto();
    			mostrarMensaje("Gestin productos", "Proceso exitoso", "Se elimino correctamente el producto", AlertType.CONFIRMATION);
    		}
    	}else{
			mostrarMensaje("Gestin productos", "Proceso incompleto", "Tiene que seleccionar un producto para poderlo eliminar", AlertType.INFORMATION);

    	}

    }

/**
 * Esta funcion me sirve para que el usuario solo pueda ingresar numeros en algunos campos de texto
 * @param event
 */
    @FXML
    void validarIngresoNumeros(KeyEvent event) {
       	if(event.getTarget() == txtCantidadExistencia){
    			if(!Character.isDigit(event.getCharacter().charAt(0))){
    				event.consume();
    			}
    		}
        	if(event.getTarget() == txtTemperaturaRecomendada){
    			if(!Character.isDigit(event.getCharacter().charAt(0))){
    				event.consume();
    			}
    		}
        	if(event.getTarget() == txtValorUnitario){
    			if(!Character.isDigit(event.getCharacter().charAt(0))){
    				event.consume();
    			}
    		}

    }

    /**
     * Este metodo funciona para regresar a almacen
     * @param event
     */
    @FXML
    void regresarAlmacen(ActionEvent event) {
    	aplicacion.mostrarVentanaAlmacen(event);
    }

  //-----------------------------------------------Metodos necesarios-------------------------------------------------------------------------
    /**
	 * Este metodo tiene como funcion convertir un string a un dato DatePicker, en caso de que no sea posible
	 * muestra un mensaje al usuario
	 * @param datePicker
	 * @param fechaString
	 */
	public void setFechaEnDatePicker(DatePicker datePicker, String fechaString) {
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate fechaLocalDate = LocalDate.parse(fechaString, formato);
            datePicker.setValue(fechaLocalDate);
        } catch (DateTimeParseException e) {
            mostrarMensaje("Gestin clientes", "Proceso incorrecto", "No fue posible mostrar la fecha de nacimiento\n ya que se encuentra en un formato no disponible", AlertType.INFORMATION);
        }
    }

	 /**
	  * Este metodo me convierte un dato de un datePicker en un string
	  * @param datePicker
	  * @param formato
	  * @return
	  */
	 public String obtenerFechaComoString(DatePicker datePicker, String formato) {
		 	LocalDate fechaSeleccionada = datePicker.getValue();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);

	        try {
	            return fechaSeleccionada.format(formatter);
	        } catch (DateTimeParseException e) {
	            return null;
	        }

	   }
	 	/**
	 	 * Este metodo sirve para mostrar las alertas
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
	     * Este metodo me sirve para limpiar la tabla usando el .clear
	     */
		private void actualizarTabla(){
			tablaProductos.getItems().clear();
			tablaProductos.setItems(getProductos());
		}

		/**
		 * Este metodo me limpia todos los datos de la ventana
		 */
		private void limpiarCamposTexto(){

	    	txtCodigoAprobacion.setText("");
	    	txtPesoEnvase.setText("");
	    	txtTemperaturaRecomendada.setText("");
	    	dateFechaEnvasado.setValue(null);
	    	dateFechaVencimiento.setValue(null);
	    	comboxPaisOrigen.setValue(null);
	    	comboxTipoProducto.setValue(null);
	    	txtNombre.setText("");
	    	txtValorUnitario.setText("");
	    	txtCantidadExistencia.setText("");
	    	txtCodigoProducto.setText("");
	    	txtDescripcion.setText("");

		}

//--------------------------------------metodos para crear productos segun su tipo---------------------------------------------------------------

		/**
		 * Este metodo sirve para crear un producto refrigerado
		 * @param codigo
		 * @param nombre
		 * @param cantidadExistencia
		 * @param valorUnitario2
		 * @param descripcion
		 * @param temperaturaRecomendada
		 * @param codigoAprobacion
		 */
				private void crearProductoRefrigerado(String codigo, String nombre, int cantidadExistencia, String valorUnitario2,
						String descripcion, String temperaturaRecomendada, String codigoAprobacion) {
					// TODO Auto-generated method stub

					int temperaturaRefrigeracion = Integer.parseInt(temperaturaRecomendada);
					Double valorUnitario = Double.parseDouble(valorUnitario2);

			    	boolean fueCreado;
			    	try {
			    		fueCreado = mfm.crearProductoRefrigerado(codigo, nombre, descripcion, valorUnitario, cantidadExistencia, temperaturaRefrigeracion , codigoAprobacion);
			    		if(fueCreado){
			    			mostrarMensaje("Gestin productos","Proceso exitoso", "Se creo correctamente el producto", AlertType.CONFIRMATION);
			    			actualizarTabla();
			    			limpiarCamposTexto();
			    		}
					} catch (ProductoException e) {
						mostrarMensaje("Gestin productos", "Proceso incorrecto", "El producto ya existe", AlertType.ERROR);

						}

				}

		/**
		 * Este metodo me sirve para crear un producto perecedero
		 * @param codigo
		 * @param nombre
		 * @param cantidaExistencia
		 * @param valorUnitario2
		 * @param descripcion
		 * @param fechaVencimiento
		 * @throws ProductoException
		 */
	    private void crearProductoPerecedero(String codigo, String nombre, String cantidaExistencia, String valorUnitario2,
			String descripcion, String fechaVencimiento) throws ProductoException {
		// TODO Auto-generated method stub

	    	int cantidadExistencia = Integer.parseInt(cantidaExistencia);
	    	Double valorUnitario = Double.parseDouble(valorUnitario2);

	    	boolean fueCreado;
	    	try {
	    		fueCreado = mfm.crearProductoPerecedero(codigo, nombre, descripcion, valorUnitario, cantidadExistencia, fechaVencimiento);
	    		if(fueCreado){
	    			mostrarMensaje("Gestin Productos","Proceso exitoso", "Se creo correctamente el producto", AlertType.CONFIRMATION);
	    			actualizarTabla();
	    			limpiarCamposTexto();
	    		}
			} catch (ProductoException e) {
				mostrarMensaje("Gestin Productos", "Proceso incorrecto", "El producto ya existe", AlertType.ERROR);

				}
	    }

	/**
	 * Este metodo sirve para crear un producto Envasado
	 * @param nombre
	 * @param codigo
	 * @param cantidadExistencia
	 * @param valorUnitario2
	 * @param descripcion
	 * @param paisOrigen
	 * @param pesoEnvasado
	 * @param fechaEnvasado
	 */
		private void crearProductoEnvasado(String nombre, String codigo, int cantidadExistencia, String valorUnitario2,
				String descripcion, PaisOrigen paisOrigen, String pesoEnvasado, String fechaEnvasado) {
			// TODO Auto-generated method stub
			Double valorUnitario = Double.parseDouble(valorUnitario2);
	    	boolean fueCreado;
	    	try {
	    		fueCreado = mfm.crearProductoEnvasado(codigo, nombre, descripcion, valorUnitario, cantidadExistencia, paisOrigen, pesoEnvasado, fechaEnvasado);
	    		if(fueCreado){
	    			mostrarMensaje("Gestin productos","Proceso exitoso", "Se creo correctamente el producto", AlertType.CONFIRMATION);
	    			actualizarTabla();
	    			limpiarCamposTexto();
	    		}
			} catch (ProductoException e) {
				mostrarMensaje("Gestin productos", "Proceso incorrecto", "El producto ya existe", AlertType.ERROR);
			}

		}

		//-------------------------Metodos para validar que los campos de texto esten completos segun el tipo de producto------------------------



	    public boolean validarCamposTextoProductoPerecedero(String nombre, String codigo, String cantidadExistencia, String valorUnitario, String descripcion, String fechaVencimiento){

	    	String msj ="Debe completar los siguientes campos de texto para poder continuar:\n";
	    	if(nombre.equals("") || nombre == null){
	    		msj+="El nombre\n";
	    	}
	    	if(codigo.equals("") || codigo == null){
	    		msj+="El codigo\n";
	    	}
	    	if(cantidadExistencia.equals("") || cantidadExistencia == null){
	    		msj+="La cantidad en existencia\n";
	    	}
	    	if(valorUnitario.equals("") || valorUnitario==null){
	    		msj+="El valor unitario\n";
	    	}
	    	if(descripcion.equals("") || descripcion == null){
	    		msj+="La descripcion\n";
	    	}
	    	if(fechaVencimiento.equals("")|| fechaVencimiento== null){
	    		msj+="La fecha de vencimiento\n";
	    	}
	    	if(msj.equals("Debe completar los siguientes campos de texto para poder continuar:\n")){
	    		return true;
	    	}
	    	mostrarMensaje("Gestin productos", "Proceso incorrecto", msj, AlertType.INFORMATION);
	    	return false;
	    }

	    public boolean validarCamposTextoProductoRefrigerado(String nombre, String codigo, String cantidadExistencia, String valorUnitario, String descripcion, String codigoAprobacion, String temperaturaRecomendada ){

	    	String msj ="Debe completar los siguientes campos de texto para poder continuar:\n";
	    	if(nombre.equals("") || nombre == null){
	    		msj+="El nombre\n";
	    	}
	    	if(codigo.equals("") || codigo == null){
	    		msj+="El codigo\n";
	    	}
	    	if(cantidadExistencia.equals("") || cantidadExistencia == null){
	    		msj+="La cantidad en existencia\n";
	    	}
	    	if(valorUnitario.equals("") || valorUnitario==null){
	    		msj+="El valor unitario\n";
	    	}
	    	if(descripcion.equals("") || descripcion == null){
	    		msj+="La descripcion\n";
	    	}
	    	if(codigoAprobacion.equals("")|| codigoAprobacion == null){
	    		msj+="El codigo de aprobacion";
	    	}
	    	if(temperaturaRecomendada.equals("")|| temperaturaRecomendada == null){
	    		msj+="La temperatura recomendada en grados celsius";
	    	}
	    	if(msj.equals("Debe completar los siguientes campos de texto para poder continuar:\n")){
	    		return true;
	    	}
	    	mostrarMensaje("Gestion productos", "Proceso incorrecto", msj, AlertType.INFORMATION);
	    	return false;
	    }

	    public boolean validarCamposTextoProductoEnvasado(String nombre, String codigo, String cantidadExistencia, String valorUnitario, String descripcion, PaisOrigen paisOrigen, String pesoEnvasado, String fechaEnvasado){

	    	String msj ="Debe completar los siguientes campos de texto para poder continuar:\n";
	    	if(nombre.equals("") || nombre == null){
	    		msj+="El nombre\n";
	    	}
	    	if(codigo.equals("") || codigo == null){
	    		msj+="El codigo\n";
	    	}
	    	if(cantidadExistencia.equals("") || cantidadExistencia == null){
	    		msj+="La cantidad en existencia\n";
	    	}
	    	if(valorUnitario.equals("") || valorUnitario==null){
	    		msj+="El valor unitario\n";
	    	}
	    	if(descripcion.equals("") || descripcion == null){
	    		msj+="La descripcion\n";
	    	}
	    	if(paisOrigen.equals("") || descripcion == null){
	    		msj+="El pais de origen\n";
	    	}
	    	if(pesoEnvasado.equals("") || descripcion == null){
	    		msj+="El peso del envasado\n";
	    	}
	    	if(fechaEnvasado.equals("") || descripcion == null){
	    		msj+="La fecha de envasado\n";
	    	}

	    	if(msj.equals("Debe completar los siguientes campos de texto para poder continuar:\n")){
	    		return true;
	    	}
	    	mostrarMensaje("Gestion productos", "Proceso incorrecto", msj, AlertType.INFORMATION);
	    	return false;
	    }

//------------------------------------Metodos para actualizar los datos de un producto segun su tipo-------------------------------------------

	    private void actualizarProductoPerecedero(String nombre, String codigo, int cantidadExistencia, double valorUnitario,
	    		String descripcion, String fechaVencimiento) throws ProductoException {

			 try {
					mfm.actualizarProductoPerecedero(nombre, codigo, cantidadExistencia, valorUnitario, descripcion, fechaVencimiento);
					mostrarMensaje("Gestion productos", "Proceso correcto", "El producto se actualizo correctamente", AlertType.CONFIRMATION);
					actualizarTabla();
					limpiarCamposTexto();

				} catch (ProductoException e) {
					mostrarMensaje("Gestion productos", "Proceso incompleto", "El producto no existe", AlertType.INFORMATION);
					}
				}




	    private void actualizarProductoRefrigerado(String nombre, String codigo, int cantidadExistencia, double valorUnitario,
	    		String descripcion, String refrigeracionRecomendada, String codigoAprobacion) {
	    	int temRecomendada  = Integer.parseInt(refrigeracionRecomendada);
			 try {
					mfm.actualizarProductoRefrigerado(nombre, codigo, cantidadExistencia, valorUnitario, descripcion, temRecomendada, codigoAprobacion);
					mostrarMensaje("Gestion productos", "Proceso correcto", "El producto se actualizo correctamente", AlertType.CONFIRMATION);
					actualizarTabla();
					limpiarCamposTexto();

				} catch (ProductoException e) {
					mostrarMensaje("Gestin productos", "Proceso incompleto", "El producto no existe", AlertType.INFORMATION);
					}
				}

	    private void actualizarProductoEnvasado(String nombre, String codigo, int cantidadExistencia, Double valorUnitario,
	    		String descripcion, PaisOrigen paisOrigen, double pesoEnvasado, String fechaEnvasado) {
	    	// TODO Auto-generated method stub
			 try {
					mfm.actualizarProductoEnvasado(nombre, codigo, cantidadExistencia, valorUnitario, descripcion, paisOrigen, pesoEnvasado, fechaEnvasado);
					mostrarMensaje("Gestin productos", "Proceso correcto", "El producto se actualizo correctamente", AlertType.CONFIRMATION);
					actualizarTabla();
					limpiarCamposTexto();

				} catch (ProductoException e) {
					mostrarMensaje("Gestion productos", "Proceso incompleto", "El producto no existe", AlertType.INFORMATION);
					}


	    }







}

