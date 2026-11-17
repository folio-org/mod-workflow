package org.folio.rest.workflow.config;

import javax.sql.DataSource;
import org.folio.rest.workflow.events.WorkflowEventHandler;
import org.folio.spring.tenant.hibernate.HibernateRoutingDataSource;
import org.folio.spring.tenant.service.SchemaService;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RespositoryConfig {

  /**
   * Initializer.
   */
  RespositoryConfig() {
    // Do nothing.
  }

  @Bean
  @Primary
  DataSource dataSource(DataSourceProperties props, SchemaService schemaService) {

    return new HibernateRoutingDataSource(props.initializeDataSourceBuilder().build(), schemaService);
  }

  @Bean
  WorkflowEventHandler workflowEventHandler() {
    return new WorkflowEventHandler();
  }
}
