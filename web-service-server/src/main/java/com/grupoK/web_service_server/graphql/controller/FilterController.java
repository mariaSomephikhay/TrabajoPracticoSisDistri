package com.grupoK.web_service_server.graphql.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.grupoK.connector.database.serviceImp.FilterService;
import com.grupoK.web_service_server.graphql.model.FilterDto;

@RestController
@RequestMapping("/filter")
public class FilterController {

	@Autowired
    private FilterService filterService;
	
	
	@PostMapping("/new")
	public ResponseEntity<Object> newFilter (@RequestBody FilterDto newFilter){	
		try {	
			filterService.saveOrUpdate(newFilter.getName(),newFilter.getValueFilter(),newFilter.getUsuario(),newFilter.getFilterType());
			return ResponseEntity.status(HttpStatus.CREATED).body("Filtro creado exitosamente");
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear el filtro, " + e.getMessage());
		}
	}
	
	@GetMapping("/{type}/{idUsuario}")
	public ResponseEntity<Object> getFilters (@PathVariable String idUsuario,@PathVariable String type){	
		try {	
			List<Object[]> lstFilter = filterService.findByUsuario(idUsuario,type);
			
			List<FilterDto> dtoList = lstFilter.stream().map(row -> {
			    FilterDto dto = new FilterDto();
			    dto.setId((Integer) row[0]);
			    dto.setName((String) row[1]);
			    dto.setValueFilter((String) row[2]);
			    dto.setUsuario(idUsuario);
			    dto.setFilterType(type);
			    return dto;
			}).collect(Collectors.toList());

			return ResponseEntity.status(HttpStatus.OK).body(dtoList);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al buscar filtro, " + e.getMessage());
		}
	}
	
	@DeleteMapping("/delete/{idFilter}")
	public ResponseEntity<Object> getFilters (@PathVariable Integer idFilter){	
		try {	
			filterService.deleteFilter(idFilter);
			return ResponseEntity.status(HttpStatus.OK).body("Filtro eliminado");
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al eliminar filtro, " + e.getMessage());
		}
	}
	
}
