package Almacen.model;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ClienteNatural extends Cliente{

	private String fechaNacimiento;
	private String email;

	public ClienteNatural(String nombre, String apellidos, String identificacion, String telefono, String direccion,
			String fechaNacimiento, String email) {
		super(nombre, apellidos, identificacion, telefono, direccion);
		this.fechaNacimiento = fechaNacimiento;
		this.email = email;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fechaNacimiento == null) ? 0 : fechaNacimiento.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClienteNatural other = (ClienteNatural) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (fechaNacimiento == null) {
			if (other.fechaNacimiento != null)
				return false;
		} else if (!fechaNacimiento.equals(other.fechaNacimiento))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return super.toString()+"ClienteNatural [fechaNacimiento=" + fechaNacimiento + ", email=" + email + "]";
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
