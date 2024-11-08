package TestNG;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BuildRequest extends RestAssured.BuildRequest {

    private RestAssured.BuildRequest buildRequest;
    private Response response;

    @BeforeClass
    public void setup() {
        buildRequest = new RestAssured.BuildRequest();
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
        Assert.assertEquals(response.jsonPath().getString("id"), "67118192ff4092dd2f7cf1ab", "El id no coincide");
        Assert.assertEquals(response.jsonPath().getString("avatarUrl"),
                "https://trello-members.s3.amazonaws.com/67118192ff4092dd2f7cf1ab/084ea27631e695e2db6de1dd0dd52b1a", "El avatar no coincide");
        Assert.assertEquals(response.jsonPath().getString("url"), "https://trello.com/xluismariox", "La url no coincide");
        Assert.assertEquals(response.jsonPath().getString("email"), "xluismariox@gmail.com", "El email no coincide");
        Assert.assertEquals(response.jsonPath().getString("fullName"), "Luis Mario", "El nombre no coincide");
        Assert.assertEquals(response.jsonPath().getString("username"), "xluismariox", "El username no coincide");
        Assert.assertEquals(response.jsonPath().getString("memberType"), "normal", "El tipo de miembro no coincide");
    }

    @Test
    public void testResponseTime() {
        Assert.assertTrue(response.getTime() < 2000, "El tiempo de respuesta debe ser menor a 2 segundos");
    }

}
