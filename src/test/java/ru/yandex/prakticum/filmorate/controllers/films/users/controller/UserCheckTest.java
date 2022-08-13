package ru.yandex.prakticum.filmorate.controllers.films.users.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class UserCheckTest {
    HttpClient client = HttpClient.newHttpClient();
    @Test
    void rightNameCheckTest () throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/users"))
                .POST(HttpRequest.BodyPublishers.ofString(
                        "{\"email\":\"mail@mail.ru\",\"login\":\"dolore\"," +
                                "\"name\":\"Nick Name\",\"birthday\":\"1946-08-20\"}"
                ))
                .header("Content-type", "application/json")
                .build();
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(response.statusCode(), 200);
    }
    @Test
    void wrongEmailCheckTest () throws URISyntaxException, IOException, InterruptedException {
        //электронная почта не может быть пустой и должна содержать символ @;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/users"))
                .POST(HttpRequest.BodyPublishers.ofString(
                        "{\"id\":3,\"email\":\"mailmail.ru\",\"login\":\"dolore\"," +
                                "\"name\":\"Nick Name\",\"birthday\":\"1946-08-20\"}"
                ))
                .header("Content-type", "application/json")
                .build();
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(500,response.statusCode());


        request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/users"))
                .POST(HttpRequest.BodyPublishers.ofString(
                        "{\"id\":3,\"email\":\" \",\"login\":\"dolore\"," +
                                "\"name\":\"Nick Name\",\"birthday\":\"1946-08-20\"}"
                ))
                .header("Content-type", "application/json")
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(500,response.statusCode());

        request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/users"))
                .POST(HttpRequest.BodyPublishers.ofString(
                        "{\"id\":3,\"email\":\"null\",\"login\":\"dolore\"," +
                                "\"name\":\"Nick Name\",\"birthday\":\"1946-08-20\"}"
                ))
                .header("Content-type", "application/json")
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(500,response.statusCode());
    }

    @Test
    void wrongLoginCheckTest () throws URISyntaxException, IOException, InterruptedException {
        //логин не может быть пустым и содержать пробелы;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/users"))
                .POST(HttpRequest.BodyPublishers.ofString(
                        "{\"email\":\"mail@mail.ru\",\"login\":\"dol ore\"," +
                                "\"name\":\"Nick Name\",\"birthday\":\"1946-08-20\"}"
                ))
                .header("Content-type", "application/json")
                .build();
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(500,response.statusCode());


        request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/users"))
                .POST(HttpRequest.BodyPublishers.ofString(
                        "{\"email\":\"mail@mail.ru\",\"login\":\"\"," +
                                "\"name\":\"Nick Name\",\"birthday\":\"1946-08-20\"}"
                ))
                .header("Content-type", "application/json")
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(500,response.statusCode());

        request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/users"))
                .POST(HttpRequest.BodyPublishers.ofString(
                        "{\"id\":3,\"email\":\"mail@mail.ru\",\"login\":\" \"," +
                                "\"name\":\"Nick Name\",\"birthday\":\"1946-08-20\"}"
                ))
                .header("Content-type", "application/json")
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(500,response.statusCode());
    }

    @Test
    void wrongDOBCheckTest () throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/users"))
                .POST(HttpRequest.BodyPublishers.ofString(
                        "{\"email\":\"mail@mail.ru\",\"login\":\"dolore\"," +
                                "\"name\":\"Nick Name\",\"birthday\":\"3000-08-20\"}"
                ))
                .header("Content-type", "application/json")
                .build();
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(500,response.statusCode());
    }

}