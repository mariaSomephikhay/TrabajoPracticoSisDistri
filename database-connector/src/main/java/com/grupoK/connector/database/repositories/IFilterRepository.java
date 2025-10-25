package com.grupoK.connector.database.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.grupoK.connector.database.entities.Filter;

@Repository
public interface IFilterRepository extends JpaRepository<Filter, Serializable>{
	@Query(value = "SELECT id, name, value_filter FROM filter WHERE id_usuario = :idUsuario AND filter_type_id = :idFilterType", nativeQuery = true)
	List<Object[]> findByUsuarioIdNative(@Param("idUsuario") String idUsuario,@Param("idFilterType") Integer idFilterType);


}
