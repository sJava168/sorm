package jp.gpc.sorm.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
/**
 * 封装了JDBC查询常用的操作
 * @author G
 *
 */

public class JDBCUtils {
	
	/**
	 * 给sql设置参数
	 * @param ps	预编译sql语句对象 
	 * @param params	参数
	 */
	public static void handleParams(PreparedStatement ps, Object[] params) {
		if(null!=params) {
			for(int i=0;i<params.length;i++) {
				try {
					ps.setObject(1+i, params[i]);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}


}
