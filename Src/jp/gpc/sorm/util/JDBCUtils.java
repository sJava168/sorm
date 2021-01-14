package jp.gpc.sorm.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
/**
 * ��װ��JDBC��ѯ���õĲ���
 * @author G
 *
 */

public class JDBCUtils {
	
	/**
	 * ��sql���ò���
	 * @param ps	Ԥ����sql������ 
	 * @param params	����
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
