use OT2
go
-- User --
INSERT INTO users (role, user_name, email, password)
VALUES ('User', 'user1', 'user1@example.com', 'password1'),
       ('Admin', 'admin1', 'admin1@example.com', 'password2'),
       ('User', 'user2', 'user2@example.com', 'password3'),
       ('Admin', 'admin2', 'admin2@example.com', 'password4'),
       ('User', 'user3', 'user3@example.com', 'password5'),
       ('Admin', 'admin3', 'admin3@example.com', 'password6'),
       ('User', 'user4', 'user4@example.com', 'password7'),
       ('Admin', 'admin4', 'admin4@example.com', 'password8'),
       ('User', 'user5', 'user5@example.com', 'password9'),
       ('Admin', 'admin5', 'admin5@example.com', 'password10');

-- post --
INSERT INTO posts (post_id, approved_date, status, contents, likes, shares, title, views, admin_name, user_name)
VALUES 
('post1', '2024-05-01', 'PENDING', 'Contents of post 1', 10, 5, 'Title of post 1', 20, NULL, 'user1'),
('post2', '2024-05-02', 'APPROVED', 'Contents of post 2', 15, 8, 'Title of post 2', 30, NULL, 'user2'),
('post3', '2024-05-03', 'REJECTED', 'Contents of post 3', 20, 10, 'Title of post 3', 40, NULL, 'user3'),
('post4', '2024-05-04', 'PENDING', 'Contents of post 4', 25, 12, 'Title of post 4', 50, NULL, 'user4'),
('post5', '2024-05-05', 'APPROVED', 'Contents of post 5', 30, 15, 'Title of post 5', 60, NULL, 'user5'),
('post6', '2024-05-06', 'REJECTED', 'Contents of post 6', 35, 18, 'Title of post 6', 70, 'admin1', NULL),
('post7', '2024-05-07', 'PENDING', 'Contents of post 7', 40, 20, 'Title of post 7', 80, NULL, 'user5'),
('post8', '2024-05-08', 'APPROVED', 'Contents of post 8', 45, 22, 'Title of post 8', 90, NULL, 'user3'),
('post9', '2024-05-09', 'REJECTED', 'Contents of post 9', 50, 25, 'Title of post 9', 100, 'admin3', NULL),
('post10', '2024-05-10', 'PENDING', 'Contents of post 10', 55, 28, 'Title of post 10', 110, NULL, 'user4'),
('post11', '2024-05-11', 'PENDING', 'Contents of post 11', 60, 30, 'Title of post 11', 120, 'admin1', NULL),
('post12', '2024-05-12', 'APPROVED', 'Contents of post 12', 65, 32, 'Title of post 12', 130, 'admin2', NULL),
('post13', '2024-05-13', 'REJECTED', 'Contents of post 13', 70, 35, 'Title of post 13', 140, 'admin3', NULL),
('post14', '2024-05-14', 'PENDING', 'Contents of post 14', 75, 38, 'Title of post 14', 150, 'admin4', NULL),
('post15', '2024-05-15', 'APPROVED', 'Contents of post 15', 80, 40, 'Title of post 15', 160, 'admin5', NULL);

-- comment --
INSERT INTO comments (comment_date, contents, likes, user_name, post_id)
VALUES 
('2024-05-01 08:00:00', 'Comment 1', 5, 'user1', 'post1'),
('2024-05-02 09:00:00', 'Comment 2', 7, 'user2', 'post2'),
('2024-05-03 10:00:00', 'Comment 3', 8, 'user3', 'post3'),
('2024-05-04 11:00:00', 'Comment 4', 9, 'user4', 'post4'),
('2024-05-05 12:00:00', 'Comment 5', 10, 'user5', 'post5'),
('2024-05-06 13:00:00', 'Comment 6', 12, 'user1', 'post6'),
('2024-05-07 14:00:00', 'Comment 7', 15, 'user5', 'post7'),
('2024-05-08 15:00:00', 'Comment 8', 17, 'user4', 'post8'),
('2024-05-09 16:00:00', 'Comment 9', 20, 'user1', 'post9'),
('2024-05-10 17:00:00', 'Comment 10', 22, 'admin1', 'post10'),
('2024-05-20 08:30:00', 'Admin comment 1', 6, 'admin1', 'post12'),
('2024-05-12 09:30:00', 'Admin comment 2', 8, 'admin2', 'post2'),
('2024-05-13 10:30:00', 'Admin comment 3', 9, 'admin3', 'post3'),
('2024-05-14 11:30:00', 'Admin comment 4', 10, 'admin4', 'post4'),
('2024-05-18 12:30:00', 'Admin comment 5', 11, 'admin5', 'post15');


select * from posts
select * from users
select * from comments


select ad.user_name, count(p.post_id) from users ad 
join posts p on ad.user_name = p.user_name
group by ad.user_name

select p.post_id, count(c.comment_date) from posts p join comments c on p.post_id = c.post_id
group by p.post_id

select * from posts
where year(approved_date) = 2024 and status = 'PENDING'

SELECT post_id, title, likes
FROM posts
group by post_id, title, likes
having likes >=all (SELECT likes FROM posts);




  