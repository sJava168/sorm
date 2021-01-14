package jp.gpc.sorm.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jp.gpc.po.T_user;
import jp.gpc.sorm.bean.ColumnInfo;
import jp.gpc.sorm.bean.TableInfo;
import jp.gpc.sorm.util.JDBCUtils;
import jp.gpc.sorm.util.ReflectUtils;
import jp.gpc.sorm.util.StringUtils;

/**
 * 负责针对Mysql数据库的查询
 * @author G
 *
 */
public class MySqlQuery implements Query{
	
	public static void main(String[] args) {
		T_user tuser = new T_user();
		
//		tuser.setId(5);		
//		new MySqlQuery().delete(tuser);
		
//		tuser.setUsername("qian");
//		tuser.setPwd("97897");
//		tuser.setCorectDate(new java.sql.Timestamp(System.currentTimeMillis()));
//		new MySqlQuery().insert(tuser);
		
//		tuser.setId(2);
//		tuser.setPwd("234098");
//		tuser.setCorectDate(null);
//		new MySqlQuery().update(tuser, new String[] {"pwd","corectDate"});
		
//		List<T_user> list = new MySqlQuery().queryRows("select id,username,pwd,age from t_user where id>? and age<?",
//				T_user.class, new Object[] {2,20});
//		System.out.println(list);
//		for(T_user tu:list) {
//			System.out.println(tu.getUsername());
//		}
		
//		Object obj = new MySqlQuery().queryValue("select count(*) from t_user where age>?", new Object[] {20});
//		System.out.println(obj);
		
		Number obj = new MySqlQuery().queryNumber("select count(*) from t_user where age>?", new Object[] {20});
		System.out.println(obj.doubleValue());
		
	}

	@Override
	public int executeDML(String sql, Object[] params) {
		Connection conn = DBManager.getConn();
		int count = 0;
		
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			
			//给sql设置参数
			JDBCUtils.handleParams(ps, params);
			
			count = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(ps, conn);
		}
		
		return count;
	}

	@Override
	public void insert(Object obj) {
		// obj-->表中。 insert into t_user (username, pwd ) values (?, ?)
		
		//通过反射获取obj相应的表信息
		Class c = obj.getClass();
		TableInfo tableInfo = TableContext.poClassTableMap.get(c);
		
		List<Object> params = new ArrayList<Object>();	//存储sql参数对象
		StringBuilder sql = new StringBuilder("insert into "+tableInfo.getTname()+" ("); 
		int countNotNullField = 0;	//计算不为空的属性数目
		
		Field[] fs = c.getDeclaredFields();
		for(Field f:fs) {
			String fieldName = f.getName();
			Object fieldValue = ReflectUtils.invokeGet(fieldName, obj);
			
			if(fieldValue!=null) {
				countNotNullField++;
				sql.append(fieldName+",");
				params.add(fieldValue);
			}
		}
		
		sql.setCharAt(sql.length()-1, ')');
		sql.append(" values (");
		for(int i=0;i<countNotNullField;i++) {
			sql.append("?,");
		}
		sql.setCharAt(sql.length()-1, ')');
		
		executeDML(sql.toString(), params.toArray());
	}

	@Override
	public void delete(Class clazz, Object id) {
		// t_user.class,5-->delete from t_user where id=5

		// 通过Class对象找TableInfo
		TableInfo tableInfo = TableContext.poClassTableMap.get(clazz);
		//获得主键
		ColumnInfo onlyPrikey = tableInfo.getOnlyPriKey();

		String sql = "delete from "+tableInfo.getTname()+" where "+onlyPrikey.getName()+"=? ";
		
		executeDML(sql, new Object[] {id});
	}

	@Override
	public void delete(Object obj) {
		Class c = obj.getClass();
		TableInfo tableInfo = TableContext.poClassTableMap.get(c);
		//获得主键
		ColumnInfo onlyPrikey = tableInfo.getOnlyPriKey();
	
		//通过反射机制，调用属性对应的get方法
		Object priKeyValue = ReflectUtils.invokeGet(onlyPrikey.getName(), obj);
		
		delete(c, priKeyValue);
		
	}

	@Override
	public int update(Object obj, String[] fieldNames) {
		// obj{"username,pwd"}-->update t_user set username=?,pwd=? where id=? 
		
		Class c = obj.getClass();
		List<Object> params = new ArrayList<Object>();
		TableInfo tableInfo = TableContext.poClassTableMap.get(c);
		ColumnInfo priKey = tableInfo.getOnlyPriKey();		//获得唯一主键
		StringBuilder sql = new StringBuilder("update "+tableInfo.getTname()+" set ");
		
		for(String fname:fieldNames) {
			Object fvalue = ReflectUtils.invokeGet(fname, obj);
			params.add(fvalue);
			sql.append(fname+"=?,");
		}
		sql.setCharAt(sql.length()-1, ' ');
		sql.append("where "+priKey.getName()+"=? ");
		params.add(ReflectUtils.invokeGet(priKey.getName(), obj));	//主键的值
		
		return executeDML(sql.toString(), params.toArray());
	}

	@Override
	public List queryRows(String sql, Class clazz, Object[] params) {
		Connection conn = DBManager.getConn();
		List list =null;	//存放查询结果的容器
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			//给sql设置参数
			JDBCUtils.handleParams(ps, params);
			System.out.println(ps);
			rs = ps.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();	//获取查询数据的列信息
			
			//多行
			while(rs.next()) {
				
				if(null==list) {
					list = new ArrayList();
//					System.out.println("结果未查到！");
				}
				
				Object rowObj = clazz.newInstance();	//调用javabean的无参构造器
				
				//多列	select username,pwd,age from t_user where id>? and age>18 
				for(int i=0;i<metaData.getColumnCount();i++) {
					String columnName = metaData.getColumnLabel(i+1);	//username
					Object columnValue = rs.getObject(i+1);
					
					//调用rowObj对象的setUsername(String username)方法，将columnValue的值设置进去
//					Method m = clazz.getDeclaredMethod("set"+StringUtils.firstChar2UpperCase(columnName), 
//							columnValue.getClass());
//					m.invoke(rowObj, columnValue);
					
					ReflectUtils.invlkeSet(rowObj, columnName, columnValue);
					
				}
				
				list.add(rowObj);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(rs, ps, conn);
		}
		
		return list;
	}

	@Override
	public Object queryUniqueRow(String sql, Class clazz, Object[] params) {
		
		List list = queryRows(sql, clazz, params);
		
		return (list==null&&list.size()>0)?null:list.get(0);
	}

	@Override
	public Object queryValue(String sql, Object[] params) {
		Connection conn = DBManager.getConn();
		Object value = null; // 存放查询结果的对象
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			// 给sql设置参数
			JDBCUtils.handleParams(ps, params);
			System.out.println(ps);
			rs = ps.executeQuery();
			while (rs.next()) {
				// select count(*) from t_user
				value = rs.getObject(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(rs, ps, conn);
		}
		return value;
	}

	@Override
	public Number queryNumber(String sql, Object[] params) {
		
		return (Number)queryValue(sql, params);
	}

	
}
