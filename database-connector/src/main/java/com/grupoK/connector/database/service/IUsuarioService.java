package com.grupoK.connector.database.service;

import com.grupoK.connector.database.entities.Usuario;

import java.util.List;

public interface IUsuarioService {
	
	List<Usuario> findAll();
	Usuario findById(Integer id);
	Usuario findByUsername(String username);
	Usuario saveOrUpdate(Usuario usuario);
	Usuario delete(Integer id);
	List<Usuario> getUsersById (List<Integer> lstId);
}
