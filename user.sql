INSERT INTO users (username, password) VALUES ('Darius', 'password123');
INSERT INTO users (username, password) VALUES ('Ionut', 'password123');
INSERT INTO users (username, password) VALUES ('AndreiV', 'password123');
INSERT INTO users (username, password) VALUES ('AndreiR', 'password123');
INSERT INTO users (username, password) VALUES ('Melih', 'password123');

-- Obține ID-urile utilizatorilor
SET @Darius_id = (SELECT id FROM users WHERE username = 'Darius');
SET @AndreiV_id = (SELECT id FROM users WHERE username = 'AndreiV');
SET @AndreiR_id = (SELECT id FROM users WHERE username = 'AndreiR');
SET @Ionut_id = (SELECT id FROM users WHERE username = 'Ionut');
SET @Melih_id = (SELECT id FROM users WHERE username = 'Melih');

-- Adaugă contactele cu statusul 1 (ACCEPTED)
INSERT INTO contacts (sender_id, receiver_id, status) VALUES (@Darius_id, @AndreiV_id, 2);
INSERT INTO contacts (sender_id, receiver_id, status) VALUES (@Darius_id, @AndreiR_id, 2);

INSERT INTO contacts (sender_id, receiver_id, status) VALUES (@Melih_id, @AndreiR_id, 2);
INSERT INTO contacts (sender_id, receiver_id, status) VALUES (@Melih_id, @Ionut_id, 2);

INSERT INTO contacts (sender_id, receiver_id, status) VALUES (@AndreiR_id, @Ionut_id, 2);
INSERT INTO contacts (sender_id, receiver_id, status) VALUES (@AndreiR_id, @AndreiV_id, 2);
