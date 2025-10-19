package com.grupoK.connector.database.repositories;

import com.grupoK.connector.database.entities.Solicitud;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ISolicitudRepository extends JpaRepository<Solicitud, String> {
}
