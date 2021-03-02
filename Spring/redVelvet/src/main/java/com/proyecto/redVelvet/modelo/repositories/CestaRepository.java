package com.proyecto.redVelvet.modelo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.redVelvet.modelo.Cesta;

@Repository
public interface CestaRepository extends CrudRepository<Cesta,Integer>{
	
	public Cesta findById(int id);
	
	@Query(value = "SELECT * FROM cesta where id_usuario = ? AND id_producto = ?", nativeQuery = true)
	public Cesta findByUsuarioAndProducto(int idUsuario, int idProducto);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE cesta SET cantidad = ? where id = ? ", nativeQuery = true)
	public void editCesta(int cantidad, int id);
	
	@Transactional
	@Modifying
	@Query(value = "INSERT into cesta (id,id_usuario, id_producto, cantidad) values (?, ? ,? , 1)", nativeQuery = true)
	public void insertCesta(int id, int idUsuario, int idProducto);
	
	@Query(value = "SELECT * FROM cesta where id_usuario = ?", nativeQuery = true)
	public List<Cesta> obtenerCestaUsuario(int idUsuario);
	
	@Query(value = "SELECT * FROM cesta ORDER BY id DESC LIMIT 1;", nativeQuery = true)
	public Cesta getLastCesta();
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM cesta WHERE id_usuario = ?", nativeQuery = true)
	public void vaciarCesta(int idUsuario);
	
}
