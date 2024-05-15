INSERT INTO member (id, username, password) VALUES (1, 'admin@boeks.io', '{noop}secret');
INSERT INTO member (id, username, password) VALUES (2, 'marten@boeks.io', '{noop}user');
INSERT INTO member (id, username, password) VALUES (3, 'jdoe@boeks.io', '{noop}unknown');

INSERT INTO member_role (member_id, role) VALUES (1, 'ROLE_ADMIN');
INSERT INTO member_role (member_id, role) VALUES (1, 'ROLE_USER');
INSERT INTO member_role (member_id, role) VALUES (2, 'ROLE_USER');
