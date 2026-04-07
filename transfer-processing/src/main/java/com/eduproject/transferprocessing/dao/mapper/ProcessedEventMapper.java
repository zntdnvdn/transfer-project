package com.eduproject.transferprocessing.dao.mapper;

import com.eduproject.transferprocessing.dto.model.ProcessedEventDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProcessedEventMapper {
    @Insert("""
            INSERT INTO ${schema}.processed_events (
            event_id,
            consumer_name,
            processed_at
            )
            VALUES (
            #{dto.eventId},
            #{dto.consumerName},
            #{dto.processedAt}
            )
            """)
    void insert(@Param("schema") String schema, @Param("dto") ProcessedEventDto dto);
}
