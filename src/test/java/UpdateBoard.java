import Utility.GetBoard;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UpdateBoard extends Utility.UpdateBoard {
    private Utility.UpdateBoard updateBoard;

    @BeforeClass
    public void setup() {
        updateBoard = new Utility.UpdateBoard();
        updateBoard.setup();
    }

    @Test
    public void testUpdateBoard() {
        Response response = updateBoard.actualizarTablero();

        Assert.assertEquals(response.getStatusCode(), 200, "El código de estado de la respuesta no es 200");

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, "El código de estado de la respuesta no es 200");
        System.out.println("Código de estado de la respuesta: " + statusCode);

        Assert.assertEquals(response.jsonPath().getString("name"), updateBoard.NombreTablero(), "El nombre del tablero no coincide");
        Assert

    }
}
