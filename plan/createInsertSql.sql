/* Create Tables */


CREATE TABLE check_io
(
        check_io_id number NOT NULL,
        resv_id number NOT NULL,
        check_in_time date,
        check_out_time date,
        PRIMARY KEY (check_io_id)
);




CREATE TABLE guest
(
        guest_id number NOT NULL,
        hotel_user_id number NOT NULL,
        guest_name varchar2(30) NOT NULL,
        guest_phone varchar2(30) NOT NULL,
        PRIMARY KEY (guest_id)
);


CREATE TABLE hotel_admin
(
	hotel_admin_id varchar2(20) NOT NULL,
	hotel_admin_pw varchar2(20) NOT NULL,
	hotel_admin_name varchar2(20) NOT NULL,
	PRIMARY KEY (hotel_admin_id)
);



CREATE TABLE hotel_user
(
        hotel_user_id number NOT NULL,
        is_guest number NOT NULL,
        PRIMARY KEY (hotel_user_id)
);




CREATE TABLE membership
(
        membership_id number NOT NULL,
        hotel_user_id number NOT NULL,
        membership_name varchar2(30) NOT NULL,
        membership_nick varchar2(30) NOT NULL,
        membership_pw number NOT NULL,
        membership_regdate date NOT NULL,
        membership_phone varchar2(30) NOT NULL,
        membership_email varchar2(30),
        membership_gender varchar2(10),
        membership_birthday date,
        PRIMARY KEY (membership_id)
);




CREATE TABLE resv
(
        resv_id number NOT NULL,
        hotel_user_id number NOT NULL,
        room_number number NOT NULL,
        resv_time date NOT NULL,
        end_time date NOT NULL,
		stay number NOT NULL,
       	resv_regtime date NOT NULL,
        PRIMARY KEY (resv_id)
);




CREATE TABLE room
(
        room_number number NOT NULL,
        room_option_id number NOT NULL,
        room_floor number NOT NULL,
        PRIMARY KEY (room_number)
);




CREATE TABLE room_option
(
        room_option_id number NOT NULL,
        room_option_name varchar2(30) NOT NULL,
        room_option_size number NOT NULL,
        room_option_bed varchar2(30) NOT NULL,
        room_option_view varchar2(30) NOT NULL,
        room_option_max number NOT NULL,
        room_option_img varchar2(30) NOT NULL,
        room_option_price number NOT NULL,
        PRIMARY KEY (room_option_id)
);




CREATE TABLE service
(
        service_id number NOT NULL,
        service_name varchar2(30) NOT NULL,
        service_img varchar2(30) NOT NULL,
        service_price number NOT NULL,
        PRIMARY KEY (service_id)
);




CREATE TABLE service_use
(
        service_use_id number NOT NULL,
        hotel_user_id number NOT NULL,
        service_id number NOT NULL,
        service_use_time date,
        PRIMARY KEY (service_use_id)
);






/* Create Foreign Keys */


ALTER TABLE guest
        ADD FOREIGN KEY (hotel_user_id)
        REFERENCES hotel_user (hotel_user_id)
;




ALTER TABLE membership
        ADD FOREIGN KEY (hotel_user_id)
        REFERENCES hotel_user (hotel_user_id)
;




ALTER TABLE resv
        ADD FOREIGN KEY (hotel_user_id)
        REFERENCES hotel_user (hotel_user_id)
;




ALTER TABLE service_use
        ADD FOREIGN KEY (hotel_user_id)
        REFERENCES hotel_user (hotel_user_id)
;




ALTER TABLE check_io
        ADD FOREIGN KEY (resv_id)
        REFERENCES resv (resv_id)
;




ALTER TABLE resv
        ADD FOREIGN KEY (room_number)
        REFERENCES room (room_number)
;




ALTER TABLE room
        ADD FOREIGN KEY (room_option_id)
        REFERENCES room_option (room_option_id)
;




ALTER TABLE service_use
        ADD FOREIGN KEY (service_id)
        REFERENCES service (service_id)
;

commit;



--1. service테이블 입력


create sequence seq_service
increment by 1
start with 1;


insert into service(service_id, service_name, service_img, service_price)
values(seq_service.nextval, '조식','breakfast.jpg',15000);


insert into service(service_id, service_name, service_img, service_price)
values(seq_service.nextval, '점심','lunch.jpg',20000);


insert into service(service_id, service_name, service_img, service_price)
values(seq_service.nextval, '저녁','dinner.jpg',30000);


insert into service(service_id, service_name, service_img, service_price)
values(seq_service.nextval, '맥주','beer.jpg',5000);


insert into service(service_id, service_name, service_img, service_price)
values(seq_service.nextval, '와인','wine.jpg',25000);


insert into service(service_id, service_name, service_img, service_price)
values(seq_service.nextval, '안주','snack.jpg',10000);






--2. hotel_user테이블 입력


create sequence seq_hotel_user
increment BY 1
start with 1;


