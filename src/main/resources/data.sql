INSERT INTO tinder_user (email, nickname, gender, attraction, passion) VALUES ('josep@tecnocampus.cat', 'El crack', 2, 3, 0);
INSERT INTO tinder_user (email, nickname, gender, attraction, passion) VALUES ('jordi@tecnocampus.cat', 'The best', 0, 3, 0);
INSERT INTO tinder_user (email, nickname, gender, attraction, passion) VALUES ('maria@tecnocampus.cat', 'Maria', 1, 0, 0);
INSERT INTO tinder_user (email, nickname, gender, attraction, passion) VALUES ('marta@tecnocampus.cat', 'Marta', 1, 0, 0);
INSERT INTO tinder_user (email, nickname, gender, attraction, passion) VALUES ('pepe@tecnocampus.cat', 'Pepeillo', 0, 3, 0);
INSERT INTO tinder_user (email, nickname, gender, attraction, passion) VALUES ('sonia@tecnocampus.cat', 'Somia', 1, 3, 0);

INSERT INTO tinder_like(origin_email, target_email, creation_date, matched) VALUES('josep@tecnocampus.cat', 'jordi@tecnocampus.cat', current_date(), false);
INSERT INTO tinder_like(origin_email, target_email, creation_date, matched) VALUES('josep@tecnocampus.cat', 'maria@tecnocampus.cat', current_date(), false);
INSERT INTO tinder_like(origin_email, target_email, creation_date, matched) VALUES('josep@tecnocampus.cat', 'marta@tecnocampus.cat', current_date(), false);
INSERT INTO tinder_like(origin_email, target_email, creation_date, matched) VALUES('jordi@tecnocampus.cat', 'maria@tecnocampus.cat', current_date(), false);
INSERT INTO tinder_like(origin_email, target_email, creation_date, matched) VALUES('jordi@tecnocampus.cat', 'marta@tecnocampus.cat', current_date(), false);
INSERT INTO tinder_like(origin_email, target_email, creation_date, matched) VALUES('marta@tecnocampus.cat', 'pepe@tecnocampus.cat', current_date(), false);
//INSERT INTO tinder_like(origin_email, target_email, creation_date) select id, (select id from tinder_user where email = 'pepe@tecnocampus.cat'), current_date() from tinder_user where email = 'marta@tecnocampus.cat';

