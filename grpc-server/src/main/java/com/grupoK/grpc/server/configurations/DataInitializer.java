package com.grupoK.grpc.server.configurations;

import com.grupoK.connector.database.entities.*;
import com.grupoK.connector.database.entities.enums.TipoCategoria;
import com.grupoK.connector.database.entities.enums.TipoRoles;
import com.grupoK.connector.database.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(IOrganizacionRepository orgRepo, ICategoriaRepository cateRepo, IDonacionRepository donacionRepo,
                               IUsuarioRepository usuarioRepo, IRolRepository rolRepo, ISolicitudRepository soliRepo, ISolicitudDonacionRepository soliDonaRepo) {
        return args -> {
            if (rolRepo.count() == 0) {
                Organizacion organizacionPropia = orgRepo.save(new Organizacion(null, "GrupoK", false));
                Organizacion organizacionExternaUno = orgRepo.save(new Organizacion(null, "Nova Trend", true));
                Organizacion organizacionExternaDos = orgRepo.save(new Organizacion(null, "Esencia Viva", true));
                orgRepo.save(new Organizacion(null, "Rincón Urbano", true));

                Rol presidente = rolRepo.save(new Rol(null, TipoRoles.PRESIDENTE));
                Rol voluntario = rolRepo.save(new Rol(null, TipoRoles.VOLUNTARIO));
                Rol coordinador = rolRepo.save(new Rol(null, TipoRoles.COORDINADOR));
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
                        presidente,         // rol
                        organizacionPropia  //organizacion
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
                        voluntario,
                        organizacionPropia
                ));

                usuarioRepo.save(new Usuario(
                        null,
                        "user3",
                        "$2b$12$q0cJWPMPkXziA2KuCig01.b88wmiRQxaBwAAQQWAwzUgU6R3VpSYe", //password (user2)
                        "user3@mail.com",
                        "Julian",
                        "Perez",
                        null,
                        true,
                        null,
                        null,
                        null,
                        coordinador,
                        organizacionExternaUno
                ));

                usuarioRepo.save(new Usuario(
                        null,
                        "user4",
                        "$2b$12$q0cJWPMPkXziA2KuCig01.b88wmiRQxaBwAAQQWAwzUgU6R3VpSYe", //password (user2)
                        "user4@mail.com",
                        "Lucas",
                        "Castillo",
                        null,
                        true,
                        null,
                        null,
                        null,
                        coordinador,
                        organizacionExternaDos
                ));
                
                usuarioRepo.save(new Usuario(
                        null,			   // id
                        "Kafka",           // username
                        "$2b$12$4pzYx189NUVwtpsK5uTuUOJR60MhqKZVYjss.2pZPuipv2/P/TwmW", // password(user1)
                        "Kafka@mail.com",   // email
                        "Kafka",          // nombre
                        "Kafka",           // apellido
                        "9091",      // telefono
                        true,              // activo
                        null,			   // lst de evento
                        null,              // fechaAlta (se genera sola)
                        null,              // fechaModificacion (se genera sola)
                        presidente,         // rol
                        organizacionPropia  //organizacion
                ));

            }

            if (cateRepo.count() == 0) {

                Organizacion organizacionPropia = orgRepo.findById(1).get();
                Organizacion organizacionExternaUno = orgRepo.findById(2).get();
                Organizacion organizacionExternaDos = orgRepo.findById(3).get();
                Organizacion organizacionExternaTres = orgRepo.findById(4).get();

                Categoria alimento = cateRepo.save(new Categoria(null, TipoCategoria.ALIMENTO));
                cateRepo.save(new Categoria(null, TipoCategoria.JUGUETE));
                cateRepo.save(new Categoria(null, TipoCategoria.ROPA));
                Categoria escolar = cateRepo.save(new Categoria(null, TipoCategoria.UTIL_ESCOLAR));

                Usuario usuario = (usuarioRepo.findByUsername("user1")).get();
                Usuario usuarioExternoUno = (usuarioRepo.findByUsername("user3")).get();
                Usuario usuarioExternoDos = (usuarioRepo.findByUsername("user4")).get();

                donacionRepo.save(new Donacion(
                        null,	// id
                        //organizacionPropia,
                        alimento, // categoria
                        "Pure de tomates", // descripcion
                        3, // cantidad
                        false,   // eliminado
                        null,          // fechaAlta (se genera sola)
                        usuario,
                        null,
                        usuario,
                        null
                ));
                donacionRepo.save(new Donacion(
                        null,	// id
                        ///organizacionPropia,
                        escolar, // categoria
                        "Cartuchera", // descripcion
                        2, // cantidad
                        false,   // eliminado
                        null,          // fechaAlta (se genera sola)
                        usuario,
                        null,
                        usuario,
                        null
                ));


                Donacion donacionExternaUno = donacionRepo.save(new Donacion(
                        null,	// id
                        //organizacionExternaUno,
                        cateRepo.findById(3).get(), // categoria
                        "Pantalones", // descripcion
                        10, // cantidad
                        false,   // eliminado
                        null,    // fechaAlta (se genera sola)
                        usuarioExternoUno,
                        null,
                        usuarioExternoUno,
                        null
                ));
                List<Donacion> donacionesExternasDos = donacionRepo.saveAll(
                        Arrays.asList(
                                new Donacion(
                                        null,	// id
                                        //organizacionExternaDos,
                                        cateRepo.findById(4).get(), // categoria
                                        "Cartucheras", // descripcion
                                        4, // cantidad
                                        false,   // eliminado
                                        null,    // fechaAlta (se genera sola)
                                        usuarioExternoDos,
                                        null,
                                        usuarioExternoDos,
                                        null
                                ),
                                new Donacion(
                                        null,	// id
                                        //organizacionExternaDos,
                                        cateRepo.findById(1).get(), // categoria
                                        "Carne", // descripcion
                                        8, // cantidad
                                        false,   // eliminado
                                        null,    // fechaAlta (se genera sola)
                                        usuarioExternoDos,
                                        null,
                                        usuarioExternoDos,
                                        null
                                )
                        )
                );

            }

            if (soliRepo.count() == 0) {
                Organizacion organizacionPropia = orgRepo.findById(1).get();
                Organizacion organizacionExternaUno = orgRepo.findById(2).get();
                Organizacion organizacionExternaDos = orgRepo.findById(3).get();
                Organizacion organizacionExternaTres = orgRepo.findById(4).get();

                Donacion donacionExternaUno = donacionRepo.findById(3).get();
                Donacion donacionExternaDos = donacionRepo.findById(4).get();
                Donacion donacionExternaTres = donacionRepo.findById(5).get();

                List<Solicitud> solicitudes = soliRepo.saveAll(
                        Arrays.asList(
                                new Solicitud(
                                        "1", // id
                                        organizacionPropia, // organizacion solicitante
                                        //organizacionExternaDos, // organizacion donante
                                        true, // activa
                                        false, // procesada
                                        null  // fechaAlta (se genera sola)
                                ),
                                new Solicitud(
                                        "2", // id
                                        organizacionExternaTres, // organizacion solicitante
                                        //organizacionExternaUno, // organizacion donante
                                        true, // activa
                                        false, // procesada
                                        null  // fechaAlta (se genera sola)
                                )
                        ));

                soliDonaRepo.saveAll(Arrays.asList(
                        new SolicitudDonacion(
                                null,
                                solicitudes.get(0),
                                donacionExternaDos//,
                                //1
                        ),
                        new SolicitudDonacion(
                                null,
                                solicitudes.get(0),
                                donacionExternaTres//,
                                //2
                        ),
                        new SolicitudDonacion(
                                null,
                                solicitudes.get(1),
                                donacionExternaUno//,
                                //2
                        )
                ));
            }
        };
    }
}