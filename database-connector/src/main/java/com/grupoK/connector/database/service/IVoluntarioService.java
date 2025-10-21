package com.grupoK.connector.database.service;

import com.grupoK.connector.database.entities.Voluntario;

public interface IVoluntarioService {
	Voluntario findById(String id) throws Exception;
	Voluntario saveOrUpdate(Voluntario voluntario);
}
