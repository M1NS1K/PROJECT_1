CREATE TABLE member (
                        id INT PRIMARY KEY,
                        name VARCHAR(100),
                        email VARCHAR(100),
                        joined_date TIMESTAMP
);

INSERT INTO member (id, name, email, joined_date) VALUES (1, '홍길동', 'hong@example.com', CURRENT_TIMESTAMP);
INSERT INTO member (id, name, email, joined_date) VALUES (2, '김철수', 'kim@example.com', CURRENT_TIMESTAMP);
INSERT INTO member (id, name, email, joined_date) VALUES (3, '이영희', 'lee@example.com', CURRENT_TIMESTAMP);
