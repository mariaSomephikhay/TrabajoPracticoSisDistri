package com.grupoK.connector.database.repositories;


import com.grupoK.connector.database.entities.Evento;
import com.grupoK.connector.database.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository
public interface IEventoRepository extends JpaRepository<Evento, Serializable>{
	Optional<Evento> findByNombre(@Param("nombre") String nombre);
	
	@Query("SELECT e.usuarios FROM Evento e WHERE e.id = :eventoId")
	List<Usuario> findUsuariosByEventoId(String eventoId);

	@Query("SELECT e FROM Evento e LEFT JOIN FETCH e.voluntarios WHERE e.id = :id")
	Evento findByIdWithVoluntarios(@Param("id") String id);

}
