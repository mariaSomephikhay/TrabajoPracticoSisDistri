package com.grupoK.Tp1SistemasDistribuidos.service;

import java.util.List;

import com.grupoK.Tp1SistemasDistribuidos.entities.Evento;

public interface IEventoService {

	Evento findById(Integer id) throws Exception;
	Evento findByNombre(String nombre) throws Exception;
	Evento saveOrUpdate(Evento evento) throws Exception;
	Evento detele(Evento evento) throws Exception;
	public List<Evento> findAll();
}
