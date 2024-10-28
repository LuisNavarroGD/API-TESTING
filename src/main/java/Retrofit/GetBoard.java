package Retrofit;

import io.github.cdimascio.dotenv.Dotenv;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.io.IOException;

public class GetBoard extends CreateBoard {
    Dotenv dotenv = Dotenv.load();

    public interface TrelloService {
        @GET("boards/{id}")
        Call<BoardResponse> getBoard(
                @Path("id") String boardID,
                @Query("key") String key,
                @Query("token") String token
        );
    }

    public BoardResponse response(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TrelloService trelloService = retrofit.create(TrelloService.class);
        String boardID = dotenv.get("ID_TABLERO");
        Call<BoardResponse> call = trelloService.getBoard(boardID, getApikey(), getToken());

        try{
            Response<BoardResponse> response = call.execute();
            if(response.isSuccessful() && response.body() != null) {
                return response.body();
            }else {
                System.err.println("Error: " + response.code());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String [] args){
        GetBoard getBoard = new GetBoard();
        getBoard.response();

        BoardResponse response = getBoard.response();

        if(response != null) {
            System.out.println("ID del tablero: " + response.getId());
            System.out.println("Nombre del tablero: " + response.getName());
            System.out.println("Descripción del tablero: " + response.getDesc());
        }else {
            System.err.println("Error al obtener la respuesta del tablero.");
        }
    }
}
