package com.grupoK.Tp1SistemasDistribuidos.service;

import java.util.List;

import com.grupoK.Tp1SistemasDistribuidos.entities.Donacion;
import com.grupoK.Tp1SistemasDistribuidos.entities.Evento;
import com.grupoK.Tp1SistemasDistribuidos.entities.Usuario;

public interface IEventoDonacionService {
	boolean insertEventoDonacion(Evento evento, Donacion lstDonacion, int cantDonacion, Usuario user) throws Exception;
}
