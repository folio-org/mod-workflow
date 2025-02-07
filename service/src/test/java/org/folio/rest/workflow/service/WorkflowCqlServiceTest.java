package org.folio.rest.workflow.service;

import static org.folio.spring.test.mock.MockMvcConstant.NULL_STR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;
import org.folio.rest.workflow.model.Workflow;
import org.folio.rest.workflow.model.repo.WorkflowRepo;
import org.folio.spring.data.OffsetRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class WorkflowCqlServiceTest {

  private static final String QUERY_ALL = "cql.allRecords=1";

  private static final String NO_RECORDS = "{\"workflows\":[],\"totalRecords\":0}";

  @InjectMocks
  private WorkflowCqlService workflowCqlService;

  @MockitoBean
  private WorkflowRepo repo;

  // Work-around to get ObjectMapper to be loaded in AbstractCqlService.
  @MockitoSpyBean
  protected ObjectMapper mapper;

  @Mock
  private Page<Workflow> page;

  @Test
  void getTypeNameWorksTest() throws IOException {
    final String name = workflowCqlService.getTypeName();

    assertEquals(Workflow.class.getSimpleName().toLowerCase() + "s", name);
  }

  @ParameterizedTest
  @MethodSource("provideFindByCql")
  void findByCqlWorksTest(String query, Long offset, Integer limit, String expect) throws IOException {
    when(repo.findAll(any(OffsetRequest.class))).thenReturn(page);
    when(repo.findByCql(anyString(), any(OffsetRequest.class))).thenReturn(page);
    when(page.toList()).thenReturn(new ArrayList<Workflow>());

    ObjectNode jsonNode = workflowCqlService.findByCql(query, offset, limit);
    assertEquals(expect, jsonNode.toString());
  }

  /**
   * Helper function for parameterized test providing tests for the write internal tests.
   *
   * @return
   *   The arguments array stream with the stream columns as:
   *     - String query (the CQL query to use)).
   *     - String offset (the offset to use)).
   *     - String limit (the limit to use)).
   *     - String tenant (the tenant to use)).
   *     - String expect (the returned JsonNode as a string to expect)).
   */
  private static Stream<Arguments> provideFindByCql() {
    return Stream.of(
      Arguments.of(NULL_STR,  0L,   20,  NO_RECORDS),
      Arguments.of(NULL_STR,  0L,   200, NO_RECORDS),
      Arguments.of(NULL_STR,  200L, 20,  NO_RECORDS),
      Arguments.of(NULL_STR,  200L, 5,   NO_RECORDS),
      Arguments.of(QUERY_ALL, 0L,   20,  NO_RECORDS),
      Arguments.of(QUERY_ALL, 0L,   200, NO_RECORDS),
      Arguments.of(QUERY_ALL, 200L, 20,  NO_RECORDS),
      Arguments.of(QUERY_ALL, 200L, 5,   NO_RECORDS)
    );
  }

}
