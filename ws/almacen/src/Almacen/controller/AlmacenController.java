package Almacen.controller;

import java.net.URL;
import java.util.ResourceBundle;

import Almacen.application.Aplicacion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AlmacenController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnVenta;

    @FXML
    private Button btnProducto;

    @FXML
    private Button btnClientes;

    private Aplicacion aplicacion;

    private Stage stage;


    public Aplicacion getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(Aplicacion aplicacion) {
		this.aplicacion = aplicacion;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@FXML
    void lanzarVentanaCliente(ActionEvent event) {
		aplicacion.mostrarVentanaCliente(event);
    }

    @FXML
    void lanzarVentanaProducto(ActionEvent event) {
    	aplicacion.mostrarVentanaProducto(event);
    }

    @FXML
    void lanzarVentanaVenta(ActionEvent event) {
    	aplicacion.mostrarVentanaVenta(event);
    }



}
