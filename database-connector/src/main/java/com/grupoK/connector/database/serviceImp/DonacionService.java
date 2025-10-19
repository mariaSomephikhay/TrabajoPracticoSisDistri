package com.grupoK.connector.database.serviceImp;


import com.grupoK.connector.database.configuration.annotations.ConsumerServerAnnotation;
import com.grupoK.connector.database.configuration.annotations.GrpcServerAnnotation;
import com.grupoK.connector.database.entities.Donacion;
import com.grupoK.connector.database.entities.Organizacion;
import com.grupoK.connector.database.entities.Usuario;
import com.grupoK.connector.database.repositories.IDonacionRepository;
import com.grupoK.connector.database.repositories.IOrganizacionRepository;
import com.grupoK.connector.database.service.IDonacionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@GrpcServerAnnotation
@ConsumerServerAnnotation
@Service
public class DonacionService implements IDonacionService {

    @Autowired
    private IOrganizacionRepository organizacionRepository;
	@Autowired
	private IDonacionRepository donacionRepository;
	
	@Override
	public Donacion saveOrUpdate(Donacion donacion) throws Exception {
		if (donacion.getCantidad()<0) throw new Exception("La cantidad no puede ser negativa");
		if(donacion.getId() == 0 || donacion.getId() == null) {
			donacion.setId(null); //Proto lo devuelve con un 0
			donacion.setUsuarioAlta(donacion.getUsuarioModificacion());
            //if(donacion.getOrganizacion() == null)
              //  donacion.setOrganizacion(obtenerOrganizacionPropia(1)); //El id 1 es de nuestra organizacion
        	return donacionRepository.save(donacion);
        }
        else {
        	Optional<Donacion> donacionActual = (donacionRepository.findById(donacion.getId()));
        	if (donacionActual.isEmpty()) throw new Exception("Donacion no encontrada");
        	map(donacionActual.get(), donacion);
        	return donacionRepository.save(donacionActual.get());
        }
	}
	
	private void map(Donacion preUpdated, Donacion updated) {
		if(!StringUtils.isEmpty(updated.getDescripcion()))
			preUpdated.setDescripcion(updated.getDescripcion());
		if(updated.getCantidad()>=0)
			preUpdated.setCantidad(updated.getCantidad());
		if(updated.getUsuarioModificacion()!=null)
			preUpdated.setUsuarioModificacion(updated.getUsuarioModificacion());
		/*if(updated.getCantidad()==0) {
			preUpdated.setEliminado(true);
		}else {
			preUpdated.setEliminado(false);
		}*/
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
	public Donacion delete(Integer id, Usuario usuario) throws Exception {
		try {
		Donacion donacion = this.findById(id);
		donacion.setEliminado(true);
		donacion.setUsuarioModificacion(usuario);
		donacionRepository.save(donacion);
		return donacion;
		}catch (Exception e) {
			throw new Exception(e);
		}
		
	}

    private Organizacion obtenerOrganizacionPropia (Integer idOrganizacion) throws  Exception{
        Optional<Organizacion> organizacionPropia = organizacionRepository.findById(idOrganizacion);
        if(organizacionPropia.isEmpty())
            throw new Exception("Organizacion propia no encontrada");

        return  organizacionPropia.get();
    }


}
