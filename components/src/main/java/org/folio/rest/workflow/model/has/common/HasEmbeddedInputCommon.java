package org.folio.rest.workflow.model.has.common;

import java.util.List;
import org.folio.rest.workflow.enums.InputAttribute;
import org.folio.rest.workflow.enums.InputType;

/**
 * This interface provides common methods for {@link org.folio.rest.workflow.model.EmbeddedInput EmbeddedInput}.
 */
public interface HasEmbeddedInputCommon {

  public List<InputAttribute> getAttributes();
  public String getDefaultValue();
  public String getFieldId();
  public String getFieldLabel();
  public InputType getInputType();
  public List<String> getOptions();
  public Boolean getRequired();

  public void setAttributes(List<InputAttribute> attributes);
  public void setDefaultValue(String defaultValue);
  public void setFieldId(String fieldId);
  public void setFieldLabel(String fieldLabel);
  public void setInputType(InputType inputType);
  public void setOptions(List<String> options);
  public void setRequired(Boolean required);

}
