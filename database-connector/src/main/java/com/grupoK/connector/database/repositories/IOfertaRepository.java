package com.grupoK.connector.database.repositories;

import com.grupoK.connector.database.entities.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface IOfertaRepository extends JpaRepository<Oferta, Serializable> {
}
