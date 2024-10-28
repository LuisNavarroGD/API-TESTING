package Retrofit;

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.DELETE;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class DeleteBoard extends UpdateBoard {
    Dotenv dotenv = Dotenv.load();
    public String boardID = dotenv.get("ID_TABLERO");

    public interface TrelloService {
        @DELETE("boards/{id}")
        Call<ResponseBody> deleteBoard (
        @Path("id") String boardID,
        @Query("key") String key,
        @Query("token") String token
        );
    }

    public void EliminarTablero() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TrelloService trelloService = retrofit.create(TrelloService.class);

        Call<ResponseBody> call = trelloService.deleteBoard(boardID, getApikey(), getToken());

        try {
            retrofit2.Response<ResponseBody> response = call.execute();
            if (response.isSuccessful()) {
                System.out.println("Tablero eliminado correctamente.");
            } else {
                System.out.println("Error al eliminar el tablero: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main (String[] args){
        DeleteBoard deleteBoard = new DeleteBoard();

        System.out.println("Tablero eliminado correctamente.");

        System.out.println("Nombre del tablero eliminado: " + DeleteBoard.NOMBRE_TABLERO);
        System.out.println("ID del tablero eliminado: " + deleteBoard.boardID);
        System.out.println("Descripción del tablero eliminado: " + DeleteBoard.DESCRIPCION_TABLERO);
    }
}
