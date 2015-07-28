DROP TABLE IF EXISTS Roles CASCADE;
DROP TABLE IF EXISTS Users CASCADE;
DROP TABLE IF EXISTS Faculties_Subjects;
DROP TABLE IF EXISTS Faculties_Entrants;
DROP TABLE IF EXISTS Entrants_final_sheets;
DROP TABLE IF EXISTS Marks;
DROP TABLE IF EXISTS Extra_Marks;
DROP TABLE IF EXISTS Entrants;
DROP TABLE IF EXISTS Faculties;
DROP TABLE IF EXISTS Subjects;


CREATE TABLE Roles (
  id   INTEGER NOT NULL,
  name TEXT    NOT NULL,
  CONSTRAINT roles_pk PRIMARY KEY (id)
);

CREATE TABLE Users (
  id         SERIAL  NOT NULL,
  password   TEXT    NOT NULL,
  first_name TEXT    NOT NULL,
  last_name  TEXT    NOT NULL,
  patronymic TEXT    NOT NULL,
  "e-mail"   TEXT    NOT NULL UNIQUE,
  role_id    INTEGER NOT NULL,
  CONSTRAINT users_pk PRIMARY KEY (id),
  CONSTRAINT role_id_fk FOREIGN KEY (role_id)
  REFERENCES Roles (id) ON DELETE CASCADE
);

CREATE TABLE Entrants (
  id                        SERIAL  NOT NULL,
  city                      TEXT    NOT NULL,
  region                    TEXT    NOT NULL,
  school                    INTEGER NOT NULL,
  without_competitive_entry BOOLEAN DEFAULT FALSE,
  user_id                   INTEGER NOT NULL UNIQUE,
  CONSTRAINT entrants_pk PRIMARY KEY (id),
  CONSTRAINT user_id_fk FOREIGN KEY (user_id)
  REFERENCES Users (id) ON DELETE CASCADE
);

CREATE TABLE Extra_Marks (
  entrant_id         INTEGER NOT NULL UNIQUE,
  certificate_points DOUBLE PRECISION CHECK (certificate_points > 0),
  extra_points       DOUBLE PRECISION DEFAULT 0 CHECK (extra_points > -1 AND extra_points < 21),
  CONSTRAINT entrant_id_fk_extra_marks FOREIGN KEY (entrant_id)
  REFERENCES Entrants (id) ON DELETE CASCADE
);

CREATE TABLE Faculties (
  id           SERIAL  NOT NULL,
  name         TEXT    NOT NULL UNIQUE,
  total_spots  INTEGER NOT NULL,
  budget_spots INTEGER NOT NULL,
  CONSTRAINT total_hier_budget_not_zero CHECK (budget_spots > 0 AND total_spots > budget_spots),
  CONSTRAINT faculties_pk PRIMARY KEY (id)
);

CREATE TABLE Subjects (
  id   SERIAL NOT NULL,
  name TEXT   NOT NULL UNIQUE,
  CONSTRAINT subjects_pk PRIMARY KEY (id)
);

CREATE TABLE Faculties_Subjects (
  faculty_id INTEGER REFERENCES Faculties (id),
  subject_id INTEGER REFERENCES Subjects (id)
);

CREATE TABLE Faculties_Entrants (
  faculty_id INTEGER REFERENCES Faculties (id),
  entrant_id INTEGER REFERENCES Entrants (id),
  priority   INTEGER CHECK (priority > 0 AND priority < 4),
  sum_marks  DOUBLE PRECISION DEFAULT 0
);

CREATE TABLE Marks (
  subject_id INTEGER          NOT NULL,
  entrant_id INTEGER          NOT NULL,
  mark_value DOUBLE PRECISION NOT NULL CHECK (mark_value > 0),
  CONSTRAINT subject_id_fk FOREIGN KEY (subject_id)
  REFERENCES Subjects (id),
  CONSTRAINT entrant_id_fk FOREIGN KEY (entrant_id)
  REFERENCES Entrants (id)
);

CREATE TABLE Entrants_final_sheets (
  faculty_id      INTEGER NOT NULL,
  entrant_id      INTEGER NOT NULL,
  passed          BOOLEAN NOT NULL,
  number_of_sheet INTEGER NOT NULL,
  CONSTRAINT facult_id_fk FOREIGN KEY (faculty_id)
  REFERENCES Faculties (id),
  CONSTRAINT entrant_id_fk FOREIGN KEY (entrant_id)
  REFERENCES Entrants (id)
);
