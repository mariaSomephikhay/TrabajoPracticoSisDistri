package com.grupoK.web_service_server.graphql.model;

import java.util.ArrayList;
import java.util.List;
import com.grupoK.connector.database.entities.Evento;


public class InformeEventoDto {
	private String mes;
    private List<Evento> eventos = new ArrayList<>();
    
	public InformeEventoDto() {
		super();
	}

	public InformeEventoDto(String mes, List<Evento> eventos) {
		super();
		this.mes = mes;
		this.eventos = eventos;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	@Override
	public String toString() {
		return "InformeEventoDto [mes=" + mes + ", eventos=" + eventos + "]";
	}
}
