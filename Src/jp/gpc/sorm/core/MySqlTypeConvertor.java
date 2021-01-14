package jp.gpc.sorm.core;

/**
 * Mysql�������ͺ�Java��������ת��
 * @param args
 */

public class MySqlTypeConvertor implements TypeConvertor {

	
	
	@Override
	public String databaseType2JavaType(String columnType) {
		
		//varchar--->string
		if("varchar".equalsIgnoreCase(columnType)||"char".equalsIgnoreCase(columnType)) {
			return "String";
		}else if("int".equalsIgnoreCase(columnType)
				||"tinyint".equalsIgnoreCase(columnType)
				||"smallint".equalsIgnoreCase(columnType)
				||"mediumint".equalsIgnoreCase(columnType)
				||"integer".equalsIgnoreCase(columnType)) {
			return "Integer";
		}else if("bigint".equalsIgnoreCase(columnType)) {
			return "long";
		}else if("double".equalsIgnoreCase(columnType)) {
			return "double";
		}else if("float".equalsIgnoreCase(columnType)) {
			return "float";
		}else if("clob".equalsIgnoreCase(columnType)) {
			return "java.sql.Clob";
		}else if("blob".equalsIgnoreCase(columnType)) {
			return "java.sql.Blob";
		}else if("date".equalsIgnoreCase(columnType)) {
			return "java.sql.Date";
		}else if("time".equalsIgnoreCase(columnType)) {
			return "java.sql.Time";
		}else if("timestamp".equalsIgnoreCase(columnType)
				||"datetime".equalsIgnoreCase(columnType)) {
			return "java.sql.Timestamp";
		}
		
		return null;
	}

	@Override
	public String javaType2DatabaseType(String javaDataType) {
		
		return null;
	}
	
	
}
