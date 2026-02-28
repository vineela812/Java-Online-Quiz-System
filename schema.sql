create database jdbc_db;
use jdbc_db;
create table logins(
		username varchar(50) unique not null,
        password varchar(50) not null
);

insert into logins(username,password) values
('vineela','vineela123'),
('nani','nani123');

select * from logins;

drop table logins;

create table questions(
	id int auto_increment primary key,
    question varchar(300),
    opt1 varchar(100),
    opt2 varchar(100),
    opt3 varchar(100),
    opt4 varchar(100),
    correct_ans varchar(100)
);

insert into questions(question,opt1,opt2,opt3,opt4,correct_ans) values
('Java is ?', 'Language','OS','Browser','Game','Language'),
('JDBC stands for?', 'Java DB Conn','Java DataBase Connectivity','Joint DB Conn','None','Java DataBase Connectivity'),
('Which keyword is used to inherit a class in Java?','this','super','extends','implements','extends'),
('Which method is the entry point of a Java program?','start()','run()','main()','init()','main()'),
('Which collection does not allow duplicate values?','List','Set','Map','ArrayList','Set');

select * from questions;

drop table questions;

create table results(
	id int auto_increment primary key,
    username varchar(50),
    score int
);

select*from results;
drop table results;