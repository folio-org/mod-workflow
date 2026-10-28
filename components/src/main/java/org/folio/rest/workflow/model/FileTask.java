package org.folio.rest.workflow.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrePersist;
import org.folio.rest.workflow.enums.FileOp;
import org.folio.rest.workflow.model.components.DelegateTask;
import org.folio.rest.workflow.model.has.HasPath;
import org.folio.rest.workflow.model.has.common.HasFileTaskCommon;
import org.hibernate.annotations.ColumnDefault;

@Entity
public class FileTask extends AbstractTask implements DelegateTask, HasFileTaskCommon, HasPath {

  /**
   * The default op value.
   */
  static final FileOp DEFAULT_OP = FileOp.READ;

  @Column(nullable = true)
  private String line;

  @Column(nullable = false)
  @ColumnDefault("'READ'")
  @Enumerated(EnumType.STRING)
  private FileOp op;

  @Column(nullable = false)
  private String path;

  @Column(nullable = true)
  private String target;

  public FileTask() {
    super();

    op = DEFAULT_OP;
    path = "";
  }

  @Override
  @PrePersist
  public void prePersist() {
    super.prePersist();

    if (op  == null) {
      op = DEFAULT_OP;
    }

    if (path == null) {
      path = "";
    }
  }

  @Override
  public String getLine() {
    return line;
  }

  @Override
  public FileOp getOp() {
    return op;
  }

  @Override
  public String getTarget() {
    return target;
  }

  @Override
  public void setLine(String line) {
    this.line = line;
  }

  @Override
  public void setOp(FileOp op) {
    this.op = op;
  }

  @Override
  public void setTarget(String target) {
    this.target = target;
  }

  @Override
  public String getPath() {
    return path;
  }

  @Override
  public void setPath(String path) {
    this.path = path;
  }

}
