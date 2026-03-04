
INSERT INTO sample (id, name) VALUES (1, 't1');
INSERT INTO users (user_id, name, email, password, role, createdAt) VALUES (1, 'user', 'user@example.com', '$2a$10$y.g.somehashhere', 'USER', NOW());
INSERT INTO users (user_id, name, email, password, role, createdAt) VALUES (2, 'newuser', 'newuser@example.com', '$2a$10$abcdefghijklmnopqrstuvwxy.abcdefghijklmnopqrstuvwxy', 'USER', NOW());
INSERT INTO users (user_id, name, email, password, role, createdAt) VALUES (3, 'easyuser', 'easyuser@example.com', '$2a$10$e.g.somehashhere.someotherhashhere', 'USER', NOW());

INSERT INTO boards (board_id, name) VALUES (1, '자유게시판');
INSERT INTO boards (board_id, name) VALUES (2, '공지사항');

INSERT INTO posts (post_id, title, body, user_id, board_id, createdAt) VALUES (1, '제목1', '내용1', 1, 1, NOW());
INSERT INTO posts (post_id, title, body, user_id, board_id, createdAt) VALUES (2, '제목2', '내용2', 1, 1, NOW());
INSERT INTO posts (post_id, title, body, user_id, board_id, createdAt) VALUES (3, '제목3', '내용3', 2, 1, NOW());
INSERT INTO posts (post_id, title, body, user_id, board_id, createdAt) VALUES (4, '공지1', '공지내용1', 1, 2, NOW());
INSERT INTO posts (post_id, title, body, user_id, board_id, createdAt) VALUES (5, '공지2', '공지내용2', 1, 2, NOW());

INSERT INTO comments (comment_id, content, post_id, user_id, parent_id, createdAt) VALUES (1, '댓글1', 1, 2, null, NOW());
INSERT INTO comments (comment_id, content, post_id, user_id, parent_id, createdAt) VALUES (2, '댓글2', 1, 3, null, NOW());
INSERT INTO comments (comment_id, content, post_id, user_id, parent_id, createdAt) VALUES (3, '대댓글1', 1, 1, 1, NOW());
INSERT INTO comments (comment_id, content, post_id, user_id, parent_id, createdAt) VALUES (4, '댓글3', 2, 2, null, NOW());
INSERT INTO comments (comment_id, content, post_id, user_id, parent_id, createdAt) VALUES (5, '댓글4', 2, 3, null, NOW());
INSERT INTO comments (comment_id, content, post_id, user_id, parent_id, createdAt) VALUES (6, '대댓글2', 2, 1, 4, NOW());
INSERT INTO comments (comment_id, content, post_id, user_id, parent_id, createdAt) VALUES (7, '댓글5', 3, 1, null, NOW());
INSERT INTO comments (id, content, post_id, user_id, parent_id, createdAt) VALUES (8, '대댓글3', 3, 2, 7, NOW());
INSERT INTO comments (comment_id, content, post_id, user_id, parent_id, createdAt) VALUES (9, '댓글6', 4, 2, null, NOW());
INSERT INTO comments (comment_id, content, post_id, user_id, parent_id, createdAt) VALUES (10, '댓글7', 5, 3, null, NOW());
