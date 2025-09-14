package com.grupoK.Tp1SistemasDistribuidos.serviceImp;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupoK.Tp1SistemasDistribuidos.entities.Usuario;
import com.grupoK.Tp1SistemasDistribuidos.repositories.IUsuarioRepository;
import com.grupoK.Tp1SistemasDistribuidos.service.IUsuarioService;

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
        	usuario.setId(null); //Proto lo devuelve con un 0
        	usuario.setActivo(true); //Si no se pasa en la request proto lo devuelve en false
        	return usuarioRepository.save(usuario);
        }
        else {
        	Optional<Usuario> userDB = usuarioRepository.findById(usuario.getId());
        	map(userDB.get(), usuario);
        	return usuarioRepository.save(userDB.get());
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
			return null;
		else
			return user.get();
	}

	
	@Override
	public Usuario findByUsername(String username) {
		Optional<Usuario> user = usuarioRepository.findByUsername(username);
		if(user.isEmpty()) 
			return null;	
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

	
}
