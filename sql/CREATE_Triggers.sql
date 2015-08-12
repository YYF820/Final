DROP TRIGGER IF EXISTS trigger_clean_users_and_faculty_entrants_after_delete_entrant ON Entrants;
DROP FUNCTION IF EXISTS clean_users_and_faculty_entrants_after_delete_entrant();

DROP TRIGGER IF EXISTS trigger_check_priority ON Faculties_Entrants;
DROP FUNCTION IF EXISTS function_check_priority();

CREATE FUNCTION clean_users_and_faculty_entrants_after_delete_entrant()
  RETURNS TRIGGER AS $$
DECLARE
  user_id_old INTEGER;
BEGIN
  user_id_old = OLD.user_id;

  DELETE FROM Users
  WHERE id = user_id_old;

  RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_clean_users_and_faculty_entrants_after_delete_entrant
AFTER DELETE ON Entrants FOR EACH ROW
EXECUTE PROCEDURE clean_users_and_faculty_entrants_after_delete_entrant();

CREATE FUNCTION function_check_priority()
  RETURNS TRIGGER AS $check_priority$
DECLARE
  priority_new           INTEGER;
  entrant_id_new         INTEGER;
  faculties_entrants_row Faculties_Entrants%ROWTYPE;
BEGIN
  priority_new = NEW.priority;
  entrant_id_new = NEW.entrant_id;

  FOR faculties_entrants_row IN SELECT *
                                FROM Faculties_Entrants fe
                                WHERE fe.entrant_id = entrant_id_new
  LOOP
    IF priority_new = faculties_entrants_row.priority
    THEN
      RAISE EXCEPTION 'Duplicate priority --> priority = %, Entrant id = %', priority_new, entrant_id_new
      USING HINT = 'Please check priority';
    END IF;
  END LOOP;
  RETURN NEW;
END;
$check_priority$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_check_priority BEFORE INSERT ON Faculties_Entrants
FOR EACH ROW EXECUTE PROCEDURE function_check_priority();