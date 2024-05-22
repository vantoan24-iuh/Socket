use OT2
go

--Liet ke ca admin co dang bai viet va so bai viet ma ho da dang
select *from users u join posts p on u.user_name=p.user_name where p.status like 'REFECTED' and p.status like 'APPROVED'