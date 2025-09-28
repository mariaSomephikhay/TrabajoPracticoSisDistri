package com.grupoK.Tp1SistemasDistribuidos.repositories;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.grupoK.Tp1SistemasDistribuidos.entities.Evento;
import com.grupoK.Tp1SistemasDistribuidos.entities.Usuario;

public interface IEventoRepository extends JpaRepository<Evento, Serializable>{
	Optional<Evento> findByNombre(@Param("nombre") String nombre);
	
	@Query("SELECT e.usuarios FROM Evento e WHERE e.id = :eventoId")
	List<Usuario> findUsuariosByEventoId(Integer eventoId);


}
