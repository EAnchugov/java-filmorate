insert into MPA_RATING(MPA_NAME) values ('G' );
insert into MPA_RATING(MPA_NAME) values ('PG' );
insert into MPA_RATING(MPA_NAME) values ('PG-13' );
insert into MPA_RATING(MPA_NAME) values ( 'R' );
insert into MPA_RATING(MPA_NAME) values ( 'NC-17' );

merge into GENRES (genre_id, name)
    values (1, 'Комедия'),
           (2, 'Драма'),
           (3, 'Мультфильм'),
           (4, 'Триллер'),
           (5, 'Документальный'),
           (6, 'Боевик');

