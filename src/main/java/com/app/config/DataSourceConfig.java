package com.app.config;

import org.springframework.context.annotation.Configuration;

/**
 * La DataSource est auto-configurée par Spring Boot à partir de application.properties.
 * On l'injecte directement via @Autowired DataSource dans les repositories.
 *
 * Pas de JPA, pas d'EntityManager — uniquement DataSource → Connection → PreparedStatement.
 */
@Configuration
public class DataSourceConfig {
    // Aucune déclaration manuelle nécessaire :
    // spring-boot-starter-jdbc + application.properties suffisent.
}
