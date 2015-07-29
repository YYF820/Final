DROP FUNCTION IF EXISTS clean_users_and_faculty_entrants_after_delete_entrant();
DROP TRIGGER IF EXISTS trigger_clean_users_and_faculty_entrants_after_delete_entrant ON Entrants;

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