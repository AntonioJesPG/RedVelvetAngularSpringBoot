package com.proyecto.redVelvet.controladores;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.redVelvet.modelo.Cesta;
import com.proyecto.redVelvet.modelo.DTO;
import com.proyecto.redVelvet.modelo.Historial;
import com.proyecto.redVelvet.modelo.Producto;
import com.proyecto.redVelvet.modelo.Usuario;
import com.proyecto.redVelvet.modelo.repositories.CestaRepository;
import com.proyecto.redVelvet.modelo.repositories.HistorialRepository;
import com.proyecto.redVelvet.modelo.repositories.ProductoRepository;
import com.proyecto.redVelvet.modelo.repositories.UsuarioRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class CestaControlador {
	
	@Autowired
	HistorialControlador hc;
	
	@Autowired
	CestaRepository cr;
		
	//Insertamos un producto en la cesta del usuario
	@GetMapping("/cesta/add")
	public DTO insertarEnCesta(int idUsuario, int idProducto){
		
		DTO dto = new DTO(); 
		dto.put("result", "Producto agregado correctamente");
		
		Cesta c = comprobarExisteProductoEnCesta(idUsuario, idProducto);
		if(c != null) {
			cr.editCesta(c.getCantidad() + 1, c.getId());
		}else {
			c = cr.getLastCesta();
			int id;
			if(c == null) {
				id = 1;
			}else {
				id = c.getId()+1;
			}
			cr.insertCesta(id,idUsuario,idProducto);
		}
		return dto;
	}
	
	//Comprobamos si el usuario ya tiene ese producto en la cesta
	@GetMapping("/cesta/comprobar")
	public Cesta comprobarExisteProductoEnCesta(int idUsuario, int idProducto){
		return cr.findByUsuarioAndProducto(idUsuario, idProducto);
		
	}
	
	//Obtenemos todos los productos de la cesta del usuario
	@GetMapping("/cesta/obtenerParaUsuario")
	public List<DTO> obtenerCestaUsuario(String idUsuario){
		int id = Integer.parseInt(idUsuario);
		List<Cesta> cesta = cr.obtenerCestaUsuario(id);
		List<DTO> objetos = new ArrayList<>();
		for(Cesta c : cesta){
			objetos.add(getDTOFromCesta(c));
		}
		
		return objetos;
	}
	
	//Obtenemos un objeto DTO a partir de una cesta
	private DTO getDTOFromCesta(Cesta c) {
		DTO dto = new DTO(); // Voy a devolver un dto
		if (c != null) {
			dto.put("id", c.getId());
			dto.put("id_usuario", c.getUsuario().getId());
			dto.put("id_producto", c.getProducto().getId());
			dto.put("nombreProducto", c.getProducto().getNombre());
			dto.put("cantidad", c.getCantidad());
			NumberFormat nf = NumberFormat.getNumberInstance();
			nf.setMaximumFractionDigits(2);
			String rounded = nf.format(c.getProducto().getPrecio()*c.getCantidad());
			dto.put("precio", rounded);
		}
		
		return dto;
	}
	
	//Si un producto ya esta en la cesta del usuario le agregamos 1 a la cantidad
	@GetMapping("/cesta/agregarProductoExistente")
	public DTO agregarProductoExistente( int id) {
		
		DTO dto = new DTO(); 
		dto.put("result", "Producto agregado correctamente");
		
		Cesta c = cr.findById(id);
		cr.editCesta(c.getCantidad()+1,id);
		return dto;
	}
	
	//Restamos 1 a la cantidad del producto que selecciona el usuario
	@GetMapping("/cesta/quitarProductoExistente")
	public DTO quitarProductoExistente( int id) {
		
		DTO dto = new DTO(); 
		dto.put("result", "Producto eliminado correctamente");
		
		Cesta c = cr.findById(id);
		cr.editCesta(c.getCantidad()-1,id);
		return dto;
	}
	
	//Eliminamos un producto de la cesta
	@GetMapping("/cesta/eliminarProductoCesta")
	public DTO eliminarProducto(int id) {
		DTO dto = new DTO();
		dto.put("result", "Producto eliminado correctamente");
		
		cr.deleteById(id);
		return dto;
	}
	
	//Vaciamos por completo la cesta
	@GetMapping("/cesta/vaciar")
	public DTO vaciarCesta( String idUsuario) {
		
		DTO dto = new DTO(); 
		dto.put("result", "Cesta vaciada correctamente");
		
		int id = Integer.parseInt(idUsuario);
		cr.vaciarCesta(id);
		
		return dto;

	}
	
	//El usuario compra los productos y por tanto los insertamos en el historial del usuario y vaciamos la cesta
	//Al insertarlo en la cesta generamos un código para diferenciar dos productos iguales en distintas compras
	@GetMapping("/cesta/comprar")
	public DTO comprarProductos( String idUsuario) {
		
		DTO dto = new DTO(); 
		dto.put("result", "La compra se ha realizado");
		
		int id = Integer.parseInt(idUsuario);
		List<Cesta> cesta = cr.obtenerCestaUsuario(id);
		Historial h = new Historial();
		
		String codigo = String.valueOf(Math.round(Math.random()*10000000));
		boolean existeCodigo = hc.comprobarCodigo(codigo);
		
		while(existeCodigo == true) {
			codigo = String.valueOf(Math.round(Math.random()*10000000));
			existeCodigo = hc.comprobarCodigo(codigo);
		}
		

		Date date = new Date();
		String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(date);
		
		for(Cesta c : cesta) {
			h.setId(hc.getLastHistorial().getId()+1);
			h.setUsuario(c.getUsuario());
			h.setIdProducto(c.getProducto().getId());
			h.setNombreProducto(c.getProducto().getNombre());
			h.setCantidad(c.getCantidad());
			h.setPrecio(c.getCantidad() * c.getProducto().getPrecio());
			h.setCodigo(codigo);
			h.setFecha(modifiedDate);

			hc.insertarEnHistorial(h);
		}
		
		vaciarCesta(idUsuario);

		return dto;
	}
	
	//Obtenemos el precio total de la cesta del usuario
	@GetMapping("/cesta/obtenerPrecioTotal")
	public DTO obtenerPrecioTotal( String idUsuario) {
		
		int id = Integer.parseInt(idUsuario);
		List<Cesta> cesta = cr.obtenerCestaUsuario(id);
		float total = 0;
		
		for(Cesta c : cesta) {
			total += c.getProducto().getPrecio()*c.getCantidad();
		}
		
		NumberFormat nf = NumberFormat.getNumberInstance();
		String precioTotal = nf.format(total);
		
		DTO dto = new DTO(); 
		dto.put("precioTotal", precioTotal);
		
		return dto;
	}
	
}



