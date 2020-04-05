CREATE TABLE person
(
    id       INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name     VARCHAR(255) NOT NULL,
    position VARCHAR(255) NOT NULL
);

CREATE TABLE conference
(
    id    INT           NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name  VARCHAR(255)  NOT NULL,
    place VARCHAR(255)  NOT NULL,
    date  BIGINT SIGNED NOT NULL
);

CREATE TABLE publication
(
    id          INT           NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(255)  NOT NULL,
    type        VARCHAR(255)  NOT NULL,
    language    VARCHAR(255)  NOT NULL,
    source      VARCHAR(255)  NOT NULL,
    pages       INT           NOT NULL,
    source_type VARCHAR(255)  NOT NULL,
    quote_index INT           NOT NULL,
    date        BIGINT SIGNED NOT NULL
);

CREATE TABLE project
(
    id        INT           NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name      VARCHAR(255)  NOT NULL,
    date_from BIGINT SIGNED NOT NULL,
    date_to   BIGINT SIGNED NOT NULL
);

CREATE TABLE book_taken
(
    id          INT           NOT NULL PRIMARY KEY AUTO_INCREMENT,
    book_name   VARCHAR(255)  NOT NULL,
    taken_by    INT           NOT NULL,
    taken_at    BIGINT SIGNED NOT NULL,
    returned_at BIGINT SIGNED NOT NULL
)