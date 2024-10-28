package Retrofit;

import io.github.cdimascio.dotenv.Dotenv;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.Response;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateBoard extends CreateBoard{
    public static String nuevaDescripcion = "Tablero actualizado con exito";

    public interface UpdateBoardService {
        @PUT("boards/{id}")
        Call<BoardResponse> updateBoard(
                @Path("id") String boardID,
                @Query("key") String key,
                @Query("token") String token,
                @Body Map<String, String> fields
        );
    }

    public BoardResponse actualizarTablero(String nuevaDescripcion){
        Dotenv  dotenv = Dotenv.load();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UpdateBoardService updateBoardService = retrofit.create(UpdateBoardService.class);

        Map<String, String>fields = new HashMap<>();
        fields.put("desc", nuevaDescripcion);

        String boardID = dotenv.get("ID_TABLERO");

        Call<BoardResponse> call = updateBoardService.updateBoard(boardID, getApikey(), getToken(), fields);
        try{
            Response<BoardResponse> response = call.execute();
            if(response.isSuccessful() && response.body() != null){
                return response.body();
            }else {
                System.err.println("Error: " + response.code());
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main (String[] args){
        UpdateBoard updateBoard = new UpdateBoard();
        BoardResponse response = updateBoard.actualizarTablero(nuevaDescripcion);

        if(response != null) {
            System.out.println("ID del tablero: " + response.getId());
            System.out.println("Nombre del tablero: " + response.getName());
            System.out.println("Descripción del tablero: " + response.getDesc());
        }
        else {
            System.err.println("Error al obtener la respuesta del tablero.");
        }
    }
}

