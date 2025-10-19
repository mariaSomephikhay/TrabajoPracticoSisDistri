package com.grupoK.connector.database.repositories;

import com.grupoK.connector.database.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface IRolRepository extends JpaRepository<Rol, Serializable>{
}
