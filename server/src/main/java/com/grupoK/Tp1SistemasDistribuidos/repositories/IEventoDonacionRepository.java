package com.grupoK.Tp1SistemasDistribuidos.repositories;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupoK.Tp1SistemasDistribuidos.entities.Donacion;
import com.grupoK.Tp1SistemasDistribuidos.entities.Evento;
import com.grupoK.Tp1SistemasDistribuidos.entities.EventoDonacion;

public interface IEventoDonacionRepository extends JpaRepository<EventoDonacion, Serializable>{
	boolean existsByEventoAndDonacion(Evento evento, Donacion donacion);
	Optional<EventoDonacion> findByEvento_IdAndDonacion_Id(Integer idEvento, Integer idDonacion);

}
