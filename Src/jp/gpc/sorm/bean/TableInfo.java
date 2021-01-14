package jp.gpc.sorm.bean;

import java.util.List;
import java.util.Map;

/**
 * 存储表结构的信息
 * @author G
 *
 */
public class TableInfo {


	/**
	 * 表名
	 */
	private String tname;

	/**
	 * 所有字段的信息
	 * key是字段名称
	 * value是字段信息
	 */
	private Map<String, ColumnInfo>column;

	/**
	 * 唯一主键（目前版本仅处理表中有且仅有一个主键的情况）
	 */
	private ColumnInfo onlyPriKey;
	
	/**
	 * 备用，如果有联合主键，则存储在此List
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
