INSERT INTO users (username, password, enabled) VALUES ('admin@boeks.io', '{bcrypt}$2a$10$E3mPTZb50e7sSW15fDx8Ne7hDZpfDjrmMPTTUp8wVjLTu.G5oPYCO', 1);
INSERT INTO users (username, password, enabled) VALUES ('marten@boeks.io', '{bcrypt}$2a$10$5VWqjwoMYnFRTTmbWCRZT.iY3WW8ny27kQuUL9yPK1/WJcPcBLFWO', 1);
INSERT INTO users (username, password, enabled) VALUES ('jdoe@boeks.io', '{bcrypt}$2a$10$cFKh0.XCUOA9L.in5smIiO2QIOT8.6ufQSwIIC.AVz26WctxhSWC6', 0);

INSERT INTO authorities (username, authority) VALUES ('admin@books.io', 'ADMIN');
INSERT INTO authorities (username, authority) VALUES ('admin@books.io', 'USER');
INSERT INTO authorities (username, authority) VALUES ('marten@books.io', 'USER');
INSERT INTO authorities (username, authority) VALUES ('jdoe@books.io', 'USER');
