package com.eduproject.transferprocessing.mapper;

import com.eduproject.transferprocessing.dto.model.TransferProcessingResultDto;
import org.apache.ibatis.annotations.*;

@Mapper
public interface TestMapper {
    @Select(
            """
            SELECT count(*)
            FROM transfer_processing_service.processed_events
            WHERE event_id = #{eventId}
            """
    )
    int countProcessedEventsWithEventId(@Param("eventId") String eventId);

    @Select(
            """
            SELECT count(*)
            FROM transfer_processing_service.transfer_processing_results
            WHERE event_id = #{eventId}
            """
    )
    int countTransferProcessingResultsWithEventId(@Param("eventId") String eventId);

    @Select(
            """
            SELECT *
            FROM transfer_processing_service.transfer_processing_results
            WHERE event_id = #{eventId}
            """
    )
    @Results(id = "results", value = {
            @Result(column = "request_id", property = "requestId"),
            @Result(column = "event_id", property = "eventId"),
            @Result(column = "processing_status", property = "processingStatus"),
            @Result(column = "message", property = "message"),
            @Result(column = "processed_at", property = "processedAt")
    })
    TransferProcessingResultDto getTransferProcessingResultsByEventId(@Param("eventId") String eventId);



}
