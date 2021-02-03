CREATE TABLE user (
	id			BIGINT NOT NULL,
	email		VARCHAR(255),
	password 	VARCHAR(255),
	first_name	VARCHAR(255),
	last_name	VARCHAR(255),
	created_at 	DATETIME,
	updated_at 	DATETIME,
	PRIMARY KEY (id)
);

CREATE TABLE generator_table (
	USER_KEY		VARCHAR(255),
	USER_KEY_NEXT	BIGINT
);