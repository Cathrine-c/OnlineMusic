drop database if exists `musicserver`;
create database if not exists `musicserver` character set utf8mb4;

use musicserver;

drop table if exists `music`;
create table `music`(
  `id` int primary key auto_increment,
  `title` varchar(50) not null comment '歌名',
  `singer` varchar(30) not null comment '歌手名字',
  `time` varchar(20) not null ,
  `url` varchar(100) not null,
  `userid` int(20) not null
);


drop table if exists `user`;
create table `user`(
  `id` int primary key auto_increment,
  `username` varchar(20) not null,
  `password` varchar(35) not null,
  `age` int not null,
 `gender` varchar(2) not null,
  `email` varchar(50) not null
);

drop table if exists `lovermusic`;
create table `lovermusic`(
`id` int primary key auto_increment,
`user_id` int(11) not null,
`music_id` int(11) not null
);


insert into lovermusic(user_id,music_id) values
(1,2),
(2,3),
(1,4);

insert into music(title,singer,time,url,userid) values
('Alan Walker,Au／Ra,Tomine Harket - Darkside','Alan Walker','1980-2-4','music\\Alan Walker,Au／Ra,Tomine Harket - Darkside',2),
('Stay With Me（翻自 Punch／朴灿烈）','KJIN','1989-9-3','music\\Stay With Me（翻自 Punch／朴灿烈）',1),
('分身情人','汪苏泷','2000-10-24','music\\分身情人',1),
('红装','徐良','2000-1-4','music\\红装',1),
('北京东路的日子','群星','2008-3-31','music\\北京东路的日子',1),
('Faded','无名','2005-4-4','music\\Faded',2);
('我想牵着你的手','许嵩','2008-9-18','music\\我想牵着你的手',2),
('Faded','AlanWalker','2008-9-24','music\\Faded',2);


('烟火里的尘埃','华晨宇','2012-10-30','烟火里的尘埃',1),
('红玫瑰','陈奕迅','2008-1-30','红玫瑰',2),
('Fade','Alan Walker','2000-5-20','music\\Fade',1),
('偏爱','小鬼','2008-9-24','music\\偏爱',2),



insert into user(username,password,age,gender,email) values
('丫丫','520','18','女','5201314@qq.com'),
('珍妮','123','20','女','123000@qq.com');





