package com.grupoK.connector.database.serviceImp;

import com.grupoK.connector.database.configuration.annotations.ConsumerServerAnnotation;
import com.grupoK.connector.database.entities.Donacion;
import com.grupoK.connector.database.entities.Organizacion;
import com.grupoK.connector.database.entities.Solicitud;
import com.grupoK.connector.database.entities.SolicitudDonacion;
import com.grupoK.connector.database.repositories.IDonacionRepository;
import com.grupoK.connector.database.repositories.ISolicitudDonacionRepository;
import com.grupoK.connector.database.repositories.ISolicitudRepository;
import com.grupoK.connector.database.service.ISolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@ConsumerServerAnnotation
@Service
public class SolicitudService implements ISolicitudService {

    @Autowired
    private ISolicitudRepository solicitudRepository;
    @Autowired
    private ISolicitudDonacionRepository solicitudDonacionRepository;
    @Autowired
    private IDonacionRepository donacionRepository;

    @Override
    public Solicitud findById(String idSolicitud) throws Exception {
        Optional<Solicitud> request = solicitudRepository.findById(idSolicitud);
        if(request.isEmpty())
            throw new Exception("Solicitud no encontrada");
        return request.get();
    }

    @Override
    public Solicitud saveOrUpdate(Solicitud solicitud, List<SolicitudDonacion> donacionesAsociadas) throws Exception {
        if(hasValidationErrors(solicitud, donacionesAsociadas))
            throw new Exception("La solicitud no cumple con las validaciones requeridas");

        //if(solicitud.getId() == null || solicitud.getId().equals(0)) {
            //Valores por defecto al crearse la solicitud
            solicitud.setActiva(true);
            solicitud.setProcesada(false);
            donacionesAsociadas.forEach(d -> donacionRepository.save(d.getDonacion()));
            
            Solicitud nuevaSolicitud = solicitudRepository.save(solicitud);

            donacionesAsociadas.forEach(d -> d.setSolicitud(nuevaSolicitud));
            solicitudDonacionRepository.saveAll(donacionesAsociadas);

            return nuevaSolicitud;
            
        //las solicitudes no se editan
        //en todo caso las organizacion las dan de bajas
        /*}
        else {
            Solicitud solicitudActual = findById(solicitud.getId());

            List<SolicitudDonacion> donacionesActuales = solicitudDonacionRepository.findAllById(
                    donacionesAsociadas.stream().map(SolicitudDonacion::getId).toList());
            if(donacionesActuales.isEmpty()) throw new Exception("La solicitud no contiene donaciones asociadas");

            processRequestAndDonations(solicitudActual, donacionesActuales);
            return solicitudRepository.save(solicitudActual);
        }*/
    }

    @Override
    public Solicitud delete(String idSolicitud) throws Exception {
        Solicitud solicitudBD = findById(idSolicitud);
        List<SolicitudDonacion> donationsAssociatedToRequest = findAllDonationsAssociatedByRequest(solicitudBD);

        //Baja fisica en la tabla SolicitudDonacion
        solicitudDonacionRepository.deleteAllById(donationsAssociatedToRequest.stream().map(SolicitudDonacion::getId).toList());

        //Baja logica de la solicitud
        solicitudBD.setActiva(false);
        return solicitudRepository.save(solicitudBD);
    }

    @Override
    public List<Solicitud> findAll() {
        return solicitudRepository.findAll();
    }

    @Override
    public List<SolicitudDonacion> findAllDonationsAssociatedByRequest(Solicitud solicitud) throws Exception {
        List<SolicitudDonacion> donationsAssociated = solicitudDonacionRepository.findAllBySolicitud(solicitud);
        if(donationsAssociated.isEmpty())
            throw new Exception("La solicitud no contiene donaciones asociadas");

        return donationsAssociated;
    }

    private Boolean hasValidationErrors(Solicitud nuevaSolicitud, List<SolicitudDonacion> donaciones) {
        if(nuevaSolicitud == null) //No puede esta vacia
            return  true;
        else if(donaciones.isEmpty()) //Debe tener al menos una donacion
            return  true;
        //else if(nuevaSolicitud.getOrganizacionDonante() == null || nuevaSolicitud.getOrganizacionSolicitante() == null) //Debe tener sus organzacion solicitante y donante
            //return true;
        //else if(nuevaSolicitud.getOrganizacionSolicitante().equals(nuevaSolicitud.getOrganizacionDonante())) //No pueden ser las mismas organizaciones
            //return true;
        //else if(donaciones.stream()
          //      .anyMatch(d -> d.getDonacion().getOrganizacion().equals(nuevaSolicitud.getOrganizacionSolicitante()))) //Las donaciones no pueden pertenecer a la misma organizacion que las solicita
           // return true;
        else
            return false;
    }

    private void processRequestAndDonations(Solicitud request, List<SolicitudDonacion> donations) {
        /**
         * Lo unico que se deberia modificar es el booleano para saber si se transfirio la solicitud
         * Tambien se debera descontar e incrementar el inventario de las organizaciones solicitantes y donantes
         */

    /*    Map<Donacion, Integer> map = donations.stream()
                .collect(Collectors.toMap(SolicitudDonacion::getDonacion, SolicitudDonacion::getCantidadSolicitada));
        map.forEach((donacion, cantidadSolicitada) -> {

            //Restar al stock de la organizacion donante
            int operation = donacion.getCantidad() - cantidadSolicitada;
            if(operation < 0)
                throw new RuntimeException("La cantidad solicitada es mayor a la que se encuentra en stock");

            donacion.setCantidad(operation);
            donacionRepository.save(donacion);
        });

        //Sumar al stock de la organizacion solicitante
        Organizacion organizacionSolicitante = request.getOrganizacionSolicitante();
        List<Donacion> donationsFromRequest = donacionRepository.findAllByOrganizacion(organizacionSolicitante);
        map.forEach((donacion, cantidadSolicitada) -> {

            // Buscar en la lista de la organización solicitante una donación con la misma categoría
            Donacion donatioRequest = donationsFromRequest.stream()
                    .filter(x -> x.getCategoria().getId() == donacion.getCategoria().getId())
                    .findFirst()
                    .orElse(null);

            if(donatioRequest == null)
                throw new RuntimeException("La organizacion solicitante no dispone de una donacion de la misma categoria");

            donatioRequest.setCantidad(donatioRequest.getCantidad() + cantidadSolicitada);
            donacionRepository.save(donatioRequest);
        });

        request.setProcesada(true); //Se proceso correctamente la solicitud*/
    }

}


