package com.grupoK.Tp1SistemasDistribuidos.serviceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupoK.Tp1SistemasDistribuidos.entities.Usuario;
import com.grupoK.Tp1SistemasDistribuidos.exceptions.UserEmailAlreadyExistsException;
import com.grupoK.Tp1SistemasDistribuidos.exceptions.UserNotFoundException;
import com.grupoK.Tp1SistemasDistribuidos.exceptions.UserUsernameAlreadyExistsException;
import com.grupoK.Tp1SistemasDistribuidos.repositories.IUsuarioRepository;
import com.grupoK.Tp1SistemasDistribuidos.service.IUsuarioService;

import ch.qos.logback.classic.Logger;

@Service
public class UsuarioService implements IUsuarioService {

	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	@Override
	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}
	

	@Override
	public Usuario saveOrUpdate(Usuario usuario) {
        if(usuario.getId() == 0 || usuario.getId() == null) {
        	if(usuarioRepository.findByUsername(usuario.getUsername()).isPresent())
                throw new UserUsernameAlreadyExistsException("El USERNAME ya está en uso.");
        	else if(usuarioRepository.findByEmail(usuario.getEmail()).isPresent())
                throw new UserEmailAlreadyExistsException("El EMAIL ya está en uso.");
        	else {
            	usuario.setId(null); //Proto lo devuelve con un 0
            	usuario.setActivo(true); //Si no se pasa en la request proto lo devuelve en false
            	return usuarioRepository.save(usuario);
        	}
        }
        else {        	
            Optional<Usuario> userDB = usuarioRepository.findById(usuario.getId());
            if (userDB.isEmpty())
                throw new UserNotFoundException("No existe un usuario con ese ID.");

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
	public Usuario findById(Integer idUser) {
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
	public Usuario delete(Integer id) {
		Usuario user = this.findById(id);
		user.setActivo(false);
		usuarioRepository.save(user);
		return user;	
	}


	@Override
	public List<Usuario> getUsersById(List<Integer> lstId) {
		List<Usuario> lstUsers = new ArrayList<Usuario>();
		try {
			for (Integer id : lstId) {
				lstUsers.add(this.findById(id));
	        }
		}catch(Exception e) {
			System.out.println("No existe usuario"); //Implementar logger info
		}
		
		return lstUsers;
	}


}
