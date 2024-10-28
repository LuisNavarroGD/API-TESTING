package RestAssured;

import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class UpdateBoard extends GetBoard {
    Dotenv dotenv = Dotenv.load();
    public static final String PATH_ENV = "src/main/resources/.env";
    public static String nuevaDescripcion = "Tablero actualizado con exito";

    // Método para actualizar el tablero en Trello
    public Response actualizarTablero() {
        return RestAssured.given()
                .queryParam("key", getApikey())
                .queryParam("token", getToken())
                .queryParam("desc", nuevaDescripcion)
                .put(getBaseURL() + "boards/" + boardID);
    }

    public static void main(String[] args) {
        UpdateBoard updateBoard = new UpdateBoard();
        updateBoard.setup();

        // Actualizamos el tablero en Trello
        Response response = updateBoard.actualizarTablero();

        System.out.println("Respuesta JSON: ");
        response.prettyPrint();
        System.out.println("\nEl código de estado es: " + response.getStatusCode());
        System.out.println("\nEl nombre del tablero actualizado es: " + response.jsonPath().getString("name"));
        System.out.println("\nLa descripción del tablero actualizado es: " + response.jsonPath().getString("desc"));
        System.out.println("\nEl ID del tablero actualizado es: " + response.jsonPath().getString("id"));

    }
}
