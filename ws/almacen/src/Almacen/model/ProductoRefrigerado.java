package Almacen.model;

public class ProductoRefrigerado extends Producto{

	private String codigoAprobacion;
	private int temperaturaRefrigeracion;

	public ProductoRefrigerado(String codigo, String nombre, String descripcion, double valorUnitario,
			int cantidadExistencia, String codigoAprobacion, int temperaturaRefrigeracion) {
		super(codigo, nombre, descripcion, valorUnitario, cantidadExistencia);
		this.codigoAprobacion = codigoAprobacion;
		this.temperaturaRefrigeracion = temperaturaRefrigeracion;
	}
	public String getCodigoAprobacion() {
		return codigoAprobacion;
	}
	public void setCodigoAprobacion(String codigoAprobacion) {
		this.codigoAprobacion = codigoAprobacion;
	}
	public int getTemperaturaRefrigeracion() {
		return temperaturaRefrigeracion;
	}
	public void setTemperaturaRefrigeracion(int temperaturaRefrigeracion) {
		this.temperaturaRefrigeracion = temperaturaRefrigeracion;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((codigoAprobacion == null) ? 0 : codigoAprobacion.hashCode());
		result = prime * result + temperaturaRefrigeracion;
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
		ProductoRefrigerado other = (ProductoRefrigerado) obj;
		if (codigoAprobacion == null) {
			if (other.codigoAprobacion != null)
				return false;
		} else if (!codigoAprobacion.equals(other.codigoAprobacion))
			return false;
		if (temperaturaRefrigeracion != other.temperaturaRefrigeracion)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ProductoRefrigerado [codigoAprobacion=" + codigoAprobacion + ", temperaturaRefrigeracion="
				+ temperaturaRefrigeracion + "]";
	}


}
