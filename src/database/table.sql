CREATE DATABASE noobcloud;

CREATE TABLE folder (
    id serial primary key,
    name varchar(255)
);

CREATE TABLE file (
    id serial primary key,
    name varchar(255),
    date date,
    size decimal(10,2),
    idFolder int,
    FOREIGN KEY (idFolder) REFERENCES folder(id) ON DELETE CASCADE
); 