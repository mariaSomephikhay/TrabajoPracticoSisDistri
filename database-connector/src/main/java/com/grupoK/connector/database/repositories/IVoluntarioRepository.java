package com.grupoK.connector.database.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupoK.connector.database.entities.Voluntario;

@Repository
public interface IVoluntarioRepository extends JpaRepository<Voluntario, Serializable>{

}
