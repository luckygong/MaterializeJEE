package com.materialize.jee.platform.codegen.model;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class TypeResolver {

    protected static Map<Integer, JdbcAndJavaTypeInfo> typeMap;
    
    public static boolean FORCE_BIGDECIMALS;
    
    static{
        typeMap = new HashMap<Integer, JdbcAndJavaTypeInfo>();

        typeMap.put(Types.ARRAY, new JdbcAndJavaTypeInfo("ARRAY", Object.class.getName()));
        typeMap.put(Types.BIGINT, new JdbcAndJavaTypeInfo("BIGINT",Long.class.getName()));
        typeMap.put(Types.BINARY, new JdbcAndJavaTypeInfo("BINARY", "byte[]"));
        typeMap.put(Types.BIT, new JdbcAndJavaTypeInfo("BIT", Boolean.class.getName()));
        typeMap.put(Types.BLOB, new JdbcAndJavaTypeInfo("BLOB", "byte[]")); 
        typeMap.put(Types.BOOLEAN, new JdbcAndJavaTypeInfo("BOOLEAN", Boolean.class.getName()));
        typeMap.put(Types.CHAR, new JdbcAndJavaTypeInfo("CHAR", String.class.getName()));
        typeMap.put(Types.CLOB, new JdbcAndJavaTypeInfo("CLOB", String.class.getName()));
        typeMap.put(Types.DATALINK, new JdbcAndJavaTypeInfo("DATALINK", Object.class.getName()));
        typeMap.put(Types.DATE, new JdbcAndJavaTypeInfo("DATE", Date.class.getName()));
        typeMap.put(Types.DISTINCT, new JdbcAndJavaTypeInfo("DISTINCT", Object.class.getName()));
        typeMap.put(Types.DOUBLE, new JdbcAndJavaTypeInfo("DOUBLE", Double.class.getName()));
        typeMap.put(Types.FLOAT, new JdbcAndJavaTypeInfo("FLOAT", Double.class.getName()));
        typeMap.put(Types.INTEGER, new JdbcAndJavaTypeInfo("INTEGER", Integer.class.getName()));
        typeMap.put(Types.JAVA_OBJECT, new JdbcAndJavaTypeInfo("JAVA_OBJECT", Object.class.getName()));
        typeMap.put(Types.LONGVARBINARY, new JdbcAndJavaTypeInfo("LONGVARBINARY", "byte[]")); 
        typeMap.put(Types.LONGVARCHAR, new JdbcAndJavaTypeInfo("LONGVARCHAR", String.class.getName()));
        typeMap.put(Types.NULL, new JdbcAndJavaTypeInfo("NULL", Object.class.getName()));
        typeMap.put(Types.OTHER, new JdbcAndJavaTypeInfo("OTHER", Object.class.getName()));
        typeMap.put(Types.REAL, new JdbcAndJavaTypeInfo("REAL", Float.class.getName()));
        typeMap.put(Types.REF, new JdbcAndJavaTypeInfo("REF", Object.class.getName()));
        typeMap.put(Types.SMALLINT, new JdbcAndJavaTypeInfo("SMALLINT", Short.class.getName()));
        typeMap.put(Types.STRUCT, new JdbcAndJavaTypeInfo("STRUCT", Object.class.getName()));
        typeMap.put(Types.TIME, new JdbcAndJavaTypeInfo("TIME", Date.class.getName()));
        typeMap.put(Types.TIMESTAMP, new JdbcAndJavaTypeInfo("TIMESTAMP", Date.class.getName()));
        typeMap.put(Types.TINYINT, new JdbcAndJavaTypeInfo("TINYINT", Byte.class.getName()));
        typeMap.put(Types.VARBINARY, new JdbcAndJavaTypeInfo("VARBINARY", "byte[]"));
        typeMap.put(Types.VARCHAR, new JdbcAndJavaTypeInfo("VARCHAR", String.class.getName()));
        
    }

    public static String calculateJavaType(Column column) {
        String answer;
        JdbcAndJavaTypeInfo JdbcAndJavaTypeInfo = typeMap.get(column.getJdbcType());

        if (JdbcAndJavaTypeInfo == null) {
            switch (column.getJdbcType()) {
            case Types.DECIMAL:
            case Types.NUMERIC:
                if (column.getDecimalDigits() > 0
                        || column.getColumnLength() > 18
                        || FORCE_BIGDECIMALS) {
                    answer = BigDecimal.class.getName();
                } else if (column.getColumnLength() > 9) {
                    answer = Long.class.getName();
                } else if (column.getColumnLength() > 4) {
                    answer = Integer.class.getName();
                } else {
                    answer = Short.class.getName();
                }
                break;

            default:
                answer = null;
                break;
            }
        } else {
            answer = JdbcAndJavaTypeInfo.getJavaTypeName();
        }

        return answer;
    }

    public static String calculateJdbcTypeName(Column column) {
        String answer;
        JdbcAndJavaTypeInfo JdbcAndJavaTypeInfo = typeMap.get(column.getJdbcType());

        if (JdbcAndJavaTypeInfo == null) {
            switch (column.getJdbcType()) {
            case Types.DECIMAL:
                answer = "DECIMAL"; //$NON-NLS-1$
                break;
            case Types.NUMERIC:
                answer = "NUMERIC"; //$NON-NLS-1$
                break;
            default:
                answer = null;
                break;
            }
        } else {
            answer = JdbcAndJavaTypeInfo.getJdbcTypeName();
        }

        return answer;
    }


    public static class JdbcAndJavaTypeInfo {
        private String jdbcTypeName;

        private String javaTypeName;

        public JdbcAndJavaTypeInfo (String jdbcTypeName, String javaTypeName) {
            this.jdbcTypeName = jdbcTypeName;
            this.javaTypeName = javaTypeName;
        }

        public String getJdbcTypeName() {
            return jdbcTypeName;
        }

        public String getJavaTypeName() {
            return javaTypeName;
        }
    }
}
