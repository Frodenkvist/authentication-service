CREATE TABLE person(
  personnummer TEXT NOT NULL PRIMARY KEY
);

CREATE TABLE permission(
  permission_id SERIAL NOT NULL PRIMARY KEY,
  name TEXT NOT NULL,
  personnummer TEXT REFERENCES person (personnummer)
);