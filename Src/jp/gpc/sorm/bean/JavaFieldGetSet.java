package jp.gpc.sorm.bean;

/**
 * ��װ��Java���Ժ�get��set������Դ����
 * @author G
 *
 */
public class JavaFieldGetSet {
	
	/**
	 * ���Ե�Դ����Ϣ���磺private int userId
	 */
	private String fieldInfo;
	
	/**
	 * get������Դ����Ϣ���磺public int getuserId() {}
	 */
	private String getInfo;
	
	/**
	 * set������Դ����Ϣ���磺public void setuserId(int id) {this.id = id;}
	 */
	private String setInfo;
	
	
	@Override
	//��дtoString�������ڵ���
	public String toString() {
		System.out.println(fieldInfo);
		System.out.println(getInfo);
		System.out.println(setInfo);
		return super.toString();
	}

	public String getFieldInfo() {
		return fieldInfo;
	}

	public void setFieldInfo(String fieldInfo) {
		this.fieldInfo = fieldInfo;
	}

	public String getGetInfo() {
		return getInfo;
	}

	public void setGetInfo(String getInfo) {
		this.getInfo = getInfo;
	}

	public String getSetInfo() {
		return setInfo;
	}

	public void setSetInfo(String setInfo) {
		this.setInfo = setInfo;
	}

	public JavaFieldGetSet(String fieldInfo, String getInfo, String setInfo) {
		super();
		this.fieldInfo = fieldInfo;
		this.getInfo = getInfo;
		this.setInfo = setInfo;
	}

	
	public JavaFieldGetSet() {
		
	}
	
	
}