insert into HOTEL_USER(hotel_user_id, is_guest) VALUES(seq_hotel_user.nextVal, 1);
insert into HOTEL_USER(hotel_user_id, is_guest) VALUES(seq_hotel_user.nextVal, 0);
insert into HOTEL_USER(hotel_user_id, is_guest) VALUES(seq_hotel_user.nextVal, 1);
insert into HOTEL_USER(hotel_user_id, is_guest) VALUES(seq_hotel_user.nextVal, 0);
insert into HOTEL_USER(hotel_user_id, is_guest) VALUES(seq_hotel_user.nextVal, 1);
insert into HOTEL_USER(hotel_user_id, is_guest) VALUES(seq_hotel_user.nextVal, 1);
insert into HOTEL_USER(hotel_user_id, is_guest) VALUES(seq_hotel_user.nextVal, 1);
insert into HOTEL_USER(hotel_user_id, is_guest) VALUES(seq_hotel_user.nextVal, 0);
insert into HOTEL_USER(hotel_user_id, is_guest) VALUES(seq_hotel_user.nextVal, 1);
insert into HOTEL_USER(hotel_user_id, is_guest) VALUES(seq_hotel_user.nextVal, 0);
insert into HOTEL_USER(hotel_user_id, is_guest) VALUES(seq_hotel_user.nextVal, 1);
insert into HOTEL_USER(hotel_user_id, is_guest) VALUES(seq_hotel_user.nextVal, 0);
insert into HOTEL_USER(hotel_user_id, is_guest) VALUES(seq_hotel_user.nextVal, 1);
insert into HOTEL_USER(hotel_user_id, is_guest) VALUES(seq_hotel_user.nextVal, 0);
insert into HOTEL_USER(hotel_user_id, is_guest) VALUES(seq_hotel_user.nextVal, 0);
insert into HOTEL_USER(hotel_user_id, is_guest) VALUES(seq_hotel_user.nextVal, 0);
insert into HOTEL_USER(hotel_user_id, is_guest) VALUES(seq_hotel_user.nextVal, 0);
insert into HOTEL_USER(hotel_user_id, is_guest) VALUES(seq_hotel_user.nextVal, 0);
insert into HOTEL_USER(hotel_user_id, is_guest) VALUES(seq_hotel_user.nextVal, 1);
insert into HOTEL_USER(hotel_user_id, is_guest) VALUES(seq_hotel_user.nextVal, 0);


--3. service_use테이블 입력
create sequence seq_service_use
increment by 1
start with 1;


insert into service_use(service_use_id,hotel_user_id,service_id,service_use_time)
values(seq_service_use.nextVal, 3, 3, to_date('2017-4-15-23-22-24', 'yyyy-mm-dd-hh24-mi-ss'));


insert into service_use(service_use_id,hotel_user_id,service_id,service_use_time)
values(seq_service_use.nextVal, 4, 3, to_date('2017-4-16-23-22-24', 'yyyy-mm-dd-hh24-mi-ss'));


insert into service_use(service_use_id,hotel_user_id,service_id,service_use_time)
values(seq_service_use.nextVal, 5, 2, to_date('2017-4-17-23-22-24', 'yyyy-mm-dd-hh24-mi-ss'));


insert into service_use(service_use_id,hotel_user_id,service_id,service_use_time)
values(seq_service_use.nextVal, 6, 4, to_date('2017-4-18-23-22-24', 'yyyy-mm-dd-hh24-mi-ss'));


insert into service_use(service_use_id,hotel_user_id,service_id,service_use_time)
values(seq_service_use.nextVal, 7, 3, to_date('2017-4-19-23-22-24', 'yyyy-mm-dd-hh24-mi-ss'));


insert into service_use(service_use_id,hotel_user_id,service_id,service_use_time)
values(seq_service_use.nextVal, 8, 1, to_date('2017-4-20-23-22-24', 'yyyy-mm-dd-hh24-mi-ss'));


insert into service_use(service_use_id,hotel_user_id,service_id,service_use_time)
values(seq_service_use.nextVal, 9, 3, to_date('2017-4-21-23-22-24', 'yyyy-mm-dd-hh24-mi-ss'));


insert into service_use(service_use_id,hotel_user_id,service_id,service_use_time)
values(seq_service_use.nextVal, 10, 5, to_date('2017-4-22-23-22-24', 'yyyy-mm-dd-hh24-mi-ss'));


insert into service_use(service_use_id,hotel_user_id,service_id,service_use_time)
values(seq_service_use.nextVal, 11, 6, to_date('2017-4-23-23-22-24', 'yyyy-mm-dd-hh24-mi-ss'));


insert into service_use(service_use_id,hotel_user_id,service_id,service_use_time)
values(seq_service_use.nextVal, 12, 2, to_date('2017-4-24-23-22-24', 'yyyy-mm-dd-hh24-mi-ss'));


insert into service_use(service_use_id,hotel_user_id,service_id,service_use_time)
values(seq_service_use.nextVal, 13, 1, to_date('2017-4-25-23-22-24', 'yyyy-mm-dd-hh24-mi-ss'));


