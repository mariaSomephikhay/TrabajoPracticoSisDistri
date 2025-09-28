package com.grupoK.Tp1SistemasDistribuidos.service;

import java.util.List;

import com.grupoK.Tp1SistemasDistribuidos.entities.Evento;
import com.grupoK.Tp1SistemasDistribuidos.entities.Usuario;

public interface IEventoService {

	Evento findById(Integer id) throws Exception;
	Evento findByNombre(String nombre) throws Exception;
	Evento saveOrUpdate(Evento evento) throws Exception;
	Evento detele(Evento evento) throws Exception;
	List<Evento> findAll();
	List<Usuario> saveUsersToEvento(Evento evento, List<Integer> lstUsers);
	List<Usuario> getUsersByIdEvento(Integer id);
}
