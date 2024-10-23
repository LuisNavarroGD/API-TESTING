package Utility;
import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetBoard extends CreateBoard {
    Dotenv dotenv = Dotenv.load();
    public String boardID = dotenv.get("ID_TABLERO");
    private String NombreTablero;
    private String IDTablero;
    private String DescripcionTablero;

    public Response response() {
        return RestAssured.given()
                .queryParam("key", getApikey())
                .queryParam("token", getToken())
                .get(getBaseURL() + "/boards/" + boardID);
    }

    public String NombreTablero() {
        return response().jsonPath().getString("name");
    }

    public String IDTablero() {
        return response().jsonPath().getString("id");
    }

    public String DescripcionTablero() {
        return response().jsonPath().getString("desc");
    }

    public static void main(String[] args) {
        GetBoard getBoard = new GetBoard();
        getBoard.setup();

        Response response = getBoard.response();

        if (response != null) {
            System.out.println("Respuesta JSON: ");
            response.prettyPrint();
            System.out.println("\nEl id es: " + getBoard.boardID);
            System.out.println("El nombre del tablero es: " + response.jsonPath().getString("name"));
            System.out.println("La descripción del tablero es: " + response.jsonPath().getString("desc"));
            System.out.println("El código de estado es: " + response.getStatusCode());
        } else {
            System.out.println("No se pudo obtener la información del tablero.");
        }

        System.out.println(getBoard.NombreTablero());
    }

}
