package org.folio.rest.workflow.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SetupTest {

  private static final String ASYNCAFTER  = "asyncAfter";
  private static final String ASYNCBEFORE = "asyncBefore";

  private Setup setup;

  @BeforeEach
  void beforeEach() {
    setup = new Setup();
  }

  @Test
  void getAsyncBeforeWorksTest() {
    setField(setup, ASYNCBEFORE, true);

    assertEquals(true, setup.getAsyncBefore());
  }

  @Test
  void setAsyncBeforeWorksTest() {
    setField(setup, ASYNCBEFORE, false);

    setup.setAsyncBefore(true);
    assertEquals(true, getField(setup, ASYNCBEFORE));
  }

  @Test
  void getAsyncAfterWorksTest() {
    setField(setup, ASYNCAFTER, true);

    assertEquals(true, setup.getAsyncAfter());
  }

  @Test
  void setAsyncAfterWorksTest() {
    setField(setup, ASYNCAFTER, false);

    setup.setAsyncAfter(true);
    assertEquals(true, getField(setup, ASYNCAFTER));
  }

  @ParameterizedTest
  @MethodSource("providePrePersistFor")
  void prePersistWorksTest(Map<String, Object> initial, Map<String, Object> expected) {
    initial.forEach((String attribute, Object value) -> {
      setField(setup, attribute, value);
    });

    setup.prePersist();

    expected.forEach((String attribute, Object value) -> {
      assertEquals(value, getField(setup, attribute));
    });
  }

  /**
   * Helper function for parameterized tests for the prePersist function.
   *
   * @return
   *   The arguments array stream with the stream columns as:
   *     - Arguments initial The initial values.
   *     - Arguments expect The expected values.
   */
  private static Stream<Arguments> providePrePersistFor() {

    return List.of(
      Arguments.of(
        helperFieldMap(null,  null),
        helperFieldMap(false, false)
      ),
      Arguments.of(
        helperFieldMap(true,  null),
        helperFieldMap(true,  false)
      ),
      Arguments.of(
        helperFieldMap(null,  true),
        helperFieldMap(false, true)
      )
    ).stream();
  }

  /**
   * Helper for reducing in line code repetition for assignments.
   *
   * @param asyncAfter The asyncAfter value.
   * @param asyncBefore The asyncBefore value.
   *
   * @return The built arguments map.
   */
  private static Map<String, Object> helperFieldMap(Boolean asyncAfter, Boolean asyncBefore) {
    final Map<String, Object> map = new HashMap<>();

    map.put(ASYNCAFTER, asyncAfter);
    map.put(ASYNCBEFORE, asyncBefore);

    return map;
  }

}
