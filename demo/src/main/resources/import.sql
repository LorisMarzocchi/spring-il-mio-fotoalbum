INSERT INTO roles (id, name) VALUES(1, 'ADMIN');
INSERT INTO roles (id, name) VALUES(2, 'SUPER_ADMIN');

INSERT INTO users (email, first_name, last_name, registered_at, password) VALUES('john@email.com', 'John', 'Doe', '2023-11-20 10:35', '{noop}john');
INSERT INTO users (email, first_name, last_name, registered_at, password) VALUES('jane@email.com', 'Jane', 'Smith', '2023-11-20 10:35','{noop}jane');
INSERT INTO users (email, first_name, last_name, registered_at, password) VALUES('pippo@email.com', 'David', 'Brown', '2023-11-20 10:35', '{noop}david');
INSERT INTO users (email, first_name, last_name, registered_at, password) VALUES('mimmo@email.com', 'Nicole', 'Anderson', '2023-11-20 10:35', '{noop}nicole');

INSERT INTO users_roles (user_id, roles_id) VALUES(1, 2);
INSERT INTO users_roles (user_id, roles_id) VALUES(2, 1);
INSERT INTO users_roles (user_id, roles_id) VALUES(3, 1);
INSERT INTO users_roles (user_id, roles_id) VALUES(4, 1);
-- Insert photos
--INSERT INTO photos (titolo, descrizione, visible, created_at, user_id) VALUES ('Alba in montagna', 'Una bellissima alba vista dalla montagna', true, '2023-11-09 15:45:00', 1);
--INSERT INTO photos (titolo, descrizione, visible, created_at, user_id) VALUES ('Vista della città di notte', 'Panoramica notturna della città illuminata', true, '2023-11-09 15:45:00', 1);
--INSERT INTO photos (titolo, descrizione, visible, created_at, user_id) VALUES ('Ritratto in bianco e nero', 'Ritratto artistico in bianco e nero', true, '2023-11-09 15:45:00', 1);
--INSERT INTO photos (titolo, descrizione, visible, created_at, user_id) VALUES ('Ritratto SDFSDF', 'Ritratto artistico in bianco e nero', true, '2023-11-09 15:45:00', 1);
--INSERT INTO photos (titolo, descrizione, visible, created_at, user_id) VALUES ('Ritratto 123455', 'Ritratto artistico in bianco e nero', true, '2023-11-09 15:45:00', 2);
--INSERT INTO photos (titolo, descrizione, visible, created_at, user_id) VALUES ('Ritratto DDDDDD', 'Ritratto artistico in bianco e nero', true, '2023-11-09 15:45:00', 2);
-- Insert categories
INSERT INTO categories (name) VALUES ('Natura');
INSERT INTO categories (name) VALUES ('Città');
INSERT INTO categories (name) VALUES ('Persone');
INSERT INTO categories (name) VALUES ('Ritratti');
INSERT INTO categories (name) VALUES ('Arte');
INSERT INTO categories (name) VALUES ('Animali');


---- Insert photo-category relationships
INSERT INTO photos_categories (photos_id, categories_id) VALUES (1, 1);
INSERT INTO photos_categories (photos_id, categories_id) VALUES (2, 2);
INSERT INTO photos_categories (photos_id, categories_id) VALUES (3, 3);
INSERT INTO photos_categories (photos_id, categories_id) VALUES (4, 1);
INSERT INTO photos_categories (photos_id, categories_id) VALUES (5, 2);
INSERT INTO photos_categories (photos_id, categories_id) VALUES (6, 3);


INSERT INTO contact (name, email, message) VALUES ("Peppe", "peppe@email.com", "ciao ciao ciao ciao ciao");
INSERT INTO contact (name, email, message) VALUES ("Pino", "pino@email.com", "ciao ciao ciao ciao ciao ciao ciao ciao ciao");
