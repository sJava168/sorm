package jp.gpc.sorm.bean;


/**
 * 管理配置信息
 * @author G
 *
 */
public class Configuration {

	/**
	 * 驱动类
	 */
	private String driver;
	/**
	 * JDBC的URL
	 */
	private String url;
	/**
	 * 数据库的用户名
	 */
	private String user;
	/**
	 * 数据库的密码
	 */
	private String pwd;
	/**
	 * 正在使用的数据裤类型
	 */
	private String usingDB;
	/**
	 * 项目的源码路径
	 */
	private String srcParth;
	/**
	 * 扫描生成Java类的包（PO即Persistence Object持久化对象，与表对应）
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