insert into service_use(service_use_id,hotel_user_id,service_id,service_use_time)
values(seq_service_use.nextVal, 14, 4, to_date('2017-4-26-23-22-24', 'yyyy-mm-dd-hh24-mi-ss'));


insert into service_use(service_use_id,hotel_user_id,service_id,service_use_time)
values(seq_service_use.nextVal, 15, 2, to_date('2017-4-27-23-22-24', 'yyyy-mm-dd-hh24-mi-ss'));


insert into service_use(service_use_id,hotel_user_id,service_id,service_use_time)
values(seq_service_use.nextVal, 16, 5, to_date('2017-4-28-23-22-24', 'yyyy-mm-dd-hh24-mi-ss'));


insert into service_use(service_use_id,hotel_user_id,service_id,service_use_time)
values(seq_service_use.nextVal, 17, 1, to_date('2017-4-29-23-22-24', 'yyyy-mm-dd-hh24-mi-ss'));


insert into service_use(service_use_id,hotel_user_id,service_id,service_use_time)
values(seq_service_use.nextVal, 18, 2, to_date('2017-4-30-23-22-24', 'yyyy-mm-dd-hh24-mi-ss'));






--4. guest 테이블 입력


create sequence seq_guest_id
increment by 1
start with 1;


insert into guest(guest_id, hotel_user_id, guest_name, guest_phone) 
 values(seq_guest_id.nextval, 1, '김성현', '010-2222-2222');


insert into guest(guest_id, hotel_user_id, guest_name, guest_phone)
values(seq_guest_id.nextval, 3, '이상훈', '010-1234-4875');


insert into guest(guest_id, hotel_user_id, guest_name, guest_phone)
values(seq_guest_id.nextval, 5, '김정일', '010-2457-5798');


insert into guest(guest_id, hotel_user_id, guest_name, guest_phone)
values(seq_guest_id.nextval, 6, '소지섭', '010-2467-4567');


insert into guest(guest_id, hotel_user_id, guest_name, guest_phone)
values(seq_guest_id.nextval, 7, '송중기', '010-7789-4123');


insert into guest(guest_id, hotel_user_id, guest_name, guest_phone)
values(seq_guest_id.nextval, 9, '한지민', '010-4478-4523');


insert into guest(guest_id, hotel_user_id, guest_name, guest_phone)
values(seq_guest_id.nextval, 11, '한예슬', '010-3333-4444');


insert into guest(guest_id, hotel_user_id, guest_name, guest_phone)
values(seq_guest_id.nextval, 13, '이영애', '010-4751-4576');


insert into guest(guest_id, hotel_user_id, guest_name, guest_phone)
values(seq_guest_id.nextval, 19, '장나라', '010-1122-4475');






--5. membership 테이블 입력


create sequence seq_membership
increment BY 1
start with 1;


insert into membership
(membership_id, hotel_user_id, membership_name, membership_nick, membership_pw, membership_regdate, membership_phone, membership_email, membership_gender, membership_birthday)
values(seq_membership.nextval, 2, '이민정', 'minjung', 1234, to_date('2016-3-23-9-20-20', 'yyyy-mm-dd-hh24-mi-ss'), '010-2232-3111', 'minjung@gmail.com', '여', to_date('1991-4-13','yyyy-mm-dd'));


insert into membership
(membership_id, hotel_user_id, membership_name, membership_nick, membership_pw, membership_regdate, membership_phone, membership_email, membership_gender, membership_birthday)
values(seq_membership.nextval, 4, '민진호', 'zino', 1323, to_date('2016-3-23-22-20-20', 'yyyy-mm-dd-hh24-mi-ss'), '010-4456-7890', 'zino@gmail.com', '남', to_date('1990-3-28','yyyy-mm-dd'));


insert into membership
(membership_id, hotel_user_id, membership_name, membership_nick, membership_pw, membership_regdate, membership_phone, membership_email, membership_gender, membership_birthday)
values(seq_membership.nextval, 8, '강동원', 'kang', 2323, to_date('2016-7-2-11-18-00', 'yyyy-mm-dd-hh24-mi-ss'), '010-5556-1234', 'dongwon@gmail.com', '남', to_date('1984-5-5','yyyy-mm-dd'));


insert into membership
(membership_id, hotel_user_id, membership_name, membership_nick, membership_pw, membership_regdate, membership_phone, membership_email, membership_gender, membership_birthday)
values(seq_membership.nextval, 10, '오바마', 'usa', 4445, to_date('2016-7-21-20-30-25', 'yyyy-mm-dd-hh24-mi-ss'), '010-4528-1237', 'obama@gmail.com', '남', to_date('1950-10-12','yyyy-mm-dd'));


insert into membership
(membership_id, hotel_user_id, membership_name, membership_nick, membership_pw, membership_regdate, membership_phone, membership_email, membership_gender, membership_birthday)
values(seq_membership.nextval, 12, '김구', 'kimgoo', 6457, to_date('2016-8-11-11-30-43', 'yyyy-mm-dd-hh24-mi-ss'), '010-1254-1218', 'goo@naver.com', '남', to_date('1946-10-13','yyyy-mm-dd'));


