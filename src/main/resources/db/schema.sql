--Tables
--ユーザテーブル
--status -> 1:有効 2:ロック 3:無効
CREATE TABLE IF NOT EXISTS users(
	user_id VARCHAR(255) PRIMARY KEY NOT NULL,
	email VARCHAR(255) CHECK(email LIKE '%_@_%') NOT NULL UNIQUE,
	user_name VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	status CHAR(1) CHECK(status IN('1', '2', '3')),
	role VARCHAR(7) CHECK(role IN('ADMIN', 'GENERAL')),
	created_at TIMESTAMP NOT NULL DEFAULT NOW(),
	updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
	password_error_count INTEGER NOT NULL DEFAULT 0 CHECK(password_error_count >= 0 AND password_error_count <= 5)
);

CREATE VIEW users_view AS SELECT
	user_id,
	email,
	user_name,
	status
	FROM users;