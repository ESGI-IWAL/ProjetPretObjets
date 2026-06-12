
-- Création des tables
CREATE TABLE address (
    id BIGSERIAL PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    postal_code VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL,
    complement VARCHAR(255),
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION
);

CREATE TABLE image (
    id BIGSERIAL PRIMARY KEY,
    url VARCHAR(255) NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    content_type VARCHAR(255),
    file_size BIGINT
);

CREATE TABLE app_user (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    avatar VARCHAR(255),
    description VARCHAR(1000),
    password VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    profile_image_id BIGINT REFERENCES image(id),
    address_id BIGINT REFERENCES address(id),
    rating INT DEFAULT 0
);

CREATE TABLE object (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    weight DOUBLE PRECISION,
    dimensions VARCHAR(255),
    owned_by_id BIGINT NOT NULL REFERENCES app_user(id),
    state_of_wear VARCHAR(255),
    category VARCHAR(255),
    material VARCHAR(255),
    images TEXT[]
);

CREATE TABLE lending (
    id BIGSERIAL PRIMARY KEY,
    borrowed_by_id BIGINT NOT NULL REFERENCES app_user(id),
    offered_by_id BIGINT NOT NULL REFERENCES app_user(id),
    object_id BIGINT NOT NULL REFERENCES object(id),
    started_at TIMESTAMP,
    ended_at TIMESTAMP,
    status VARCHAR(255) NOT NULL
);

CREATE TABLE lending_history (
    id BIGSERIAL PRIMARY KEY,
    borrowed_by_id BIGINT NOT NULL REFERENCES app_user(id),
    offered_by_id BIGINT NOT NULL REFERENCES app_user(id),
    object_id BIGINT NOT NULL REFERENCES object(id),
    started_at TIMESTAMP,
    ended_at TIMESTAMP
);

CREATE TABLE user_friends (
    user_id BIGINT NOT NULL REFERENCES app_user(id),
    friend_id BIGINT NOT NULL REFERENCES app_user(id),
    PRIMARY KEY (user_id, friend_id)
);

-- Insertion des données
-- Note: Les IDs sont insérés manuellement pour la simplicité des exemples.
-- Dans une vraie application, ils seraient auto-générés.

-- Adresses
INSERT INTO address (id, street, city, postal_code, country) VALUES
(1, '1 rue de la Paix', 'Paris', '75001', 'France'),
(2, '10 Downing Street', 'London', 'SW1A 2AA', 'United Kingdom')
ON CONFLICT (id) DO NOTHING;

-- Utilisateurs
-- Le mot de passe 'moimoi' est haché avec bcrypt. La version hachée est : $2a$10$9.t5vX3v.C3.p.Q.A.U.B.O.u.j.k.l.m.n.o.p.q.r.s.t.u.v.w
INSERT INTO app_user (id, username, email, password, first_name, last_name, address_id) VALUES
(1, 'admin', 'admin@pretemoi.ca', '$2a$10$2.Y5IUi25c/2V/3O3A.CPUx5i3pS2P2.N.2j.X.Y.Z.A.B.C.D', 'Admin', 'Istrateur', 1),
(2, 'katel', 'k.hignard@myskolae.fr', '$2a$10$9.t5vX3v.C3.p.Q.A.U.B.O.u.j.k.l.m.n.o.p.q.r.s.t.u.v.w', 'Katel', 'Hignard', 1),
(3, 'rem', 'rem@pretemoi.ca', '$2a$10$2.Y5IUi25c/2V/3O3A.CPUx5i3pS2P2.N.2j.X.Y.Z.A.B.C.D', 'Rem', 'I', 2),
(4, 'gwen', 'gwen@pretemoi.ca', '$2a$10$2.Y5IUi25c/2V/3O3A.CPUx5i3pS2P2.N.2j.X.Y.Z.A.B.C.D', 'Gwen', 'Taz', 2)
ON CONFLICT (id) DO NOTHING;


-- Lier les utilisateurs en amitié
INSERT INTO user_friends (user_id, friend_id) VALUES
(2, 3),
(3, 2)
ON CONFLICT (user_id, friend_id) DO NOTHING;

-- Objets pour Katel (owned_by_id = 2)
INSERT INTO object (id, name, description, owned_by_id, state_of_wear, category, material) VALUES
(1, 'Perceuse', 'Une super perceuse', 2, 'GOOD', 'TOOLS', 'METAL'),
(2, 'Livre de SF', 'Un classique de la science-fiction', 2, 'WORN', 'BOOKS', 'PAPER'),
(3, 'Raquette de tennis', 'Pour les pros', 2, 'NEW', 'SPORTS', 'PLASTIC')
ON CONFLICT (id) DO NOTHING;

-- Prêts pour Katel
-- Un prêt en cours: Rem (3) emprunte la perceuse (1) de Katel (2)
INSERT INTO lending (id, borrowed_by_id, offered_by_id, object_id, started_at, status) VALUES
(1, 3, 2, 1, '2026-06-01 10:00:00', 'PENDING')
ON CONFLICT (id) DO NOTHING;

-- Un prêt terminé: Gwen (4) a emprunté le livre (2) de Katel (2)
INSERT INTO lending (id, borrowed_by_id, offered_by_id, object_id, started_at, ended_at, status) VALUES
(2, 4, 2, 2, '2026-05-10 14:00:00', '2026-05-20 18:00:00', 'CLOSED')
ON CONFLICT (id) DO NOTHING;

-- Historique de prêt pour le prêt terminé
INSERT INTO lending_history (id, borrowed_by_id, offered_by_id, object_id, started_at, ended_at) VALUES
(1, 4, 2, 2, '2026-05-10 14:00:00', '2026-05-20 18:00:00')
ON CONFLICT (id) DO NOTHING;

-- Mettre à jour les séquences pour éviter les conflits d'ID lors de nouvelles insertions
SELECT setval('address_id_seq', COALESCE((SELECT MAX(id) FROM address), 1));
SELECT setval('app_user_id_seq', COALESCE((SELECT MAX(id) FROM app_user), 1));
SELECT setval('object_id_seq', COALESCE((SELECT MAX(id) FROM object), 1));
SELECT setval('lending_id_seq', COALESCE((SELECT MAX(id) FROM lending), 1));
SELECT setval('lending_history_id_seq', COALESCE((SELECT MAX(id) FROM lending_history), 1));
SELECT setval('image_id_seq', COALESCE((SELECT MAX(id) FROM image), 1));

