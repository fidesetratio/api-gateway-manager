package com.app.controller.template;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.app.controller.ClientController;
import com.app.controller.SingleTemplateController;
import com.app.controller.datatables.models.PaginationCriteria;
import com.app.controller.datatables.models.TablePage;
import com.app.rest.model.ClientDetails;
import com.app.rest.model.EntityResponse;

public abstract class SimpleCrud extends SingleTemplateController {
	
	private static Logger logger = LoggerFactory.getLogger(SimpleCrud.class);
	
	public abstract  DataTablesWidget init();
	public abstract  TablePage listsPage(PaginationCriteria treq);
	
	
	public String index(HttpServletRequest request, Model model) {
		String data= super.index(request, model);
		DataTablesWidget widget = init();
		String target = getTarget(request, "simplecrud");
		model.addAttribute("titleprovider", widget.getTitle());
		List<String> headers = widget.getHeaders();	
		model.addAttribute("headers", headers);
		model.addAttribute("table_url",widget.getDestination());
		model.addAttribute("button",widget.isUseButton());
		model.addAttribute("typeForm",widget.getTypeForm());
		model.addAttribute("selectInput",widget.getSelectInput());
		model.addAttribute("formSearch01",new ArrayList<FormInput>());
		model.addAttribute("formSearch02",new ArrayList<FormInput>());
		model.addAttribute("formSearch03",new ArrayList<FormInput>());
		
		if(widget.getSearchForm().size()>0 && widget.getSearchForm().size()<3){
			int max = widget.getSearchForm().size();
			model.addAttribute("formSearch01", widget.getSearchForm().subList(0, max));
		};

		if(widget.getSearchForm().size()>=3 && widget.getSearchForm().size()<=5){
			int max = widget.getSearchForm().size();
			model.addAttribute("formSearch01", widget.getSearchForm().subList(0, 3));
			model.addAttribute("formSearch02", widget.getSearchForm().subList(3,max));
		};
		if(widget.getSearchForm().size()>5 && widget.getSearchForm().size()<=10){
			int max = widget.getSearchForm().size();
			model.addAttribute("formSearch01", widget.getSearchForm().subList(0, 3));
			model.addAttribute("formSearch02", widget.getSearchForm().subList(3,6));
			model.addAttribute("formSearch03", widget.getSearchForm().subList(6,max));
			
		};
		
		model.addAttribute("providercontent","fragments/"+target);
		return data;
	}
	
	
	
	
	@RequestMapping(value="/lists",method=RequestMethod.POST,produces="application/json")
	public @ResponseBody TablePage lists(@RequestBody PaginationCriteria treq) {
		return listsPage(treq);
	}
	

	
/*	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model){
			return "";
	}*/
	

	
	
}
