-- liquibase formatted sql

-- changeset oalekseenko:1
CREATE TABLE animal (
                        id bigint generated by default as identity primary key,
                        "name" varchar(50) not null ,
                        animal_type varchar(10) not null

);
CREATE TABLE person (
                        id bigint generated by default as identity primary key,
                        "name" varchar(50) not null,
                        email varchar(50) not null,
                        phone varchar(20) not null,
                        address varchar(100) not null,
                        chat_id bigint not null,
                        animal_id bigint unique references animal(id),
                        animal_adopt_date date,
                        last_report_date date
);
CREATE TABLE report (
                        id bigint generated by default as identity primary key,
                        photo_path varchar not null,
                        photo_size int not null,
                        media_type varchar not null,
                        photo_data bytea not null,
                        description text not null,
                        report_date date not null,
                        person_id bigint references person(id)
);

-- changeset gabriel:1
CREATE TABLE person_cat (
                        id bigint generated by default as identity primary key,
                        "name" varchar(50) not null,
                        email varchar(50) not null,
                        phone varchar(20) not null,
                        address varchar(100) not null,
                        chat_id bigint not null,
                        animal_id bigint unique references animal(id),
                        animal_adopt_date date,
                        last_report_date date
);

ALTER TABLE report
    add column person_cat_id bigint references person_cat (id);

-- changeset gabriel:2
CREATE TABLE volunteer (
                        id bigint generated by default as identity primary key,
                        "name" varchar (50) not null,
                        email varchar(50) not null,
                        phone varchar(20) not null

)