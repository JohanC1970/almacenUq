package Almacen.model;

public class ProductoPerecedero extends Producto{

	private String fechaVencimiento;

	public ProductoPerecedero(String codigo, String nombre, String descripcion, double valorUnitario,
			int cantidadExistencia, String fechaVencimiento) {
		super(codigo, nombre, descripcion, valorUnitario, cantidadExistencia);
		this.fechaVencimiento = fechaVencimiento;
	}

	public String getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(String fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	@Override
	public String toString() {
		return super.toString()+"ProductoPerecedero [fechaVencimiento=" + fechaVencimiento + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((fechaVencimiento == null) ? 0 : fechaVencimiento.hashCode());
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
		ProductoPerecedero other = (ProductoPerecedero) obj;
		if (fechaVencimiento == null) {
			if (other.fechaVencimiento != null)
				return false;
		} else if (!fechaVencimiento.equals(other.fechaVencimiento))
			return false;
		return true;
	}


}
