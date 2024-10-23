package Utility;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class DeleteBoard extends UpdateBoard{
    public Response response (){
        return RestAssured.given()
                .queryParam("key", getApikey())
                .queryParam("token", getToken())
                .delete(getBaseURL() + "/boards/" + boardID);
    }

    public static void main (String [] args){
        DeleteBoard deleteBoard = new DeleteBoard();
        deleteBoard.setup();

        Response response = deleteBoard.response();

        System.out.println("Respuesta JSON: ");
        response.prettyPrint();
        System.out.println("\n Se ha borrado el tablero con el siguiente ID: " + deleteBoard.boardID);
        System.out.println("\n El código de estado es: " + response.getStatusCode());
    }
}
