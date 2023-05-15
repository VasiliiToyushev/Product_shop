CREATE TABLE products (id serial, title varchar(100), price numeric(6, 2), views int DEFAULT(0));

INSERT INTO products (title, price)
VALUES
('Product #1', 10),
('Product #2', 20),
('Product #3', 30),
('Product #4', 40),
('Product #5', 50),
('Product #6', 60),
('Product #7', 70),
('Product #8', 80),
('Product #9', 40),
('Product #10', 30),
('Product #11', 20),
('Product #12', 30),
('Milk #1', 70),
('Milk #2', 80);

CREATE TABLE users (
 id         serial,
 username   varchar(50) NOT NULL,
 password   varchar(100) NOT NULL,
 name       varchar(100) NOT NULL,
 email      varchar(50) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE roles (
 id serial,
 name varchar(50) DEFAULT NULL,
 PRIMARY KEY (id)
);

CREATE TABLE users_roles (
 user_id int not null,
 role_id int not null,
 PRIMARY KEY (user_id, role_id),

 CONSTRAINT FK_USER_ID_01 FOREIGN KEY (user_id)
 REFERENCES users (id)
 ON DELETE NO ACTION ON UPDATE NO ACTION,

 CONSTRAINT FK_ROLE_ID FOREIGN KEY (role_id)
 REFERENCES roles (id)
 ON DELETE NO ACTION ON UPDATE NO ACTION
);

INSERT INTO roles (name)
VALUES
('ROLE_USER'), ('ROLE_ADMIN');

INSERT INTO users (username, password, name, email)
VALUES
('admin', '$2a$10$zhQ5mq.mil9VOT3.JYR5yuz/b9bU3H5p6WlRplKTMci12G5C6RWAu', 'Bob', 'bob@gmail.com'),
('user', '$2a$10$zhQ5mq.mil9VOT3.JYR5yuz/b9bU3H5p6WlRplKTMci12G5C6RWAu', 'Bil', 'bil@gmail.com');

INSERT INTO users_roles (user_id, role_id)
VALUES
(1, 1),
(1, 2),
(2, 1);


--CREATE TABLE users (
--    username VARCHAR(50) NOT NULL,
--    password VARCHAR(100) NOT NULL,
--    enabled boolean NOT NULL,
--    PRIMARY KEY (username)
--);
--
--INSERT INTO users
--VALUES
--('user1', '{noop}123', true),
--('user2', '{noop}123', true);
--
--CREATE TABLE authorities (
--    username varchar(50) NOT NULL,
--    authority varchar(50) NOT NULL,
--
--    CONSTRAINT authorities_idx UNIQUE (username, authority),
--
--    CONSTRAINT authorities_ibfk_1
--    FOREIGN KEY (username)
--    REFERENCES users (username)
--);
--
--INSERT INTO authorities
--VALUES
--('user1', 'ROLE_ADMIN'),
--('user1', 'ROLE_USER'),
--('user2', 'ROLE_USER');