package org.folio.rest.workflow.provisioner;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.SchemaManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.folio.spring.tenant.interfaces.TenantDatabaseProvisioner;
import org.folio.spring.tenant.storage.ThreadLocalStorage;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Workflow-specific implementation of TenantDatabasProvisioner.
 */
public class WorkflowTenantDatabaseProvisioner implements TenantDatabaseProvisioner {

  private static final Logger LOG = LoggerFactory.getLogger(WorkflowTenantDatabaseProvisioner.class);

  private final EntityManagerFactory entityManagerFactory;

  /**
   * Initializer.
   *
   * @param entityManagerFactory The entity m anager factory.
   */
  public WorkflowTenantDatabaseProvisioner(EntityManagerFactory entityManagerFactory) {

    this.entityManagerFactory = entityManagerFactory;
  }

  @Override
  public void provisionSchema(String schema, String tenant, Connection connection) throws SQLException {

    LOG.debug("Provisioning schema '{}' for tenant '{}'.", schema, tenant);

    try (final Statement statement = connection.createStatement()) {
      statement.execute(String.format("CREATE SCHEMA IF NOT EXISTS %s", schema));
    }

    /* Entity manager example:
    LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
    emf.setDataSource(dataSource);
    emf.setPackagesToScan("org.folio.rest.workflow");
    emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

    Map<String, Object> props = new HashMap<>();
    props.put("hibernate.hbm2ddl.auto", "update");
    props.put("hibernate.default_schema", schema);
    emf.setJpaPropertyMap(props);

    emf.afterPropertiesSet();
    emf.destroy();
    */

    final String originalTlsTenant = ThreadLocalStorage.getTenant();

    // Set the tenant context so the MultiTenantConnectionProvider routes correctly
    ThreadLocalStorage.setTenant(tenant);
    try {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        SchemaManager schemaManager = sessionFactory.getSchemaManager();
        schemaManager.create(false);
        //schemaManager.update(false);
    } finally {
      ThreadLocalStorage.unload();
    }

    if (originalTlsTenant != null) {
      ThreadLocalStorage.setTenant(originalTlsTenant);
    }
  }

}
