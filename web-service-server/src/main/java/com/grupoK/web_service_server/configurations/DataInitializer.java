package com.grupoK.web_service_server.configurations;

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

                Usuario usuario = (usuarioRepo.findByUsername("user1")).get();
                Usuario kafka = (usuarioRepo.findByUsername("Kafka")).get();
                //Usuario usuarioExternoDos = (usuarioRepo.findByUsername("user4")).get();

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


                Donacion donacionExternaUno = donacionRepo.save(new Donacion(
                        null,	// id
                        //organizacionExternaUno,
                        cateRepo.findById(3).get(), // categoria
                        "Pantalones", // descripcion
                        10, // cantidad
                        false,   // eliminado
                        null,    // fechaAlta (se genera sola)
                        kafka,
                        null,
                        kafka,
                        null
                ));
                List<Donacion> donacionesinternasS = donacionRepo.saveAll(
                        Arrays.asList(
                                new Donacion(
                                        null,	// id
                                        //organizacionExternaDos,
                                        cateRepo.findById(4).get(), // categoria
                                        "Cartucheras", // descripcion
                                        4, // cantidad
                                        false,   // eliminado
                                        null,    // fechaAlta (se genera sola)
                                        usuario,
                                        null,
                                        usuario,
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
                                        usuario,
                                        null,
                                        usuario,
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
                Donacion donacioninternaSDos = donacionRepo.findById(4).get();
                Donacion donacioninterneSTres = donacionRepo.findById(5).get();

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
                                donacioninternaSDos//,
                                //1
                        ),
                        new SolicitudDonacion(
                                null,
                                solicitudes.get(0),
                                donacioninterneSTres//,
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