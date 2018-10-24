package com.starwin.smsservice.mapper;

import com.starwin.smsservice.entity.ActionMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ActionMapper {
    // 收集事件
    @Insert({"insert into table_action(actions, times,extra) values(#{actions}, #{times}, #{extra})"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertAction(ActionMessage smsMessage);
}
