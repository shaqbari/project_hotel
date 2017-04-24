--예약되어있는 방을 구한다.
select *
 from ROOM_OPTION o, ROOM r, RESV re
 where o.ROOM_OPTION_ID=r.ROOM_OPTION_ID and r.ROOM_NUMBER = re.ROOM_NUMBER
 and o.ROOM_OPTION_NAME='business'
 and (to_Char(re.RESV_TIME, 'yyyy-mm-dd')<='2017-05-03'
 and to_Char(re.END_TIME, 'yyyy-mm-dd')>='2017-04-28');
--endTime을 추가해서 지정된 입퇴실시간과 비교하면 되겠다.
 
 -- to_Char(re.END_TIME+ (INTERVAL '1' DAY), 'yyyy-mm-dd')>'2017-04-20'
 
 --예약된 방을 제외한 방을 구한다.
 select room_number
  from ROOM_OPTION o, ROOM r
  where o.ROOM_OPTION_ID=r.ROOM_OPTION_ID
  and o.ROOM_OPTION_NAME='business'
  and r.ROOM_NUMBER!=all(302, 202);