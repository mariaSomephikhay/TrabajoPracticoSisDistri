package com.grupoK.connector.database.repositories;

import com.grupoK.connector.database.entities.Categoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Optional;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Serializable>{
	Optional<Categoria> findByDescripcion(@Param("descripcion") String descripcion);
}
