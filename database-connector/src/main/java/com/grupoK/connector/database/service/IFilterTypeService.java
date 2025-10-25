package com.grupoK.connector.database.service;

import com.grupoK.connector.database.entities.FilterType;

public interface IFilterTypeService {
	FilterType findByType(String type);
}
