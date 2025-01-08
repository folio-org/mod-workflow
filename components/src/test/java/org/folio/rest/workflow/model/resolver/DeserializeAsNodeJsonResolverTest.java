package org.folio.rest.workflow.model.resolver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeserializeAsNodeJsonResolverTest {

  private ObjectMapper mapper;

  @Mock
  private DatabindContext databindContext;

  @InjectMocks
  private DeserializeAsNodeJsonResolver deserializeAsJsonResolver;

  @BeforeEach
  void beforeEach() {
    mapper = new ObjectMapper();
  }

  @Test
  void idFromValueWorksTest() {
    String result = deserializeAsJsonResolver.idFromValue(new String());

    assertEquals(String.class.getSimpleName(), result);
  }

  @Test
  void idFromValueAndTypeWorksTest() {
    String result = deserializeAsJsonResolver.idFromValueAndType(new String(), JsonNode.class);

    assertEquals(String.class.getSimpleName(), result);
  }

  @Test
  void getMechanismWorksTest() {
    Id result = deserializeAsJsonResolver.getMechanism();

    assertEquals(Id.SIMPLE_NAME, result);
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = { "doesNotExist", "12345", "a.b", "1-2+4;", "!\\'", "EmbeddedInput", "Workflow" })
  void typeFromIdWorksExpectNullTest(String id) {
    JavaType result = deserializeAsJsonResolver.typeFromId(databindContext, id);

    assertNull(result);
  }

  @ParameterizedTest
  @MethodSource("provideClassNameFor")
  void typeFromIdWorksWithKnownClassesTest(String className, String simpleName) {
    when(databindContext.getTypeFactory()).thenReturn(mapper.getTypeFactory());

    JavaType result = deserializeAsJsonResolver.typeFromId(databindContext, className);

    assertEquals(simpleName, result.getRawClass().getSimpleName());
  }

  /**
   * Helper function for parameterized test providing tests for the testingClasses.
   *
   * @return
   *   The arguments array stream with the stream columns as:
   *     - String className The name of the class.
   *     - String simpleName The simple name returned by the class.
   */
  private static Stream<Arguments> provideClassNameFor() {
    Builder<Arguments> stream = Stream.builder();

    DeserializeAsNodeJsonResolver.CLASSES.forEach((key, value) -> {
      stream.add(Arguments.of(key, value.getSimpleName()));
    });

    return stream.build();
  }

}
