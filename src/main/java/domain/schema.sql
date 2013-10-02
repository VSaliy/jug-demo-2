drop table COURSE if exists;

create table COURSE (
    ID integer identity primary key,
    NAME varchar(20) not null,
    NUMBER integer not null,
    LOCATION varchar(20) not null,
    START date not null,
    DURATION integer not null,
    PRICE decimal not null,
    DISCOUNT integer not null,
    unique(NUMBER));
