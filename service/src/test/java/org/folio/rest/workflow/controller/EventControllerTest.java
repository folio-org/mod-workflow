package org.folio.rest.workflow.controller;

import static java.io.File.separator;
import static java.lang.String.join;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystemException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.apache.commons.io.FileUtils;
import org.folio.rest.workflow.dto.TriggerDto;
import org.folio.rest.workflow.enums.HttpMethod;
import org.folio.rest.workflow.kafka.EventProducer;
import org.folio.rest.workflow.model.repo.TriggerRepo;
import org.folio.spring.tenant.properties.TenantProperties;
import org.folio.spring.tenant.resolver.TenantHeaderResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(EventController.class)
class EventControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private EventController eventController;

  @MockBean
  private EventProducer eventProducer;

  @MockBean
  private TriggerRepo triggerRepo;

  @Mock
  private TriggerDto trigger;

  @MockBean
  private TenantProperties tenantProperties;

  private List<TriggerDto> triggers;

  @BeforeEach
  void beforeEach() {
    mockMvc = MockMvcBuilders.standaloneSetup(eventController)
        .setCustomArgumentResolvers(new TenantHeaderResolver("X-Okapi-Tenant"))
        .build();

    triggers = new ArrayList<>();
    triggers.add(trigger);
  }

  @ParameterizedTest
  @MethodSource
  void upload(String tenant, String dir, String file, String expectedPath) throws Exception {
    when(trigger.getId()).thenReturn("id");
    when(trigger.getMethod()).thenReturn(HttpMethod.POST);
    when(trigger.getPathPattern()).thenReturn("/events");

    when(triggerRepo.findViewAllBy(TriggerDto.class)).thenReturn(triggers);

    mockMvc.perform(upload(tenant, dir, file))
        .andExpectAll(status().isOk(), jsonPath("inputFilePath").value(expectedPath));

    assertThat(readFile(expectedPath)).isEqualTo("This is the file content");
  }

  static Stream<Arguments> upload() {
    return Stream.of(
        arguments("diku",
          join(separator, "d1", "d2", "d3"), "filename.txt",
          join(separator, "events", "diku", "d1", "d2", "d3", "filename.txt")),
        arguments("diku",
          "",
          "baz.txt",
          join(separator, "events", "diku", "baz.txt")),
        arguments("foo",
          join(separator, "a", "..", "b"),
          join(separator,"c", "d", "..", "e", "f.txt"),
          join(separator, "events", "foo", "b", "c", "e", "f.txt")));
  }

  @ParameterizedTest
  @MethodSource
  void uploadRejected(String tenant, String dir, String file) {
    assertThrows(FileSystemException.class, () ->
        mockMvc.perform(upload(tenant, dir, file)));
  }

  static Stream<Arguments> uploadRejected() {
    return Stream.of(
        arguments(join(separator, "diku", "..", "x"), "a", "a.txt"),
        arguments(join(separator, "diku", "..", "..", "x"), "a", "a.txt"),
        arguments("diku", join(separator, "a", "..", "..", "x"), "a.txt"),
        arguments("diku", join(separator, "..", "x"), "a.txt"),
        arguments("diku", "a", join(separator,"..", "..", "x.txt")),
        arguments("diku", "a", join(separator,"..", "..", "..", "x.txt")),
        arguments("diku", "a", join(separator,"..", "..", "..", "..", "x.txt")));
  }

  MockHttpServletRequestBuilder upload(String tenant, String dir, String file) throws Exception {
    var sampleFile = new MockMultipartFile(
        "file",
        file,
        MediaType.TEXT_PLAIN_VALUE,
        "This is the file content".getBytes());

    return MockMvcRequestBuilders.multipart("/events").file(sampleFile)
        .header("X-Okapi-Tenant", tenant).param("path", dir);
  }

  private static String readFile(String path) throws IOException {
    return FileUtils.readFileToString(new File(path), StandardCharsets.UTF_8);
  }
}
