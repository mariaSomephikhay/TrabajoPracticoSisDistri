package com.grupoK.Tp1SistemasDistribuidos.service;

import java.util.List;

import com.grupoK.Tp1SistemasDistribuidos.entities.Usuario;

public interface IUsuarioService {
	
	List<Usuario> findAll();
	Usuario findById(Integer id);
	Usuario saveOrUpdate(Usuario usuario);
}
