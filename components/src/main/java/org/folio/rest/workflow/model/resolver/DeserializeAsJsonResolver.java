package org.folio.rest.workflow.model.resolver;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;
import org.folio.rest.workflow.model.Node;

public class DeserializeAsJsonResolver extends TypeIdResolverBase {

  @Override
  public String idFromValue(Object value) {
    System.out.print("\n\n\nDEBUG: value simple name is " + value.getClass().getSimpleName() + "\n\n\n");
    return value.getClass().getSimpleName();
  }

  @Override
  public String idFromValueAndType(Object value, Class<?> suggestedType) {
    return idFromValue(value);
  }

  @Override
  public Id getMechanism() {
    return Id.SIMPLE_NAME;
  }

  @Override
  public JavaType typeFromId(DatabindContext context, String id) {
    System.out.print("\n\n\nDEBUG: id to resolve = " + id + "\n\n\n");
    if (id.length() > 0) {
      try {
        //return context.getTypeFactory().constructType(Node.class.getClassLoader().loadClass(id));
        Class<?> type = Class.forName("org.folio.rest.workflow.model." + id);
        if (type != null) {
          return context.getTypeFactory().constructType(type);
        }
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
  }

    return null;
  }

}
