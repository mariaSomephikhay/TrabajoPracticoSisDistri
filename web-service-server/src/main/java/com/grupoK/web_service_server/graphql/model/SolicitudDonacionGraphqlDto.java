package com.grupoK.web_service_server.graphql.model;

import com.grupoK.connector.database.entities.Organizacion;

import java.util.List;

import com.grupoK.connector.database.entities.Donacion;

public class SolicitudDonacionGraphqlDto {
	private String id;
	private Organizacion organizacionSolicitante;
	private Boolean activa;
	private Boolean procesada;
	private List<Donacion> donaciones;
	
    public SolicitudDonacionGraphqlDto() {
    }
    
    public SolicitudDonacionGraphqlDto(String id, Organizacion organizacionSolicitante, Boolean activa, Boolean procesada, List<Donacion> donaciones) {
        this.id = id;
        this.organizacionSolicitante = organizacionSolicitante;
        this.activa = activa;
        this.procesada = procesada;
        this.donaciones = donaciones;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Organizacion getOrganizacionSolicitante() {
        return organizacionSolicitante;
    }

    public void setOrganizacionSolicitante(Organizacion organizacionSolicitante) {
        this.organizacionSolicitante = organizacionSolicitante;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    public Boolean getProcesada() {
        return procesada;
    }

    public void setProcesada(Boolean procesada) {
        this.procesada = procesada;
    }

    public List<Donacion> getDonaciones() {
        return donaciones;
    }

    public void setDonaciones(List<Donacion> donaciones) {
        this.donaciones = donaciones;
    }

    @Override
    public String toString() {
        return "SolicitudDonacionGraphqlDto{" +
                "id='" + id + '\'' +
                ", organizacionSolicitante=" + organizacionSolicitante +
                ", activa=" + activa +
                ", procesada=" + procesada +
                ", donaciones=" + donaciones +
                '}';
    }

}
