package com.grupoK.connector.database.repositories;

import com.grupoK.connector.database.entities.Solicitud;
import com.grupoK.connector.database.entities.SolicitudDonacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISolicitudDonacionRepository extends JpaRepository<SolicitudDonacion, Integer> {
    List<SolicitudDonacion> findAllBySolicitud(@Param("solicitud") Solicitud solicitud);

}
