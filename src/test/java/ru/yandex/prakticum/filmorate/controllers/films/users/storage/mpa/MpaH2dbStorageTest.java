package ru.yandex.prakticum.filmorate.controllers.films.users.storage.mpa;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.prakticum.filmorate.controllers.films.users.controller.MpaController;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MpaH2dbStorageTest {
    @Autowired
    private MpaController mpaController;

    @Test
    void getAllMpa() {
        assertEquals(5, mpaController.getAllMpa().size());
    }

    @Test
    void getMpa() {
        assertEquals("G", mpaController.getMpa(1).getName());
    }
}