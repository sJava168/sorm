package jp.gpc.sorm.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.gpc.sorm.bean.ColumnInfo;
import jp.gpc.sorm.bean.JavaFieldGetSet;
import jp.gpc.sorm.bean.TableInfo;
import jp.gpc.sorm.core.DBManager;
import jp.gpc.sorm.core.MySqlTypeConvertor;
import jp.gpc.sorm.core.TableContext;
import jp.gpc.sorm.core.TypeConvertor;

/**
 * 封装了生成Java文件（源代码）常用的操作
 * @author G
 *
 */
public class JavaFileUtils {
	
	/**
	 * 根据字段信息生成Java属性信息。如：varchar username-->private String username;以及相应的get/set方法的源码
	 * @param column	字段信息
	 * @param convertor		类型转化器
	 * @return		返回Java属性和get/set源码
	 */
	public static JavaFieldGetSet creatFieldGetSetSRC(ColumnInfo column, TypeConvertor convertor) {
		
		JavaFieldGetSet jfgs = new JavaFieldGetSet();
		
		String javaFieldType = convertor.databaseType2JavaType(column.getDataType());
		
		//为了代码美观，前方加入\t一个制表符，后方加入\n换行符。
		jfgs.setFieldInfo("\tprivate "+javaFieldType+" "+column.getName()+";\n");
		
		//	public String getFieldInfo() {return fieldInfo;}
		//生成get方法源代码
		StringBuilder getSrc = new StringBuilder();
		getSrc.append("\tpublic "+javaFieldType+" get"+StringUtils.firstChar2UpperCase(column.getName())+"(){\n");
		getSrc.append("\t\treturn "+column.getName()+";\n");
		getSrc.append("\t}\n");
		jfgs.setGetInfo(getSrc.toString());

		//public void setFieldInfo(String fieldInfo) {this.fieldInfo = fieldInfo;}
		//生成set方法源代码
		StringBuilder setSrc = new StringBuilder();
		setSrc.append("\tpublic void set" + StringUtils.firstChar2UpperCase(column.getName()) + "(");
		setSrc.append(javaFieldType+" "+column.getName()+"){\n");
		setSrc.append("\t\tthis." + column.getName() +" = "+column.getName()+ ";\n");
		setSrc.append("\t}\n");
		jfgs.setSetInfo(setSrc.toString());
		
		return jfgs;
	}
	
	/**
	 * 根据表信息生成Java类的源代码
	 * @param tableInfo		表信息
	 * @param convertor		数据类型转化器
	 * @return		java类的源代码
	 */
	public static String creatJavaSrc(TableInfo tableInfo, TypeConvertor convertor) {
		
		Map<String, ColumnInfo> columns = tableInfo.getColumn();
		List<JavaFieldGetSet> javaFields = new ArrayList<JavaFieldGetSet>();
		
		for(ColumnInfo c: columns.values()) {
			javaFields.add(creatFieldGetSetSRC(c, convertor));
		}
		
		StringBuilder src = new StringBuilder();
		//生成package语句
		src.append("package "+DBManager.getConf().getPoPackage()+";\n\n");
		//生成import语句
		src.append("import java.sql.*;\n");
		src.append("import java.util.*;\n\n");
		
		//生成类声明语句
		src.append("public class "+StringUtils.firstChar2UpperCase(tableInfo.getTname()+" {\n\n"));
		//生成属性列表
		for(JavaFieldGetSet f: javaFields) {
			src.append(f.getFieldInfo());
		}
		src.append("\n\n");
		//生成get方法列表
		for(JavaFieldGetSet f: javaFields) {
			src.append(f.getGetInfo());
		}
		//生成set方法列表
		for(JavaFieldGetSet f: javaFields) {
			src.append(f.getSetInfo());
		}
		//生成类结束符
		src.append("}\n");
//		System.out.println(src);
		return src.toString();
		
	}
	
	/**
	 * @param tableInfo
	 * @param convertor
	 */
	public static void createJavaPoFile(TableInfo tableInfo, TypeConvertor convertor) {
		
		String src = creatJavaSrc(tableInfo, convertor);
		String srcParth = DBManager.getConf().getSrcParth()+"\\";
		String packageParth = DBManager.getConf().getPoPackage().replaceAll("\\.", "/");
		
		File f = new File(srcParth+packageParth);
		if(!f.exists()) {	//如果指定目录不存在，则新建
			f.mkdirs(); 
		}
		
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(f.getAbsolutePath()+"/"
		+StringUtils.firstChar2UpperCase(tableInfo.getTname())+".java"));
			bw.write(src);
			System.out.println("建立表"+tableInfo.getTname()
			+"对应的java类："+StringUtils.firstChar2UpperCase(tableInfo.getTname())+".java");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != bw) {
					bw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		
	}
	
	
	public static void main(String[] args) {
//		ColumnInfo ci = new ColumnInfo("id","int",0);	转换指定字段信息
//		JavaFieldGetSet f = creatFieldGetSetSRC(ci, new MySqlTypeConvertor());
//		System.out.println(f);
		
		Map<String,TableInfo> map = TableContext.tables;
		for(TableInfo t:map.values()) {		//遍历数据库的所有表
			createJavaPoFile(t, new MySqlTypeConvertor());
		}
//		TableInfo t = map.get("t_user");	只读取一张表
		
	}
	

}
