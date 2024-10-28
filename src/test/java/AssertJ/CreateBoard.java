package AssertJ;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateBoard extends RestAssured.CreateBoard {
    private static CreateBoard createBoard;

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

    @BeforeAll
    public static void setupGB() {
        createBoard = new CreateBoard();
        createBoard.setup();
    }

    @Test
    public void testNewBoard() {
        Response response = createBoard.createBoard();

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body().jsonPath().getString("name"))
                .as("El nombre no coincide").isEqualTo(NOMBRE_TABLERO);
        assertThat(response.body().jsonPath().getString("desc"))
                .as("La descripción no coincide").isEqualTo(DESCRIPCION_TABLERO);
        assertThat(response.body().jsonPath().getString("idOrganization"))
                .as("El ID de organización no debe de ser nulo").isNotNull();
        assertThat(response.body().jsonPath().getString("id"))
                .as("El ID no coincide").isEqualTo(createBoard.boardID);
    }

    @Test
    public void testUpdateEnvFile() {
        // Leer el archivo y reemplazar ID_TABLERO con el nuevo boardID
        String envFileContent = readEnvFile(PathENV);
        String updatedContent = envFileContent.replace("ID_TABLERO=", "ID_TABLERO=" + createBoard.boardID);

        // Guardar el archivo actualizado
        writeEnvFile(PathENV, updatedContent);

        // Leer de nuevo el archivo para verificar el contenido
        String newContent = readEnvFile(PathENV);

        // Verificaciones
        assertThat(newContent)
                .as("El contenido del archivo env.properties no coincide")
                .isEqualTo(updatedContent);
        assertThat(newContent)
                .as("El ID del tablero no se encuentra en el archivo env.properties")
                .contains("ID_TABLERO=" + createBoard.boardID);
    }
}
