package org.folio.rest.workflow.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = FolioSpringLiquibaseConfig.class)
class FolioSpringLiquibaseConfigTest {

  @Autowired
  private FolioSpringLiquibaseConfig folioSpringLiquibaseConfig;

  @Test
  void testFolioSpringLiquibaseConfigBeanExists() {

    assertNotNull(folioSpringLiquibaseConfig);
  }

}
