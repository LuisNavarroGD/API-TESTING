package PojoClasses;

import RestAssured.BuildRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BoardPojo extends BuildRequest {

    private String id;
    private String name;
    private String username;
    private String email;
    private String url;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("fullName")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static void main (String[] args) {

        BuildRequest buildRequest = new BuildRequest();
        buildRequest.setup();
        buildRequest.IdRequest();

        String json = "{\n" +
                "    \"id\": \""  + buildRequest.IdRequest() + "\",\n" +
                "    \"fullName\": \"Luis Mario\",\n" +
                "    \"username\": \"xluismariox\",\n" +
                "    \"email\": \"xluismariox@gmail.com\",\n" +
                "    \"url\": \"https://trello.com/xluismariox\"\n" +
                "}";

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            BoardPojo boardPojo = objectMapper.readValue(json, BoardPojo.class);
            System.out.println("ID: " + boardPojo.getId());
            System.out.println("Nombre: " + boardPojo.getName());
            System.out.println("Usuario: " + boardPojo.getUsername());
            System.out.println("Correo: " + boardPojo.getEmail());
            System.out.println("URL: " + boardPojo.getUrl());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}