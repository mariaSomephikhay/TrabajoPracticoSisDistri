package com.grupoK.connector.database.serviceImp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupoK.connector.database.entities.Organizacion;
import com.grupoK.connector.database.repositories.IOrganizacionRepository;
import com.grupoK.connector.database.service.IOrganizacionService;

@Service
public class OrganizacionService implements IOrganizacionService {
	
	@Autowired
	IOrganizacionRepository organizacionRepository;

	@Override
	public Organizacion findById(Integer id) throws Exception {
		Optional<Organizacion> request = organizacionRepository.findById(id);
        if(request.isEmpty())
            throw new Exception("Organizacion no encontrada");
        return request.get();
	}

}
