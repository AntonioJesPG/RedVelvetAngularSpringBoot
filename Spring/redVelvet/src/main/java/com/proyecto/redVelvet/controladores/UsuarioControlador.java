package com.proyecto.redVelvet.controladores;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.redVelvet.AutenticadorJWT;
import com.proyecto.redVelvet.modelo.DTO;
import com.proyecto.redVelvet.modelo.Usuario;
import com.proyecto.redVelvet.modelo.repositories.HistorialRepository;
import com.proyecto.redVelvet.modelo.repositories.RolRepository;
import com.proyecto.redVelvet.modelo.repositories.UsuarioRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UsuarioControlador {

	@Autowired
	UsuarioRepository ur;
	RolRepository rr;
	
	//Función usada para logear al usuario en la web
	@PostMapping("/usuario/autentica")
	public DTO autenticarUsuario (@RequestBody DatosAutenticacionUsuario datos) {
		
		DTO dto = new DTO();
		
		Usuario u = this.ur.findByEmailAndPassword(datos.email, datos.password);
		if ( u != null) {
			dto.put("jwt", AutenticadorJWT.codificaJWT(u));
		}
		return  dto;
	}
	
	//Comprueba si el email introducido existe
	@PostMapping("/usuario/comprueba")
	public Usuario comprobarUsuario (@RequestBody String email) {
		return  this.ur.findByEmail(email);
	}
	
	//Obtiene un usuario a partir de una id (se usa string debido a limitaciones de angular)
	@GetMapping("/usuario/obtenerUsuario")
	public DTO getUsuario(String id) {
		
		int idUsuario = Integer.parseInt(id);
		
		Usuario u = ur.findById(idUsuario).get();
		
		DTO dto = getDTOFromUsuario(u);
		
		return dto;
		
	}
	
	
	@GetMapping("/usuario/obtenerLogeado")
	public DTO getUsuarioLogeado(HttpServletRequest request) {
	
		int idUsuario = AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request);
		
		Usuario usuario = ur.findById(idUsuario).get();
		return getDTOFromUsuario(usuario);
	}
	
	//Data Type Object -> lo usamos para almacenar los datos que le queremos mandar al proyecto de angular
	private DTO getDTOFromUsuario(Usuario usu) {
		DTO dto = new DTO(); 
		if (usu != null) {
			dto.put("id", usu.getId());
			dto.put("nombre", usu.getNombre());
			dto.put("password", usu.getPassword());
			dto.put("email", usu.getEmail());
			dto.put("direccion", usu.getDireccion());
			dto.put("ciudad", usu.getCiudad());
			dto.put("telefono", usu.getTelefono());
			dto.put("saldo", usu.getSaldo());
			dto.put("rol", usu.getRol().getId());
		}
		return dto;
	}
	
	//Creamos un usuario normal, es decir, sin rol de administrador
	@PostMapping("/usuario/crearUsuarioNormal")
	public DTO crearUsuarioNormal (@RequestBody DatosUsuario datos) {
		
		DTO dto = new DTO();
		dto.put("result", "error");
		
		Usuario u = new Usuario();
		
		u = ur.findByEmail(datos.email);
		
		if(u == null) {
		
			int id = ur.getLastUsuario()+1;
			
			ur.insertarUsuario(id, datos.nombre, datos.email, datos.password, datos.direccion, datos.ciudad, datos.telefono, 0, 2);
			
			u = ur.findById(id).get();
			
			dto.put("result", "Usuario registrado correctamente");
			
		}
		
		return dto;
	}
	
	
}

//Obtenemos los datos del usuario que quiere logearse, los recibimos por POST en angular
class DatosAutenticacionUsuario {
	String email;
	String password;

	public DatosAutenticacionUsuario(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
}

//Obtenemos los datos del usuario que nos envia angular por método POST y los insertamos en esta clase para usarlos en el método
class DatosUsuario {
	String email;
	String password;
	String nombre;
	String ciudad;
	String direccion;
	String telefono;
	
	public DatosUsuario(String email, String password,String nombre, String ciudad,String direccion, String telefono) {
		super();
		this.email = email;
		this.password = password;
		this.nombre = nombre;
		this.ciudad = ciudad;
		this.direccion = direccion;
		this.telefono = telefono;
	}
}
