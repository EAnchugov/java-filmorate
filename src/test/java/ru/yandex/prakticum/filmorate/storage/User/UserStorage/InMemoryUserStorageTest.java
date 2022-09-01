//Тут просто тренируюсь
/*package ru.yandex.prakticum.filmorate.storage.User.UserStorage;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.Film;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class InMemoryUserStorageTest {
    private  Film film = Film.builder()
            .description("asdasd")
            .duration(200)
            .releaseDate(LocalDate.of(1967,03,25))
            .name("фывфывфыв")
            .build();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;




    @Test
    void shouldReturn500() throws Exception{
        this.mockMvc.perform(put("/films"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
    @Test
    void shouldReturn200() throws Exception {
        this.mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(film)))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString(objectMapper.writeValueAsString(film))));
      //                          .containsString(objectMapper.writeValueAsString(film))));

    }

    @Test
        void wrongNameTest() throws Exception{
        film.setName("");
            this.mockMvc.perform(post("/films")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(film)))
                            .andDo(print())
                            .andExpect(status().is4xxClientError())
                            .andExpect(content().string(containsString("error")));

        }
}*/