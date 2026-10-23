package org.folio.rest.workflow.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import org.folio.rest.workflow.model.has.HasAsync;
import org.hibernate.annotations.ColumnDefault;

@Embeddable
public class Setup implements HasAsync {

  /**
   * The default asyncAfter value.
   */
  static final boolean DEFAULT_ASYNC_AFTER = false;

  /**
   * The default asyncBefore value.
   */
  static final boolean DEFAULT_ASYNC_BEFORE = false;

  @Column(nullable = false)
  @ColumnDefault("" + DEFAULT_ASYNC_AFTER)
  private Boolean asyncAfter;

  @Column(nullable = false)
  @ColumnDefault("" + DEFAULT_ASYNC_BEFORE)
  private Boolean asyncBefore;

  public Setup() {
    super();

    asyncAfter = DEFAULT_ASYNC_AFTER;
    asyncBefore = DEFAULT_ASYNC_BEFORE;
  }

  /**
   * Perform pre-persist setup to ensure good state.
   *
   * Embeddables do not utilize @PrePersist annotation due to it not working well in certain circumstances.
   * Therefore, this must be manually called by the class utilizing this class.
   */
  @PrePersist
  public void prePersist() {

    if (asyncAfter == null) {
      asyncAfter = DEFAULT_ASYNC_AFTER;
    }

    if (asyncBefore == null) {
      asyncBefore = DEFAULT_ASYNC_BEFORE;
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
