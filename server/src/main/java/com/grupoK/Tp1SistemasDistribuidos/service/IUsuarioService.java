package com.grupoK.Tp1SistemasDistribuidos.service;

import java.util.List;

import com.grupoK.Tp1SistemasDistribuidos.entities.Usuario;

public interface IUsuarioService {
	
	List<Usuario> findAll();
	Usuario saveOrUpdate(Usuario usuario);
}
