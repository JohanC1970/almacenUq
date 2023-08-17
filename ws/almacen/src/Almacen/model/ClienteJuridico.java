package Almacen.model;

public class ClienteJuridico extends Cliente{

	private String nit;

	public ClienteJuridico(String nombre, String apellidos, String identificacion, String telefono, String direccion,
			String nit) {
		super(nombre, apellidos, identificacion, telefono, direccion);
		this.nit = nit;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((nit == null) ? 0 : nit.hashCode());
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
		ClienteJuridico other = (ClienteJuridico) obj;
		if (nit == null) {
			if (other.nit != null)
				return false;
		} else if (!nit.equals(other.nit))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ClienteJuridico [nit=" + nit + "]";
	}



}
