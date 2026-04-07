package com.eduproject.transferrequest.dao.mapper;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.sql.*;
import java.util.UUID;

@Slf4j
@Component
@MappedTypes(value = UUID.class)
public class UUIDTypeHandler implements TypeHandler<UUID> {
    private static UUID toUUID(String val) {
        if(StringUtils.hasText(val)) {
            try {
                return UUID.fromString(val);
            } catch (IllegalArgumentException e) {
                log.warn("Invalid UUID format: {}", val);
            }
        }
        return null;
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, UUID parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter != null ? parameter.toString() : null, Types.OTHER);
    }

    @Override
    public UUID getResult(ResultSet rs, String columnName) throws SQLException {
        return toUUID(rs.getString(columnName));
    }

    @Override
    public UUID getResult(ResultSet rs, int columnIndex) throws SQLException {
        return toUUID(rs.getString(columnIndex));
    }

    @Override
    public UUID getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return toUUID(cs.getString(columnIndex));
    }
}
