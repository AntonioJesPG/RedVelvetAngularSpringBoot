package com.proyecto.redVelvet.modelo.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.redVelvet.modelo.Historial;
import com.proyecto.redVelvet.modelo.Tipo;

@Repository
public interface TipoRepository extends CrudRepository<Tipo,Integer>{
	
}
