package com.grupoK.connector.database.service;

import com.grupoK.connector.database.entities.Oferta;
import com.grupoK.connector.database.entities.Organizacion;

import java.util.List;

public interface IOfertaService {
    List<Oferta> findAllByOrganizationId(Integer id);
    Oferta save(Oferta oferta) throws  Exception;
    Oferta getLast();

}
