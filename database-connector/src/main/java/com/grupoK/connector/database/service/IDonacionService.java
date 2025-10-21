package com.grupoK.connector.database.service;



import com.grupoK.connector.database.entities.Donacion;
import com.grupoK.connector.database.entities.Usuario;

import java.util.List;

public interface IDonacionService {
	List<Donacion> findAll();
	List<Donacion> findAllStock();
	Donacion findById(Integer id) throws Exception;
	Donacion saveOrUpdate(Donacion donacion) throws Exception;
	Donacion delete(Integer id, Usuario usuario) throws Exception;
}
