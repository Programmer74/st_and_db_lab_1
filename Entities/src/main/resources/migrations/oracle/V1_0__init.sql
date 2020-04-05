CREATE TABLE person
(
    id                  NUMBER GENERATED ALWAYS AS IDENTITY,
    name                VARCHAR2(255) NOT NULL,
    birth_date          TIMESTAMP     NOT NULL,
    birth_place         VARCHAR2(255) NOT NULL,
    faculty             VARCHAR2(255) NOT NULL,
    position            VARCHAR2(255) NOT NULL,
    is_contract_student NUMBER        NOT NULL,
    contract_from       TIMESTAMP     NOT NULL,
    contract_to         TIMESTAMP     NOT NULL,
    group_id            NUMBER
);

CREATE TABLE study_group
(
    id            NUMBER GENERATED ALWAYS AS IDENTITY,
    name          VARCHAR2(255)  NOT NULL,
    study_form    VARCHAR2(255)  NOT NULL,
    school        VARCHAR2(1024) NOT NULL,
    speciality    VARCHAR2(1024) NOT NULL,
    qualification VARCHAR2(255)  NOT NULL,
    study_year    VARCHAR2(255)  NOT NULL
);

CREATE TABLE lesson_entry
(
    id         NUMBER GENERATED ALWAYS AS IDENTITY,
    name       VARCHAR2(255) NOT NULL,
    teacher_id NUMBER,
    weekday    NUMBER,
    hour       NUMBER,
    minute     NUMBER,
    room       VARCHAR2(255)
);

CREATE TABLE assessment
(
    id          NUMBER GENERATED ALWAYS AS IDENTITY,
    name        VARCHAR2(255) NOT NULL,
    mark        NUMBER,
    mark_letter VARCHAR2(255),
    achieved_at TIMESTAMP
);