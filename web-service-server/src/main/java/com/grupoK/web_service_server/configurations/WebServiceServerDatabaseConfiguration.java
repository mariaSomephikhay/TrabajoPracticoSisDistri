package com.grupoK.web_service_server.configurations;

import com.grupoK.connector.database.configuration.annotations.GrpcServerAnnotation;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.grupoK.connector.database.entities")
@ComponentScan(basePackages = "com.grupoK.connector.database.serviceImp")
@EnableJpaRepositories(basePackages = "com.grupoK.connector.database.repositories")
public class WebServiceServerDatabaseConfiguration {
}
