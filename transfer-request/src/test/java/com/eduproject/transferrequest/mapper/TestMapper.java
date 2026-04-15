package com.eduproject.transferrequest.mapper;

import com.eduproject.transferrequest.dto.model.TransferRequestDto;
import org.apache.ibatis.annotations.*;

@Mapper
public interface TestMapper {
    @Insert("""
            INSERT INTO transfer_request_service.transfer_requests (
                request_id,
                from_account,
                to_account,
                amount,
                currency,
                status,
                created_at,
                updated_at
            )
            VALUES (
                #{dto.requestId},
                #{dto.fromAccount},
                #{dto.toAccount},
                #{dto.amount},
                #{dto.currency},
                #{dto.status},
                #{dto.createdAt},
                #{dto.updatedAt}
            )
            """)
    void insert(@Param("dto") TransferRequestDto dto);
    @Delete("""
            DELETE FROM transfer_request_service.transfer_requests
            """)
    void delete();



}
