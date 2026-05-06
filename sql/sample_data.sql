USE vinyl_store;

INSERT INTO artist (name) VALUES
('Pink Floyd'),
('Miles Davis'),
('The Beatles'),
('Daft Punk'),
('Nirvana'),
('Amy Winehouse'),
('Michael Jackson'),
('David Bowie'),
('Adele'),
('Queen'),
('Bob Marley');

INSERT INTO vinyl (title, id_artist, genre, release_year, price, quantity, is_for_sale, is_sold, is_out_of_stock, is_reserved) VALUES
('The Dark Side of the Moon', 1, 'Progressive Rock', 1973, 29.99, 50, 1, 0, 0, 0),
('Wish You Were Here', 1, 'Progressive Rock', 1975, 28.99, 45, 1, 0, 0, 0),
('The Wall', 1, 'Progressive Rock', 1979, 34.99, 30, 1, 0, 0, 0),
('Kind of Blue', 2, 'Modal Jazz', 1959, 24.99, 60, 1, 0, 0, 0),
('Bitches Brew', 2, 'Jazz Fusion', 1970, 26.99, 35, 1, 0, 0, 0),
('Abbey Road', 3, 'Rock', 1969, 32.99, 40, 1, 0, 0, 0),
('Let It Be', 3, 'Rock', 1970, 27.99, 38, 1, 0, 0, 0),
('Nevermind', 5, 'Grunge', 1991, 27.99, 45, 1, 0, 0, 0),
('In Utero', 5, 'Grunge', 1993, 26.99, 35, 1, 0, 0, 0),
('Back to Black', 6, 'Soul', 2006, 26.99, 42, 1, 0, 0, 0),
('Frank', 6, 'Soul', 2003, 23.99, 30, 1, 0, 0, 0),
('Thriller', 7, 'Pop', 1982, 29.99, 55, 1, 0, 0, 0),
('Bad', 7, 'Pop', 1987, 28.99, 45, 1, 0, 0, 0),
('A Night at the Opera', 10, 'Rock', 1975, 31.99, 25, 1, 0, 0, 0),
('A Day at the Races', 10, 'Rock', 1976, 29.99, 28, 1, 0, 0, 0),
('Random Access Memories', 4, 'Electronic', 2013, 33.99, 45, 1, 0, 0, 0),
('Discovery', 4, 'Electronic', 2001, 29.99, 38, 1, 0, 0, 0),
('Legends', 11, 'Reggae', 1984, 24.99, 60, 1, 0, 0, 0),
('Exodus', 11, 'Reggae', 1977, 25.99, 40, 1, 0, 0, 0),
('21', 9, 'Pop', 2011, 27.99, 50, 1, 0, 0, 0),
('25', 9, 'Pop', 2015, 28.99, 48, 1, 0, 0, 0),
('The Rise and Fall of Ziggy Stardust', 8, 'Rock', 1972, 26.99, 35, 1, 0, 0, 0);

INSERT INTO users (name, email, password, is_admin) VALUES
('Admin User', 'admin@vinylstore.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 1),
('John Doe', 'john.doe@gmail.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 0),
('Jane Smith', 'jane.smith@hotmail.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 0),
('Mike Wilson', 'mike.wilson@yahoo.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 0),
('Sarah Jones', 'sarah.jones@outlook.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 0);

INSERT INTO sale (id_user, total_amount) VALUES
(2, 57.98),
(2, 29.99),
(3, 83.97),
(4, 82.97),
(5, 67.98);

INSERT INTO sale_items (id_sale, id_vinyl, quantity, unit_price) VALUES
(1, 1, 1, 29.99),
(1, 4, 1, 27.99),
(2, 16, 1, 29.99),
(3, 10, 1, 26.99),
(3, 11, 1, 23.99),
(3, 12, 1, 29.99),
(4, 6, 1, 32.99),
(4, 7, 1, 27.99),
(4, 14, 1, 31.99),
(5, 19, 1, 25.99),
(5, 20, 1, 27.99),
(5, 21, 1, 28.99);
