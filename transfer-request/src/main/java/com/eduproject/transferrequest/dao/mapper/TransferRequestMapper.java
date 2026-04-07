package com.eduproject.transferrequest.dao.mapper;

import com.eduproject.transferrequest.dto.model.TransferRequestDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TransferRequestMapper {
    @Insert("""
            INSERT INTO ${schema}.transfer_requests (
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
    public void insert(@Param("schema") String schema, @Param("dto") TransferRequestDto dto);
}
