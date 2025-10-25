package com.grupoK.connector.database.repositories;

import com.grupoK.connector.database.entities.Solicitud;
import com.grupoK.connector.database.entities.SolicitudDonacion;
import com.grupoK.connector.database.entities.enums.TipoCategoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ISolicitudDonacionRepository extends JpaRepository<SolicitudDonacion, Integer> {
    List<SolicitudDonacion> findAllBySolicitud(@Param("solicitud") Solicitud solicitud);
	@Query("""
		    SELECT DISTINCT sd FROM SolicitudDonacion sd
		    JOIN sd.solicitud s
		    JOIN sd.donacion d
		    JOIN d.categoria c
		    WHERE s.procesada = true
		      AND (:categoria IS NULL OR c.descripcion = :categoria)
		      AND (:desde IS NULL OR s.fechaAlta >= :desde)
		      AND (:hasta IS NULL OR s.fechaAlta <= :hasta)
		      AND (:eliminado IS NULL OR s.activa = :eliminado)
		    ORDER BY s.fechaAlta ASC
		""")
	List<SolicitudDonacion> filtrarSolicitud(
		    @Param("categoria") TipoCategoria categoria,
		    @Param("desde") LocalDateTime desde,
		    @Param("hasta") LocalDateTime hasta,
		    @Param("eliminado") Boolean eliminado
		);
}
