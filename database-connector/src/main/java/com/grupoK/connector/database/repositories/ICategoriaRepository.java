package com.grupoK.connector.database.repositories;

import com.grupoK.connector.database.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Serializable>{

}
