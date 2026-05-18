package org.folio.rest.workflow.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.folio.rest.workflow.model.components.DelegateTask;
import org.folio.rest.workflow.model.has.common.HasEmailTaskCommon;
import org.jspecify.annotations.NonNull;

@Entity
public class EmailTask extends AbstractTask implements DelegateTask, HasEmailTaskCommon {

  @Getter
  @Setter
  @Column(nullable = true)
  private String attachmentPath;

  @Getter
  @Setter
  @Column(nullable = true)
  private String includeAttachment;

  @Getter
  @Setter
  @Column(nullable = true)
  private String mailBcc;

  @Getter
  @Setter
  @Column(nullable = true)
  private String mailCc;

  @Getter
  @Setter
  @NonNull
  @Size(min = 3, max = 256)
  @Column(nullable = false)
  private String mailFrom;

  @Getter
  @Setter
  @NonNull
  @Size(min = 2)
  @Column(columnDefinition = "TEXT", nullable = false)
  private String mailText;

  @Getter
  @Setter
  @NonNull
  @Size(min = 3, max = 256)
  @Column(nullable = false)
  private String mailTo;

  @Getter
  @Setter
  @Column(columnDefinition = "TEXT", nullable = true)
  private String mailMarkup;

  @Getter
  @Setter
  @NonNull
  @Size(min = 2, max = 256)
  @Column(nullable = false)
  private String mailSubject;

  public EmailTask() {
    super();

    mailFrom = "";
    mailText = "";
    mailTo = "";
    mailSubject = "";
  }

  @Override
  @PrePersist
  public void prePersist() {
    super.prePersist();

    if (mailFrom == null) {
      mailFrom = "";
    }

    if (mailText == null) {
      mailText = "";
    }

    if (mailTo == null) {
      mailTo = "";
    }

    if (mailSubject == null) {
      mailSubject = "";
    }
  }

}
