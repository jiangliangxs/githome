package test.xml.auto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Tables")
public class Tables {
	// 表
	private List<Table> table;

	public List<Table> getTable() {
		return table;
	}

	public void setTable(List<Table> table) {
		this.table = table;
	}

}
