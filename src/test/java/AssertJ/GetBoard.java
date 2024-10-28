package AssertJ;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GetBoard extends RestAssured.GetBoard{
    private RestAssured.GetBoard getBoard;

    @BeforeEach
    public void setUp() {
        getBoard = new RestAssured.GetBoard();
        getBoard.setup();
    }

    @Test
    public void testGetBoard(){
        Response response = getBoard.response();

        //Verificaciones usando AssertJ
        assertThat(response).as("La respuesta no debe de ser nulla").isNotNull();
        assertThat(response.getBody().asString()).contains("id");
        assertThat(response.getStatusCode()).isEqualTo(200);

        assertThat(response.getHeader("Content-Type")).isEqualTo("application/json; charset=utf-8");
        assertThat(response.jsonPath().getString("id")).as("El id del tablero deberia ser: ").isEqualTo(getBoard.boardID);
        assertThat(response.jsonPath().getString("name")).as("El nombre del tablero deberia ser: ").isEqualTo(NOMBRE_TABLERO);
        assertThat(response.jsonPath().getString("desc")).as("La descripcion del tablero deberia ser: ").isEqualTo(DESCRIPCION_TABLERO);

    }
}