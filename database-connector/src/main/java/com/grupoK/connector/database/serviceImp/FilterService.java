package com.grupoK.connector.database.serviceImp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupoK.connector.database.entities.Filter;
import com.grupoK.connector.database.entities.FilterType;
import com.grupoK.connector.database.entities.Usuario;
import com.grupoK.connector.database.repositories.IFilterRepository;
import com.grupoK.connector.database.service.IFilterService;

@Service
public class FilterService implements IFilterService{
	
	@Autowired
	private IFilterRepository filterRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private FilterTypeService filterTypeService;


	@Override
	public List<Object[]> findByUsuario(String idUsuario,String type) throws Exception {
		//buscarTipo
		FilterType filterType = filterTypeService.findByType(type);
		
		return filterRepository.findByUsuarioIdNative(idUsuario,filterType.getId());
	}

	@Override
	public Filter saveOrUpdate(String name, String value, String idUsuario, String type) throws Exception {
		
		//buscarUsuario
		Usuario user = usuarioService.findById(idUsuario);
		
		//buscarTipo
		FilterType filterType = filterTypeService.findByType(type);
		
		Filter filter = new Filter(null, name,value,user,filterType);
		return filterRepository.save(filter);
	}

	@Override
	public void deleteFilter(Integer id) {
		Optional<Filter> filter = filterRepository.findById(id);
		if(filter!=null) {
			Filter filterEntidad = filter.get();
			filterRepository.delete(filterEntidad);
		}
	}

}
