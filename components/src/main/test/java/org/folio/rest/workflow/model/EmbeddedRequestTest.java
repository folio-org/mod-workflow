import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmbeddedRequestTest {

    private EmbeddedRequest embeddedRequest;
    private Validator validator;

    @BeforeEach
    void setUp() {
        embeddedRequest = new EmbeddedRequest();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testUrlGetterSetter() {
        String url = "https://example.com/api";
        embeddedRequest.setUrl(url);
        assertEquals(url, embeddedRequest.getUrl());
    }

    @Test
    void testMethodGetterSetter() {
        HttpMethod method = HttpMethod.GET;
        embeddedRequest.setMethod(method);
        assertEquals(method, embeddedRequest.getMethod());
    }

    @Test
    void testContentTypeGetterSetter() {
        String contentType = "application/json";
        embeddedRequest.setContentType(contentType);
        assertEquals(contentType, embeddedRequest.getContentType());
    }

    @Test
    void testAcceptGetterSetter() {
        String accept = "application/json";
        embeddedRequest.setAccept(accept);
        assertEquals(accept, embeddedRequest.getAccept());
    }

    @Test
    void testBodyTemplateGetterSetter() {
        String bodyTemplate = "{\"key\": \"value\"}";
        embeddedRequest.setBodyTemplate(bodyTemplate);
        assertEquals(bodyTemplate, embeddedRequest.getBodyTemplate());
    }

    @Test
    void testIterableGetterSetter() {
        embeddedRequest.setIterable(true);
        assertEquals(true, embeddedRequest.isIterable());
    }

    @Test
    void testIterableKeyGetterSetter() {
        String iterableKey = "key";
        embeddedRequest.setIterableKey(iterableKey);
        assertEquals(iterableKey, embeddedRequest.getIterableKey());
    }

    @Test
    void testResponseKeyGetterSetter() {
        String responseKey = "response";
        embeddedRequest.setResponseKey(responseKey);
        assertEquals(responseKey, embeddedRequest.getResponseKey());
    }

    @Test
    void testNotNullConstraints() {
        assertThrows(Exception.class, () -> {
            embeddedRequest.setUrl(null);
            validator.validateProperty(embeddedRequest, "url");
        });

        assertThrows(Exception.class, () -> {
            embeddedRequest.setMethod(null);
            validator.validateProperty(embeddedRequest, "method");
        });

        assertThrows(Exception.class, () -> {
            embeddedRequest.setContentType(null);
            validator.validateProperty(embeddedRequest, "contentType");
        });

        assertThrows(Exception.class, () -> {
            embeddedRequest.setAccept(null);
            validator.validateProperty(embeddedRequest, "accept");
        });
    }
}
