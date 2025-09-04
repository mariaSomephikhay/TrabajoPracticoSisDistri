package com.grupoK.Tp1SistemasDistribuidos.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupoK.Tp1SistemasDistribuidos.entities.Rol;

@Repository
public interface IRolRepository extends JpaRepository<Rol, Serializable>{
}
