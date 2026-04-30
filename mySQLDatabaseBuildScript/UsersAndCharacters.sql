drop database if exists cosmererpgcharacters;

create database cosmererpgcharacters;
use cosmererpgcharacters;

create table users(
	name varchar(30) primary key,
    password varchar(30),
    currentCharacter int
);
create table characters(
	characterID int unsigned primary key auto_increment,
	userName varchar(30),
	name varchar(30),
    level tinyInt unsigned,
    maxHP Int unsigned,
    currHP Int unsigned,
    strength tinyInt unsigned,
    speed tinyInt unsigned,
    intellect tinyInt unsigned,
    willpower tinyInt unsigned,
    awareness tinyInt unsigned,
    presence tinyInt unsigned,
    inventory text,
    foreign key (userName) references users(name)
);
insert into users values
	("Sam", "Pass", 1);
insert into characters values 
(1, "Sam", "Gamidoi", 2, 10, 10, 1, 1, 1, 1, 1, 1, ""),
(2, "Sam", "qwerty", 5, 5, 17, 2, 2, 2, 2, 2, 2, ""),
(3, "Sam", "asdfg", 13, 7, 15, 4, 4, 4, 4, 4, 4, "");

/* 
select * from users;
select * from characters;

*/