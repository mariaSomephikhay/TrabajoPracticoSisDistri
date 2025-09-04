package com.grupoK.Tp1SistemasDistribuidos.configurations;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.grupoK.Tp1SistemasDistribuidos.entities.Rol;
import com.grupoK.Tp1SistemasDistribuidos.entities.Usuario;
import com.grupoK.Tp1SistemasDistribuidos.enums.TipoRoles;
import com.grupoK.Tp1SistemasDistribuidos.repositories.IRolRepository;
import com.grupoK.Tp1SistemasDistribuidos.repositories.IUsuarioRepository;

@Configuration
public class DataInitializer {
	
    @Bean
    CommandLineRunner initData(IRolRepository rolRepo, IUsuarioRepository usuarioRepo) {
        return args -> {
            if (rolRepo.count() == 0) {
                Rol presidente = rolRepo.save(new Rol(null, TipoRoles.PRESIDENTE));
                Rol voluntario = rolRepo.save(new Rol(null, TipoRoles.VOLUNTARIO));
                rolRepo.save(new Rol(null, TipoRoles.COORDINADOR));
                rolRepo.save(new Rol(null, TipoRoles.VOCAL));
                
                usuarioRepo.save(new Usuario(
                        null,			   // id
                        "user1",           // username
                        "12345",		   // password
                        "user@mail.com",   // email
                        "Manuel",          // nombre
                        "Lopez",           // apellido
                        "1122334455",      // telefono
                        true,              // activo
                        null,              // fechaAlta (se genera sola)
                        null,              // fechaModificacion (se genera sola)
                        presidente         // rol
                ));

                usuarioRepo.save(new Usuario(
                        null,
                        "user2",
                        "abcd",
                        "user2@mail.com",
                        "Lucia",
                        "Garcia",
                        "1198765432",
                        true,
                        null,
                        null,
                        voluntario
                ));
            }
        };
    }
}
