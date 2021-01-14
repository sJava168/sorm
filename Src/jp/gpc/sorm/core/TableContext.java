package jp.gpc.sorm.core;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jp.gpc.sorm.bean.ColumnInfo;
import jp.gpc.sorm.bean.TableInfo;
import jp.gpc.sorm.util.JavaFileUtils;
import jp.gpc.sorm.util.StringUtils;

/**
 * �����ȡ�������ݿ����б�ṹ����ṹ�Ĺ�ϵ�������Ը��ݱ�ṹ������ṹ
 * @author G
 *
 */
public class TableContext {
	
	/**
	 * ����Ϊkey������Ϣ����Ϊvalue
	 */
	public static Map<String,TableInfo> tables = new HashMap<String, TableInfo>();
	
	/**
	 * ��po��class����ͱ���Ϣ��������������������ã�
	 */
	public static Map<Class,TableInfo> poClassTableMap = new HashMap<Class, TableInfo>();
	
	private TableContext() {}
	
	static {
		
		
		try {
			//��ʼ����ñ���Ϣ
			Connection con = DBManager.getConn();
			DatabaseMetaData dbmb = con.getMetaData();
			
			ResultSet tableRet = dbmb.getTables(null, "%", "%", new String[] {"Table"});
			
			while(tableRet.next()) {
				String tableName = (String) tableRet.getObject("TABLE_NAME");
				
				TableInfo ti = new TableInfo(tableName, new ArrayList<ColumnInfo>(), 
						new HashMap<String, ColumnInfo>());
				tables.put(tableName, ti);
				
				ResultSet set = dbmb.getColumns(null, "%", tableName, "%");	//��ѯ���е������ֶ�
				while(set.next()) {
					ColumnInfo ci = new ColumnInfo(set.getString("COLUMN_NAME"),
							set.getString("TYPE_NAME"),0);
					ti.getColumn().put(set.getString("COLUMN_NAME"), ci);
				}
				
				ResultSet set2 = dbmb.getPrimaryKeys(null, "%", tableName);	//��ѯ���е���������
				while(set2.next()) {
					ColumnInfo ci2 = (ColumnInfo)ti.getColumn().get(set2.getObject("COLUMN_NAME"));
					ci2.setKeyType(1);	//����Ϊ��������
					ti.getPriKeys().add(ci2);
				}
				
				if(ti.getPriKeys().size()>0) {	//ȡΨһ����������ʹ�á������������������Ϊ��
					ti.setOnlyPriKey(ti.getPriKeys().get(0));
				}
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		//ÿ��������������ṹ
		updatePoFile();
		
		//����po�������е��࣬�������ã����Ч��
		loadPoTables();
	}
	
//	public static Map<String, TableInfo> getTableInfos(){
//		return tables;
//	}

	/**
	 * ���ݱ�ṹ���������õ�po���µ�java��
	 * ʵ���˴ӱ�ṹת��Ϊ��ṹ
	 */
	public static void updatePoFile() {
		Map<String,TableInfo> map = TableContext.tables;
		for(TableInfo t:map.values()) {		//�������ݿ�����б�
			JavaFileUtils.createJavaPoFile(t, new MySqlTypeConvertor());
		}
	}
	
	/**
	 * ����po���������
	 */
	public static void loadPoTables() {

		for (TableInfo tableInfo : tables.values()) {
			try {
				Class c = Class.forName(DBManager.getConf().getPoPackage() + "."
						+ StringUtils.firstChar2UpperCase(tableInfo.getTname()));
				poClassTableMap.put(c, tableInfo);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		Map<String, TableInfo> tables = TableContext.tables;
		System.out.println(tables);
	}
	

}
