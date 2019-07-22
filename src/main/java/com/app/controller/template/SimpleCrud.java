package com.app.controller.template;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.app.controller.SingleTemplateController;

public abstract class SimpleCrud extends SingleTemplateController {
	

	public abstract  DataTablesWidget init();
	
	
	public String index(HttpServletRequest request, Model model) {
		String data= super.index(request, model);
		DataTablesWidget widget = init();
		String target = getTarget(request, "simplecrud");
		model.addAttribute("titleprovider", widget.getTitle());
		List<String> headers = widget.getHeaders();	
		model.addAttribute("headers", headers);
		model.addAttribute("table_url",widget.getDestination());
		model.addAttribute("button","true");
		model.addAttribute("providercontent","fragments/"+target);
		return data;
	}

	
	
}
