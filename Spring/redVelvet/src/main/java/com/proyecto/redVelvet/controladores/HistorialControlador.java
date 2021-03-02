package com.proyecto.redVelvet.controladores;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.redVelvet.modelo.Cesta;
import com.proyecto.redVelvet.modelo.DTO;
import com.proyecto.redVelvet.modelo.Historial;
import com.proyecto.redVelvet.modelo.Producto;
import com.proyecto.redVelvet.modelo.repositories.HistorialRepository;
import com.proyecto.redVelvet.modelo.repositories.ProductoRepository;
import com.proyecto.redVelvet.modelo.repositories.UsuarioRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class HistorialControlador {
	
	@Autowired
	HistorialRepository hr;

	@Autowired
	ProductoControlador pc;

	//Insertamos en la tabla historial una nueva entrada
	public void insertarEnHistorial(Historial h) {
		hr.insertHistorial(h.getId(),h.getUsuario().getId(),h.getIdProducto(),h.getNombreProducto(),h.getCantidad(),h.getPrecio(),h.getCodigo(),h.getFecha());
	}
	
	
	//Obtenemos el ultimo elemento insertado en el historial
	public Historial getLastHistorial() {
		
		Historial h =  hr.getLastHistorial();
		
		if(h == null) {
			h = new Historial();
			h.setId(0);
		}
		
		return h;
	}
	
	//Se usa para comprobar si existe el código en el historial
	public boolean comprobarCodigo(String codigo) {
		Historial h = hr.comprobarCodigo(codigo);
		if(h == null) {
			return false;
		}
		
		return true;
	}
	
	
	//obtenemos el historial del usuario
	@GetMapping("/historial/obtenerParaUsuario")
	public List<DTO> obtenerHistorialUsuario(String idUsuario){
		
		int id = Integer.parseInt(idUsuario);
		List<Historial> historial = hr.obtenerHistorialUsuario(id);
		List<DTO> objetos = new ArrayList<>();
		
		for(Historial h : historial){
			objetos.add(getDTOFromHistorial(h));
		}
		
		return objetos;
	}
	
	//Obtenemos un objeto DTO a partir de una entrada del historial
	private DTO getDTOFromHistorial(Historial h) {
		DTO dto = new DTO(); // Voy a devolver un dto
		if (h != null) {
			
			dto.put("id", h.getId());
			dto.put("id_usuario", h.getUsuario().getId());
			dto.put("id_producto", h.getIdProducto());
			dto.put("nombreProducto",h.getNombreProducto());
			dto.put("cantidad", h.getCantidad());
			NumberFormat nf = NumberFormat.getNumberInstance();
			nf.setMaximumFractionDigits(2);
			String rounded = nf.format(h.getPrecio());
			dto.put("precio", rounded);
			dto.put("codigo", h.getCodigo());
			dto.put("fecha", h.getFecha());
		}
		
		return dto;
	}
	
	
}



