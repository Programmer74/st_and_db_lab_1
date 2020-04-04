CREATE TABLE discipline
(
    id              SERIAL NOT NULL PRIMARY KEY,
    university_name TEXT   NOT NULL,
    discipline_name TEXT   NOT NULL,
    study_form      TEXT   NOT NULL,
    faculty         TEXT   NOT NULL,
    speciality      TEXT   NOT NULL,
    semester        INT    NOT NULL,
    lection_hours   INT    NOT NULL,
    practice_hours  INT    NOT NULL,
    lab_hours       INT    NOT NULL,
    is_exam         BOOLEAN -- decided not to use enums though postgres&hibernate supports them
);

CREATE TABLE assessment
(
    id            SERIAL NOT NULL PRIMARY KEY,
    discipline_id BIGINT NOT NULL,
    score         INT    NOT NULL,
    date          BIGINT NOT NULL, -- ISO TIMESTAMP
    teacher_name  TEXT   NOT NULL,
    teacher_id    INT    NOT NULL,
    student_name  TEXT   NOT NULL,
    student_i     INT    NOT NULL
);

ALTER TABLE assessment
    ADD CONSTRAINT fk_assessment_to_discipline
        FOREIGN KEY (discipline_id)
            REFERENCES discipline (id);