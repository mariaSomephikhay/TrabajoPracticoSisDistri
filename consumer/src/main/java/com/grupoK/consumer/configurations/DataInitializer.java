package com.grupoK.consumer.configurations;


import com.grupoK.connector.database.entities.*;
import com.grupoK.connector.database.entities.enums.TipoCategoria;
import com.grupoK.connector.database.entities.enums.TipoRoles;
import com.grupoK.connector.database.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(IOrganizacionRepository orgRepo, ICategoriaRepository cateRepo, IDonacionRepository donacionRepo,
                               IUsuarioRepository usuarioRepo, IRolRepository rolRepo, ISolicitudRepository soliRepo, ISolicitudDonacionRepository soliDonaRepo, IEventoRepository eventoRepo, IFilterTypeRepository filterTypeRepository, IFilterRepository filterRepository) {
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
                        "GK-20251017133221-1111",			   // id
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
                        "GK-20251017133221-2222",
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
                        "GK-20251017133221-3333",
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
                        "GK-20251017133221-4444",
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
                        "GK-20251017133221-5555",			   // id
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

                Usuario usuarioExternoUno = (usuarioRepo.findByUsername("user3")).get();
                Usuario usuarioExternoDos = (usuarioRepo.findByUsername("user4")).get();

                Usuario kafka = (usuarioRepo.findByUsername("Kafka")).get();

                donacionRepo.save(new Donacion(
                        null,	// id
                        //organizacionPropia,
                        alimento, // categoria
                        "Pure de tomates", // descripcion
                        3, // cantidad
                        false,   // eliminado
                        null,          // fechaAlta (se genera sola)
                        kafka,
                        null,
                        kafka,
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
                        kafka,
                        null,
                        kafka,
                        null
                ));

                List<Donacion> donacionesinternasS = donacionRepo.saveAll(
                        Arrays.asList(
                                new Donacion(
                                        null,	// id
                                        //org interna,
                                        cateRepo.findById(4).get(), // categoria
                                        "Cartucheras", // descripcion
                                        4, // cantidad
                                        false,   // eliminado
                                        null,    // fechaAlta (se genera sola)
                                        kafka,
                                        null,
                                        kafka,
                                        null

                                ),
                                new Donacion(
                                        null,	// id
                                        //oorg interna,
                                        cateRepo.findById(1).get(), // categoria
                                        "Carne", // descripcion
                                        8, // cantidad
                                        false,   // eliminado
                                        null,    // fechaAlta (se genera sola)
                                        kafka,
                                        null,
                                        kafka,
                                        null
                                )
                        )
                );

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


                donacionRepo.save(new Donacion(
                        null,	// id
                        //organizacionExterna,
                        alimento, // categoria
                        "Arroz", // descripcion
                        10, // cantidad
                        false,   // eliminado
                        null,          // fechaAlta (se genera sola)
                        usuarioExternoUno,
                        null,
                        usuarioExternoUno,
                        null
                ));
                donacionRepo.save(new Donacion(
                        null,	// id
                        ///organizacionExterna,
                        escolar, // categoria
                        "Mochila", // descripcion
                        5, // cantidad
                        false,   // eliminado
                        null,          // fechaAlta (se genera sola)
                        usuarioExternoUno,
                        null,
                        usuarioExternoUno,
                        null
                ));
                donacionRepo.save(new Donacion(
                        null,	// id
                        ///organizacionExterna,
                        cateRepo.findById(3).get(), // categoria
                        "Remeras", // descripcion
                        7, // cantidad
                        false,   // eliminado
                        null,          // fechaAlta (se genera sola)
                        usuarioExternoUno,
                        null,
                        usuarioExternoUno,
                        null
                ));
                donacionRepo.save(new Donacion(
                        null,	// id
                        ///interna,
                        escolar, // categoria
                        "Reglas", // descripcion
                        100, // cantidad
                        false,   // eliminado
                        null,          // fechaAlta (se genera sola)
                        usuarioExternoUno,
                        null,
                        usuarioExternoUno,
                        null
                ));
                donacionRepo.save(new Donacion(
                        null,	// id
                        //interna,
                        alimento, // categoria
                        "Chocolate", // descripcion
                        150, // cantidad
                        false,   // eliminado
                        null,          // fechaAlta (se genera sola)
                        usuarioExternoDos,
                        null,
                        usuarioExternoDos,
                        null
                ));
                donacionRepo.save(new Donacion(
                        null,	// id
                        //interna,
                        alimento, // categoria
                        "Fideos", // descripcion
                        110, // cantidad
                        false,   // eliminado
                        null,          // fechaAlta (se genera sola)
                        usuarioExternoDos,
                        null,
                        usuarioExternoDos,
                        null
                ));

            }

            if (soliRepo.count() == 0) {
                Organizacion organizacionPropia = orgRepo.findById(1).get();
                Organizacion organizacionExternaUno = orgRepo.findById(2).get();
                Organizacion organizacionExternaDos = orgRepo.findById(3).get();
                Organizacion organizacionExternaTres = orgRepo.findById(4).get();


                Donacion donacioninternaSUno = donacionRepo.findById(1).get();
                Donacion donacioninterneSDos = donacionRepo.findById(2).get();
                Donacion donacioninterneSTres = donacionRepo.findById(3).get();
                Donacion donacioninterneSCuatro = donacionRepo.findById(4).get();

                Donacion donacionExternaUno = donacionRepo.findById(5).get();
                Donacion arrozExterna = donacionRepo.findById(6).get();
                Donacion mochilaExterna = donacionRepo.findById(7).get();
                Donacion remerasExterna = donacionRepo.findById(8).get();
                Donacion reglaExterna = donacionRepo.findById(9).get();
                Donacion chocolateExterna = donacionRepo.findById(10).get();
                Donacion fideosExterna = donacionRepo.findById(11).get();

                List<Solicitud> solicitudes = soliRepo.saveAll(
                        Arrays.asList(
                                new Solicitud(
                                        "1", // id
                                        organizacionPropia, // organizacion solicitante
                                        true, // activa
                                        false, // procesada
                                        null  // fechaAlta (se genera sola)
                                ),
                                new Solicitud(
                                        "2", // id
                                        organizacionExternaTres, // organizacion solicitante
                                        true, // activa
                                        false, // procesada
                                        null  // fechaAlta (se genera sola)
                                ),
                                new Solicitud(
                                        "3", // id
                                        organizacionExternaDos, // organizacion solicitante
                                        true, // activa
                                        false, // procesada
                                        null  // fechaAlta (se genera sola)
                                ),
                                new Solicitud(
                                        "4", // id
                                        organizacionExternaUno, // organizacion solicitante
                                        true, // activa
                                        false, // procesada
                                        null  // fechaAlta (se genera sola)
                                ),
                                new Solicitud(
                                        "5", // id
                                        organizacionPropia, // organizacion solicitante
                                        true, // activa
                                        true, // procesada
                                        null  // fechaAlta (se genera sola)
                                )
                        ));

                soliDonaRepo.saveAll(Arrays.asList(
                        new SolicitudDonacion(
                                null,
                                solicitudes.get(0),
                                mochilaExterna //,
                                //1
                        ),
                        new SolicitudDonacion(
                                null,
                                solicitudes.get(0),
                                arrozExterna//,
                                //2
                        ),
                        new SolicitudDonacion(
                                null,
                                solicitudes.get(1),
                                donacioninterneSTres//,
                                //2
                        ),
                        new SolicitudDonacion(
                                null,
                                solicitudes.get(2),
                                donacioninternaSUno//,
                                //2
                        ),
                        new SolicitudDonacion(
                                null,
                                solicitudes.get(2),
                                donacioninterneSDos//,
                                //2
                        ),
                        new SolicitudDonacion(
                                null,
                                solicitudes.get(3),
                                chocolateExterna  //,
                                //2
                        ),
                        new SolicitudDonacion(
                                null,
                                solicitudes.get(3),
                                fideosExterna   //,
                                //2
                        ),
                        new SolicitudDonacion(
                                null,
                                solicitudes.get(4),
                                reglaExterna//,
                                //2
                        ),
                        new SolicitudDonacion(
                                null,
                                solicitudes.get(4),
                                donacionExternaUno //,
                                //2
                        )
                ));
            }

            if(eventoRepo.count() == 0) {
                Usuario usuario = (usuarioRepo.findByUsername("user1")).get();
                Organizacion organizacionPropia = orgRepo.findById(1).get();

                Evento evento = new Evento
                        ("GK-20251019203949-1111",
                                "Dia de la Independencia",
                                "Dia de la Independencia",
                                LocalDate.of(2025, 7, 9).atStartOfDay(),
                                null,
                                null,
                                usuario,
                                organizacionPropia,
                                true,
                                false,
                                null,
                                null);

                Evento evento2 = new Evento
                        ("GK-20251019203949-2222",
                                "Dia del Programador",
                                "Dia del Programador",
                                LocalDate.of(2025, 11, 25).atStartOfDay(),
                                null,
                                null,
                                usuario,
                                organizacionPropia,
                                true,
                                false,
                                null,
                                null);

                eventoRepo.save(evento);
                eventoRepo.save(evento2);
            }

            if(filterTypeRepository.count() == 0 && filterRepository.count() == 0) {
                FilterType filterTypeEvento = new FilterType(null, "Evento");
                FilterType filterTypeDonacion = new FilterType(null, "Donacion");

                filterTypeRepository.save(filterTypeEvento);
                filterTypeRepository.save(filterTypeDonacion);

                Usuario usuario = (usuarioRepo.findByUsername("user1")).get();
                Filter filter = new Filter(null,"Filtro por año 2024-2025","fechaDesde:2025-01-01;fechaHasta:2025-12-31;tieneDonacion:0",usuario,filterTypeEvento);
                filterRepository.save(filter);
            }
        };
    }
}