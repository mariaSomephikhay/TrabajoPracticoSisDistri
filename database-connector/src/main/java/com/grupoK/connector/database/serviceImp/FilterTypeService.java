package com.grupoK.connector.database.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupoK.connector.database.entities.FilterType;
import com.grupoK.connector.database.repositories.IFilterTypeRepository;
import com.grupoK.connector.database.service.IFilterTypeService;

@Service
public class FilterTypeService implements IFilterTypeService{

	@Autowired
	private IFilterTypeRepository filterTypeRepository;
	
	@Override
	public FilterType findByType(String type) {
		return filterTypeRepository.findByType(type)
                .orElseThrow(() -> new RuntimeException("FilterType no encontrado"));
	}
	

}
