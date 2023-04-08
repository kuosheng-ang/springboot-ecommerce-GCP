

--
-- Table structure for table `admin`
--

--
--
--
INSERT INTO users (user_id, username, firstname, lastname, email, password, role) VALUES (1001, 'kuosheng', 'kuo', 'sheng', 'kuo@net.com','p@ssw0rd','USER')
--
--
INSERT INTO products (product_id, category_id, description, in_stock_number, in_stock_quantity, price, slug, updated_at)
VALUES (1, 1, 'The Invitation is a 2022 American horror thriller film directed by Jessica M. Thompson and written by Blair Butler. The film stars Nathalie Emmanuel and Thomas Doherty. Inspired by the novel Dracula by Bram Stoker, the film follows a young woman who, following her mothers death, meets long-lost family members for the first time, only to discover the dark secrets they carry with them', '60244887', 15, '17.50','horror',NOW())
--
INSERT INTO categories (category_id,name,slug, sorting)  VALUES (1, 'horror','horror',0)
--
INSERT INTO categories (category_id,name,slug, sorting ) VALUES (2, 'sci-fiction','sci-fiction',0)
--
INSERT INTO categories (category_id,name,slug, sorting ) VALUES (3, 'comics','comics',0)
--
INSERT INTO products (product_id, category_id, description, in_stock_number,  in_stock_quantity,price, slug, updated_at)
VALUES (2, 1, 'The Invitation is a 2022 American horror thriller film directed by Jessica M. Thompson and written by Blair Butler. The film stars Nathalie Emmanuel and Thomas Doherty. Inspired by the novel Dracula by Bram Stoker, the film follows a young woman who, following her mothers death, meets long-lost family members for the first time, only to discover the dark secrets they carry with them', '61244387', 15, '17.50','horror',NOW())
--
INSERT INTO products (product_id, category_id, description, in_stock_number, in_stock_quantity, price, slug, updated_at)
VALUES (3, 1,'A young couple struggling to stay together, when they are offered an amazing deal on a home with a questionable past that would normally be beyond their means. In a final attempt to start fresh as a couple they take the deal',
'67254120', 20,'21.80','horror',NOW())
--
--INSERT into pages (id, content, slug, sorting, title)
--VALUES (1,'movies','sci-fi',0,'Star Wars')
--
--INSERT into pages (id, content, slug, sorting, title)
--VALUES (2,'movies','horror',0,'Rotten Tomatoes')
--
--INSERT into pages (id, content, slug, sorting, title)
--VALUES (3,'movies','sci-fi',0,'Lightyear')
--
--INSERT into pages (id, content, slug, sorting, title)
--VALUES (4,'books','comics',0,'Manga')

--INSERT into pages (id, content, slug, sorting, title)
--VALUES (5,'books','home',0,'Manga')

--INSERT into admin (id, password, username) VALUES (1,'p@ssw0rd', 'admin')
--
insert into role (role_id, role_desc) VALUES (1, 'admin')
--
insert into role (role_id, role_desc) VALUES (2, 'user')
--
--insert into role (role_id, role_desc) VALUES (3, 'members')
--
--insert into admin (id, password, username) VALUES (1, 'p@ssw0rd', 'admin')
--
INSERT INTO products (product_id, category_id, description, in_stock_number, in_stock_quantity, price, slug, updated_at)
VALUES (4, 2,'A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.',
'60254920', 25,'18.90','sci-fic',NOW())

INSERT into role (id, role_desc) VALUES (1,'ROLE_ADMIN')
INSERT into role (id, role_desc) VALUES (2,'ROLE_USER')

INSERT into roles_privileges (role_id, privilege_id) VALUES (1 ,1)
INSERT into roles_privileges (role_id, privilege_id) VALUES (1 ,2)
INSERT into roles_privileges (role_id, privilege_id) VALUES (1 ,3)

INSERT into roles_privileges (role_id, privilege_id) VALUES (2 ,1)
INSERT into roles_privileges (role_id, privilege_id) VALUES (2 ,3)