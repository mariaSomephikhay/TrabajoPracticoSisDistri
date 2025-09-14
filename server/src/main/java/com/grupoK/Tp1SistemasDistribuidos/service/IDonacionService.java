package com.grupoK.Tp1SistemasDistribuidos.service;

import java.util.List;

import com.grupoK.Tp1SistemasDistribuidos.entities.Donacion;

public interface IDonacionService {
	List<Donacion> findAll();
	Donacion findById(Integer id) throws Exception;
	Donacion saveOrUpdate(Donacion donacion) throws Exception;
	Donacion delete(Integer id) throws Exception;
}
