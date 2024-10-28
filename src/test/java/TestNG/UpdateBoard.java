package TestNG;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UpdateBoard extends RestAssured.UpdateBoard {
    private RestAssured.UpdateBoard updateBoard;

    @BeforeClass
    public void setup() {
        updateBoard = new RestAssured.UpdateBoard();
        updateBoard.setup();
    }

    @Test
    public void testUpdateBoard() {
        Response response = updateBoard.actualizarTablero();

        Assert.assertEquals(response.getStatusCode(), 200, "El código de estado de la respuesta no es 200");
        Assert.assertEquals(response.jsonPath().getString("name"), NOMBRE_TABLERO,
                "El nombre del tablero no coincide");
        Assert.assertEquals(response.jsonPath().getString("desc"), DESCRIPCION_TABLERO,
                "La descripción del tablero no coincide");
    }
}
