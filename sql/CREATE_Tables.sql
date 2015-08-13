DROP TABLE IF EXISTS Roles CASCADE;
DROP TABLE IF EXISTS Entrants_Statuses CASCADE;
DROP TABLE IF EXISTS Users CASCADE;
DROP TABLE IF EXISTS Faculties_Subjects;
DROP TABLE IF EXISTS Faculties_Entrants;
DROP TABLE IF EXISTS Enter_University_Status CASCADE;
DROP TABLE IF EXISTS Entrants_final_sheets;
DROP TABLE IF EXISTS Marks;
DROP TABLE IF EXISTS Extra_Marks;
DROP TABLE IF EXISTS Entrants;
DROP TABLE IF EXISTS Faculties CASCADE;
DROP TABLE IF EXISTS Subjects;


CREATE TABLE Roles (
  id   INTEGER NOT NULL,
  name TEXT    NOT NULL,
  CONSTRAINT roles_pk PRIMARY KEY (id)
);

CREATE TABLE Entrants_Statuses (
  id   INTEGER NOT NULL,
  name TEXT    NOT NULL,
  CONSTRAINT entrants_status_pk PRIMARY KEY (id)
);


CREATE TABLE Users (
  id         SERIAL  NOT NULL,
  password   TEXT    NOT NULL,
  first_name TEXT    NOT NULL,
  last_name  TEXT    NOT NULL,
  patronymic TEXT    NOT NULL,
  email      TEXT    NOT NULL UNIQUE,
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
  entrant_status_id         INTEGER DEFAULT 3,
  user_id                   INTEGER NOT NULL UNIQUE,
  CONSTRAINT entrants_pk PRIMARY KEY (id),
  CONSTRAINT entrant_id_fk FOREIGN KEY (entrant_status_id)
  REFERENCES Entrants_Statuses (id) ON DELETE CASCADE,
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
  faculty_id INTEGER NOT NULL,
  subject_id INTEGER NOT NULL,
  CONSTRAINT faculty_id_fk_faculties_subjects FOREIGN KEY (faculty_id)
  REFERENCES Faculties (id) ON DELETE CASCADE,
  CONSTRAINT subject_id_fk_faculties_subjects FOREIGN KEY (subject_id)
  REFERENCES Subjects (id) ON DELETE CASCADE
);

CREATE TABLE Faculties_Entrants (
  faculty_id INTEGER NOT NULL,
  entrant_id INTEGER NOT NULL,
  priority   INTEGER CHECK (priority > 0 AND priority < 4),
  sum_marks  DOUBLE PRECISION DEFAULT 0,
  CONSTRAINT faculty_id_fk_faculties_entrants FOREIGN KEY (faculty_id)
  REFERENCES Faculties (id) ON DELETE CASCADE,
  CONSTRAINT entrant_id_fk_faculties_entrants FOREIGN KEY (entrant_id)
  REFERENCES Entrants (id) ON DELETE CASCADE
);

CREATE TABLE Marks (
  subject_id INTEGER          NOT NULL,
  entrant_id INTEGER          NOT NULL,
  mark_value DOUBLE PRECISION NOT NULL CHECK (mark_value > 0),
  CONSTRAINT subject_id_fk_marks FOREIGN KEY (subject_id)
  REFERENCES Subjects (id) ON DELETE CASCADE,
  CONSTRAINT entrant_id_fk_marks FOREIGN KEY (entrant_id)
  REFERENCES Entrants (id) ON DELETE CASCADE
);

CREATE TABLE Enter_University_Status (
  id   INTEGER NOT NULL,
  name TEXT    NOT NULL,
  CONSTRAINT enter_university_status_pk PRIMARY KEY (id)
);

CREATE TABLE Entrants_Final_Sheets (
  faculty_id                 INTEGER NOT NULL,
  entrant_id                 INTEGER NOT NULL,
  enter_university_status_id INTEGER NOT NULL,
  number_of_sheet            INTEGER NOT NULL,
  CONSTRAINT faculty_id_fk_sheet FOREIGN KEY (faculty_id)
  REFERENCES Faculties (id) ON DELETE CASCADE,
  CONSTRAINT entrant_id_fk_sheet FOREIGN KEY (entrant_id)
  REFERENCES Entrants (id) ON DELETE CASCADE,
  CONSTRAINT enter_university_status_fk FOREIGN KEY (enter_university_status_id)
  REFERENCES Enter_university_status (id) ON DELETE CASCADE
);


