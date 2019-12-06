CREATE DATABASE workshop2
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_polish_ci;

CREATE TABLE user_groups
(
    id   INT AUTO_INCREMENT,
    name VARCHAR(255),
    primary key (id)
);
CREATE TABLE users
(
    id            INT AUTO_INCREMENT,
    user_name     VARCHAR(255),
    email         VARCHAR(255),
    password      VARCHAR(245),
    user_group_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_group_id) REFERENCES user_groups (id)
);
CREATE TABLE exercises
(
    id          INT AUTO_INCREMENT,
    title       VARCHAR(255),
    description TEXT,
    PRIMARY KEY (id)
);
CREATE TABLE solutions
(
    id          INT AUTO_INCREMENT,
    created     DATETIME,
    updated     DATETIME,
    description TEXT,
    exercise_id INT NOT NULL,
    user_id     INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (exercise_id) REFERENCES exercises (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

