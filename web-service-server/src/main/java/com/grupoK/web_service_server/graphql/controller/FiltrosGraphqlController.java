package com.grupoK.web_service_server.graphql.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.grupoK.connector.database.entities.Filter;
import com.grupoK.connector.database.serviceImp.FilterService;
import com.grupoK.web_service_server.graphql.model.FilterInput;
import com.grupoK.web_service_server.graphql.model.GraphQLResponse;
import com.grupoK.web_service_server.rest.model.FilterDto;

@Controller
public class FiltrosGraphqlController {
	
	@Autowired
	FilterService filterservice;
	
    @MutationMapping
    public GraphQLResponse<FilterInput> guardarFiltro(@Argument FilterInput filtro) {
		try {	
			filterservice.saveOrUpdate(null,filtro.getName(),filtro.getValueFilter(),filtro.getUsuario(),filtro.getFilterType());
			return new GraphQLResponse<>("success","Filtro creado exitosamente", filtro);
		}catch(Exception e) {
			return new GraphQLResponse<>("success","Error al crear el filtro, " + e.getMessage(), filtro);
		}
    }
    
    @QueryMapping
    public GraphQLResponse<List<FilterInput>> traerFiltros(@Argument String tipo, @Argument String usuario) {
		try {	
			List<Object[]> filtro = filterservice.findByUsuario(usuario,tipo);
			
			List<FilterInput> filtrodto = filtro.stream().map(row -> {
				FilterInput dto = new FilterInput();
			    dto.setId((Integer) row[0]);
			    dto.setName((String) row[1]);
			    dto.setValueFilter((String) row[2]);
			    dto.setUsuario(usuario);
			    dto.setFilterType(tipo);
			    return dto;
			}).collect(Collectors.toList());
			return new GraphQLResponse<>("success","Filtros obtenido", filtrodto);
		}catch(Exception e) {
			return new GraphQLResponse<>("success","Error al buscar filtro, " + e.getMessage(), null);
		}
    }
    
    @MutationMapping
    public GraphQLResponse<FilterInput> borrarFiltro(@Argument Integer id) {
		try {
			filterservice.deleteFilter(id);
			return new GraphQLResponse<>("success","Filtro eliminado", null);
		}catch(Exception e) {
			return new GraphQLResponse<>("success","Error al eliminar el filtro, " + e.getMessage(), null);
		}
    }

}
