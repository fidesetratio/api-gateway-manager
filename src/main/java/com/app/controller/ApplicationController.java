package com.app.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.app.controller.datatables.SimplePaginator;
import com.app.controller.datatables.TablePaginator;
import com.app.controller.datatables.models.PaginationCriteria;
import com.app.controller.datatables.models.TablePage;
import com.app.controller.template.DataTablesWidget;
import com.app.controller.template.FormInput;
import com.app.manager.model.Application;
import com.app.manager.model.AuthenticationProvider;
import com.app.manager.model.Link;
import com.app.manager.model.RoleCategory;
import com.app.manager.model.Roles;
import com.app.manager.repo.ApplicationRepository;
import com.app.manager.repo.AuthenticationProviderRepository;
import com.app.manager.repo.LinkRepository;
import com.app.manager.repo.RolesCategoriesRepository;
import com.app.services.LinkServices;


@Controller
@RequestMapping("/application")
public class ApplicationController extends SingleTemplateController{

	@Autowired
	private AuthenticationProviderRepository authenticationProviderRepository;

	
	@Autowired
	private Environment env;
	
	@Autowired
	private LinkRepository repo;
	
	@Autowired
	private RolesCategoriesRepository roleCategories;
	

	@Autowired
	private ApplicationRepository applicationRepo;
	

	@Autowired
	private LinkRepository linkRepository;

	
	
