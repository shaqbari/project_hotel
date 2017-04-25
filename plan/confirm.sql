--예약이 없는방도 나와야하므로 left outerjoin을 이용하자.x 테이블이 2개일때만 가능하다.
--충돌나는 방을 구하고 그 방이 아닌 방을 구하자.

select r.ROOM_Number
from ROOM r, ROOM_OPTION o
where r.ROOM_OPTION_ID=o.ROOM_OPTION_ID
 and o.ROOM_OPTION_NAME='business'
 and r.ROOM_NUMBER!=All(select r.ROOM_NUMBER
 from ROOM r,  ROOM_OPTION o,  RESV re
 where o.ROOM_OPTION_ID=r.ROOM_OPTION_ID and r.ROOM_NUMBER = re.ROOM_NUMBER
 and o.ROOM_OPTION_NAME='business'
 and (to_Char(re.RESV_TIME, 'yyyy-mm-dd-hh24-mi-ss')<='2017-04-18-12-00-00'
 and to_Char(re.END_TIME, 'yyyy-mm-dd-hh24-mi-ss')>='2017-04-16-14-00-00'));
 --한자리인 달이나 일 앞에 0이 없으면 인식하지 못한다. mydateUtil이용
--any를 이용하면 충돌나는 방이 없는 경우 결과가 없다.
 
 --충돌안나는 방을 left outer join으로 구하고(예약없는 방은 나오지만 충돌나도 나와버린다.) 그중에서 특정옵션인것을 고른다.
 --xx쓰면 안된다.
 select r.ROOM_NUMBER
 from ROOM r, ROOM_OPTION o
 where r.ROOM_OPTION_ID=o.ROOM_OPTION_ID
 and  o.ROOM_OPTION_NAME='business' and  r.ROOM_NUMBER=
 any(select r.ROOM_Number
 from ROOM r left outer join RESV re
 on r.ROOM_NUMBER = re.ROOM_NUMBER
 and  (to_Char(re.RESV_TIME, 'yyyy-mm-dd-hh24-mi-ss')>'2017-04-18-12-00-00'
 or to_Char(re.END_TIME, 'yyyy-mm-dd-hh24-mi-ss')<'2017-04-16-14-00-00'));
 