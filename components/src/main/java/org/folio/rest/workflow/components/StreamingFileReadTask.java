package org.folio.rest.workflow.components;

import javax.persistence.Entity;

@Entity
public class StreamingFileReadTask extends AbstractFileTask {

  Long delay;

  public StreamingFileReadTask() {
    super();
    setDelegate("streamingFileReadDelegate");
    setDelay(0L);
    setStreaming(true);
  }

  public StreamingFileReadTask(String name) {
    this();
    setName(name);
  }

  public Long getDelay() {
    return delay;
  }

  public void setDelay(Long delay) {
    this.delay = delay;
  }

}
