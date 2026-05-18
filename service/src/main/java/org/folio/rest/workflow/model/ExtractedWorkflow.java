package org.folio.rest.workflow.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import tools.jackson.databind.JsonNode;

/**
 * A collection of Workflow Nodes for use during import.
 */
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

  /**
   * @return the expanded
   */
  public List<String> getExpanded() {
    return expanded;
  }

  /**
   * @return the required
   */
  public Map<String, JsonNode> getRequired() {
    return required;
  }

  /**
   * @return the nodes
   */
  public Map<String, JsonNode> getNodes() {
    return nodes;
  }

  /**
   * @return the triggers
   */
  public Map<String, JsonNode> getTriggers() {
    return triggers;
  }

  /**
   * @return the scripts
   */
  public Map<String, String> getScripts() {
    return scripts;
  }

  /**
   * @param expanded the expanded to set
   */
  public void setExpanded(List<String> expanded) {
    this.expanded = expanded;
  }

  /**
   * @param required the required to set
   */
  public void setRequired(Map<String, JsonNode> required) {
    this.required = required;
  }

  /**
   * @param nodes the nodes to set
   */
  public void setNodes(Map<String, JsonNode> nodes) {
    this.nodes = nodes;
  }

  /**
   * @param triggers the triggers to set
   */
  public void setTriggers(Map<String, JsonNode> triggers) {
    this.triggers = triggers;
  }

  /**
   * @param scripts the scripts to set
   */
  public void setScripts(Map<String, String> scripts) {
    this.scripts = scripts;
  }

}
