package com.starwin.smsservice.mapper;

import com.starwin.smsservice.entity.ActionMessage;
import com.starwin.smsservice.entity.Value;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ActionMapper {
    // 收集事件
    @Insert({"insert into table_action(actions, times,extra) values(#{actions}, #{times}, #{extra})"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertAction(ActionMessage smsMessage);

    // 收集key-value
    @Insert({"insert into table_values(_key, value) values(#{key}, #{value})"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertKeyValue(@Param(value = "key") String key, @Param(value = "value") String value);

    @Update({"update table_values set _key = #{key} ,value = #{value} where _key = #{key}"})
    int updateKeyValue(@Param(value = "key") String key, @Param(value = "value") String value);

    @Select({"select * from table_values where _key = #{key}"})
    Value getValue(@Param(value = "key") String key);
}
