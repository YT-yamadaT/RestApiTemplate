DELETE FROM users;

INSERT INTO users(user_id, email, user_name, password, status, role) 
--パスワード -> pass
VALUES('yamadaT', 'yamada@gmail.com', 'やまだT', '{bcrypt}$2a$10$y.wTqBxZzPlSjChDuF7kuuHlOynB6E8xDpq2cexeZ76vFOoTtELO.', '1', 'GENERAL'),
('admin', 'admin@gmail.com', 'ADMIN', '{bcrypt}$2a$10$y.wTqBxZzPlSjChDuF7kuuHlOynB6E8xDpq2cexeZ76vFOoTtELO.', '1', 'ADMIN');

