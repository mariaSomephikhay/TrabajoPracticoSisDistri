package com.grupoK.connector.database.serviceImp;

import com.grupoK.connector.database.configuration.annotations.ConsumerServerAnnotation;
import com.grupoK.connector.database.configuration.annotations.GrpcServerAnnotation;
import com.grupoK.connector.database.entities.Donacion;
import com.grupoK.connector.database.entities.Oferta;
import com.grupoK.connector.database.entities.Organizacion;
import com.grupoK.connector.database.repositories.IOfertaRepository;
import com.grupoK.connector.database.service.IOfertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@GrpcServerAnnotation
@ConsumerServerAnnotation
@Service
public class OfertaService implements IOfertaService {

    @Autowired
    private IOfertaRepository ofertaRepository;

    @Autowired
    private  DonacionService donacionService;

    @Override
    public List<Oferta> findAllByOrganizationId(Integer id) {
        return ofertaRepository.findAllByOrganizacionDonante_Id(id);
    }

    @Override
    public Oferta save(Oferta oferta) throws Exception {
        Optional<Oferta> offerDB = ofertaRepository.findById(oferta.getId());
        if(offerDB.isPresent())
            throw new Exception("La oferta con ese ID ya existe");
        if(hasValidationsErrorsByDonations(oferta.getDonaciones()))
            throw new Exception("La oferta no cumple con las validaciones requeridas");

        Oferta newOffer = ofertaRepository.save(oferta);
        oferta.getDonaciones().stream().forEach(x -> {
            try {
                x.setOferta(oferta);
                donacionService.saveOrUpdate(x);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return newOffer;
    }

    @Override
    public Oferta getLast() {
        return ofertaRepository.findTopByOrderByIdDesc();
    }

    private boolean hasValidationsErrorsByDonations(List<Donacion> donaciones){
        if(donaciones == null)
            return true;
        else if(donaciones.isEmpty())
            return true;
        else if(donaciones.stream().anyMatch(d -> d.getOferta() != null))
            return true;
        else
            return false;


    }

}
