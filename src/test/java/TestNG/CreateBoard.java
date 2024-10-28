package TestNG;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CreateBoard extends RestAssured.CreateBoard {
    private RestAssured.CreateBoard createBoard;

    private String readEnvFile(String path) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    private void writeEnvFile(String path, String content) {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeClass
    public void setup() {
        createBoard = new RestAssured.CreateBoard();
        createBoard.setup();
    }

    @Test
    public void testNewBoard() {
        Response response = createBoard.createBoard();

        Assert.assertEquals(response.getStatusCode(), 200, "El código de estatus no es 200");
        Assert.assertNotNull(response.jsonPath().getString("id"), "El ID del tablero es nulo");
        Assert.assertEquals(createBoard.boardID, response.jsonPath().getString("id"), "El ID del tablero no coincide");
        Assert.assertEquals(NOMBRE_TABLERO, response.jsonPath().getString("name"), "El nombre del tablero no coincide");
    }

    @Test(dependsOnMethods = "testNewBoard")
    public void testUpdateEnvFile() {
        // Leer el archivo y reemplazar ID_TABLERO con el nuevo boardID
        String envFileContent = readEnvFile(PathENV);
        String updatedContent = envFileContent.replace("ID_TABLERO=", "ID_TABLERO=" + createBoard.boardID);

        // Guardar el archivo actualizado
        writeEnvFile(PathENV, updatedContent);

        // Leer de nuevo el archivo para verificar el contenido
        String newContent = readEnvFile(PathENV);

        // Verificaciones
        Assert.assertEquals(newContent, updatedContent, "El contenido del archivo env.properties no coincide");
        Assert.assertTrue(newContent.contains("ID_TABLERO=" + createBoard.boardID),
                "El ID del tablero no se encuentra en el archivo env.properties");
    }
}
