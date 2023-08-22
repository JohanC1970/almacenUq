package Almacen.model;

import java.util.ArrayList;
import java.util.List;

import Almacen.exception.ProductoException;

public class Venta {

	private String codigo;
	private String fecha;
	private Cliente clienteCompra;
	private Double total;
	private static final Double IVA = 0.19;
	private List<DetalleVenta> listaDetalleVenta = new ArrayList<DetalleVenta>();

	public Venta(String codigo, String fecha, Cliente clienteCompra, Double total) {
		super();
		this.codigo = codigo;
		this.fecha = fecha;
		this.clienteCompra = clienteCompra;
		this.total = total;
		this.listaDetalleVenta = listaDetalleVenta;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public Cliente getClienteCompra() {
		return clienteCompra;
	}

	public void setClienteCompra(Cliente clienteCompra) {
		this.clienteCompra = clienteCompra;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public List<DetalleVenta> getListaDetalleVenta() {
		return listaDetalleVenta;
	}

	public void setListaDetalleVenta(List<DetalleVenta> listaDetalleVenta) {
		this.listaDetalleVenta = listaDetalleVenta;
	}

	public static Double getIva() {
		return IVA;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clienteCompra == null) ? 0 : clienteCompra.hashCode());
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((listaDetalleVenta == null) ? 0 : listaDetalleVenta.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
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
		Venta other = (Venta) obj;
		if (clienteCompra == null) {
			if (other.clienteCompra != null)
				return false;
		} else if (!clienteCompra.equals(other.clienteCompra))
			return false;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (listaDetalleVenta == null) {
			if (other.listaDetalleVenta != null)
				return false;
		} else if (!listaDetalleVenta.equals(other.listaDetalleVenta))
			return false;
		if (total == null) {
			if (other.total != null)
				return false;
		} else if (!total.equals(other.total))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Venta [codigo=" + codigo + ", fecha=" + fecha + ", clienteCompra=" + clienteCompra + ", total=" + total
				+ ", listaDetalleVenta=" + listaDetalleVenta + "]";
	}

	/**
	 * Se calcula el total de la venta
	 * @return
	 */
	public double calcularTotal() {
		double total=0.0;
		for (DetalleVenta detalleVenta : this.getListaDetalleVenta()) {
			total+= detalleVenta.getSubTotal();
		}
		return total;
	}

	/**
	 * Este metodo es para realizar la compra de un producto
	 * @param producto
	 * @param cantidad
	 * @return
	 * @throws ProductoException
	 */
	public boolean comprarProducto(Producto producto, int cantidad) throws ProductoException{

		if(producto.getCantidadExistencia()>=cantidad){
			producto.setCantidadExistencia(producto.getCantidadExistencia()-cantidad);
			double subTotal = producto.getValorUnitario()*cantidad;
			DetalleVenta detalleVenta = new DetalleVenta(producto, cantidad, subTotal);
			this.getListaDetalleVenta().add(detalleVenta);
			return true;
		}else{
			throw new ProductoException("No hay unidades disponibles para realizar la compra");
		}
	}

}
