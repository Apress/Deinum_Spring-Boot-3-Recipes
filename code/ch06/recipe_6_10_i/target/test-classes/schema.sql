DROP TABLE IF EXISTS customer;

CREATE TABLE customer (
  id IDENTITY PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(255) NOT NULL,
  UNIQUE(name)
);