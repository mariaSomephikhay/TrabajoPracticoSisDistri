package com.grupoK.connector.database.service;

import com.grupoK.connector.database.entities.Categoria;
import com.grupoK.connector.database.entities.Solicitud;
import com.grupoK.connector.database.entities.SolicitudDonacion;
import com.grupoK.connector.database.entities.enums.TipoCategoria;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ISolicitudService {
    Solicitud findById (String idSolicitud)throws Exception;
    Solicitud saveOrUpdate(Solicitud solicitud, List<SolicitudDonacion> donacionesAsociadas) throws Exception;
    Solicitud delete(String idSolicitud) throws Exception;
    List<Solicitud> findAll();
    List<SolicitudDonacion> findAllDonationsAssociatedByRequest(Solicitud solicitud) throws  Exception;
    List<Solicitud> find(TipoCategoria categoria, LocalDateTime fechaDesde, LocalDateTime fechaHasta, Boolean activa);
}
