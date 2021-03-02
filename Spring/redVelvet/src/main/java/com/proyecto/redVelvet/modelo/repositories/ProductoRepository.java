package com.proyecto.redVelvet.modelo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.redVelvet.modelo.Producto;

@Repository
public interface ProductoRepository extends CrudRepository<Producto,Integer>{
	public List<Producto> findAll();
	
	@Query(value = "SELECT * FROM producto where id_tipo = ?", nativeQuery = true)
	public List<Producto> findProductoTipo(int idTipo);
	
	@Query(value = "SELECT id FROM producto ORDER BY id DESC LIMIT 1;", nativeQuery = true)
	public int getLastProducto();
}
