package com.grupoK.connector.database.service;

import java.util.List;

import com.grupoK.connector.database.entities.Filter;

public interface IFilterService {
	List<Object[]>findByUsuario(String idUsuario,String type) throws Exception;
	Filter saveOrUpdate(String name, String value, String idUsuario, String type) throws Exception;
	void deleteFilter(Integer id);
}
