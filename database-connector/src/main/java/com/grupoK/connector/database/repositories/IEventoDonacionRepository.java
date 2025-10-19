package com.grupoK.connector.database.repositories;


import com.grupoK.connector.database.entities.Donacion;
import com.grupoK.connector.database.entities.Evento;
import com.grupoK.connector.database.entities.EventoDonacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository
public interface IEventoDonacionRepository extends JpaRepository<EventoDonacion, Serializable>{
	boolean existsByEventoAndDonacion(Evento evento, Donacion donacion);
	Optional<EventoDonacion> findByEvento_IdAndDonacion_Id(String idEvento, Integer idDonacion);
	Optional<List<EventoDonacion>> findByEvento_Id(String idEvento);

}
