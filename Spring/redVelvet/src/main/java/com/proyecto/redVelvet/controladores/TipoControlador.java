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
import com.proyecto.redVelvet.modelo.Tipo;
import com.proyecto.redVelvet.modelo.repositories.HistorialRepository;
import com.proyecto.redVelvet.modelo.repositories.ProductoRepository;
import com.proyecto.redVelvet.modelo.repositories.TipoRepository;
import com.proyecto.redVelvet.modelo.repositories.UsuarioRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TipoControlador {
	
	@Autowired
	TipoRepository tr;

	public Tipo findTipo(int id) {
		return tr.findById(id).get();
	}
	
	
}



