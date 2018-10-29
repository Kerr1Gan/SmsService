package com.starwin.smsservice.mapper;

import com.starwin.smsservice.entity.SmsMessage;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface SmsMapper {

    @Select("select * from table_sms order by id desc")
    List<SmsMessage> getAllSms();

    @Select("select * from table_sms  order by id desc limit ${index},${length}")
    List<SmsMessage> getSmsByIndex(@Param(value = "index") int index, @Param(value = "length") int length);

    @Select("select * from table_sms where self_phone =#{selfPhone}  order by id desc limit ${index},${length}")
    List<SmsMessage> getSmsByIndexAndPhone(@Param(value = "index") int index, @Param(value = "length") int length, @Param(value = "selfPhone") String selfPhone);

    @Insert({"insert into table_sms(phone, type, content,date,self_phone) values(#{phone}, #{type}, #{content}, #{date},#{selfPhone})"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertMessage(SmsMessage smsMessage);

    @Select("select distinct self_phone from table_sms limit #{index},#{length}")
    List<String> getPhoneList(@Param(value = "index") int index, @Param(value = "length") int length);
}