insert into membership
(membership_id, hotel_user_id, membership_name, membership_nick, membership_pw, membership_regdate, membership_phone, membership_email, membership_gender, membership_birthday)
values(seq_membership.nextval, 14, '추미애', 'jobs', 5646, to_date('2016-8-23-11-22-20', 'yyyy-mm-dd-hh24-mi-ss'), '010-5554-1117', 'steve@daum.net', '여', to_date('1977-7-28','yyyy-mm-dd'));


insert into membership
(membership_id, hotel_user_id, membership_name, membership_nick, membership_pw, membership_regdate, membership_phone, membership_email, membership_gender, membership_birthday)
values(seq_membership.nextval, 14, '메이', 'may', 5441, to_date('2016-8-23-18-12-23', 'yyyy-mm-dd-hh24-mi-ss'), '010-1357-1547', 'hello@gmail.com', '여', to_date('1982-1-25','yyyy-mm-dd'));


insert into membership
(membership_id, hotel_user_id, membership_name, membership_nick, membership_pw, membership_regdate, membership_phone, membership_email, membership_gender, membership_birthday)
values(seq_membership.nextval, 16, '푸틴', 'putin', 2417, to_date('2016-10-13-22-23-27', 'yyyy-mm-dd-hh24-mi-ss'), '010-1398-4587', 'rus@hotmail.com', '남', to_date('1990-1-30','yyyy-mm-dd'));


insert into membership
(membership_id, hotel_user_id, membership_name, membership_nick, membership_pw, membership_regdate, membership_phone, membership_email, membership_gender, membership_birthday)
values(seq_membership.nextval, 17, '넬라', 'nella', 1547, to_date('2016-10-13-20-24-20', 'yyyy-mm-dd-hh24-mi-ss'), '010-1658-1544', 'win@gmail.com', '남', to_date('1995-11-11','yyyy-mm-dd'));


insert into membership
(membership_id, hotel_user_id, membership_name, membership_nick, membership_pw, membership_regdate, membership_phone, membership_email, membership_gender, membership_birthday)
values(seq_membership.nextval, 18, '트럼프', 'rich', 1123, to_date('2016-11-9-9-13-45', 'yyyy-mm-dd-hh24-mi-ss'), '010-1144-6689', 'wow@yahoo.com', '남', to_date('1976-12-25','yyyy-mm-dd'));


insert into membership
(membership_id, hotel_user_id, membership_name, membership_nick, membership_pw, membership_regdate, membership_phone, membership_email, membership_gender, membership_birthday)
values(seq_membership.nextval, 20, '메르켈', 'merkel', 4889, to_date('2017-1-1-15-16-34', 'yyyy-mm-dd-hh24-mi-ss'), '010-4684-1546', 'sweet@gmail.com', '여', to_date('1953-7-8','yyyy-mm-dd'));


--6. room_option 테이블 입력
create sequence seq_room_option
increment by 1
start with 1;


insert into room_option(room_option_id        , room_option_name,        room_option_size,        room_option_bed,        room_option_view,        room_option_max,        room_option_img,        room_option_price)
values(seq_room_option.nextval,'deluxe',30,'single','mountain',2,'deluxe.jpg',70000);


insert into room_option(room_option_id        , room_option_name,        room_option_size,        room_option_bed,        room_option_view,        room_option_max,        room_option_img,        room_option_price)
values(seq_room_option.nextval,'business',30,'twin','ocean',        2,        'business.jpg',        90000);


insert into room_option(room_option_id        , room_option_name,        room_option_size,        room_option_bed,        room_option_view,        room_option_max,        room_option_img,        room_option_price)
values(seq_room_option.nextval,'grand', 45, 'single','mountain',4,        'grand.jpg',        130000);


insert into room_option(room_option_id        , room_option_name,        room_option_size,        room_option_bed,        room_option_view,        room_option_max,        room_option_img,        room_option_price)
values(seq_room_option.nextval,'first',        45,'twin',        'ocean',        4,        'first.jpg',        150000);


insert into room_option(room_option_id        , room_option_name,        room_option_size,        room_option_bed,        room_option_view,        room_option_max,        room_option_img,        room_option_price)
values(seq_room_option.nextval,'vip'        ,60,'queen','mountain',6,'vip.jpg',180000);


insert into room_option(room_option_id        , room_option_name,        room_option_size,        room_option_bed,        room_option_view,        room_option_max,        room_option_img,        room_option_price)
values(seq_room_option.nextval,'vvip',60,'king','ocean',6,'vvip.jpg',200000);


insert into room_option(room_option_id        , room_option_name,        room_option_size,        room_option_bed,        room_option_view,        room_option_max,        room_option_img,        room_option_price)
values(seq_room_option.nextval,'sweet',100,'king_double','ocean',10,'sweet.jpg',400000);


commit;






--7. room테이블 입력


--1층-----------------------------------


insert into room(room_number,room_option_id,room_floor)
values(101,1,1);


