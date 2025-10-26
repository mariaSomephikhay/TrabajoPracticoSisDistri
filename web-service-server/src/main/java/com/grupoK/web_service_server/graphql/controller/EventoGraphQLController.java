package com.grupoK.web_service_server.graphql.controller;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import com.grupoK.connector.database.entities.Evento;
import com.grupoK.connector.database.serviceImp.EventoService;
import com.grupoK.web_service_server.graphql.model.InformeEventoDto;

@Controller
public class EventoGraphQLController {

	@Autowired
    private EventoService eventoService;
	
	
	@QueryMapping
    public List<InformeEventoDto> informeParticipacionEventos(@Argument String usuarioId,
    															@Argument String fechaDesde,@Argument String fechaHasta
    															,@Argument String tieneDonacion) {

		LocalDate fechaDesdeDate = (fechaDesde != null && !fechaDesde.isBlank()) ? LocalDate.parse(fechaDesde) : null;
		LocalDate fechaHastaDate = (fechaHasta != null && !fechaHasta.isBlank()) ? LocalDate.parse(fechaHasta) : null;

        List<InformeEventoDto> lstInforme = new ArrayList<>();
        
        try {
			String mes = "";
			
			List<Evento> eventosAsociados = eventoService.obtenerInforme(usuarioId, fechaDesdeDate, fechaHastaDate, Integer.valueOf(tieneDonacion));
			InformeEventoDto informe = new InformeEventoDto();
			for(Evento e : eventosAsociados) {
				if(mes.isEmpty()) {
					mes = e.getFecha().getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
					informe.getEventos().add(e);
				}else {
					String auxMes = e.getFecha().getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
					if(auxMes.equalsIgnoreCase(mes)) {
						informe.getEventos().add(e);
					}else {
						//guardo la lista generada
						informe.setMes(mes);
						//informe.setEventos(lstEventoDetalle);
						lstInforme.add(informe);
							
						//vuelvo a generar la nueva lista
						mes=auxMes;
						informe = new InformeEventoDto();
						informe.getEventos().add(e);
					}
				}
			}
			
			if(informe!=null) {
		        informe.setMes(mes);
		        lstInforme.add(informe);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        return lstInforme;
    }
}
