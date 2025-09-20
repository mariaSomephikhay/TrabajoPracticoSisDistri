package com.grupoK.Tp1SistemasDistribuidos.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupoK.Tp1SistemasDistribuidos.entities.Donacion;

public interface IDonacionRepository extends JpaRepository<Donacion, Serializable>{ 

}
