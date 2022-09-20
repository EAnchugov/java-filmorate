package ru.yandex.prakticum.filmorate.controllers.films.users.storage.friend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.prakticum.filmorate.controllers.films.users.exceptions.NotFoundException;
import ru.yandex.prakticum.filmorate.controllers.films.users.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class FriendH2dbStorage implements friendStorage {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public FriendH2dbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public void addFriend(Integer userId, Integer friendId) {
        try {
            String sql = "insert into FRIENDS (USER_ID, FRIEND_ID, STATUS) values ( ?,?,?)";
            jdbcTemplate.update(sql,
                    userId,
                    friendId,
                    0);
        }
        catch (RuntimeException e){
            throw new NotFoundException("Ошибка при добавлении друзей");
        }
    }

    @Override
    public void deleteFriend(Integer userId, Integer friendId) {
        String sqlQuery = "delete from FRIENDS where USER_ID = ? AND FRIEND_ID = ?";
        jdbcTemplate.update(sqlQuery,userId,friendId);
    }

    @Override
    public List<User> getUserFriends(Integer id) {
        final String sql = "SELECT * FROM USERS WHERE USER_ID IN (SELECT friend_id FROM FRIENDS WHERE user_id = ?)";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> mapRowToUser(resultSet), id);
    }



    @Override
    public List<User> getCommonFriends(Integer userId, Integer friendId) {
        final String sql = "SELECT * FROM USERS WHERE USER_ID IN (SELECT FRIEND_ID FROM FRIENDS WHERE user_id = ? AND friend_id IN " +
                "(SELECT friend_id FROM FRIENDS WHERE user_id = ?))";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> mapRowToUser(resultSet), userId, friendId);
    }

    private User mapRowToUser(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("USER_ID"))
                .name(resultSet.getString("NAME"))
                .email(resultSet.getString("EMAIL"))
                .birthday(resultSet.getDate("BIRTHDAY").toLocalDate())
                .login(resultSet.getString("LOGIN"))
                .build();
    }
}