insert into room(room_number,room_option_id,room_floor)
values(102,2,1);


insert into room(room_number,room_option_id,room_floor)
values(103,3,1);


insert into room(room_number,room_option_id,room_floor)
values(104,4,1);


insert into room(room_number,room_option_id,room_floor)
values(105,5,1);


insert into room(room_number,room_option_id,room_floor)
values(106,6,1);


--2층------------------------------------


insert into room(room_number,room_option_id,room_floor)
values(201,1,2);


insert into room(room_number,room_option_id,room_floor)
values(202,2,2);


insert into room(room_number,room_option_id,room_floor)
values(203,3,2);


insert into room(room_number,room_option_id,room_floor)
values(204,4,2);


insert into room(room_number,room_option_id,room_floor)
values(205,5,2);


insert into room(room_number,room_option_id,room_floor)
values(206,6,2);


--3층----------------------------------------


insert into room(room_number,room_option_id,room_floor)
values(301,1,3);


insert into room(room_number,room_option_id,room_floor)
values(302,2,3);


insert into room(room_number,room_option_id,room_floor)
values(303,3,3);


insert into room(room_number,room_option_id,room_floor)
values(304,4,3);


insert into room(room_number,room_option_id,room_floor)
values(305,5,3);


insert into room(room_number,room_option_id,room_floor)
values(306,6,3);


--4층------------------------------------------


insert into room(room_number,room_option_id,room_floor)
values(401,1,4);


insert into room(room_number,room_option_id,room_floor)
values(402,2,4);


insert into room(room_number,room_option_id,room_floor)
values(403,3,4);


insert into room(room_number,room_option_id,room_floor)
values(404,4,4);


insert into room(room_number,room_option_id,room_floor)
values(405,5,4);


insert into room(room_number,room_option_id,room_floor)
values(406,6,4);


--5층-----------------------------------------


insert into room(room_number,room_option_id,room_floor)
values(501,1,5);


insert into room(room_number,room_option_id,room_floor)
values(502,2,5);


insert into room(room_number,room_option_id,room_floor)
values(503,3,5);


insert into room(room_number,room_option_id,room_floor)
values(504,4,5);


insert into room(room_number,room_option_id,room_floor)
values(505,5,5);


insert into room(room_number,room_option_id,room_floor)
values(506,6,5);


--6층-------------------------------------------


insert into room(room_number,room_option_id,room_floor)
values(601,7,6);


insert into room(room_number,room_option_id,room_floor)
values(602,7,6);










--8. resv테이블 입력


create sequence seq_resv
increment by 1
start with 1;




insert into resv(resv_id, hotel_user_id, room_number, resv_time, end_time, stay, resv_regtime)
values(seq_resv.nextval,1,203,        to_date('2017-04-13-14-00-00', 'yyyy-mm-dd-hh24-mi-ss'), to_date('2017-04-14-12-00-00', 'yyyy-mm-dd-hh24-mi-ss'), 1, to_date('2017-4-13-12-00-00','yyyy-mm-dd-hh24-mi-ss'));


insert into resv(resv_id,hotel_user_id,        room_number,resv_time, end_time,stay, resv_regtime)
values(seq_resv.nextval,        2        ,404        ,to_date('2017-04-14-14-00-00', 'yyyy-mm-dd-hh24-mi-ss'), to_date('2017-04-15-12-00-00', 'yyyy-mm-dd-hh24-mi-ss'),1, to_date('2017-4-14-12-00-00','yyyy-mm-dd-hh24-mi-ss'));


insert into resv(resv_id,hotel_user_id,        room_number,resv_time, end_time,stay, resv_regtime)
values(seq_resv.nextval,        3,        104,        to_date('2017-04-15-14-00-00', 'yyyy-mm-dd-hh24-mi-ss'), to_date('2017-04-16-12-00-00', 'yyyy-mm-dd-hh24-mi-ss'),1, to_date('2017-4-15-12-00-00','yyyy-mm-dd-hh24-mi-ss'));


insert into resv(resv_id,hotel_user_id,        room_number,resv_time, end_time,stay, resv_regtime)
values(seq_resv.nextval,4,        106,        to_date('2017-04-16-14-00-00', 'yyyy-mm-dd-hh24-mi-ss'), to_date('2017-04-17-12-00-00', 'yyyy-mm-dd-hh24-mi-ss'), 1, to_date('2017-4-16-20-00-00','yyyy-mm-dd-hh24-mi-ss'));


insert into resv(resv_id,hotel_user_id,        room_number,resv_time, end_time,stay, resv_regtime)
values(seq_resv.nextval,5,        202,        to_date('2017-04-17-14-00-00', 'yyyy-mm-dd-hh24-mi-ss'), to_date('2017-04-20-12-00-00', 'yyyy-mm-dd-hh24-mi-ss'), 3, to_date('2017-4-17-21-00-00','yyyy-mm-dd-hh24-mi-ss'));


