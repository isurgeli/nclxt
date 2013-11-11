package nc.itf.lxt.pub.sqltool;

import java.util.Comparator;

public class SQLTable implements Comparator<SQLTable>{
	private String key;
	private String name;
	private int level;
	
	public SQLTable(String _key, String _name) {
		key = _key;
		name = _name;
	}

	public String getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public int compare(SQLTable p0, SQLTable p1) {
		if (p0.level<p1.level)
			return -1;
		else if (p0.level>p1.level)
			return 1;
		else if (p0.key != null && p1.key != null)
			return p0.key.compareTo(p1.key);
		else
			return p0.name.compareTo(p1.name);
	}
}
