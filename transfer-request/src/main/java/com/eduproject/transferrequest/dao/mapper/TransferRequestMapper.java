package com.eduproject.transferrequest.dao.mapper;

import com.eduproject.transferrequest.dto.model.TransferRequestDto;
import org.apache.ibatis.annotations.*;

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
     void insert(@Param("schema") String schema, @Param("dto") TransferRequestDto dto);

    @Select("""
            SELECT *
            FROM ${schema}.transfer_requests
            WHERE request_id = #{requestId}
            """)
    @Results(value = {
            @Result(column = "request_id", property = "requestId"),
            @Result(column = "from_account", property = "fromAccount"),
            @Result(column = "to_account", property = "toAccount"),
            @Result(column = "amount", property = "amount"),
            @Result(column = "currency", property = "currency"),
            @Result(column = "status", property = "status"),
            @Result(column = "created_at", property = "createdAt"),
            @Result(column = "updated_at", property = "updatedAt")

    })
    TransferRequestDto findByRequestId(@Param("schema") String schema,
                                       @Param("requestId")String requestId);
}
