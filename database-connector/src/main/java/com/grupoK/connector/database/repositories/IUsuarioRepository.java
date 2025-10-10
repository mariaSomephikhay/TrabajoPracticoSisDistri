package com.grupoK.connector.database.repositories;

import com.grupoK.connector.database.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Serializable>{
	Optional<Usuario> findByUsername(@Param("username") String username);
	Optional<Usuario> findByEmail(@Param("email") String email);
}