insert into resv(resv_id,hotel_user_id,        room_number,resv_time, end_time,stay, resv_regtime)
values(seq_resv.nextval,6,        303,        to_date('2017-04-18-14-00-00', 'yyyy-mm-dd-hh24-mi-ss'), to_date('2017-04-21-12-00-00', 'yyyy-mm-dd-hh24-mi-ss'), 3, to_date('2017-4-18-12-00-00','yyyy-mm-dd-hh24-mi-ss'));


insert into resv(resv_id,hotel_user_id,        room_number,resv_time, end_time,stay, resv_regtime)
values(seq_resv.nextval,8,        105,        to_date('2017-04-19-14-00-00', 'yyyy-mm-dd-hh24-mi-ss'), to_date('2017-04-20-12-00-00', 'yyyy-mm-dd-hh24-mi-ss'), 1, to_date('2017-4-19-12-00-00','yyyy-mm-dd-hh24-mi-ss'));


insert into resv(resv_id,hotel_user_id,        room_number,resv_time, end_time,stay, resv_regtime)
values(seq_resv.nextval,7,        501,        to_date('2017-04-20-14-00-00', 'yyyy-mm-dd-hh24-mi-ss'), to_date('2017-04-21-12-00-00', 'yyyy-mm-dd-hh24-mi-ss'), 1, to_date('2017-4-20-12-00-00','yyyy-mm-dd-hh24-mi-ss'));


insert into resv(resv_id,hotel_user_id,        room_number,resv_time, end_time,stay, resv_regtime)
values(seq_resv.nextval,9,        505,        to_date('2017-04-21-14-00-00', 'yyyy-mm-dd-hh24-mi-ss'), to_date('2017-04-22-12-00-00', 'yyyy-mm-dd-hh24-mi-ss'), 1, to_date('2017-4-21-12-00-00','yyyy-mm-dd-hh24-mi-ss'));


insert into resv(resv_id,hotel_user_id,        room_number,resv_time, end_time,stay, resv_regtime)
values(seq_resv.nextval,10,        504,        to_date('2017-04-22-14-00-00', 'yyyy-mm-dd-hh24-mi-ss'), to_date('2017-04-23-12-00-00', 'yyyy-mm-dd-hh24-mi-ss'), 1, to_date('2017-4-22-12-00-00','yyyy-mm-dd-hh24-mi-ss'));


insert into resv(resv_id,hotel_user_id,        room_number,resv_time, end_time,stay, resv_regtime)
values(seq_resv.nextval,11,        403,        to_date('2017-04-23-14-00-00', 'yyyy-mm-dd-hh24-mi-ss'), to_date('2017-04-24-12-00-00', 'yyyy-mm-dd-hh24-mi-ss'), 1, to_date('2017-4-23-12-00-00','yyyy-mm-dd-hh24-mi-ss'));


insert into resv(resv_id,hotel_user_id,        room_number,resv_time, end_time,stay, resv_regtime)
values(seq_resv.nextval,12,        404,        to_date('2017-04-24-14-00-00', 'yyyy-mm-dd-hh24-mi-ss'), to_date('2017-04-26-12-00-00', 'yyyy-mm-dd-hh24-mi-ss'), 2, to_date('2017-4-24-12-00-00','yyyy-mm-dd-hh24-mi-ss'));


insert into resv(resv_id,hotel_user_id,        room_number,resv_time, end_time,stay, resv_regtime)
values(seq_resv.nextval,14,        601,        to_date('2017-04-25-14-00-00', 'yyyy-mm-dd-hh24-mi-ss'), to_date('2017-04-28-12-00-00', 'yyyy-mm-dd-hh24-mi-ss'), 3, to_date('2017-4-25-12-00-00','yyyy-mm-dd-hh24-mi-ss'));


insert into resv(resv_id,hotel_user_id,        room_number,resv_time, end_time,stay, resv_regtime)
values(seq_resv.nextval,13,        506,        to_date('2017-04-26-14-00-00', 'yyyy-mm-dd-hh24-mi-ss'), to_date('2017-04-28-12-00-00', 'yyyy-mm-dd-hh24-mi-ss'),2, to_date('2017-4-26-12-00-00','yyyy-mm-dd-hh24-mi-ss'));


insert into resv(resv_id,hotel_user_id,        room_number,resv_time, end_time,stay, resv_regtime)
values(seq_resv.nextval,15,        101,        to_date('2017-04-27-14-00-00', 'yyyy-mm-dd-hh24-mi-ss'), to_date('2017-04-29-12-00-00', 'yyyy-mm-dd-hh24-mi-ss'), 2, to_date('2017-4-27-12-00-00','yyyy-mm-dd-hh24-mi-ss'));


insert into resv(resv_id,hotel_user_id,        room_number,resv_time, end_time,stay, resv_regtime)
values(seq_resv.nextval,16,        103,        to_date('2017-04-28-14-00-00', 'yyyy-mm-dd-hh24-mi-ss'), to_date('2017-04-29-12-00-00', 'yyyy-mm-dd-hh24-mi-ss'), 1, to_date('2017-4-28-12-00-00','yyyy-mm-dd-hh24-mi-ss'));


