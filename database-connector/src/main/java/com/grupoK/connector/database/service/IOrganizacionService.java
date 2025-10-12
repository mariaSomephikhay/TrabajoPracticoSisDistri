package com.grupoK.connector.database.service;

import com.grupoK.connector.database.entities.Organizacion;

public interface IOrganizacionService {
	 Organizacion findById(Integer id)throws Exception;
}
