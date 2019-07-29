package com.app.controller.template;

public class SelectValue {
	
	private String label;
	
	private String value;
	
	public SelectValue(String label,String value){
		this.label = label;
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
