package com.app.controller.template;

import java.util.ArrayList;
import java.util.List;

public class FormInput {



	private String label;
	private String name;
	private int inputType;
	
	public static final int INPUT = 1;

	public static final int SELECT = 2;
	
	private List<SelectValue> selectValues;
	
	public FormInput(String label, String name, int inputType) {
		super();
		this.label = label;
		this.name = name;
		this.inputType = inputType;
		this.selectValues = new ArrayList<SelectValue>();
	}

	public FormInput(String label, String name) {
		super();
		this.label = label;
		this.name = name;
		this.inputType = INPUT;
		this.selectValues = new ArrayList<SelectValue>();
	}

	
	public void addSelect(String name,String value){
		if(this.selectValues != null){
			this.inputType = SELECT;
			SelectValue v = new SelectValue(name, value);
			this.selectValues.add(v);
		}
	};
	
	
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getInputType() {
		return inputType;
	}

	public void setInputType(int inputType) {
		this.inputType = inputType;
	}

	public List<SelectValue> getSelectValues() {
		return selectValues;
	}

	public void setSelectValues(List<SelectValue> selectValues) {
		this.selectValues = selectValues;
	}
}
