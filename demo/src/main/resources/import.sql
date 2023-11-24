-- Insert photos
INSERT INTO photos (titolo, descrizione, url_image, visible, created_at) VALUES ('Alba in montagna', 'Una bellissima alba vista dalla montagna', 'https://picsum.photos/id/1023/200/300', true, '2023-11-09 15:45:00');
INSERT INTO photos (titolo, descrizione, url_image, visible, created_at) VALUES ('Vista della città di notte', 'Panoramica notturna della città illuminata', 'https://picsum.photos/id/1033/200/300', true, '2023-11-09 15:45:00');
INSERT INTO photos (titolo, descrizione, url_image, visible, created_at) VALUES ('Ritratto in bianco e nero', 'Ritratto artistico in bianco e nero', 'https://picsum.photos/id/1003/200/300', true, '2023-11-09 15:45:00');
-- Insert categories
INSERT INTO categories (name) VALUES ('Natura');
INSERT INTO categories (name) VALUES ('Città');
INSERT INTO categories (name) VALUES ('Persone');


---- Insert photo-category relationships
INSERT INTO photos_categories (photos_id, categories_id) VALUES (1, 1);
INSERT INTO photos_categories (photos_id, categories_id) VALUES (2, 2);
INSERT INTO photos_categories (photos_id, categories_id) VALUES (3, 3);

INSERT INTO roles (id, name) VALUES(1, 'ADMIN');
INSERT INTO roles (id, name) VALUES(2, 'USER');

INSERT INTO users (email, first_name, last_name, registered_at, password) VALUES('john@email.com', 'John', 'Doe', '2023-11-20 10:35', '{noop}john');
INSERT INTO users (email, first_name, last_name, registered_at, password) VALUES('jane@email.com', 'Jane', 'Smith', '2023-11-20 10:35','{noop}jane');
INSERT INTO users (email, first_name, last_name, registered_at, password) VALUES('pippo@email.com', 'Pippo', 'Pazzo', '2023-11-20 10:35', '{noop}pippo');
INSERT INTO users (email, first_name, last_name, registered_at, password) VALUES('mimmo@email.com', 'Mimmo', 'Modem', '2023-11-20 10:35', '{noop}mimmo');

INSERT INTO users_roles (user_id, roles_id) VALUES(1, 1);
INSERT INTO users_roles (user_id, roles_id) VALUES(2, 1);
INSERT INTO users_roles (user_id, roles_id) VALUES(3, 1);
INSERT INTO users_roles (user_id, roles_id) VALUES(4, 1);