package jp.gpc.sorm.bean;

import java.util.List;
import java.util.Map;

/**
 * �洢��ṹ����Ϣ
 * @author G
 *
 */
public class TableInfo {


	/**
	 * ����
	 */
	private String tname;

	/**
	 * �����ֶε���Ϣ
	 * key���ֶ�����
	 * value���ֶ���Ϣ
	 */
	private Map<String, ColumnInfo>column;

	/**
	 * Ψһ������Ŀǰ�汾������������ҽ���һ�������������
	 */
	private ColumnInfo onlyPriKey;
	
	/**
	 * ���ã������������������洢�ڴ�List
	 */
	private List<ColumnInfo> priKeys;



	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public Map<String, ColumnInfo> getColumn() {
		return column;
	}

	public void setColumn(Map<String, ColumnInfo> column) {
		this.column = column;
	}

	public ColumnInfo getOnlyPriKey() {
		return onlyPriKey;
	}

	public void setOnlyPriKey(ColumnInfo onlyPriKey) {
		this.onlyPriKey = onlyPriKey;
	}
	
	public List<ColumnInfo> getPriKeys() {
		return priKeys;
	}

	public void setPriKeys(List<ColumnInfo> priKeys) {
		this.priKeys = priKeys;
	}


	public TableInfo(String tname, Map<String, ColumnInfo> column, ColumnInfo onlyPriKey, List<ColumnInfo> priKeys) {
		super();
		this.tname = tname;
		this.column = column;
		this.onlyPriKey = onlyPriKey;
		this.priKeys = priKeys;
	}

	public TableInfo() {

	}

	public TableInfo(String tname, List<ColumnInfo> priKeys, Map<String, ColumnInfo> column) {
		super();
		this.tname = tname;
		this.column = column;
		this.priKeys = priKeys;
	}
	
	




}
