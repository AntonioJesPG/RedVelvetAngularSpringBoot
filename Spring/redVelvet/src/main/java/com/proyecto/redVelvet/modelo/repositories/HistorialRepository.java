package com.proyecto.redVelvet.modelo.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.redVelvet.modelo.Historial;

@Repository
public interface HistorialRepository extends CrudRepository<Historial,Integer>{
	
	@Transactional
	@Modifying
	@Query(value = "INSERT into historial (id,id_usuario, id_producto, nombre_producto, cantidad, precio, codigo, fecha) values (?, ? ,? , ?, ? , ?, ? ,?)", nativeQuery = true)
	public void insertHistorial(int id, int idUsuario, int idProducto,String nombreProducto, float cantidad,float precio, String codigo, String fecha);
	
	@Query(value= "SELECT * FROM historial where codigo=?", nativeQuery= true)
	public Historial comprobarCodigo(String codigo);
	
	@Query(value= "SELECT * FROM historial ORDER BY id DESC LIMIT 1;", nativeQuery= true)
	public Historial getLastHistorial();
	
	@Query(value = "SELECT * FROM historial where id_usuario = ?", nativeQuery = true)
	public List<Historial> obtenerHistorialUsuario(int id);
}
