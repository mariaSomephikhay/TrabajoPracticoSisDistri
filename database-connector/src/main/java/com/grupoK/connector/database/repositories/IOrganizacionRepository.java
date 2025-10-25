package com.grupoK.connector.database.repositories;

import com.grupoK.connector.database.entities.Organizacion;
import com.grupoK.connector.database.entities.Solicitud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.graphql.data.GraphQlRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface IOrganizacionRepository extends JpaRepository<Organizacion, Integer> {
}
