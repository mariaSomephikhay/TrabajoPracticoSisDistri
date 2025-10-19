package com.grupoK.connector.database.service;



import com.grupoK.connector.database.entities.Donacion;
import com.grupoK.connector.database.entities.Evento;
import com.grupoK.connector.database.entities.EventoDonacion;
import com.grupoK.connector.database.entities.Usuario;

import java.util.List;

public interface IEventoDonacionService {
	boolean insertEventoDonacion(Evento evento, Donacion lstDonacion, int cantDonacion, Usuario user) throws Exception;
	List<EventoDonacion> getEventoWithDonacionByIdEvento(String idEvento);
}
