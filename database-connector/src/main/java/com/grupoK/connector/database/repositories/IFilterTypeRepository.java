package com.grupoK.connector.database.repositories;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.grupoK.connector.database.entities.FilterType;

@Repository
public interface IFilterTypeRepository extends JpaRepository<FilterType, Serializable>{
	Optional<FilterType> findByType(String type);
}
