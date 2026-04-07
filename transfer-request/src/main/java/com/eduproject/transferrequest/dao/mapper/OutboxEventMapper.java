package com.eduproject.transferrequest.dao.mapper;

import com.eduproject.transferrequest.dto.model.OutboxEventDto;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OutboxEventMapper {
    @Insert("""
            INSERT INTO ${schema}.outbox_events (
            request_id,
            event_id,
            event_type,
            topic_name,
            partition_key,
            payload,
            status,
            attempt_count,
            created_at,
            updated_at,
            published_at
            )
            VALUES (
            #{dto.requestId},
            #{dto.eventId},
            #{dto.eventType},
            #{dto.topicName},
            #{dto.partitionKey},
            #{dto.payload},
            #{dto.status},
            #{dto.attemptCount},
            #{dto.createdAt},
            #{dto.updatedAt},
            #{dto.publishedAt}
            )
            """)
    void insert(@Param("schema") String schema, @Param("dto") OutboxEventDto dto);


    @Select("""
            SELECT
            request_id,
            event_id,
            event_type,
            topic_name,
            partition_key,
            payload,
            status,
            attempt_count,
            created_at,
            updated_at,
            published_at
            FROM ${schema}.outbox_events
            WHERE status = 'NEW'
            ORDER BY created_at
            LIMIT #{toTake}
            FOR UPDATE SKIP LOCKED
            """)
    @Results(value = {
            @Result(column = "request_id", property = "requestId"),
            @Result(column = "event_id", property = "eventId"),
            @Result(column = "event_type", property = "eventType"),
            @Result(column = "topic_name", property = "topicName"),
            @Result(column = "partition_key", property = "partitionKey"),
            @Result(column = "payload", property = "payload"),
            @Result(column = "status", property = "status"),
            @Result(column = "attempt_count", property = "attemptCount"),
            @Result(column = "created_at", property = "createdAt"),
            @Result(column = "updated_at", property = "updatedAt"),
            @Result(column = "published_at", property = "publishedAt")

    })
    List<OutboxEventDto> findEventsToProcess(@Param("schema") String schema, @Param("toTake") int toTake);

    @Update("""
            UPDATE ${schema}.outbox_events
            SET status = 'SENT',
                published_at = #{publishedAt},
                updated_at = #{updatedAt}
            WHERE event_id = #{eventId}
            """)
    void markAsSent(@Param("schema") String schema,
            @Param("eventId") String id,
                    @Param("publishedAt") LocalDateTime publishedAt,
                    @Param("updatedAt") LocalDateTime updatedAt);
}

