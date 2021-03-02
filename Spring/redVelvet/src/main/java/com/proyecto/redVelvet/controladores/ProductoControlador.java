package com.proyecto.redVelvet.controladores;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.redVelvet.modelo.DTO;
import com.proyecto.redVelvet.modelo.Producto;
import com.proyecto.redVelvet.modelo.Usuario;
import com.proyecto.redVelvet.modelo.repositories.ProductoRepository;
import com.proyecto.redVelvet.modelo.repositories.UsuarioRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ProductoControlador {
	
	@Autowired
	ProductoRepository pr;
	
	@Autowired
	TipoControlador tc;
		
	@GetMapping("/producto/listado")
	public List<Producto> obtenerProductos(){
		
		List<Producto> productos = this.pr.findAll();
		return productos;
		
	}
	
	@GetMapping("/producto/listado/tipo")
	public List<Producto> obtenerProductosTipo(String idTipo){
		
		int id = Integer.parseInt(idTipo);
		
		List<Producto> productos = this.pr.findProductoTipo(id);
		return productos;
		
	}
	
	@GetMapping("/producto/eliminar")
	public DTO eliminarProducto(int idProducto) {
		
		pr.deleteById(idProducto);
		
		DTO dto = new DTO();
		dto.put("result", "error");
		
		if( pr.findById(idProducto).isEmpty()) {
			dto.put("result", "Producto eliminado correctamente");
		}
		
		return dto;
	}
	
	@GetMapping("/producto/obtenerProducto")
	public DTO obtenerProducto(String idProducto) {
		
		DTO dto = new DTO();
		
		int id = Integer.parseInt(idProducto);
		
		dto = getDTOFromProducto(pr.findById(id).get());
		
		return dto;
	}
	
	private DTO getDTOFromProducto(Producto p) {
		DTO dto = new DTO(); 
		if (p != null) {
			dto.put("id", p.getId());
			dto.put("nombre", p.getNombre());
			dto.put("precio", p.getPrecio());
			dto.put("tipo", p.getTipo().getId());
			dto.put("imagen", p.getImagen());
		}
		return dto;
	}
	
	
	
	public Producto obtenerProducto(int id) {
		Producto p = pr.findById(id).get();
		return pr.findById(id).get();
	}
	
	@PostMapping("/producto/modificarProducto")
	public DTO modificarProducto (@RequestBody DatosProducto datos) {
		
		DTO dto = new DTO();
		dto.put("result", "error");
		
		Producto p = new Producto();
		
		p = pr.findById(datos.id).get();
		
		if(p != null) {
			p.setNombre(datos.nombre);
			p.setPrecio(datos.precio);
			p.setImagen(Base64.decodeBase64((String) datos.imagen));
			p.setTipo(tc.findTipo(datos.tipo));
			
			pr.save(p);
			
			dto.put("result", "Producto modificado correctamente");
		}
		
		return dto;
	}
	
	@PostMapping("/producto/agregarProducto")
	public DTO agregarProducto (@RequestBody DatosProducto2 datos) {
		
		DTO dto = new DTO();
		
		Producto p = new Producto();
		
		int id = pr.getLastProducto();
		
		p.setId(id+1);
		p.setNombre(datos.nombre);
		p.setPrecio(datos.precio);
		if(datos.imagen != null) {
			p.setImagen(Base64.decodeBase64((String) datos.imagen));
		}else {
			p.setImagen(null);
		}
		p.setTipo(tc.findTipo(datos.tipo));
		
		pr.save(p);
		
		dto.put("result", "Producto modificado correctamente");
		
		
		return dto;
	}
	
	
}

class DatosProducto {
	int id;
	String nombre;
	float precio;
	int tipo;
	String imagen;
	
	public DatosProducto(int id,String nombre, float precio,int tipo, String imagen) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.tipo = tipo;
		this.imagen = imagen;
	}
}

class DatosProducto2 {
	String nombre;
	float precio;
	int tipo;
	String imagen;
	
	public DatosProducto2(String nombre, float precio,int tipo, String imagen) {
		super();
		this.nombre = nombre;
		this.precio = precio;
		this.tipo = tipo;
		this.imagen = imagen;
	}
}
