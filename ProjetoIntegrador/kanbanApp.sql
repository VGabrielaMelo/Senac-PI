CREATE DATABASE KanbanApp;
USE KanbanApp;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL
);

CREATE TABLE notes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    title VARCHAR(100),
    description TEXT,
    status VARCHAR(20) DEFAULT 'TODO',
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE post_its (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL
);


INSERT INTO users (username,password) VALUES ("Gabriela" , 1234);

