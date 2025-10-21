package com.grupoK.connector.database.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupoK.connector.database.configuration.annotations.GrpcServerAnnotation;
import com.grupoK.connector.database.entities.Voluntario;
import com.grupoK.connector.database.exceptions.VoluntarioNotFoundException;
import com.grupoK.connector.database.repositories.IVoluntarioRepository;
import com.grupoK.connector.database.service.IVoluntarioService;

@GrpcServerAnnotation
@Service
public class VoluntarioService implements IVoluntarioService {

	@Autowired
	private IVoluntarioRepository voluntarioRepository;
	
	@Override
	public Voluntario findById(String id) throws Exception {
		return voluntarioRepository.findById(id)
			.orElseThrow(() -> new VoluntarioNotFoundException("No se encontró voluntario"));
	}

	@Override
	public Voluntario saveOrUpdate(Voluntario voluntario) {
		return voluntarioRepository.save(voluntario);
	}
}
