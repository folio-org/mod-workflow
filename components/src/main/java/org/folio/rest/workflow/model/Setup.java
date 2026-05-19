package org.folio.rest.workflow.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import org.folio.rest.workflow.model.has.HasAsync;
import org.hibernate.annotations.ColumnDefault;

@Embeddable
public class Setup implements HasAsync {

  @Column(nullable = false)
  @ColumnDefault("false")
  private Boolean asyncAfter;

  @Column(nullable = false)
  @ColumnDefault("false")
  private Boolean asyncBefore;

  public Setup() {
    super();

    asyncAfter = false;
    asyncBefore = false;
  }

  @PrePersist
  public void prePersist() {
    if (asyncAfter == null) {
      asyncAfter = false;
    }

    if (asyncBefore == null) {
      asyncBefore = false;
    }
  }

  @Override
  public Boolean getAsyncAfter() {
    return asyncAfter;
  }

  @Override
  public Boolean getAsyncBefore() {
    return asyncBefore;
  }

  @Override
  public void setAsyncAfter(Boolean asyncAfter) {
    this.asyncAfter = asyncAfter;
  }

  @Override
  public void setAsyncBefore(Boolean asyncBefore) {
    this.asyncBefore = asyncBefore;
  }

}
