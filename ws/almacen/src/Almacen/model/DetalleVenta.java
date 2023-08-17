package Almacen.model;

public class DetalleVenta {

	private Producto productoVendido;
	private int cantidadProducto;
	private double subTotal;

	public DetalleVenta(Producto productoVendido, int cantidadProducto, double subTotal) {
		super();
		this.productoVendido = productoVendido;
		this.cantidadProducto = cantidadProducto;
		this.subTotal = subTotal;
	}

	public Producto getProductoVendido() {
		return productoVendido;
	}
	public void setProductoVendido(Producto productoVendido) {
		this.productoVendido = productoVendido;
	}
	public int getCantidadProducto() {
		return cantidadProducto;
	}
	public void setCantidadProducto(int cantidadProducto) {
		this.cantidadProducto = cantidadProducto;
	}
	public double getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cantidadProducto;
		result = prime * result + ((productoVendido == null) ? 0 : productoVendido.hashCode());
		long temp;
		temp = Double.doubleToLongBits(subTotal);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DetalleVenta other = (DetalleVenta) obj;
		if (cantidadProducto != other.cantidadProducto)
			return false;
		if (productoVendido == null) {
			if (other.productoVendido != null)
				return false;
		} else if (!productoVendido.equals(other.productoVendido))
			return false;
		if (Double.doubleToLongBits(subTotal) != Double.doubleToLongBits(other.subTotal))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DetalleVenta [productoVendido=" + productoVendido + ", cantidadProducto=" + cantidadProducto
				+ ", subTotal=" + subTotal + "]";
	}



}
