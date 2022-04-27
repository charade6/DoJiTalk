create database dojidb;
use dojidb;

CREATE TABLE member (
 uname       VARCHAR(30) not null,
 uemail     VARCHAR(30) not null,
 password    VARCHAR(30) not null, 
  PRIMARY KEY(uemail)
) ENGINE=MYISAM CHARSET=utf8;

INSERT INTO MEMBER VALUES('장지원','a','a');
insert into MEMBER values('김도현','b','b');
insert into MEMBER values('엄인섭','c','c');
insert into MEMBER values('양승호','d','d');
insert into MEMBER values('졸려유','asdf','asdf');
insert into MEMBER values('헬로','asas','asas');
insert into MEMBER values('오이','asd1','asd1');

create table friendList(
 userEmail varchar(30),
 friendEmail varchar(30),
   foreign key (userEmail)
   references member (uemail),
   foreign key (friendEmail)
   references member (uemail)
) ENGINE=MYISAM CHARSET=utf8;

insert into friendList values('a','b');
insert into friendList values('a','c');
insert into friendList values('a','d');

insert into friendList values('b','a');
insert into friendList values('b','c');
insert into friendList values('b','d');

insert into friendList values('c','b');
insert into friendList values('c','a');
insert into friendList values('c','d');

insert into friendList values('d','b');
insert into friendList values('d','c');
insert into friendList values('d','a');
