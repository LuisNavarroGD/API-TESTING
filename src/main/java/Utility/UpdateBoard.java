package Utility;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class UpdateBoard extends GetBoard {
     public Response actualizarTablero(){
         return RestAssured.given()
                 .queryParam("key", getApikey())
                 .queryParam("token", getToken())
                 //.queryParam("name", "Tablero Actualizado")
                 .queryParam("desc", "Tablero actualizado correctamente")
                 .put(getBaseURL() + "/boards/" + boardID);
     }

     public String NombreTablero(){
         return response().jsonPath().getString("name");
     }

     public static void main(String[]args){
         UpdateBoard updateBoard = new UpdateBoard();
         updateBoard.setup();

         Response response = updateBoard.actualizarTablero();

         System.out.println("Respuesta JSON: ");
         response.prettyPrint();
         System.out.println("\n El código de estado es: " + response.getStatusCode());
         System.out.println("\n El nombre del tablero actualizado es: " + response.jsonPath().getString("name"));
         System.out.println("\n La descripción del tablero actualizado es: " + response.jsonPath().getString("desc"));
         System.out.println("\n El id del tablero actualizado es: " + response.jsonPath().getString("id"));
     }
}
