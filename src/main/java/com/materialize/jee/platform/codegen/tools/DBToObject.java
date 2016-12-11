package com.materialize.jee.platform.codegen.tools;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.materialize.jee.platform.codegen.model.Column;
import com.materialize.jee.platform.codegen.model.Table;
import com.materialize.jee.platform.codegen.model.TypeResolver;

public class DBToObject {
	
	
	public static Table loadTable(String tableName) throws Exception {
        Table table = new Table(tableName);
        DatabaseMetaData dbmd = ConnectUtil.getConnection().getMetaData();
        
        ResultSet keyrs = dbmd.getPrimaryKeys(null, null, tableName);
        List<String> keys = new ArrayList<String>();
        while (keyrs.next()){  
        	String columnName = keyrs.getString("COLUMN_NAME");//列名  
        	keys.add(columnName);
        }  
        
        ResultSet colrs = dbmd.getColumns(null, null, tableName, "%");
        while (colrs.next()){
        	Column column = new Column();
            String columnName = colrs.getString("COLUMN_NAME");//列名   
            int dataType = colrs.getInt("DATA_TYPE"); //对应的java.sql.Types类型      
            int columnSize = colrs.getInt("COLUMN_SIZE");//列大小   
            int decimalDigits = colrs.getInt("DECIMAL_DIGITS");//小数位数   
            String remarks = colrs.getString("REMARKS");//列描述   
            int isNullAble = colrs.getInt("NULLABLE");//是否允许为null   
            
            column.setColumnLength(columnSize);
            column.setColumnName(columnName);
            column.setPropertyName(NameFormatUtils.getColumnJavaName(columnName));
            column.setPropertyInMethodName(NameFormatUtils.getPropertyInMethodName(column.getPropertyName()));
            column.setDecimalDigits(decimalDigits);
            column.setIsNullAble(isNullAble==0?false:true);
            column.setJdbcType(dataType);
            column.setJavaType(TypeResolver.calculateJavaType(column));
            column.setJdbcTypeName(TypeResolver.calculateJdbcTypeName(column));
            column.setRemarks(remarks);
            if(keys.contains(columnName)){
            	column.setPrimary(true);
            	table.setHasPrimary(true);
            	table.addPrimaryKeysList(column);
            }else{
            	column.setPrimary(false);
            	table.addCommonColumnList(column);
            }
            if(table.isHasPrimary() && table.getPrimaryKeysList().size()>1){
            	table.setHasMutiKey(true);
            }else{
            	table.setHasMutiKey(false);
            }
        }
        
        return table;
    }
	
}
