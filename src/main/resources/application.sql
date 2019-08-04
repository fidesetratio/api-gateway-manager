create table application(
	appId int not null primary key auto_increment,
	applicationName varchar(255) not null default '',
	description varchar(255) not null default '',
	context varchar(255) not null default '',
	photo varchar(255) not null default '',
	permitAll int not null default 0,
	roleCategoryId int not null default 0,
	providerId int not null default 0		
);