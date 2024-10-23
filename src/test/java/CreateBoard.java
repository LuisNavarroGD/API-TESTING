import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CreateBoard extends Utility.CreateBoard{
    private Utility.CreateBoard createBoard;
    private String readEnvFile(String path){
        StringBuilder content = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while((line = reader.readLine()) != null){
                content.append(line).append("\n");
            }
            reader.close();
        }catch (IOException e){
            e.printStackTrace();
        }return content.toString();
    }

    @BeforeClass
    public void setup(){
        createBoard = new CreateBoard();
        createBoard.setup();
    }

    @Test
    public void testNewBoard(){
        Response response = createBoard.newBoard();
        //Validar codigo de respuesta
        Assert.assertEquals(response.getStatusCode(), 200, "El código de estatus no es 200");
        //Verificar el ID del tablero
        String boardID = response.jsonPath().getString("id");
        createBoard.setBoardID(boardID);
        Assert.assertNotNull(boardID, "El ID del tablero es nulo");
        System.out.println("ID del tablero: " + boardID);
        Assert.assertEquals(boardID, createBoard.getBoardID(), "El ID del tablero no coincide");
        //Verificar el nombre del tablero
        String boardName = response.jsonPath().getString("name");
        Assert.assertEquals(boardName, createBoard.getBoardName(), "El nombre del tablero no coincide");
    }

    @Test(dependsOnMethods = "testNewBoard")
    public void testUpdateEnvFile(){
        String envFile = readEnvFile("src/test/resources/env.properties");
        envFile = envFile.replace("boardID", createBoard.getBoardID());
        System.out.println(envFile);
        String content = readEnvFile(Utility.CreateBoard.pathID);
        Assert.assertEquals(content, envFile, "El contenido del archivo env.properties no coincide");
        Assert.assertTrue(content.contains(createBoard.getBoardID()),
                "El ID del tablero no se encuentra en el archivo env.properties");
    }

}