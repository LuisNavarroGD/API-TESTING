import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class GetBoard extends Utility.GetBoard {
    private Utility.GetBoard getBoard;

    @BeforeClass
    public void setup(){
        getBoard = new Utility.GetBoard();
        getBoard.setup();
    }

    @Test
    public void testGetBoard(){
        Response response = getBoard.response();

        //Verificaciones
        Assert.assertNotNull(response, "La respuesta no deberia ser nula");
        Assert.assertEquals(response.statusCode(), 200, "El codigo de respuesta deberia ser 200");
        Assert.assertEquals(response.jsonPath().getString("id"), getBoard.IDTablero(),"El id del tablero deberia ser: " + getBoard.IDTablero());
        Assert.assertEquals(response.jsonPath().getString("name"), getBoard.NombreTablero(),
                "El nombre del tablero deberia ser: " + getBoard.NombreTablero());
        Assert.assertEquals(response.jsonPath().getString("desc"), getBoard.DescripcionTablero(),
                "La descripcion del tablero deberia ser: " + getBoard.DescripcionTablero());
    }
}
