package OkHttp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import okhttp3.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CreateBoard extends Base {

    public static final String PathENV = "src/main/resources/.env";
    public static final String NOMBRE_TABLERO = "TABLERO_PRUEBA_NUEVO_00000000000";
    public static final String DESCRIPCION_TABLERO = "Creando tablero nuevo";
    public String boardID;
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public JsonObject createBoard() throws IOException {
        HttpUrl url = HttpUrl.parse(getBaseURL() + "boards/")
                .newBuilder()
                .addQueryParameter("key", getApikey())
                .addQueryParameter("token", getToken())
                .addQueryParameter("name", NOMBRE_TABLERO)
                .addQueryParameter("desc", DESCRIPCION_TABLERO)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create("", MediaType.parse("application/json")))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Error en la respuesta: " + response);
            }
            String responseBody = response.body().string();
            JsonObject jsonObject = gson.fromJson(responseBody, JsonObject.class);
            boardID = jsonObject.get("id").getAsString();
            return jsonObject;
        }
    }

    private void updateEnvFile() {
        Map<String, String> envValues = new HashMap<>();
        envValues.put("ID_TABLERO", boardID);
        envValues.put("NOMBRE_TABLERO", NOMBRE_TABLERO);
        envValues.put("DESCRIPCION_TABLERO", DESCRIPCION_TABLERO);

        try (BufferedReader reader = new BufferedReader(new FileReader(PathENV));
             BufferedWriter writer = new BufferedWriter(new FileWriter(PathENV + ".tmp"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ID_TABLERO") || line.startsWith("NOMBRE_TABLERO") || line.startsWith("DESCRIPCION_TABLERO")) {
                    String key = line.split("=")[0];
                    writer.write(key + "=" + envValues.get(key) + "\n");
                    envValues.remove(key);
                } else {
                    writer.write(line + "\n");
                }
            }
            for (Map.Entry<String, String> entry : envValues.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error actualizando el archivo .env: " + e.getMessage());
        }

        // Reemplazamos el archivo original con el nuevo
        new File(PathENV + ".tmp").renameTo(new File(PathENV));
    }

    private void printResponseInfo(JsonObject response) {
        System.out.println("Respuesta JSON:");
        System.out.println(gson.toJson(response));
        System.out.println("ID del tablero: " + response.get("id").getAsString());
        System.out.println("Nombre del tablero: " + response.get("name").getAsString());
    }

    public static void main(String[] args) {
        CreateBoard createBoard = new CreateBoard();
        createBoard.setup();

        try {
            JsonObject response = createBoard.createBoard();
            createBoard.printResponseInfo(response);
            createBoard.updateEnvFile();
        } catch (IOException e) {
            System.err.println("Error al crear el tablero: " + e.getMessage());
        }
    }
}
