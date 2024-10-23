import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BuildRequest extends Utility.BuildRequest {

    private Utility.BuildRequest buildRequest;
    private Response response;

    @BeforeClass
    public void setup() {
        buildRequest = new Utility.BuildRequest();
        buildRequest.setup();
        response = buildRequest.buildRequest();
    }

    @Test
    public void testResponseCode() {
        Assert.assertEquals(response.getStatusCode(), 200, "Response code should be 200");
        Assert.assertEquals(response.getContentType(),
                "application/json; charset=utf-8", "Content type should be application/json");


    }

    @Test
    public void testResponseBody() {
        Assert.assertNotNull(response.jsonPath().getString("id"), "El id no debe ser nulo");
        Assert.assertNotNull(response.jsonPath().getString("avatarUrl"), "El avatar no debe ser nulo");
        Assert.assertNotNull(response.jsonPath().getString("url"), "La URL no debe ser nula");
        Assert.assertNotNull(response.jsonPath().getString("email"), "El correo no debe ser nulo");
        Assert.assertNotNull(response.jsonPath().getString("fullName"), "El nombre completo no debe ser nulo");
        Assert.assertNotNull(response.jsonPath().getString("username"), "El nombre de usuario no debe ser nulo");
        Assert.assertNotNull(response.jsonPath().getString("memberType"), "El tipo de usuario no debe ser nulo");
    }

    @Test
    public void testResponseTime() {
        Assert.assertTrue(response.getTime() < 2000, "El tiempo de respuesta debe ser menor a 2 segundos");
    }

}
