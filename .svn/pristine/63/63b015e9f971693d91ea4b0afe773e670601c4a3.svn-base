DROP FUNCTION IF EXISTS function_sum_marks();
/* Function summ all marks for each faculty and student */
CREATE FUNCTION function_sum_marks()
  RETURNS VOID AS $sum_marks$
DECLARE
  entrant_id_new  INTEGER;
  sum_of_marks    DOUBLE PRECISION;
  entrant_row     Entrants%ROWTYPE;
  marks_row       Marks%ROWTYPE;
  extra_marks_row Extra_marks%ROWTYPE;
BEGIN
  FOR entrant_id_new IN SELECT id
                        FROM Entrants
  LOOP
    sum_of_marks = 0;
    FOR marks_row IN SELECT *
                     FROM Marks
                     WHERE entrant_id = entrant_id_new
    LOOP
      sum_of_marks = sum_of_marks + marks_row.mark_value;
    END LOOP;

    FOR extra_marks_row IN SELECT *
                           FROM Extra_Marks
                           WHERE entrant_id = entrant_id_new
    LOOP
      sum_of_marks = sum_of_marks + extra_marks_row.certificate_points + extra_marks_row.extra_points;
    END LOOP;
    UPDATE Faculties_Entrants
    SET sum_marks = sum_of_marks
    WHERE entrant_id = entrant_id_new;
  END LOOP;
  RETURN;
END;
$sum_marks$ LANGUAGE plpgsql;