package Retrofit;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CreateBoard extends Base {

    public static final String PathENV = "src/main/resources/.env";
    public static final String NOMBRE_TABLERO = "TABLERO_PRUEBA_NUEVO_00000000000";
    public static final String DESCRIPCION_TABLERO = "Creando tablero nuevo";
    public String boardID;

    // Interfaz de Retrofit
    public interface CrearTableroService {
        @POST("boards/")
        Call<BoardResponse> createBoard(
                @Query("key") String key,
                @Query("token") String token,
                @Query("name") String name,
                @Query("desc") String desc
        );
    }


    // Clase para mapear la respuesta
    public static class BoardResponse {
        private String id;
        private String name;
        private String desc;

        // Getters y setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getDesc() { return desc; }
        public void setDesc(String desc) { this.desc = desc; }
    }

    private CrearTableroService crearTableroService;

    // Constructor
    public CreateBoard() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crearTableroService = retrofit.create(CrearTableroService.class);
    }

    // Método para crear el tablero
    public BoardResponse createBoard() {
        Call<BoardResponse> call = crearTableroService.createBoard(getApikey(), getToken(), NOMBRE_TABLERO, DESCRIPCION_TABLERO);
        try {
            Response<BoardResponse> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                BoardResponse boardResponse = response.body();
                boardID = boardResponse.getId();
                printResponseInfo(boardResponse);
                updateEnvFile();
                return boardResponse;
            } else {
                System.out.println("Error: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para actualizar el archivo .env
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

        // Reemplaza el archivo original con el nuevo
        new File(PathENV + ".tmp").renameTo(new File(PathENV));
    }

    // Método para imprimir información de la respuesta
    private void printResponseInfo(BoardResponse boardResponse) {
        System.out.println("ID del tablero: " + boardResponse.getId());
        System.out.println("Nombre del tablero: " + boardResponse.getName());
    }

    public static void main(String[] args) {
        CreateBoard createBoard = new CreateBoard();
        createBoard.createBoard();
    }
}
