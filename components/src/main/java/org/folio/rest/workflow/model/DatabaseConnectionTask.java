package org.folio.rest.workflow.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import org.folio.rest.workflow.model.components.DelegateTask;
import org.folio.rest.workflow.model.has.HasDesignation;
import org.folio.rest.workflow.model.has.HasPassword;
import org.folio.rest.workflow.model.has.HasUrl;
import org.folio.rest.workflow.model.has.HasUsername;

@Entity
public class DatabaseConnectionTask extends AbstractTask implements DelegateTask, HasDesignation, HasPassword, HasUrl, HasUsername {

  @Column(nullable = false)
  private String designation;

  @Column(nullable = true)
  private String password;

  @Column(nullable = false)
  private String url;

  @Column(nullable = true)
  private String username;

  public DatabaseConnectionTask() {
    super();

    designation = "";
    url = "";
  }

  @Override
  @PrePersist
  public void prePersist() {
    super.prePersist();

    if (designation == null) {
      designation = "";
    }

    if (url == null) {
      url = "";
    }
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public String getUrl() {
    return url;
  }

  @Override
  public void setUrl(String url) {
    this.url = url;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String getDesignation() {
    return designation;
  }

  @Override
  public void setDesignation(String designation) {
    this.designation = designation;
  }

}
