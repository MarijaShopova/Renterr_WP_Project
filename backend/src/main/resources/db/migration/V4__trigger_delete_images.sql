CREATE OR REPLACE FUNCTION delete_profile_picture()
    RETURNS trigger AS
$$
BEGIN
    DELETE FROM image WHERE id = OLD.image_id;
    RETURN NULL;
END;
$$
    LANGUAGE 'plpgsql';

CREATE TRIGGER on_delete_account
    AFTER DELETE
    ON account
    FOR EACH ROW
EXECUTE PROCEDURE delete_profile_picture();

-----------------------------------------------------------------------

CREATE OR REPLACE FUNCTION delete_main_apartment_image()
    RETURNS trigger AS
$$
BEGIN
    DELETE FROM image WHERE id = OLD.image_id;
    RETURN NULL;
END;
$$
    LANGUAGE 'plpgsql';

CREATE TRIGGER on_delete_apartment
    AFTER DELETE
    ON apartment
    FOR EACH ROW
EXECUTE PROCEDURE delete_main_apartment_image();

-----------------------------------------------------------------------

CREATE OR REPLACE FUNCTION delete_apartment_image()
    RETURNS trigger AS
$$
BEGIN
    DELETE FROM image WHERE id = OLD.image_id;
    RETURN NULL;
END;
$$
    LANGUAGE 'plpgsql';

CREATE TRIGGER on_delete_apartment
    AFTER DELETE
    ON apartment_image
    FOR EACH ROW
EXECUTE PROCEDURE delete_apartment_image();
