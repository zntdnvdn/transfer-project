package com.eduproject.transferprocessing.dao.mapper;

import com.eduproject.transferprocessing.dto.model.ProcessedEventDto;
import com.eduproject.transferprocessing.dto.model.TransferProcessingResultDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TransferProcessingResultMapper {
    @Insert("""
            INSERT INTO ${schema}.transfer_processing_results (
            request_id,
            event_id,
            processing_status,
            message,
            processed_at
            )
            VALUES (
            #{dto.requestId},
            #{dto.eventId},
            #{dto.processingStatus},
            #{dto.message},
            #{dto.processedAt}
            )
            """)
    void insert(@Param("schema") String schema, @Param("dto") TransferProcessingResultDto dto);
}
