package com.grupoK.web_service_server.graphql.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.grupoK.connector.database.entities.Organizacion;
import com.grupoK.connector.database.serviceImp.OrganizacionService;
import com.grupoK.web_service_server.graphql.model.GraphQLResponse;

@Controller
public class OrganizacionGraphqlController {
	
	@Autowired
	OrganizacionService organizacionService;
	
	@QueryMapping
	public GraphQLResponse<Organizacion> getOrganizacion(@Argument int id) {
		try {
			System.out.println(organizacionService.findById(id));
			return new GraphQLResponse<>("success", organizacionService.findById(id));
		}catch (Exception e){
			return new GraphQLResponse<>("fail",e.getMessage(), null);
		}
	}

}
