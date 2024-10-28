package OkHttp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class DeleteBoard extends UpdateBoard{
    Dotenv dotenv = Dotenv.load();
    private final OkHttpClient client = new OkHttpClient();

    public Response response() throws IOException {
        HttpUrl url = HttpUrl.parse(getBaseURL() + "boards/" + boardID)
                .newBuilder()
                .addQueryParameter("key", getApikey())
                .addQueryParameter("token", getToken())
                .build();

        Request  request = new Request.Builder()
                .url(url)
                .delete()
                .build();

        return client.newCall(request).execute();
    }

    public static void main(String[] args) {
        DeleteBoard deleteBoard = new DeleteBoard();
        deleteBoard.setup();

        try {
            Response response = deleteBoard.response();

            if(response.isSuccessful()) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("name", deleteBoard.boardName);
                jsonObject.addProperty("id", deleteBoard.boardID);

                System.out.println(jsonObject.get("name").getAsString() + " eliminado");
                System.out.println("Codigo de estado: " + response.code());
                System.out.println("Se ha borrado el tablero con el siguiente ID: " + jsonObject.get("id").getAsString());
            }else {
                System.out.println("Error al eliminar el tablero: " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
