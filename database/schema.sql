use powerpark;
create table user (
	user_id int primary key auto_increment,
    username varchar(20),
    password varchar(20),
    email varchar(50),
    phone_num varchar(15),
    address varchar(100)
);

create table admin(
	admin_id int primary key auto_increment,
    name varchar(50),
    password varchar(50),
    email varchar(50),
    phone_num varchar(50)
);
create table charging_station(
	station_id int primary key auto_increment,
	station_name varchar(30),
    location varchar(100),
    latitude varchar(10),
    longitude varchar(10),
    charger_type varchar(10),
    total_slots int,
    available_slots int
);

create table slots(
    slot_id int primary key auto_increment,
    station_id int,
    status varchar(15),
    start_time time,
    end_time time,
    constraint slot_stat_id_fk foreign key (station_id) references charging_station(station_id)
);
create table booking(
	booking_id int primary key auto_increment,
    user_id int,
    station_id int,
    slot_id int,
    booking_date date,
    constraint bk_user_id_fk foreign key (user_id) references user(user_id),
    constraint bk_stat_id_fk foreign key (station_id) references charging_station(station_id),
    constraint bk_slot_id_fk foreign key (slot_id) references slots(slot_id)
);
create table invoice(
	invoice_id int primary key auto_increment,
    booking_id int,
    amount int,
    status varchar(15),
    constraint invoice_bkid_fk foreign key (booking_id) references booking(booking_id)
);

