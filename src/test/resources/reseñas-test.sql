INSERT INTO plataformas (id, plataforma) VALUES (1, 'PC'), (2, 'PS5'), (3, 'Xbox Series X');

INSERT INTO generos (id, genero) VALUES (1, 'RPG'), (2, 'Action');

INSERT INTO videojuegos (id, titulo, sinopsis, imagen, fecha_lanzamiento, puntuacion_promedio)
VALUES
    (1, 'Red Dead', 'Descripción del juego 1', 'imagen1.jpg', '2023-10-01', 0.0),
    (2, 'Juego 2', 'Descripción del juego 2', 'imagen2.jpg', '2021-10-02', 0.0);

INSERT INTO plataforma_videojuego (fk_plataforma, fk_videojuego) VALUES (1, 1), (2, 2);
INSERT INTO genero_videojuego (fk_genero, fk_videojuego) VALUES (1, 1), (1, 2), (2, 2);

INSERT into usuarios (id, username, email, password, fecha_nacimiento, activo,admin,moderador,bloqueado)
VALUES
    (1,'test','test@gmail.com','password','2002-07-19',true,false,false,false),
    (2,'test2','test2@gmail.com','password2','2002-07-19',true,false,false,false);


