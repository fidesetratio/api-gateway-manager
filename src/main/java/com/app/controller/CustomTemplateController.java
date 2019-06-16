package com.app.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.controller.datatables.SimplePaginator;
import com.app.controller.datatables.TablePaginator;
import com.app.controller.datatables.models.PaginationCriteria;
import com.app.controller.datatables.models.TablePage;
import com.app.rest.model.ConfigPaging;
import com.app.rest.model.FormModelTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/customtemplate")
public class CustomTemplateController extends SingleTemplateController {

	public String index(HttpServletRequest request, Model model) {
		String data= super.index(request, model);
		String target = getTarget(request, "single-list");
		model.addAttribute("titleprovider", "Jagiring");
		model.addAttribute("providercontent","fragments/"+target);
		return data;
	}
	
	
	@RequestMapping("/singlelist")
	public String singlelist(HttpServletRequest request, Model model) {
		try {
			Integer page  = ServletRequestUtils.getIntParameter(request, "page");
			model.addAttribute("totalpage",page);

			System.out.println("page1="+page);
		} catch (ServletRequestBindingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		String data= super.index(request, model);
		String target = getTarget(request, "single-list2");
		model.addAttribute("titleprovider", "Jagiring");
		model.addAttribute("providercontent","fragments/"+target);
		return "fragments/"+target;
	}
	
	
	@RequestMapping("/add")
	public String add(Model model) {
		FormModelTest formModelTest = new FormModelTest();
		
		model.addAttribute("formModelTest",formModelTest);
		return "fragments/single-form-add3";
	}
	@RequestMapping("/addtwo")
	public String addtwo(Model model) {
		FormModelTest formModelTest = new FormModelTest();
		
		model.addAttribute("formModelTest",formModelTest);
		return "fragments/single-form-add";
	}
	

	@RequestMapping("/update")
	public String update( @RequestParam(value="clientId",required=false) Long clientId,Model model) {
		FormModelTest formModelTest = new FormModelTest();
		if(clientId != null) {
			formModelTest.setDescription("Description example");
			formModelTest.setFirstName("firstName");
		}
		model.addAttribute("formModelTest",formModelTest);
		return "fragments/single-form-update";
	}
	
	@RequestMapping(value="/submitupdate",method=RequestMethod.POST)
	public String submitupdate(@Valid @ModelAttribute("formModelTest")  FormModelTest formModelTest, BindingResult bindingResult, Model model) {
		System.out.println(formModelTest.getFirstName());
		
		
		if (bindingResult.hasErrors()) {
		//	System.out.println("error");
			model.addAttribute("formModelTest",formModelTest);
			
		}
		return "fragments/single-form-update";
	}
	
	
	
	
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public String delete(@RequestParam(value="clientId",required=false) Long clientId) {
		System.out.println("clientId:"+clientId);
		return "fragments/ok";
		
	}
	
	
	@RequestMapping(value="/totalpage",method=RequestMethod.GET,produces="application/json")
	public @ResponseBody String totalpage(@RequestParam(value="clientId",required=false) Long clientId) {
		System.out.println("clientId:"+clientId);
		ObjectMapper objectMapper = new ObjectMapper();
		ConfigPaging totalPage = new ConfigPaging(10, 5,"/customtemplate/singlelist");
		String ret  = null;
		try {
			ret = objectMapper.writeValueAsString(totalPage);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
		
	}
	
	//https://www.opencodez.com/java/datatable-with-spring-boot.htm
	@RequestMapping(value="/products",method=RequestMethod.POST,produces="application/json")
	public @ResponseBody TablePage products(@RequestBody PaginationCriteria treq) {
		System.out.println("patar");
		TablePage tablePage =  paginator.getPage(treq);
		
		System.out.println(tablePage.getData());
		return tablePage;
	}
	
	
	
	private TablePaginator paginator = new SimplePaginator(
			new UserTableRepository());
  public static class Container{
	  private String draw;
	  private Integer recordsTotal;
	  private Integer recordsFiltered;
	  private List data;
	  
	  
	  
	public Container(String draw, Integer recordsTotal, Integer recordsFiltered) {
		super();
		this.draw = draw;
		this.recordsTotal = recordsTotal;
		this.recordsFiltered = recordsFiltered;
	}
	public String getDraw() {
		return draw;
	}
	public void setDraw(String draw) {
		this.draw = draw;
	}
	public Integer getRecordsTotal() {
		return recordsTotal;
	}
	public void setRecordsTotal(Integer recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
	public Integer getRecordsFiltered() {
		return recordsFiltered;
	}
	public void setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}
	public List getData() {
		return data;
	}
	public void setData(List data) {
		this.data = data;
	}
	  
	  
		  

  }
}
