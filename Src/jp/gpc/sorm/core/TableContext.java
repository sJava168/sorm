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
 * 负责获取管理数据库所有表结构和类结构的关系，并可以根据表结构生成类结构
 * @author G
 *
 */
public class TableContext {
	
	/**
	 * 表名为key，表信息对象为value
	 */
	public static Map<String,TableInfo> tables = new HashMap<String, TableInfo>();
	
	/**
	 * 将po的class对象和表信息对象关联起来，便于重用！
	 */
	public static Map<Class,TableInfo> poClassTableMap = new HashMap<Class, TableInfo>();
	
	private TableContext() {}
	
	static {
		
		
		try {
			//初始化获得表信息
			Connection con = DBManager.getConn();
			DatabaseMetaData dbmb = con.getMetaData();
			
			ResultSet tableRet = dbmb.getTables(null, "%", "%", new String[] {"Table"});
			
			while(tableRet.next()) {
				String tableName = (String) tableRet.getObject("TABLE_NAME");
				
				TableInfo ti = new TableInfo(tableName, new ArrayList<ColumnInfo>(), 
						new HashMap<String, ColumnInfo>());
				tables.put(tableName, ti);
				
				ResultSet set = dbmb.getColumns(null, "%", tableName, "%");	//查询表中的所有字段
				while(set.next()) {
					ColumnInfo ci = new ColumnInfo(set.getString("COLUMN_NAME"),
							set.getString("TYPE_NAME"),0);
					ti.getColumn().put(set.getString("COLUMN_NAME"), ci);
				}
				
				ResultSet set2 = dbmb.getPrimaryKeys(null, "%", tableName);	//查询表中的所有主键
				while(set2.next()) {
					ColumnInfo ci2 = (ColumnInfo)ti.getColumn().get(set2.getObject("COLUMN_NAME"));
					ci2.setKeyType(1);	//设置为主键类型
					ti.getPriKeys().add(ci2);
				}
				
				if(ti.getPriKeys().size()>0) {	//取唯一主键。方便使用。如果是联合主键，则为空
					ti.setOnlyPriKey(ti.getPriKeys().get(0));
				}
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		//每次启动均更新类结构
		updatePoFile();
		
		//加载po包下所有的类，便于重用，提高效率
		loadPoTables();
	}
	
//	public static Map<String, TableInfo> getTableInfos(){
//		return tables;
//	}

	/**
	 * 根据表结构，更新配置的po包下的java类
	 * 实现了从表结构转化为类结构
	 */
	public static void updatePoFile() {
		Map<String,TableInfo> map = TableContext.tables;
		for(TableInfo t:map.values()) {		//遍历数据库的所有表
			JavaFileUtils.createJavaPoFile(t, new MySqlTypeConvertor());
		}
	}
	
	/**
	 * 加载po包下面的类
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
