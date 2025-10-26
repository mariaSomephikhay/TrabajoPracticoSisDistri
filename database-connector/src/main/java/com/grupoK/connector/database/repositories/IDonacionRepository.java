package com.grupoK.connector.database.repositories;

import com.grupoK.connector.database.entities.Donacion;
import com.grupoK.connector.database.entities.Organizacion;
import com.grupoK.connector.database.entities.SolicitudDonacion;
import com.grupoK.connector.database.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface IDonacionRepository extends JpaRepository<Donacion, Integer>{
    //List<Donacion> findAllByOrganizacion(@Param("organizacion") Organizacion organizacion);
	@Query("SELECT d FROM Donacion d WHERE NOT EXISTS (SELECT sd FROM SolicitudDonacion sd WHERE sd.donacion = d )")
	List<Donacion> findAllStock();

    @Query("""
        SELECT d.usuarioAlta 
        FROM Donacion d 
        WHERE d.id = :idDonacion
    """)
    Usuario findUsuarioAltaByDonacionId(@Param("idDonacion") Integer idDonacion);

    @Query("""
        SELECT DISTINCT d 
        FROM Donacion d
        JOIN d.usuarioAlta u
        JOIN u.organizacion org
        WHERE org.id = :idOrganizacion
    """)
    List<Donacion> findAllByOrganizacion(@Param("idOrganizacion") Integer idOrganizacion);

}
