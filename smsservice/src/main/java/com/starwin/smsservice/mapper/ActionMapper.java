package com.starwin.smsservice.mapper;

import com.starwin.smsservice.entity.ActionMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ActionMapper {

    @Insert({"insert into table_action(action, time，extra) values(#{action}, #{time}, #{extra})"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertAction(ActionMessage smsMessage);
}
