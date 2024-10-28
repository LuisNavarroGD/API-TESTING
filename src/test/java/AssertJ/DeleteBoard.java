package AssertJ;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DeleteBoard extends RestAssured.DeleteBoard{

    private RestAssured.DeleteBoard deleteBoard;

    @BeforeEach
    public void setUp() {
        deleteBoard = new RestAssured.DeleteBoard();
        deleteBoard.setup();
    }

    @Test
    public void testDeleteBoard() {
        Response response = deleteBoard.response();

        assertThat(response.getStatusCode()).as("El estatus code debe de ser 200").isEqualTo(200);
        assertThat(response.jsonPath().getString("id")).as("El id debe de ser: " + deleteBoard.boardID).isEqualTo(deleteBoard.boardID);
        assertThat(response.jsonPath().getString("name")).as("El nombre debe de ser: " + NOMBRE_TABLERO).isEqualTo(NOMBRE_TABLERO);
        assertThat(response.jsonPath().getString("desc")).as("La descripcion debe de ser: " + DESCRIPCION_TABLERO).isEqualTo(DESCRIPCION_TABLERO);

        String valueField = response.jsonPath().getString("_value");

        assertThat(valueField).as("El body deberia de ser nulo " + valueField).isNull();
    }

}
