package com.grupoK.Tp1SistemasDistribuidos.serviceImp;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupoK.Tp1SistemasDistribuidos.entities.Donacion;
import com.grupoK.Tp1SistemasDistribuidos.entities.Usuario;
import com.grupoK.Tp1SistemasDistribuidos.repositories.IDonacionRepository;
import com.grupoK.Tp1SistemasDistribuidos.service.IDonacionService;

@Service
public class DonacionService implements IDonacionService{

	@Autowired
	private IDonacionRepository donacionRepository;
	
	@Override
	public Donacion saveOrUpdate(Donacion donacion) throws Exception {
		if (donacion.getCantidad()<0) throw new Exception("La cantidad no puede ser negativa");
		if(donacion.getId() == 0 || donacion.getId() == null) {
			donacion.setId(null); //Proto lo devuelve con un 0
			donacion.setIdUsuarioAlta(donacion.getIdUsuarioModificacion());
        	return donacionRepository.save(donacion);
        }
        else {
        	Donacion donacionActual = (donacionRepository.findById(donacion.getId())).get();
        	map(donacionActual, donacion);
        	return donacionRepository.save(donacionActual);
        }
	}
	
	private void map(Donacion preUpdated, Donacion updated) {
		if(!StringUtils.isEmpty(updated.getDescripcion()))
			preUpdated.setDescripcion(updated.getDescripcion());
		if(updated.getCantidad()>0)
			preUpdated.setCantidad(updated.getCantidad());
		if(updated.getIdUsuarioModificacion()!=null)
			preUpdated.setIdUsuarioModificacion(updated.getIdUsuarioModificacion());
	}

	@Override
	public List<Donacion> findAll() {
		return donacionRepository.findAll();
	}

	@Override
	public Donacion findById(Integer id) throws Exception {
		Optional<Donacion> donacion = donacionRepository.findById(id);
		if (donacion.isEmpty()) throw new Exception("Donacion no encontrada");
		return donacion.get();
	}

	@Override
	public Donacion delete(Integer id) throws Exception {
		try {
		Donacion donacion = this.findById(id);
		donacion.setEliminado(true);
		donacionRepository.save(donacion);
		return donacion;
		}catch (Exception e) {
			throw new Exception(e);
		}
		
	}


}
