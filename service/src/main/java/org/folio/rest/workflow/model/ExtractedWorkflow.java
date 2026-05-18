package org.folio.rest.workflow.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import lombok.Getter;
import tools.jackson.databind.JsonNode;

/**
 * A collection of Workflow Nodes for use during import.
 */
@Getter
public class ExtractedWorkflow {

  /**
   * A string representing the "code" key.
   */
  public static final String CODE = "code";

  /**
   * A string representing the "deserializeAs" key.
   */
  public static final String DESERIALIZE_AS = "deserializeAs";

  /**
   * The name of the FWZ JSON file within the archive.
   */
  public static final String FWZ_JSON = "fwz.json";

  /**
   * A string representing the "id" key.
   */
  public static final String ID = "id";

  /**
   * The JavaScript type.
   */
  public static final String JAVASCRIPT = "javascript";

  /**
   * The JavaScript extension name.
   */
  public static final String JAVASCRIPT_EXT_NAME = "js";

  /**
   * The JSON extension.
   */
  public static final String JSON_EXT = ".json";

  /**
   * A string representing the "nodes" key.
   */
  public static final String NODES = "nodes";

  /**
   * The Python type.
   */
  public static final String PYTHON = "python";

  /**
   * The Python extension name.
   */
  public static final String PYTHON_EXT_NAME = "py";

  /**
   * The Ruby type.
   */
  public static final String RUBY = "ruby";

  /**
   * The Ruby extension name.
   */
  public static final String RUBY_EXT_NAME = "rb";

  /**
   * A string representing the "scriptFormat" key.
   */
  public static final String SCRIPT_FORMAT = "scriptFormat";

  /**
   * A string representing the "ScriptTask" Node type.
   */
  public static final String SCRIPT_TASK = "ScriptTask";

  /**
   * The name of the setup JSON file within the archive.
   */
  public static final String SETUP_JSON = "setup.json";

  /**
   * A string representing the "triggers" directory.
   */
  public static final String TRIGGERS = "triggers";

  /**
   * A string representing the "version" key.
   */
  public static final String VERSION = "version";

  /**
   * The version regex pattern supporting 1.0.* versions.
   */
  public static final Pattern VERSION_PATTERN_1_0 = Pattern.compile("^1\\.0($|\\.\\d)");

  /**
   * The name of the workflow JSON file within the archive.
   */
  public static final String WORKFLOW_JSON = "workflow.json";

  private List<String> expanded;

  private Map<String, JsonNode> required;

  private Map<String, JsonNode> nodes;

  private Map<String, JsonNode> triggers;

  private Map<String, String> scripts;

  public ExtractedWorkflow() {
    expanded = new ArrayList<>();
    required = new HashMap<>();
    nodes = new HashMap<>();
    triggers = new HashMap<>();
    scripts = new HashMap<>();
  }

}
