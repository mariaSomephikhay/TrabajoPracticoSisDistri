package com.grupoK.web_service_server.rest.model;

public class FilterDto {
	
	private Integer id;
	private String name;
	private String valueFilter;
	private String usuario;
	private String filterType;
	
	public FilterDto() {
		super();
	}
	
	public FilterDto(Integer id, String name, String valueFilter, String usuario, String filterType) {
		super();
		this.id = id;
		this.name = name;
		this.valueFilter = valueFilter;
		this.usuario = usuario;
		this.filterType = filterType;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValueFilter() {
		return valueFilter;
	}

	public void setValueFilter(String valueFilter) {
		this.valueFilter = valueFilter;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getFilterType() {
		return filterType;
	}

	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}
	
}
