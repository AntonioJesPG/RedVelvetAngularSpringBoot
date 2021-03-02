package com.proyecto.redVelvet.modelo;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the producto database table.
 * 
 */
@Entity
@NamedQuery(name="Producto.findAll", query="SELECT p FROM Producto p")
public class Producto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Lob
	private byte[] imagen;

	private String nombre;

	private float precio;

	//bi-directional many-to-one association to Cesta
	@OneToMany(mappedBy="producto")
	@JsonIgnore
	private List<Cesta> cestas;

	//bi-directional many-to-one association to Tipo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_tipo")
	@JsonIgnore
	private Tipo tipo;

	public Producto() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getImagen() {
		return this.imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getPrecio() {
		return this.precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public List<Cesta> getCestas() {
		return this.cestas;
	}

	public void setCestas(List<Cesta> cestas) {
		this.cestas = cestas;
	}

	public Cesta addCesta(Cesta cesta) {
		getCestas().add(cesta);
		cesta.setProducto(this);

		return cesta;
	}

	public Cesta removeCesta(Cesta cesta) {
		getCestas().remove(cesta);
		cesta.setProducto(null);

		return cesta;
	}


	public Tipo getTipo() {
		return this.tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

}