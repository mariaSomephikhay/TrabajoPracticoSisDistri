package com.grupoK.connector.database.service;

import com.grupoK.connector.database.entities.Evento;
import com.grupoK.connector.database.entities.Usuario;

import java.util.List;

public interface IEventoService {

	Evento findById(String id) throws Exception;
	Evento findByNombre(String nombre) throws Exception;
	Evento saveOrUpdate(Evento evento) throws Exception;
	Evento detele(Evento evento) throws Exception;
	List<Evento> findAll();
	List<Usuario> saveUsersToEvento(Evento evento, List<Integer> lstUsers);
	List<Usuario> getUsersByIdEvento(String id);
}
