package RestAssured;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CreateBoard extends Base {

    public static final String PathENV = "src/main/resources/.env";
    public static final String NOMBRE_TABLERO = "TABLERO_PRUEBA_NUEVO_00000000000";
    public static final String DESCRIPCION_TABLERO = "Creando tablero nuevo";
    public String boardID;

    public Response createBoard() {
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .queryParam("key", getApikey())
                .queryParam("token", getToken())
                .queryParam("name", NOMBRE_TABLERO)
                .queryParam("desc", DESCRIPCION_TABLERO)
                .post(getBaseURL() + "boards/");

        if (response.getStatusCode() == 200) {
            boardID = response.jsonPath().getString("id");
        }
        return response;
    }

    private void updateEnvFile() {
        Map<String, String> envValues = new HashMap<>();
        envValues.put("ID_TABLERO", boardID);
        envValues.put("NOMBRE_TABLERO", NOMBRE_TABLERO);
        envValues.put("DESCRIPCION_TABLERO", DESCRIPCION_TABLERO);

        try (BufferedReader reader = new BufferedReader(new FileReader(PathENV));
             BufferedWriter writer = new BufferedWriter(new FileWriter(PathENV + ".tmp"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ID_TABLERO") || line.startsWith("NOMBRE_TABLERO") || line.startsWith("DESCRIPCION_TABLERO")) {
                    String key = line.split("=")[0];
                    writer.write(key + "=" + envValues.get(key) + "\n");
                    envValues.remove(key);
                } else {
                    writer.write(line + "\n");
                }
            }
            for (Map.Entry<String, String> entry : envValues.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error actualizando el archivo .env: " + e.getMessage());
        }

        // Reemplazamos el archivo original con el nuevo
        new File(PathENV + ".tmp").renameTo(new File(PathENV));
    }

    private void printResponseInfo(Response response) {
        System.out.println("Respuesta JSON:");
        response.prettyPrint();
        System.out.println("ID del tablero: " + response.jsonPath().getString("id"));
        System.out.println("Nombre del tablero: " + response.jsonPath().getString("name"));
        System.out.println("Código de estado: " + response.getStatusCode());
    }

    public static void main(String[] args) {
        CreateBoard createBoard = new CreateBoard();
        createBoard.setup();

        Response response = createBoard.createBoard();

        if (response.getStatusCode() == 200) {
            createBoard.printResponseInfo(response);
            createBoard.updateEnvFile();
        } else {
            System.err.println("Error al crear el tablero. Código de estado: " + response.getStatusCode());
        }
    }
}