insert into resv(resv_id,hotel_user_id,        room_number,resv_time, end_time,stay, resv_regtime)
values(seq_resv.nextval,17,        406,        to_date('2017-04-29-14-00-00', 'yyyy-mm-dd-hh24-mi-ss'), to_date('2017-05-02-12-00-00', 'yyyy-mm-dd-hh24-mi-ss'), 3, to_date('2017-4-29-12-00-00','yyyy-mm-dd-hh24-mi-ss'));


insert into resv(resv_id,hotel_user_id,        room_number,resv_time, end_time,stay, resv_regtime)
values(seq_resv.nextval,18,        401,        to_date('2017-04-30-14-00-00', 'yyyy-mm-dd-hh24-mi-ss'), to_date('2017-05-04-12-00-00', 'yyyy-mm-dd-hh24-mi-ss'), 4, to_date('2017-4-30-12-00-00','yyyy-mm-dd-hh24-mi-ss'));


insert into resv(resv_id,hotel_user_id,        room_number,resv_time, end_time,stay, resv_regtime)
values(seq_resv.nextval,19,        102,        to_date('2017-05-01-14-00-00', 'yyyy-mm-dd-hh24-mi-ss'), to_date('2017-05-02-12-00-00', 'yyyy-mm-dd-hh24-mi-ss'), 1, to_date('2017-5-01-12-00-00','yyyy-mm-dd-hh24-mi-ss'));


insert into resv(resv_id,hotel_user_id,        room_number,resv_time, end_time,stay, resv_regtime)
values(seq_resv.nextval,20,        204,        to_date('2017-05-02-14-00-00', 'yyyy-mm-dd-hh24-mi-ss'), to_date('2017-05-06-12-00-00', 'yyyy-mm-dd-hh24-mi-ss'), 4, to_date('2017-5-02-12-00-00','yyyy-mm-dd-hh24-mi-ss'));




--9. chek_io테이블 입력
create sequence seq_check_io
increment BY 1
start with 1;




