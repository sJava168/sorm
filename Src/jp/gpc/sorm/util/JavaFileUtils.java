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
 * ��װ������Java�ļ���Դ���룩���õĲ���
 * @author G
 *
 */
public class JavaFileUtils {
	
	/**
	 * �����ֶ���Ϣ����Java������Ϣ���磺varchar username-->private String username;�Լ���Ӧ��get/set������Դ��
	 * @param column	�ֶ���Ϣ
	 * @param convertor		����ת����
	 * @return		����Java���Ժ�get/setԴ��
	 */
	public static JavaFieldGetSet creatFieldGetSetSRC(ColumnInfo column, TypeConvertor convertor) {
		
		JavaFieldGetSet jfgs = new JavaFieldGetSet();
		
		String javaFieldType = convertor.databaseType2JavaType(column.getDataType());
		
		//Ϊ�˴������ۣ�ǰ������\tһ���Ʊ�����󷽼���\n���з���
		jfgs.setFieldInfo("\tprivate "+javaFieldType+" "+column.getName()+";\n");
		
		//	public String getFieldInfo() {return fieldInfo;}
		//����get����Դ����
		StringBuilder getSrc = new StringBuilder();
		getSrc.append("\tpublic "+javaFieldType+" get"+StringUtils.firstChar2UpperCase(column.getName())+"(){\n");
		getSrc.append("\t\treturn "+column.getName()+";\n");
		getSrc.append("\t}\n");
		jfgs.setGetInfo(getSrc.toString());

		//public void setFieldInfo(String fieldInfo) {this.fieldInfo = fieldInfo;}
		//����set����Դ����
		StringBuilder setSrc = new StringBuilder();
		setSrc.append("\tpublic void set" + StringUtils.firstChar2UpperCase(column.getName()) + "(");
		setSrc.append(javaFieldType+" "+column.getName()+"){\n");
		setSrc.append("\t\tthis." + column.getName() +" = "+column.getName()+ ";\n");
		setSrc.append("\t}\n");
		jfgs.setSetInfo(setSrc.toString());
		
		return jfgs;
	}
	
	/**
	 * ���ݱ���Ϣ����Java���Դ����
	 * @param tableInfo		����Ϣ
	 * @param convertor		��������ת����
	 * @return		java���Դ����
	 */
	public static String creatJavaSrc(TableInfo tableInfo, TypeConvertor convertor) {
		
		Map<String, ColumnInfo> columns = tableInfo.getColumn();
		List<JavaFieldGetSet> javaFields = new ArrayList<JavaFieldGetSet>();
		
		for(ColumnInfo c: columns.values()) {
			javaFields.add(creatFieldGetSetSRC(c, convertor));
		}
		
		StringBuilder src = new StringBuilder();
		//����package���
		src.append("package "+DBManager.getConf().getPoPackage()+";\n\n");
		//����import���
		src.append("import java.sql.*;\n");
		src.append("import java.util.*;\n\n");
		
		//�������������
		src.append("public class "+StringUtils.firstChar2UpperCase(tableInfo.getTname()+" {\n\n"));
		//���������б�
		for(JavaFieldGetSet f: javaFields) {
			src.append(f.getFieldInfo());
		}
		src.append("\n\n");
		//����get�����б�
		for(JavaFieldGetSet f: javaFields) {
			src.append(f.getGetInfo());
		}
		//����set�����б�
		for(JavaFieldGetSet f: javaFields) {
			src.append(f.getSetInfo());
		}
		//�����������
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
		if(!f.exists()) {	//���ָ��Ŀ¼�����ڣ����½�
			f.mkdirs(); 
		}
		
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(f.getAbsolutePath()+"/"
		+StringUtils.firstChar2UpperCase(tableInfo.getTname())+".java"));
			bw.write(src);
			System.out.println("������"+tableInfo.getTname()
			+"��Ӧ��java�ࣺ"+StringUtils.firstChar2UpperCase(tableInfo.getTname())+".java");
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
//		ColumnInfo ci = new ColumnInfo("id","int",0);	ת��ָ���ֶ���Ϣ
//		JavaFieldGetSet f = creatFieldGetSetSRC(ci, new MySqlTypeConvertor());
//		System.out.println(f);
		
		Map<String,TableInfo> map = TableContext.tables;
		for(TableInfo t:map.values()) {		//�������ݿ�����б�
			createJavaPoFile(t, new MySqlTypeConvertor());
		}
//		TableInfo t = map.get("t_user");	ֻ��ȡһ�ű�
		
	}
	

}
