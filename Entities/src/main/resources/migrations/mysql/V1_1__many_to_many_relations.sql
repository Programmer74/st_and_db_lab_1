CREATE TABLE conference_participant
(
    conference_id INT NOT NULL,
    person_id     INT NOT NULL,
    PRIMARY KEY(conference_id, person_id)
);