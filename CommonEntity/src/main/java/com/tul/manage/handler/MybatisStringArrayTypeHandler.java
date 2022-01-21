package com.tul.manage.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description: 自定义mybatis类型转换配置
 * @author: zengyu
 * @create: 2021-08-12 11:37
 **/
public class MybatisStringArrayTypeHandler extends BaseTypeHandler<List<String>> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List<String> strings, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, String.join(",", strings));
    }

    @Override
    public List<String> getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String str = resultSet.getString(s);
        if (resultSet.wasNull()){
            return null;
        }
        return new ArrayList<>(Arrays.asList(str.split(",")) );
    }

    @Override
    public List<String> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String str = resultSet.getString(i);
        if (resultSet.wasNull()){
            return null;
        }
        return new ArrayList<>(Arrays.asList(str.split(",")) );
    }

    @Override
    public List<String> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String str = callableStatement.getString(i);
        if (callableStatement.wasNull()){
            return null;
        }
        return new ArrayList<>(Arrays.asList(str.split(",")) );
    }
}
