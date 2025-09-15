package com.grupoK.Tp1SistemasDistribuidos.repositories;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.grupoK.Tp1SistemasDistribuidos.entities.Usuario;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Serializable>{
	Optional<Usuario> findByUsername(@Param("username") String username);
	Optional<Usuario> findByEmail(@Param("email") String email);
}
