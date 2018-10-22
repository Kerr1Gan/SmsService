package com.starwin.smsservice.mapper;

import com.starwin.smsservice.entity.SmsMessage;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface SmsMapper {

    @Select("select * from table_sms")
    List<SmsMessage> getAllSms();

    @Select("select * from table_sms,table_phone where table_sms.self_phone = table_phone.id and table_phone.phone = #{phone} limit ${index},${length}")
    List<SmsMessage> getSmsByIndex(@Param(value = "index") int index, @Param(value = "length") int length);

    @Insert({"insert into table_sms(phone, type, content,date,self_phone) values(#{phone}, #{type}, #{content}, #{date},#{selfPhone})"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertMessage(SmsMessage smsMessage);
}
