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

create table characterTalents (
	characterID int unsigned,
    talentID int,
    primary key (characterID, talentID),
    foreign key (characterID) references characters(characterID) on delete cascade);
    
insert into characterTalents values
(1, 7),
(1, 37);

/* 
select * from users;
select * from characters;

select currentCharacter from users where name = 'Sam';


update characters set 
	name = "newName", level = 4, maxHP = 13, currHP = 13, strength = 14, intellect = 2, willpower = 4, awareness = 9, presence = 1 where characterID = 1;

update users set
	currentCharacter = 2 where name = "test 2";
    
select characterID from characters where userName = "Sam";
delete from characters where characterID = 1;

*/