package org.folio.rest.workflow.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.folio.rest.workflow.model.components.DelegateTask;

@Entity
public class FileTask extends Node implements DelegateTask {

  @ElementCollection
  private Set<EmbeddedVariable> inputVariables;

  @Embedded
  private EmbeddedVariable outputVariable;

  @Column(nullable = false)
  private boolean asyncBefore;

  @Column(nullable = false)
  private boolean asyncAfter;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private FileOp op;

  @Column(nullable = false)
  private String path;

  @Column(nullable = true)
  private String target;

  @Column(nullable = true)
  private String line;

  public FileTask() {
    super();
    inputVariables = new HashSet<EmbeddedVariable>();
    asyncBefore = false;
    asyncAfter = false;
  }

  public Set<EmbeddedVariable> getInputVariables() {
    return inputVariables;
  }

  public void setInputVariables(Set<EmbeddedVariable> inputVariables) {
    this.inputVariables = inputVariables;
  }

  public EmbeddedVariable getOutputVariable() {
    return outputVariable;
  }

  public void setOutputVariable(EmbeddedVariable outputVariable) {
    this.outputVariable = outputVariable;
  }

  public boolean isAsyncBefore() {
    return asyncBefore;
  }

  public void setAsyncBefore(boolean asyncBefore) {
    this.asyncBefore = asyncBefore;
  }

  public boolean isAsyncAfter() {
    return asyncAfter;
  }

  public void setAsyncAfter(boolean asyncAfter) {
    this.asyncAfter = asyncAfter;
  }

  public FileOp getOp() {
    return op;
  }

  public void setOp(FileOp op) {
    this.op = op;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  public String getLine() {
    return line;
  }

  public void setLine(String line) {
    this.line = line;
  }

}
