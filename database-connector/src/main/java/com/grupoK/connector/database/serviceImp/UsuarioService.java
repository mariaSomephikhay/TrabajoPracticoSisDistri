package com.grupoK.connector.database.serviceImp;


import com.grupoK.connector.database.configuration.annotations.GrpcServerAnnotation;
import com.grupoK.connector.database.entities.Organizacion;
import com.grupoK.connector.database.entities.Usuario;
import com.grupoK.connector.database.exceptions.UserEmailAlreadyExistsException;
import com.grupoK.connector.database.exceptions.UserNotFoundException;
import com.grupoK.connector.database.exceptions.UserUsernameAlreadyExistsException;
import com.grupoK.connector.database.repositories.IOrganizacionRepository;
import com.grupoK.connector.database.repositories.IUsuarioRepository;
import com.grupoK.connector.database.service.IUsuarioService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@GrpcServerAnnotation
@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private IOrganizacionRepository organizacionRepository;
	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	@Override
	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}
	

	@Override
	public Usuario saveOrUpdate(Usuario usuario) {
		Optional<Usuario> userDB = usuarioRepository.findById(usuario.getId());
		if (userDB.isEmpty()){
			if(usuarioRepository.findByUsername(usuario.getUsername()).isPresent())
				throw new UserUsernameAlreadyExistsException("El USERNAME ya está en uso.");
	        else if(usuarioRepository.findByEmail(usuario.getEmail()).isPresent())
	             throw new UserEmailAlreadyExistsException("El EMAIL ya está en uso.");
	        else {
	            usuario.setActivo(true); //Si no se pasa en la request proto lo devuelve en false
	            if(usuario.getOrganizacion() == null) {
	            	try {
	            		usuario.setOrganizacion(obtenerOrganizacionPropia(1));
	                } catch (Exception e) {
	                	throw new RuntimeException(e);
	                }
	            }
	            	return usuarioRepository.save(usuario);
	        }
		}else {
				Usuario existingUser = userDB.get();

	            // Se valida username solo si se cambio
	            if (!StringUtils.equals(usuario.getUsername(), existingUser.getUsername())) {
	                usuarioRepository.findByUsername(usuario.getUsername()).ifPresent(u -> {
	                    if (!u.getId().equals(usuario.getId()))
	                        throw new UserUsernameAlreadyExistsException("El USERNAME ya está en uso.");
	                });
	            }

	            // Se valida email solo si se cambio
	            if (!StringUtils.equals(usuario.getEmail(), existingUser.getEmail())) {
	                usuarioRepository.findByEmail(usuario.getEmail()).ifPresent(u -> {
	                    if (!u.getId().equals(usuario.getId()))
	                        throw new UserEmailAlreadyExistsException("El EMAIL ya está en uso.");
	                });
	            }  		
	        	map(existingUser, usuario);
	        	return usuarioRepository.save(existingUser);
			}
	}
	
	
	private void map(Usuario preUpdated, Usuario updated) {
		if(!StringUtils.isEmpty(updated.getNombre()))
			preUpdated.setNombre(updated.getNombre());
		if(!StringUtils.isEmpty(updated.getApellido()))
			preUpdated.setApellido(updated.getApellido());
		if(!StringUtils.isEmpty(updated.getUsername()))
			preUpdated.setUsername(updated.getUsername());
		if(!StringUtils.isEmpty(updated.getEmail()))
			preUpdated.setEmail(updated.getEmail());
		if(!StringUtils.isEmpty(updated.getTelefono()))
			preUpdated.setTelefono(updated.getTelefono());
		if(updated.getActivo() != null)
			preUpdated.setActivo(updated.getActivo());
		if(updated.getRol() != null)
			preUpdated.setRol(updated.getRol());
	}


	@Override
	public Usuario findById(String idUser) {
		Optional<Usuario> user = usuarioRepository.findById(idUser);
		if(user.isEmpty()) 
            throw new UserNotFoundException("No existe un usuario con ese ID.");
		else
			return user.get();
	}

	
	@Override
	public Usuario findByUsername(String username) {
		Optional<Usuario> user = usuarioRepository.findByUsername(username);
		if(user.isEmpty()) 
            throw new UserNotFoundException("No existe un usuario con ese USERNAME.");
		else
			return user.get();
	}



	@Override
	public Usuario delete(String id) {
		Usuario user = this.findById(id);
		user.setActivo(false);
		usuarioRepository.save(user);
		return user;	
	}


	@Override
	public List<Usuario> getUsersById(List<String> lstId) {
		List<Usuario> lstUsers = new ArrayList<Usuario>();
		try {
			for (String id : lstId) {
				lstUsers.add(this.findById(id));
	        }
		}catch(Exception e) {
			System.out.println("No existe usuario"); //Implementar logger info
		}
		
		return lstUsers;
	}

    private Organizacion obtenerOrganizacionPropia (Integer idOrganizacion) throws  Exception{
        Optional<Organizacion> organizacionPropia = organizacionRepository.findById(idOrganizacion);
        if(organizacionPropia.isEmpty())
            throw new Exception("Organizacion propia no encontrada");

        return  organizacionPropia.get();
    }

}
