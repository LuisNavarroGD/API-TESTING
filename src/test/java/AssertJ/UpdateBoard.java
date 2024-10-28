package AssertJ;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UpdateBoard extends RestAssured.UpdateBoard {

    private RestAssured.UpdateBoard updateBoard;

    @BeforeEach
    public void setup() {
        updateBoard = new RestAssured.UpdateBoard();
        updateBoard.setup();
    }

    @Test
    public void testUpdateBoard() {
        Response response = updateBoard.actualizarTablero();

        assertThat(response.getStatusCode()).as("La respuesta debe de ser 200").isEqualTo(200);
        assertThat(response.jsonPath().getString("name")).as("El nombre deberia ser" + NOMBRE_TABLERO).isEqualTo(NOMBRE_TABLERO);
        assertThat(response.jsonPath().getString("desc")).as("La descripcion deberia ser" + DESCRIPCION_TABLERO).isEqualTo(DESCRIPCION_TABLERO);
        assertThat(response.jsonPath().getString("id")).as("El idOrganization deberia ser" + updateBoard.boardID).isEqualTo(updateBoard.boardID);
    }
}
