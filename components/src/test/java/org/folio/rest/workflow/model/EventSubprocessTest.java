package org.folio.rest.workflow.model;

import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EventSubprocessTest {

  private static final String ASYNCAFTER    = "asyncAfter";
  private static final String ASYNCBEFORE   = "asyncBefore";
  private static final String DESCRIPTION   = "description";
  private static final String DESERIALIZEAS = "deserializeAs";
  private static final String ID            = "id";
  private static final String NAME          = "name";
  private static final String NODES         = "nodes";

  @Mock
  private Node node;

  private List<Node> nodes;

  private EventSubprocess eventSubprocess;

  @BeforeEach
  void beforeEach() {
    eventSubprocess = new EventSubprocess();
    nodes = new ArrayList<>();
    nodes.add(node);
  }

  @Test
  void getIdWorksTest() {
    setField(eventSubprocess, ID, VALUE);

    assertEquals(VALUE, eventSubprocess.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(eventSubprocess, ID, null);

    eventSubprocess.setId(VALUE);
    assertEquals(VALUE, getField(eventSubprocess, ID));
  }

  @Test
  void getNameWorksTest() {
    setField(eventSubprocess, NAME, VALUE);

    assertEquals(VALUE, eventSubprocess.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(eventSubprocess, NAME, null);

    eventSubprocess.setName(VALUE);
    assertEquals(VALUE, getField(eventSubprocess, NAME));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(eventSubprocess, DESCRIPTION, VALUE);

    assertEquals(VALUE, eventSubprocess.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(eventSubprocess, DESCRIPTION, null);

    eventSubprocess.setDescription(VALUE);
    assertEquals(VALUE, getField(eventSubprocess, DESCRIPTION));
  }

  @Test
  void getDeserializeAsWorksTest() {
    setField(eventSubprocess, DESERIALIZEAS, VALUE);

    assertEquals(VALUE, eventSubprocess.getDeserializeAs());
  }

  @Test
  void setDeserializeAsWorksTest() {
    setField(eventSubprocess, DESERIALIZEAS, null);

    eventSubprocess.setDeserializeAs(VALUE);
    assertEquals(VALUE, getField(eventSubprocess, DESERIALIZEAS));
  }

  @Test
  void getAsyncBeforeWorksTest() {
    setField(eventSubprocess, ASYNCBEFORE, true);

    assertEquals(true, eventSubprocess.getAsyncBefore());
  }

  @Test
  void setAsyncBeforeWorksTest() {
    setField(eventSubprocess, ASYNCBEFORE, false);

    eventSubprocess.setAsyncBefore(true);
    assertEquals(true, getField(eventSubprocess, ASYNCBEFORE));
  }

  @Test
  void getAsyncAfterWorksTest() {
    setField(eventSubprocess, ASYNCAFTER, true);

    assertEquals(true, eventSubprocess.getAsyncAfter());
  }

  @Test
  void setAsyncAfterWorksTest() {
    setField(eventSubprocess, ASYNCAFTER, false);

    eventSubprocess.setAsyncAfter(true);
    assertEquals(true, getField(eventSubprocess, ASYNCAFTER));
  }

  @Test
  void getNodesWorksTest() {
    setField(eventSubprocess, NODES, nodes);

    assertEquals(nodes, eventSubprocess.getNodes());
  }

  @Test
  void setNodesWorksTest() {
    setField(eventSubprocess, NODES, null);

    eventSubprocess.setNodes(nodes);
    assertEquals(nodes, getField(eventSubprocess, NODES));
  }

}
