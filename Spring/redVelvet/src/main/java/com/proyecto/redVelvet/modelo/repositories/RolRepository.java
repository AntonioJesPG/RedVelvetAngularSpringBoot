package com.proyecto.redVelvet.modelo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.redVelvet.modelo.Cesta;
import com.proyecto.redVelvet.modelo.Rol;

@Repository
public interface RolRepository extends CrudRepository<Rol,Integer>{
	
	public Rol findById(int id);

}
