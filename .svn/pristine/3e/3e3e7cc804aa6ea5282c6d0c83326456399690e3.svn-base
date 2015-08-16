DROP FUNCTION IF EXISTS function_select_faculties_subjects_more_than_three();
CREATE FUNCTION function_select_faculties_subjects_more_than_three()
  RETURNS SETOF Faculties AS $faculties$
DECLARE
  faculty_row            Faculties%ROWTYPE;
  faculty_result_row     Faculties%ROWTYPE;
  faculties_subjects_row Faculties_Subjects%ROWTYPE;
  counter                INTEGER;
  faculty_id_remember    INTEGER;
BEGIN
  FOR faculty_row IN SELECT *
                     FROM Faculties
  LOOP
    counter := 0;
    FOR faculties_subjects_row IN SELECT *
                                  FROM Faculties_Subjects
                                  WHERE faculty_row.id = Faculties_Subjects.faculty_id

    LOOP
      counter := counter + 1;
      faculty_id_remember := faculty_row.id;
    END LOOP;
    IF counter = 3 OR counter > 3
    THEN
      RETURN NEXT faculty_row;
    END IF;
  END LOOP;
END;
$faculties$ LANGUAGE plpgsql;