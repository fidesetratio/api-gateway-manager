package com.app.controller.template;

public class SelectInput extends FormInput {

	public SelectInput(String label, String name) {
		super(label, name);
		setInputType(FormInput.SELECT);
	}

}
