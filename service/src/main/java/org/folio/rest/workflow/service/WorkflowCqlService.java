package org.folio.rest.workflow.service;

import org.apache.commons.lang3.StringUtils;
import org.folio.rest.workflow.model.Workflow;
import org.folio.rest.workflow.model.repo.WorkflowRepo;
import org.folio.spring.data.OffsetRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

@Service
public class WorkflowCqlService extends AbstractCqlService<Workflow> {

  private WorkflowRepo repo;

  public WorkflowCqlService(ObjectMapper mapper, WorkflowRepo repo) {
    this.mapper = mapper;
    this.repo = repo;
  }

  @Override
  public ObjectNode findByCql(String query, Long offset, Integer limit) {
    Page<Workflow> page = null;
    long total = 0;

    if (StringUtils.isBlank(query)) {
      page = repo.findAll(new OffsetRequest(offset, limit));
      total = repo.countAll();
    } else {
      page = repo.findByCql(query, new OffsetRequest(offset, limit));
      total = repo.countByCql(query);
    }

    return toJson(page.toList(), total);
  }

  @Override
  protected String getTypeName() {
    return Workflow.class.getSimpleName().toLowerCase() + "s";
  }

}