	public String index(HttpServletRequest request, Model model) {
		String data= super.index(request, model);
		String target="application";
		model.addAttribute("titleprovider", "Application");
		model.addAttribute("providercontent","fragments/"+target);
		return data;
	}

	
	@RequestMapping(value="/addForm",method=RequestMethod.GET)
	public String addForm(Model model){
			System.out.println("patar");
			model.addAttribute("application", new Application());
			List<AuthenticationProvider> listAuthenticationProvider = (List<AuthenticationProvider>)authenticationProviderRepository.findAll();
			model.addAttribute("listProviders",listAuthenticationProvider);		
			List<RoleCategory> listCategories = (List<RoleCategory>)roleCategories.findAll();
			model.addAttribute("listCategories",listCategories);
			return "fragments/addapplication";
	};
	
	
	@RequestMapping(value="/addForm/submit",method=RequestMethod.POST)
	public String addFormSubmit(@Valid @ModelAttribute("application")  Application application, BindingResult bindingResult, Model model){
			System.out.println("patar submit");			
			if (bindingResult.hasErrors()) {
				for(ObjectError er:bindingResult.getAllErrors()) {
					System.out.println("error:"+er.getDefaultMessage());
				}
				model.addAttribute("application", application);
				List<AuthenticationProvider> listAuthenticationProvider = (List<AuthenticationProvider>)authenticationProviderRepository.findAll();
				model.addAttribute("listProviders",listAuthenticationProvider);		
				List<RoleCategory> listCategories = (List<RoleCategory>)roleCategories.findAll();
				model.addAttribute("listCategories",listCategories);
				return "fragments/addapplication";
			};
			
			applicationRepo.save(application);

			return "fragments/ok";
	};
	
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model,@RequestParam(name="hiddenCategory",required = false) Long hiddenCategory){
		System.out.println("category :"+hiddenCategory);
			Link link = new Link();
			link.setAppId(hiddenCategory);
			link.setContext("/");
			link.setServiceId("/hidden");
			List<Link> links = new ArrayList<Link>();
			links = (List<Link>)linkRepository.findAll();
			List<AuthenticationProvider> listAuthenticationProvider = (List<AuthenticationProvider>)authenticationProviderRepository.findAll();
		
			
			
			model.addAttribute("link",link);
			
			List<RoleCategory> listCategories = (List<RoleCategory>)roleCategories.findAll();
			model.addAttribute("listCategories",listCategories);		
			model.addAttribute("listProviders",listAuthenticationProvider);		
			model.addAttribute("link",link);
			model.addAttribute("links",links);
			
			
			return "fragments/addLinkByApp";
	}

	@RequestMapping(value="/add/submit",method=RequestMethod.POST)
	public String submit(@Valid @ModelAttribute("link")  Link link, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "fragments/addLinkByApp";
		}
		
		Application app = applicationRepo.findByAppId(link.getAppId());
		
		UUID uiid = UUID.randomUUID();
		String uiidString = uiid.toString();
		String context = app.getApplicationName()+"-"+uiidString;
		link.setContext(context);
		String serviceId = app.getApplicationName()+"-"+uiidString;
		System.out.println("uuid:"+serviceId);
		
		link.setServiceId(serviceId);
		link.setActive(true);
		link.setPermitAll(app.getPermitAll()==1?true:false);
		int providerId = app.getProviderId();
		link.setProviderId(Long.parseLong(Integer.toString(providerId)));
		int categoryId = app.getRoleCategoryId();
		link.setCategoryId(Long.parseLong(Integer.toString(categoryId)));
		RoleCategory category = roleCategories.findByRoleCategoryId(link.getCategoryId());
		List<String> rt = new ArrayList<String>();
		link.setRoles(rt);
		if(category !=null)
		for(Roles r:category.getRoles()) {
			rt.add(r.getRoleName());
		}
		if(rt.size()>0) {
			link.setRoles(rt);
		};
		String path = app.getContext()+link.getPath();
		link.setPath(path.toString());
		link.setStripPrefix(true);
		link.setAppId(app.getAppId());
		List<String> sensitiveHeaders = new ArrayList<String>();
		sensitiveHeaders.add("Cookie");
		sensitiveHeaders.add("Set-Cookie");
		link.setSensitiveHeaders(sensitiveHeaders);
		repo.save(link);
		return "fragments/ok";
	}
	
	
	@RequestMapping(value="/modify/submit",method=RequestMethod.POST)
	public String modifyFormSubmit(@Valid @ModelAttribute("application")  Application application, BindingResult bindingResult, Model model){
			System.out.println("patar submit");			
			if (bindingResult.hasErrors()) {
				for(ObjectError er:bindingResult.getAllErrors()) {
					System.out.println("error:"+er.getDefaultMessage());
				}
				model.addAttribute("application", application);
				List<AuthenticationProvider> listAuthenticationProvider = (List<AuthenticationProvider>)authenticationProviderRepository.findAll();
				model.addAttribute("listProviders",listAuthenticationProvider);		
				List<RoleCategory> listCategories = (List<RoleCategory>)roleCategories.findAll();
				model.addAttribute("listCategories",listCategories);
				return "fragments/modifyapplication";
			};
			
			List<Link> list = linkRepository.findByAppId(application.getAppId());
			
			for(Link l:list){
				l.setPermitAll(application.getPermitAll()==1?true:false);
				int categoryId = application.getRoleCategoryId();
				l.setCategoryId(Long.parseLong(Integer.toString(categoryId)));
				RoleCategory category = roleCategories.findByRoleCategoryId(l.getCategoryId());
				List<String> rt = new ArrayList<String>();
				l.setRoles(rt);
				if(category !=null)
				for(Roles r:category.getRoles()) {
					rt.add(r.getRoleName());
				}
				if(rt.size()>0) {
					l.setRoles(rt);
				};
				int providerId = application.getProviderId();
				l.setProviderId(Long.parseLong(Integer.toString(providerId)));
				String path = l.getPath();
				String match = "/";
				int i =0;
				int counter = 1;
			        while((i=(path.indexOf(match,i)+1))>0){
			        	System.out.println(i);
						if(counter >= 2){
							break;
						}
			        	counter++;
				    }
			        
			    path = application.getContext()+path.substring(i-1);
			    l.setPath(path);
			    
			    linkRepository.save(l);
			}
			
			
			applicationRepo.save(application);

			
			
			return "fragments/ok";
	};
	
	
	
	@RequestMapping(value="/listsdata",method=RequestMethod.GET,produces="application/json")
	public @ResponseBody List<Application> lists(@RequestParam(name="search",required = false) String search) {
		List<Application> page = (List<Application>) applicationRepo.findAll();
		return page;
	}
	
	

	@RequestMapping(value="/modifyapplication",method=RequestMethod.GET)
	public String modifyApplication(Model model,@RequestParam(name="id",required = false) Long id){
		Application application = applicationRepo.findByAppId(id);
		model.addAttribute("application", application);
		List<AuthenticationProvider> listAuthenticationProvider = (List<AuthenticationProvider>)authenticationProviderRepository.findAll();
		model.addAttribute("listProviders",listAuthenticationProvider);		
		List<RoleCategory> listCategories = (List<RoleCategory>)roleCategories.findAll();
		model.addAttribute("listCategories",listCategories);
		return "fragments/modifyapplication";
	}
	
	@RequestMapping(value="/remove",method=RequestMethod.GET)
	public String  remove(@RequestParam(name="removeId",required = false) Long removeId) {
		Optional<Application> app = applicationRepo.findById(removeId);
		applicationRepo.delete(app.get());
		return "fragments/ok";
	}
	
	@RequestMapping(value="/index/addLink",method=RequestMethod.GET)
	public String  addAppLink(@RequestParam(name="categoryId",required = false) Long categoryId,Model model,HttpServletRequest request) {
	
		Application application = applicationRepo.findByAppId(categoryId);
		DataTablesWidget widget = new DataTablesWidget();
		widget.setTitle("Links Lists");
		widget.setDestination("/application");
		widget.addHeader("LinkId");
		widget.addHeader("Link Id");
		widget.addHeader("Context");
		widget.addHeader("ServiceId");
		widget.addHeader("PermitAll");
		widget.addHeader("Active");
		widget.setHiddenCategory(Long.toString(categoryId));
		
			model.addAttribute("titleprovider", widget.getTitle()+" >> "+application.getApplicationName());
			List<String> headers = widget.getHeaders();	
			model.addAttribute("headers", headers);
			model.addAttribute("table_url",widget.getDestination());
			model.addAttribute("button",widget.isUseButton());
			model.addAttribute("typeForm",widget.getTypeForm());
			model.addAttribute("selectInput",widget.getSelectInput());
			model.addAttribute("formSearch01",new ArrayList<FormInput>());
			model.addAttribute("formSearch02",new ArrayList<FormInput>());
			model.addAttribute("formSearch03",new ArrayList<FormInput>());
			model.addAttribute("hiddenCategory",widget.getHiddenCategory());
			
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
			model.addAttribute("providercontent","fragments/addAppLink");
		 return "single-template";
	}
	
	@RequestMapping(value="/lists",method=RequestMethod.POST,produces="application/json")
	public @ResponseBody TablePage lists(@RequestBody PaginationCriteria treq) {
		TablePaginator paginator = new SimplePaginator(new LinkServices(repo,Long.parseLong(treq.getHiddenCategory().getValue())));
		TablePage tablePage =  paginator.getPage(treq);
		return tablePage;
	}

	
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> uploadFile(
	    @RequestParam("file") MultipartFile uploadfile) {
	  try {
	    // Get the filename and build the local file path (be sure that the 
	    // application have write permissions on such directory)
	    String filename = uploadfile.getOriginalFilename();
	    String directory =  env.getProperty("folder.images");
	    String filepath = Paths.get(directory, filename).toString();
	    
	    // Save the file locally
	    BufferedOutputStream stream =
	        new BufferedOutputStream(new FileOutputStream(new File(filepath)));
	    stream.write(uploadfile.getBytes());
	    stream.close();
	  }
	  catch (Exception e) {
	    System.out.println(e.getMessage());
	    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	  }
	  
	  return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
