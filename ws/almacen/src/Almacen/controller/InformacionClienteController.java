package Almacen.controller;

import java.net.URL;
import java.util.ResourceBundle;

import Almacen.application.Aplicacion;
import Almacen.model.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class InformacionClienteController{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lblTelefono;

    @FXML
    private Button btnRegresar;

    @FXML
    private Label lblNombre;

    @FXML
    private Button btnContinuar;

    @FXML
    private Label lblIdentificacion;

    @FXML
    private Label lblApellidos;

    private String codigoVenta;

    private String fechaVenta;

    private Cliente clienteVenta;

    private Stage stage;

    private Aplicacion aplicacion;


    public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Aplicacion getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(Aplicacion aplicacion, String codigo, String fecha, Cliente cliente) {
		this.aplicacion = aplicacion;
		this.codigoVenta= codigo;
		this.fechaVenta = fecha;
		this.clienteVenta = cliente;

		lblNombre.setText(cliente.getNombre());
		lblApellidos.setText(cliente.getApellidos());
		lblIdentificacion.setText(cliente.getIdentificacion());
		lblTelefono.setText(cliente.getTelefono());
	}

	@FXML
    void regresarVentanaVentas(ActionEvent event) {
		aplicacion.mostrarVentanaVenta(event);
    }

    @FXML
    void continuarVenta(ActionEvent event) {
    	aplicacion.mostrarVentanaDetalleVenta(event, codigoVenta, fechaVenta, clienteVenta);
    }

}
