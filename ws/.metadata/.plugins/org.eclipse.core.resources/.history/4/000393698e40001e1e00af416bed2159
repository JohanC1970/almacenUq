package Almacen.model;

public class ProductoEnvasado extends Producto{

	private String fechaEnvasado;
	private double peso;
	private PaisOrigen paisOrigen;

	public ProductoEnvasado(String codigo, String nombre, String descripcion, double valorUnitario,
			int cantidadExistencia, String fechaEnvasado, double peso, PaisOrigen paisOrigen) {
		super(codigo, nombre, descripcion, valorUnitario, cantidadExistencia);
		this.fechaEnvasado = fechaEnvasado;
		this.peso = peso;
		this.paisOrigen = paisOrigen;
	}

	public String getFechaEnvasado() {
		return fechaEnvasado;
	}

	public void setFechaEnvasado(String fechaEnvasado) {
		this.fechaEnvasado = fechaEnvasado;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public PaisOrigen getPaisOrigen() {
		return paisOrigen;
	}

	public void setPaisOrigen(PaisOrigen paisOrigen) {
		this.paisOrigen = paisOrigen;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((fechaEnvasado == null) ? 0 : fechaEnvasado.hashCode());
		result = prime * result + ((paisOrigen == null) ? 0 : paisOrigen.hashCode());
		long temp;
		temp = Double.doubleToLongBits(peso);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		ProductoEnvasado other = (ProductoEnvasado) obj;
		if (fechaEnvasado == null) {
			if (other.fechaEnvasado != null)
				return false;
		} else if (!fechaEnvasado.equals(other.fechaEnvasado))
			return false;
		if (paisOrigen != other.paisOrigen)
			return false;
		if (Double.doubleToLongBits(peso) != Double.doubleToLongBits(other.peso))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductoEnvasado [fechaEnvasado=" + fechaEnvasado + ", peso=" + peso + ", paisOrigen=" + paisOrigen
				+ "]";
	}


}
