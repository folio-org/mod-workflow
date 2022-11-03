package org.folio.rest.workflow.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.folio.rest.workflow.model.components.DelegateTask;
import org.springframework.lang.NonNull;

@Entity
public class EmailTask extends Node implements DelegateTask {

  @ElementCollection
  private Set<EmbeddedVariable> inputVariables;

  @Embedded
  private EmbeddedVariable outputVariable;

  @Column(nullable = false)
  private boolean asyncBefore;

  @Column(nullable = false)
  private boolean asyncAfter;

  @NonNull
  @Size(min = 3, max = 256)
  @Column(nullable = false)
  private String mailTo;

  @Column(nullable = true)
  private String mailCc;

  @Column(nullable = true)
  private String mailBcc;

  @NonNull
  @Size(min = 3, max = 256)
  @Column(nullable = false)
  private String mailFrom;

  @NonNull
  @Size(min = 2, max = 256)
  @Column(nullable = false)
  private String mailSubject;

  @NonNull
  @Size(min = 2)
  @Column(columnDefinition = "TEXT", nullable = false)
  private String mailText;

  @Column(columnDefinition = "TEXT", nullable = true)
  private String mailMarkup;

  @Column(nullable = true)
  private String attachmentPath;

  @Column(nullable = false)
  private Boolean includeAttachment;

  public EmailTask() {
    super();
    inputVariables = new HashSet<EmbeddedVariable>();
    asyncBefore = false;
    asyncAfter = false;
    mailTo = "";
    mailFrom = "";
    mailSubject = "";
    mailText = "";
    includeAttachment = true;
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

  public String getMailTo() {
    return mailTo;
  }

  public void setMailTo(String mailTo) {
    this.mailTo = mailTo;
  }

  public String getMailCc() {
    return mailCc;
  }

  public void setMailCc(String mailCc) {
    this.mailCc = mailCc;
  }

  public String getMailBcc() {
    return mailBcc;
  }

  public void setMailBcc(String mailBcc) {
    this.mailBcc = mailBcc;
  }

  public String getMailFrom() {
    return mailFrom;
  }

  public void setMailFrom(String mailFrom) {
    this.mailFrom = mailFrom;
  }

  public String getMailSubject() {
    return mailSubject;
  }

  public void setMailSubject(String mailSubject) {
    this.mailSubject = mailSubject;
  }

  public String getMailText() {
    return mailText;
  }

  public void setMailText(String mailText) {
    this.mailText = mailText;
  }

  public String getMailMarkup() {
    return mailMarkup;
  }

  public void setMailMarkup(String mailMarkup) {
    this.mailMarkup = mailMarkup;
  }

  public String getAttachmentPath() {
    return attachmentPath;
  }

  public void setAttachmentPath(String attachmentPath) {
    this.attachmentPath = attachmentPath;
  }

  public boolean isIncludeAttachment() {
    return includeAttachment;
  }

  public void setIncludeAttachment(boolean includeAttachment) {
    this.includeAttachment = includeAttachment;
  }

}
