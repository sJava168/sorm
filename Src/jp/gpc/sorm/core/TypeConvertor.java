package jp.gpc.sorm.core;

/**
 * ����Java�������ͺ����ݿ����͵Ļ���ת��
 * @author G
 *
 */

public interface TypeConvertor {

	/**
	 * �����ݿ���������ת����Java��������
	 * @param columnType	���ݿ��ֶε���������
	 * @return		Java����������
	 */
	public String databaseType2JavaType(String columnType);

	/**
	 * ��Java��������ת�������ݿ���������
	 * @param columnType	Java��������
	 * @return		���ݿ���������
	 */
	public String javaType2DatabaseType(String javaDataType);





}
