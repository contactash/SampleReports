create database college;
use college;
create table student (name varchar(50), id int(30), freetext varchar(80), subject varchar(100));
insert into student values ("john", 1, "some comments here", "C#");
insert into student values ("Jane", 2, "some comments here", "Java");
insert into student values ("Doug", 2, "some comments here", "JavaScript");
insert into student values ("Mike", 2, "some comments here", "Coffeescript");
commit;