CREATE TABLE conference_participant
(
    conference_id INT NOT NULL,
    person_id     INT NOT NULL,
    PRIMARY KEY(conference_id, person_id)
);

CREATE TABLE publication_author
(
    publication_id INT NOT NULL,
    person_id      INT NOT NULL,
    PRIMARY KEY(publication_id, person_id)
);

CREATE TABLE project_worker
(
    project_id INT NOT NULL,
    person_id  INT NOT NULL,
    PRIMARY KEY(project_id, person_id)
);