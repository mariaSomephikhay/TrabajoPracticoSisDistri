package com.grupoK.grpc.server.configurations;

import com.grupoK.connector.database.entities.Categoria;
import com.grupoK.connector.database.entities.Donacion;
import com.grupoK.connector.database.entities.Rol;
import com.grupoK.connector.database.entities.Usuario;
import com.grupoK.connector.database.entities.enums.TipoCategoria;
import com.grupoK.connector.database.entities.enums.TipoRoles;
import com.grupoK.connector.database.repositories.ICategoriaRepository;
import com.grupoK.connector.database.repositories.IDonacionRepository;
import com.grupoK.connector.database.repositories.IRolRepository;
import com.grupoK.connector.database.repositories.IUsuarioRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
                        "$2b$12$4pzYx189NUVwtpsK5uTuUOJR60MhqKZVYjss.2pZPuipv2/P/TwmW", // password(user1)
                        "user@mail.com",   // email
                        "Manuel",          // nombre
                        "Lopez",           // apellido
                        "1122334455",      // telefono
                        true,              // activo
                        null,			   // lst de evento
                        null,              // fechaAlta (se genera sola)
                        null,              // fechaModificacion (se genera sola)
                        presidente         // rol
                ));

                usuarioRepo.save(new Usuario(
                        null,
                        "user2",
                        "$2b$12$q0cJWPMPkXziA2KuCig01.b88wmiRQxaBwAAQQWAwzUgU6R3VpSYe", //password (user2)
                        "user2@mail.com",
                        "Lucia",
                        "Garcia",
                        "1198765432",
                        true,
                        null,
                        null,
                        null,
                        voluntario
                ));
            }
        };
    }
    
    @Bean
    CommandLineRunner initDataDonacion(ICategoriaRepository cateRepo, IDonacionRepository donacionRepo, IUsuarioRepository usuarioRepo) {
        return args -> {
            if (cateRepo.count() == 0) {
                Categoria alimento = cateRepo.save(new Categoria(null, TipoCategoria.ALIMENTO));
                cateRepo.save(new Categoria(null, TipoCategoria.JUGUETE));
                cateRepo.save(new Categoria(null, TipoCategoria.ROPA));
                cateRepo.save(new Categoria(null, TipoCategoria.UTIL_ESCOLAR));
                Usuario usuario = (usuarioRepo.findByUsername("user1")).get();
                
                donacionRepo.save(new Donacion(
                        null,	// id
                        alimento, // categoria
                        "Pure de tomates", // descripcion
                        3, // cantidad
                        false,   // eliminado
                        null,          // fechaAlta (se genera sola)
                        usuario,
                        null,
                        usuario
                ));

              
            }
        };
    }
}