insert into CHECK_IO (CHECK_IO_ID, RESV_ID, CHECK_IN_TIME, CHECK_OUT_TIME)
 VALUES (seq_check_io.nextVal, 1, to_date('2017-4-13-14-00-00','yyyy-mm-dd-hh24-mi-ss'), to_date('2017-4-14-12-00-00','yyyy-mm-dd-hh24-mi-ss'));
 insert into CHECK_IO (CHECK_IO_ID, RESV_ID, CHECK_IN_TIME, CHECK_OUT_TIME)
 VALUES (seq_check_io.nextVal, 2, to_date('2017-4-14-14-00-00','yyyy-mm-dd-hh24-mi-ss'), to_date('2017-4-15-12-00-00','yyyy-mm-dd-hh24-mi-ss')); 
 insert into CHECK_IO (CHECK_IO_ID, RESV_ID, CHECK_IN_TIME, CHECK_OUT_TIME)
 VALUES (seq_check_io.nextVal, 3, to_date('2017-4-15-14-00-00','yyyy-mm-dd-hh24-mi-ss'), to_date('2017-4-16-12-00-00','yyyy-mm-dd-hh24-mi-ss')); 
 insert into CHECK_IO (CHECK_IO_ID, RESV_ID, CHECK_IN_TIME, CHECK_OUT_TIME)
 VALUES (seq_check_io.nextVal, 4, to_date('2017-4-16-14-00-00','yyyy-mm-dd-hh24-mi-ss'), to_date('2017-4-17-12-00-00','yyyy-mm-dd-hh24-mi-ss')); 
 insert into CHECK_IO (CHECK_IO_ID, RESV_ID, CHECK_IN_TIME, CHECK_OUT_TIME)
 VALUES (seq_check_io.nextVal, 5, to_date('2017-4-17-14-00-00','yyyy-mm-dd-hh24-mi-ss'), to_date('2017-4-20-12-00-00','yyyy-mm-dd-hh24-mi-ss')); 
 insert into CHECK_IO (CHECK_IO_ID, RESV_ID, CHECK_IN_TIME, CHECK_OUT_TIME)
 VALUES (seq_check_io.nextVal, 6, to_date('2017-4-18-14-00-00','yyyy-mm-dd-hh24-mi-ss'), to_date('2017-4-21-12-00-00','yyyy-mm-dd-hh24-mi-ss')); 
 insert into CHECK_IO (CHECK_IO_ID, RESV_ID, CHECK_IN_TIME, CHECK_OUT_TIME)
 VALUES (seq_check_io.nextVal, 7, to_date('2017-4-19-14-00-00','yyyy-mm-dd-hh24-mi-ss'), to_date('2017-4-20-12-00-00','yyyy-mm-dd-hh24-mi-ss')); 
 insert into CHECK_IO (CHECK_IO_ID, RESV_ID, CHECK_IN_TIME, CHECK_OUT_TIME)
 VALUES (seq_check_io.nextVal, 8, to_date('2017-4-20-14-00-00','yyyy-mm-dd-hh24-mi-ss'), to_date('2017-4-21-12-00-00','yyyy-mm-dd-hh24-mi-ss')); 
 insert into CHECK_IO (CHECK_IO_ID, RESV_ID, CHECK_IN_TIME, CHECK_OUT_TIME)
 VALUES (seq_check_io.nextVal, 9, to_date('2017-4-21-14-00-00','yyyy-mm-dd-hh24-mi-ss'), to_date('2017-4-22-12-00-00','yyyy-mm-dd-hh24-mi-ss')); 
 insert into CHECK_IO (CHECK_IO_ID, RESV_ID, CHECK_IN_TIME, CHECK_OUT_TIME)
 VALUES (seq_check_io.nextVal, 10, to_date('2017-4-22-14-00-00','yyyy-mm-dd-hh24-mi-ss'), to_date('2017-4-23-12-00-00','yyyy-mm-dd-hh24-mi-ss')); 
 insert into CHECK_IO (CHECK_IO_ID, RESV_ID, CHECK_IN_TIME, CHECK_OUT_TIME)
 VALUES (seq_check_io.nextVal, 11, to_date('2017-4-23-14-00-00','yyyy-mm-dd-hh24-mi-ss'), to_date('2017-4-24-12-00-00','yyyy-mm-dd-hh24-mi-ss')); 
 insert into CHECK_IO (CHECK_IO_ID, RESV_ID, CHECK_IN_TIME, CHECK_OUT_TIME)
 VALUES (seq_check_io.nextVal, 12, to_date('2017-4-24-14-00-00','yyyy-mm-dd-hh24-mi-ss'), to_date('2017-4-26-12-00-00','yyyy-mm-dd-hh24-mi-ss')); 
 insert into CHECK_IO (CHECK_IO_ID, RESV_ID, CHECK_IN_TIME, CHECK_OUT_TIME)
 VALUES (seq_check_io.nextVal, 13, to_date('2017-4-25-14-00-00','yyyy-mm-dd-hh24-mi-ss'), to_date('2017-4-28-12-00-00','yyyy-mm-dd-hh24-mi-ss')); 
 insert into CHECK_IO (CHECK_IO_ID, RESV_ID, CHECK_IN_TIME, CHECK_OUT_TIME)
 VALUES (seq_check_io.nextVal, 14, to_date('2017-4-26-14-00-00','yyyy-mm-dd-hh24-mi-ss'), to_date('2017-4-28-12-00-00','yyyy-mm-dd-hh24-mi-ss')); 
 insert into CHECK_IO (CHECK_IO_ID, RESV_ID, CHECK_IN_TIME, CHECK_OUT_TIME)
 VALUES (seq_check_io.nextVal, 15, to_date('2017-4-27-14-00-00','yyyy-mm-dd-hh24-mi-ss'), to_date('2017-4-29-12-00-00','yyyy-mm-dd-hh24-mi-ss')); 
 insert into CHECK_IO (CHECK_IO_ID, RESV_ID, CHECK_IN_TIME, CHECK_OUT_TIME)
 VALUES (seq_check_io.nextVal, 16, to_date('2017-4-28-14-00-00','yyyy-mm-dd-hh24-mi-ss'), to_date('2017-4-29-12-00-00','yyyy-mm-dd-hh24-mi-ss')); 
 insert into CHECK_IO (CHECK_IO_ID, RESV_ID, CHECK_IN_TIME, CHECK_OUT_TIME)
 VALUES (seq_check_io.nextVal, 17, to_date('2017-4-29-14-00-00','yyyy-mm-dd-hh24-mi-ss'), to_date('2017-5-1-12-00-00','yyyy-mm-dd-hh24-mi-ss')); 
 insert into CHECK_IO (CHECK_IO_ID, RESV_ID, CHECK_IN_TIME, CHECK_OUT_TIME)
 VALUES (seq_check_io.nextVal, 18, to_date('2017-4-30-14-00-00','yyyy-mm-dd-hh24-mi-ss'), to_date('2017-5-4-12-00-00','yyyy-mm-dd-hh24-mi-ss')); 
 insert into CHECK_IO (CHECK_IO_ID, RESV_ID, CHECK_IN_TIME, CHECK_OUT_TIME)
 VALUES (seq_check_io.nextVal, 19, to_date('2017-5-1-14-00-00','yyyy-mm-dd-hh24-mi-ss'), to_date('2017-5-2-12-00-00','yyyy-mm-dd-hh24-mi-ss')); 
 insert into CHECK_IO (CHECK_IO_ID, RESV_ID, CHECK_IN_TIME, CHECK_OUT_TIME)
 VALUES (seq_check_io.nextVal, 20, to_date('2017-5-2-14-00-00','yyyy-mm-dd-hh24-mi-ss'), to_date('2017-5-6-12-00-00','yyyy-mm-dd-hh24-mi-ss')); 

--10.hotel_admin테이블 입력
insert into HOTEL_ADMIN (HOTEL_ADMIN_ID, HOTEL_ADMIN_PW, HOTEL_ADMIN_NAME) VALUES ('admin','admin', 'admin');




commit;