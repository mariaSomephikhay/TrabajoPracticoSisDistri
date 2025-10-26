package com.grupoK.connector.database.repositories;

import com.grupoK.connector.database.entities.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface IOfertaRepository extends JpaRepository<Oferta, Serializable> {
    List<Oferta> findAllByOrganizacionDonante_Id(@Param("idOrganizacionDonante") Integer idOrganizacionDonante);

}
