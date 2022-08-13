package ru.yandex.prakticum.filmorate.controllers.films.users.controller;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

class FilmCheckTest {

    HttpClient client = HttpClient.newHttpClient();
    @Test
    void rightFilmCheckTest () throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/films"))
                .POST(HttpRequest.BodyPublishers.ofString(
                        "{\n" +
                                "  \"name\": \"nisi eiusmod\",\n" +
                                "  \"description\": \"adipisicing\",\n" +
                                "  \"releaseDate\": \"1967-03-25\",\n" +
                                "  \"duration\": 100\n" +
                                "}"
                ))
                .header("Content-type", "application/json")
                .build();
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(response.statusCode(), 200);
    }

    @Test
    void wrongFilmNameCheckTest () throws URISyntaxException, IOException, InterruptedException {
        //    название не может быть пустым;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/films"))
                .POST(HttpRequest.BodyPublishers.ofString(
                        "{\n" +
                                "  \"name\": \" \",\n" +
                                "  \"description\": \"adipisicing\",\n" +
                                "  \"releaseDate\": \"1967-03-25\",\n" +
                                "  \"duration\": 100\n" +
                                "}"
                ))
                .header("Content-type", "application/json")
                .build();
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(500,response.statusCode());


        request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/films"))
                .POST(HttpRequest.BodyPublishers.ofString(
                        "{\n" +
                                "  \"name\": \"\",\n" +
                                "  \"description\": \"adipisicing\",\n" +
                                "  \"releaseDate\": \"1967-03-25\",\n" +
                                "  \"duration\": 100\n" +
                                "}"
                ))
                .header("Content-type", "application/json")
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(500,response.statusCode());
    }

    @Test
    //    максимальная длина описания — 200 символов;
    void wrongDescriptionLengthTest () throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/films"))
                .POST(HttpRequest.BodyPublishers.ofString(
                        "{\n" +
                                "  \"name\": \"nisi eiusmod\",\n" +
                                "  \"description\": \"Те, кому когда-либо приходилось делать в квартире ремонт," +
                                " наверное, обращали внимание на старые газеты, наклеенные под обоями. " +
                                "Как правило, пока все статьи не перечитаешь, ничего другого делать не можешь. " +
                                "Интересно же — обрывки текста, чья-то жизнь... " +
                                "Так же и с тестами. Пока ревьюэр не прочтет всё, он не успокоится. " +
                                "Бывали случаи, когда спринт принимался именно из-за текста тестов," +
                                " который, разумеется, никакого отношения к работе не имел.\",\n" +
                                "  \"releaseDate\": \"1967-03-25\",\n" +
                                "  \"duration\": 100\n" +
                                "}"
                ))
                .header("Content-type", "application/json")
                .build();
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(500, response.statusCode());
    }

    //    дата релиза — не раньше 28 декабря 1895 года;
    @Test
    void wrongReleaseDateCheckTest () throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/films"))
                .POST(HttpRequest.BodyPublishers.ofString(
                        "{\n" +
                                "  \"name\": \"nisi eiusmod\",\n" +
                                "  \"description\": \"adipisicing\",\n" +
                                "  \"releaseDate\": \"1667-03-25\",\n" +
                                "  \"duration\": 100\n" +
                                "}"
                ))
                .header("Content-type", "application/json")
                .build();
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals( 500, response.statusCode());
    }

    //    продолжительность фильма должна быть положительной.
    @Test
    void wrongDurationFilmCheckTest () throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/films"))
                .POST(HttpRequest.BodyPublishers.ofString(
                        "{\n" +
                                "  \"name\": \"nisi eiusmod\",\n" +
                                "  \"description\": \"adipisicing\",\n" +
                                "  \"releaseDate\": \"1967-03-25\",\n" +
                                "  \"duration\": -100\n" +
                                "}"
                ))
                .header("Content-type", "application/json")
                .build();
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(500, response.statusCode());
    }
}