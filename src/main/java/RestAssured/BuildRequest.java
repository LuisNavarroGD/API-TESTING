package RestAssured;

import io.restassured.response.Response;
import io.restassured.RestAssured;

public class BuildRequest extends Base {

    public Response buildRequest(){

        return RestAssured.given()
                .queryParam("key", getApikey())
                .queryParam("token", getToken())
                .get(getBaseURL() + "members/me");
    }

    public String IdRequest(){
        Response response = buildRequest();
        return response.jsonPath().getString("id");
    }

    public static void main(String[]args){
        BuildRequest buildRequest = new BuildRequest();

        buildRequest.setup();

        Response response = buildRequest.buildRequest();

        System.out.println("Respuesta JSON: ");
        response.prettyPrint();
        System.out.println("\nEl id es: " + response.jsonPath().getString("id"));
        System.out.println("El avatar es: " + response.jsonPath().getString("avatarUrl"));
        System.out.println("La url es: " + response.jsonPath().getString("url"));
        System.out.println("El correo es: " + response.jsonPath().getString("email"));
        System.out.println("El nombre completo de usuario es: " + response.jsonPath().getString("fullName"));
        System.out.println("El nombre de usuario es: " + response.jsonPath().getString("username"));
        System.out.println("El tipo de usuario es: " + response.jsonPath().getString("memberType"));

    }
}