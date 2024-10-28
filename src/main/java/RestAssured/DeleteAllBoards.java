package RestAssured;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;

public class DeleteAllBoards extends BuildRequest {

    // Método para obtener la respuesta que contiene los tableros del usuario
    public Response getBoardsResponse() {
        return RestAssured.given()
                .queryParam("key", getApikey())
                .queryParam("token", getToken())
                .get(getBaseURL() + "members/me/boards");
    }

    // Método para extraer los IDs de los tableros desde la respuesta
    public List<String> getBoardIds() {
        Response response = getBoardsResponse();
        return response.jsonPath().getList("id");
    }

    public static void main(String[] args) {
        DeleteAllBoards deleteAllBoards = new DeleteAllBoards();
        deleteAllBoards.setup();  // Configura el entorno, si es necesario (e.g., API keys)

        // Itera sobre los IDs de los tableros y elimina cada uno
        for (String id : deleteAllBoards.getBoardIds()) {
            RestAssured.given()
                    .queryParam("key", deleteAllBoards.getApikey())
                    .queryParam("token", deleteAllBoards.getToken())
                    .delete(deleteAllBoards.getBaseURL() + "boards/" + id);

            System.out.println("Tablero con ID " + id + " eliminado.");
        }

        if(!deleteAllBoards.getBoardIds().isEmpty()){
            System.out.println("No se han eliminado todos los tableros.");
        }else{
            System.out.println("Todos los tableros han sido eliminados correctamente.");
        }
    }
}
