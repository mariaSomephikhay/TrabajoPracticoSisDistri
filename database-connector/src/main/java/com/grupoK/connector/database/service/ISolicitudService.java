package com.grupoK.connector.database.service;

import com.grupoK.connector.database.entities.Solicitud;
import com.grupoK.connector.database.entities.SolicitudDonacion;

import java.util.List;
import java.util.Optional;

public interface ISolicitudService {
    Solicitud findById (String idSolicitud)throws Exception;
    Solicitud saveOrUpdate(Solicitud solicitud, List<SolicitudDonacion> donacionesAsociadas) throws Exception;
    Solicitud delete(String idSolicitud) throws Exception;
    List<Solicitud> findAll();
    List<SolicitudDonacion> findAllDonationsAssociatedByRequest(Solicitud solicitud) throws  Exception;
}
