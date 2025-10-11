package com.grupoK.connector.database.repositories;

import com.grupoK.connector.database.entities.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface ISolicitudRepository extends JpaRepository<Solicitud, Integer> {
}
