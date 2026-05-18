package org.folio.rest.workflow.config;

import org.folio.spring.liquibase.FolioSpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FolioSpringLiquibaseConfig {

  @Bean
  FolioSpringLiquibase folioSpringLiquibase() {
    return new FolioSpringLiquibase();
  }

}
