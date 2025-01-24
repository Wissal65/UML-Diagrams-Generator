package org.mql.java.analyzer.models;

public class RelationShips {

	private String source;
	private String target;
	private String type;

	public RelationShips(String target, String type, String source) {
		this.source = source;
		this.target = target;
		this.type = type;

	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
