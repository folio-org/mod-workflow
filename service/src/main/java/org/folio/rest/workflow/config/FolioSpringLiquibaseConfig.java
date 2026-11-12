package org.folio.rest.workflow.config;

import org.folio.spring.liquibase.FolioSpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Provide custom overrides to FOLIO Spring Support Liquibase related configuration.
 */
@Configuration
public class FolioSpringLiquibaseConfig {

  /**
   * Enable liquibase to work-around problems where FolioSpringLiquibase requires it but it causes problems and workflow doesn't use it.
   *
   * Specifically disable running using configuration.
   * Cannot return NULL to designate don't use because folio-spring-support doesn't verify the beans existence.
   *
   * @return
   */
  @Bean
  FolioSpringLiquibase folioSpringLiquibase() {
    final FolioSpringLiquibase folioSpringLiquibase = new FolioSpringLiquibase();

    folioSpringLiquibase.setShouldRun(false);

    return folioSpringLiquibase;
  }

}
