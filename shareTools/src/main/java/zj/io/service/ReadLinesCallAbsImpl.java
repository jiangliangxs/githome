package zj.io.service;

import java.util.Collection;


public abstract class ReadLinesCallAbsImpl<T extends Collection<String>> implements ReadLinesCallI<T> {
	private long startLineNum;
	private long endLineNum;
	public boolean filter(String line, long lineNum){
		return false;
	}

	public boolean interrupt(String line, long lineNum) {
		return false;
	}

	public String changeLine(String line, long lineNum) {
		return line;
	}

	public long getStartLineNum() {
		return startLineNum;
	}

	public void setStartLineNum(long startLineNum) {
		this.startLineNum = startLineNum;
	}

	public long getEndLineNum() {
		return endLineNum;
	}

	public void setEndLineNum(long endLineNum) {
		this.endLineNum = endLineNum;
	}
	
}
