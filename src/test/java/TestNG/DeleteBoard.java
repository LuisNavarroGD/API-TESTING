package TestNG;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class DeleteBoard extends RestAssured.DeleteBoard {
    private RestAssured.DeleteBoard deleteBoard;
    @BeforeClass

    public void setUp() {
        deleteBoard = new RestAssured.DeleteBoard();
        deleteBoard.setup();
    }

    @Test
    public void testDeleteBoard() {
        Response response = deleteBoard.response();

        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");
        Assert.assertEquals(response.jsonPath().getString("id"), deleteBoard.boardID,
                "El id deberia ser: " + deleteBoard.boardID);
        Assert.assertEquals(boardName, response.jsonPath().getString("name"),
                "El nombre deberia ser: " + NOMBRE_TABLERO);
        //Value del JSON body
        String valueField = response.jsonPath().getString("_value");
        Assert.assertNull(valueField, "El campo deberia de ser null");
    }
}
