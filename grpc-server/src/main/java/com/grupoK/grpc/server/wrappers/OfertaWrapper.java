package com.grupoK.grpc.server.wrappers;

import com.grupoK.connector.database.entities.Oferta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OfertaWrapper {
    @Autowired
    private OrganizacionWrapper organizacionWrapper;

    @Autowired
    private DonacionWrapper donacionWrapper;

    public com.grupoK.grpc.proto.Oferta toGrpOferta(Oferta oferta) {

        return com.grupoK.grpc.proto.Oferta.newBuilder()
                .setId(oferta.getId())
                .setOrganizacionDonante(organizacionWrapper.toGrpcRol(oferta.getOrganizacionDonante()))
                .addAllDonaciones(oferta.getDonaciones().stream()
                        .map(donacionWrapper::toGrpcDonacion)
                        .toList()
                )
                .build();
    }

}
