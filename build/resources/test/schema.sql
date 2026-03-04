DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS boards;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS sample;

CREATE TABLE sample (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(255),
    created_at DATETIME
);

CREATE TABLE boards (
    board_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    created_at DATETIME
);

CREATE TABLE posts (
    post_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    body TEXT,
    view INT,
    likes INT,
    created_at DATETIME,
    user_id BIGINT,
    board_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (board_id) REFERENCES boards(board_id)
);

CREATE TABLE comments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content VARCHAR(2000),
    created_at DATETIME,
    updated_at DATETIME,
    post_id BIGINT,
    user_id BIGINT,
    parent_id BIGINT,
    FOREIGN KEY (post_id) REFERENCES posts(post_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (parent_id) REFERENCES comments(id)
);
