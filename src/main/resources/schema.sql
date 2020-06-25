/*
서버 시작시 스프링에서 자동생성
schema.sql – (DDL) 스키마를 초기화할때 (예 : 테이블 및 종속성 생성).
data.sql – (DML)
*/
DROP TABLE IF EXISTS sprinkle;

CREATE TABLE  sprinkle( conSeq INT PRIMARY KEY AUTO_INCREMENT
, a VARCHAR
, b VARCHAR
, c DATE
, d VARCHAR
, e INT
, f TIMESTAMP DEFAULT CURRENT_TIMESTAMP
, g TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


INSERT INTO sprinkle (a, b, c, d, e) VALUES ('B','ym',now(),'1,2,3,',0);
INSERT INTO sprinkle (a, b, c, d, e) VALUES ('B','ym',now(),'5,6,',0);
INSERT INTO sprinkle (a, b, c, d, e) VALUES ('C','ym2',now(),'9,10',0);
INSERT INTO sprinkle (a, b, c, d, e) VALUES ('B','ym2','2019-11-24','1,2,3,4',0);