package com.grupoK.connector.database.repositories;


import com.grupoK.connector.database.entities.Evento;
import com.grupoK.connector.database.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IEventoRepository extends JpaRepository<Evento, Serializable>{
	Optional<Evento> findByNombre(@Param("nombre") String nombre);
	
	@Query("SELECT e.usuarios FROM Evento e WHERE e.id = :eventoId")
	List<Usuario> findUsuariosByEventoId(String eventoId);

	@Query("SELECT e FROM Evento e LEFT JOIN FETCH e.voluntarios WHERE e.id = :id")
	Evento findByIdWithVoluntarios(@Param("id") String id);
	
	@Query("""
		    SELECT DISTINCT e FROM Evento e
		    JOIN e.usuarios p
		    WHERE e.activo = true
		      AND p.id = :usuarioId
		      AND (:desde IS NULL OR e.fecha >= :desde)
		      AND (:hasta IS NULL OR e.fecha <= :hasta)
		    ORDER BY e.fecha ASC
		""")
		List<Evento> filtrarEventos(
		    @Param("usuarioId") String usuarioId,
		    @Param("desde") LocalDateTime desde,
		    @Param("hasta") LocalDateTime hasta
		);

	@Query("""
			SELECT DISTINCT e
			FROM Evento e
			LEFT JOIN FETCH e.eventoDonaciones ed
			LEFT JOIN FETCH ed.donacion d
			WHERE e.id = :id
			  AND (
			       :modo = 0
			    OR (:modo = 1 AND ed.id IS NOT NULL)
			    OR (:modo = 2 AND ed.id IS NULL)
			  )
			""")
			List<Evento> findEventosByIdAndModo(@Param("id") String id, @Param("modo") Integer modo);







}
