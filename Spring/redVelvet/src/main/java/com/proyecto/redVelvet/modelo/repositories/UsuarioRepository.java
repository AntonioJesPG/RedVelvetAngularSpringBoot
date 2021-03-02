package com.proyecto.redVelvet.modelo.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.redVelvet.modelo.Cesta;
import com.proyecto.redVelvet.modelo.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario,Integer>{
	
	public Usuario findByEmail(String email);
	
	public Usuario findByEmailAndPassword(String email, String password);
	
	@Query(value = "SELECT id FROM usuario ORDER BY id DESC LIMIT 1;", nativeQuery = true)
	public int getLastUsuario();
	
	@Transactional
	@Modifying
	@Query(value = "INSERT into usuario  values (?, ? ,? , ?, ? ,? ,? ,? ,?)", nativeQuery = true)
	public void insertarUsuario(int id, String nombre,String email, String password, String direccion, String ciudad, String telefono, float saldo, int id_rol );
}
