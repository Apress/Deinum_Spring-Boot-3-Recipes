INSERT INTO users (username, password, enabled) VALUES ('admin@boeks.io', '{noop}secret', 1);
INSERT INTO users (username, password, enabled) VALUES ('marten@boeks.io', '{noop}user', 1);
INSERT INTO users (username, password, enabled) VALUES ('jdoe@boeks.io', '{noop}unknown', 0);

INSERT INTO authorities (username, authority) VALUES ('admin@books.io', 'ADMIN');
INSERT INTO authorities (username, authority) VALUES ('admin@books.io', 'USER');
INSERT INTO authorities (username, authority) VALUES ('marten@books.io', 'USER');
INSERT INTO authorities (username, authority) VALUES ('jdoe@books.io', 'USER');
