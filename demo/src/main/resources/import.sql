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
