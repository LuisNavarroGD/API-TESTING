package AssertJ;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BuildRequest extends RestAssured.BuildRequest {

    private static BuildRequest buildRequest;
    private static Response response;

    @BeforeAll
    public static void setUp() {
        buildRequest = new BuildRequest();
        buildRequest.setup();
        response = buildRequest.buildRequest();
    }

    @Test
    public void testResponseCode() {
        assertThat(response.getStatusCode()).as("El código de respuesta debe de ser 200").isEqualTo(200);
        assertThat(response.getContentType()).as("El contenido debe de ser de tipo JSON").contains("application/json");
        assertThat(response.getBody().asString()).as("El cuerpo de la respuesta no debe de estar vacío").isNotEmpty();
    }

    @Test
    public void testResponseBody() {
        assertThat(response.jsonPath().getString("url")).isNotNull();
       assertThat(response.jsonPath().getString("id")).as("El id no debe de ser nulo").isNotNull();
       assertThat(response.jsonPath().getString("avatarUrl")).isNotNull();
       assertThat(response.jsonPath().getString("fullName")).isNotNull();
       assertThat(response.jsonPath().getString("username")).isNotNull();
       assertThat(response.jsonPath().getString("email")).isNotNull();
        assertThat(response.jsonPath().getString("memberType")).isNotNull();
    }

    @Test
    public void testResponseTime() {
        assertThat(response.getTime()).as("El tiempo de respuesta debe de ser menor a 2 segundos").isLessThan(2000L);
    }

}