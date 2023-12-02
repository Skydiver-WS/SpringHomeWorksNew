CREATE SCHEMA IF NOT EXISTS list_students;
CREATE TABLE IF NOT EXISTS list_students.students
(
    id          BIGINT PRIMARY KEY,
    first_name  VARCHAR(255) NOT NULL,
    last_name   VARCHAR(255) NOT NULL,
    email       VARCHAR(255) NOT NULL,
    tell_number VARCHAR(255) NOT NULL
);