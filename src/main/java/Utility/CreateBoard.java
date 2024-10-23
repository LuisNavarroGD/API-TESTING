package Utility;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class CreateBoard extends Base {

    //Path para crear txt ID
    public static String pathID = "src/main/resources/.env";
    String boardName = "TABLERO_PRUEBA_NUEVO_00000000000";
    private String boardID;

    public Response newBoard() {
        Response response = RestAssured.given()
                .header("Content-type", "application/json")
                .queryParam("key", getApikey())
                .queryParam("token", getToken())
                .queryParam("name", boardName)
                .post(getBaseURL() + "/boards/");

        if(response.getStatusCode() == 200) {
            boardID = response.jsonPath().getString("id");
        }
        return response;
    }

    public String getBoardID() {
        return boardID;
    }

    public void setBoardID(String boardID) {
        this.boardID = boardID;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public static void main(String[] args) {
        CreateBoard createBoard = new CreateBoard();
        createBoard.setup();
        Response response = createBoard.newBoard();
        // Crear una variable para almacenar el contenido actualizado
        StringBuilder contenidoActualizado = new StringBuilder();
        boolean encontrado = false; // Para verificar si la variable ID_TABLERO fue encontrada


        // Verificar si la respuesta fue exitosa antes de intentar procesar el JSON
        if (response.getStatusCode() == 200) {
            System.out.println("Respuesta JSON: ");
            response.prettyPrint();
            System.out.println("\nEl ID es: " + response.jsonPath().getString("id"));
            System.out.println("\nEl nombre del tablero es: " + response.jsonPath().getString("name"));
            // Imprimir el código de estado y el cuerpo de la respuesta
            System.out.println("Código de estado: " + response.getStatusCode());
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(pathID));
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.contains("ID_TABLERO")) {
                    contenidoActualizado.append("ID_TABLERO=").append(createBoard.getBoardID()).append("\n");
                    encontrado = true;
                } else {
                    contenidoActualizado.append(linea).append("\n");
                }
            }
            reader.close();

            if (!encontrado) {
                contenidoActualizado.append("\nID_TABLERO=").append(createBoard.getBoardID());
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(pathID));
            writer.write(contenidoActualizado.toString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}