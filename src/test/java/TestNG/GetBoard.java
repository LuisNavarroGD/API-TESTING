package TestNG;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class GetBoard extends RestAssured.GetBoard {
    private RestAssured.GetBoard getBoard;

    @BeforeClass
    public void setup(){
        getBoard = new RestAssured.GetBoard();
        getBoard.setup();
    }

    @Test
    public void testGetBoard(){
        Response response = getBoard.response();

        //Verificaciones
        Assert.assertNotNull(response, "La respuesta no deberia ser nula");
        Assert.assertEquals(response.statusCode(), 200, "El codigo de respuesta deberia ser 200");

        Assert.assertEquals(response.getHeader("Content-Type"),"application/json; charset=utf-8", "El content type deberia ser JSON");
        Assert.assertEquals(getBoard.boardID, response.jsonPath().getString("id"), "El id del tablero deberia ser: " + getBoard.boardID);
        Assert.assertEquals(getBoard.boardName, response.jsonPath().getString("name"), "El nombre del tablero deberia ser: " + NOMBRE_TABLERO);
        Assert.assertEquals(getBoard.boardDesc, response.jsonPath().getString("desc"), "La descripcion del tablero deberia ser: " + DESCRIPCION_TABLERO);
    }
}
