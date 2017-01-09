# Users schema

# --- !Ups
CREATE TABLE Events (
    id IDENTITY PRIMARY KEY,
    device_id VARCHAR(255) NOT NULL
);

# --- !Downs
DROP TABLE Events;