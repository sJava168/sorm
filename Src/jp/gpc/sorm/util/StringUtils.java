package jp.gpc.sorm.util;

/**
 * ��װ���ַ������õĲ���
 * @author G
 *
 */
public class StringUtils {

	/**
	 * ��Ŀ���ַ�������ĸ��Ϊ��д 
	 * @param str	Ŀ���ַ���
	 * @return	����ĸ�ѱ�Ϊ��д���ַ���
	 */
	public static String firstChar2UpperCase(String str) {
		
		//abcd-->Abcd
		//abcd-->ABCD-->A+bcd-->Abcd
		return str.toUpperCase().substring(0, 1)+str.substring(1);
		
	}
}
