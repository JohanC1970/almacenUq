package Almacen.application;

import java.io.IOException;

import Almacen.controller.AlmacenController;
import Almacen.controller.ClienteController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Aplicacion extends Application{

	private Stage primaryStage;

	@Override
	public void start(Stage stage) throws Exception {
		this.primaryStage=stage;
		mostrarVentanaPrincipal();
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	private void mostrarVentanaPrincipal() {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Aplicacion.class.getResource("/Almacen/view/AlmacenView.fxml"));
			AnchorPane anchorPane = (AnchorPane)loader.load();
			AlmacenController almacenController = loader.getController();
			almacenController.setAplicacion(this);

			Scene scene = new Scene(anchorPane);
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (IOException e) {
			// TODO: handle exception
		}

	}

	/**
	 * Este metodo muestra la ventana de gestion clientes
	 * @param event
	 */
	public void mostrarVentanaCliente(ActionEvent event){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Aplicacion.class.getResource("/Almacen/view/ClienteView.fxml"));
			AnchorPane anchorPane = (AnchorPane)loader.load();
			ClienteController clienteController = loader.getController();
			clienteController.setAplicacion(this);

			Scene scene = new Scene(anchorPane);
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (IOException e) {
			// TODO: handle exception
		}
	}

	/**
	 *Este evento muestra la ventana del almacen
	 * @param event
	 */
	public void mostrarVentanaAlmacen(ActionEvent event){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Aplicacion.class.getResource("/Almacen/view/AlmacenView.fxml"));
			AnchorPane anchorPane = (AnchorPane)loader.load();
			AlmacenController almacenController = loader.getController();
			almacenController.setAplicacion(this);

			Scene scene = new Scene(anchorPane);
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (IOException e) {
			// TODO: handle exception
		}
	}

	public static void main(String[]args){
		launch(args);
	}

}
