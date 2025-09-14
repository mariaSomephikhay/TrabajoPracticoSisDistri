package com.grupoK.Tp1SistemasDistribuidos.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupoK.Tp1SistemasDistribuidos.entities.Categoria;

public interface ICategoriaRepository extends JpaRepository<Categoria, Serializable>{

}
