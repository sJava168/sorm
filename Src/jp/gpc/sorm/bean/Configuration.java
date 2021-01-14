package jp.gpc.sorm.bean;


/**
 * ����������Ϣ
 * @author G
 *
 */
public class Configuration {

	/**
	 * ������
	 */
	private String driver;
	/**
	 * JDBC��URL
	 */
	private String url;
	/**
	 * ���ݿ���û���
	 */
	private String user;
	/**
	 * ���ݿ������
	 */
	private String pwd;
	/**
	 * ����ʹ�õ����ݿ�����
	 */
	private String usingDB;
	/**
	 * ��Ŀ��Դ��·��
	 */
	private String srcParth;
	/**
	 * ɨ������Java��İ���PO��Persistence Object�־û���������Ӧ��
	 */
	private String poPackage;
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getUsingDB() {
		return usingDB;
	}
	public void setUsingDB(String usingDB) {
		this.usingDB = usingDB;
	}
	public String getSrcParth() {
		return srcParth;
	}
	public void setSrcParth(String srcParth) {
		this.srcParth = srcParth;
	}
	public String getPoPackage() {
		return poPackage;
	}
	public void setPoPackage(String poPackage) {
		this.poPackage = poPackage;
	}
	
	
	public Configuration(String driver, String url, String user, String pwd, String usingDB, String srcParth,
			String poPackage) {
		super();
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.pwd = pwd;
		this.usingDB = usingDB;
		this.srcParth = srcParth;
		this.poPackage = poPackage;
	}
	
	public Configuration() {
		
	}
	
	

}
