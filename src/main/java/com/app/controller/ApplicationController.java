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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
import com.app.rest.model.EntityResponse;
import com.app.services.LinkServices;
import com.app.utils.AppUtil;


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
	};
	
	
	

	
	

	
	@RequestMapping(value="/modify",method=RequestMethod.POST,consumes="application/json",produces = { MediaType.TEXT_HTML_VALUE,
            MediaType.APPLICATION_XHTML_XML_VALUE })
	public String modify(@RequestBody Link l,Model model){
			///logger.info("modify"+detail.getClientId());
			Link link = linkRepository.findByLinkId(l.getLinkId());
			
			String path = link.getPath();
			
			String match = "/";
			int i =0;
			int counter = 1;
		   try {    
			while((i=(path.indexOf(match,i)+1))>0){
		        	if(counter >= 2){
						break;
					}
		        	counter++;
			   }
		        
		    path = path.substring(i-1);
		    link.setPath(path);
		   }catch(Exception e) {
			   e.printStackTrace();
		   };
		    
		    model.addAttribute("link", link);
			
		    return "fragments/modifyLinkByApp";
			
	}
	
	
	
	@RequestMapping(value="/modify/submit",method=RequestMethod.POST)
	public String modifysubmit(@Valid @ModelAttribute("link")  Link link, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			for(FieldError error:bindingResult.getFieldErrors()) {
				System.out.println(error.getField()+":"+error.getDefaultMessage());
			};
			
			
			String path = link.getPath();
			String match = "/";
			int i =0;
			int counter = 1;
		        while((i=(path.indexOf(match,i)+1))>0){
		        	if(counter >= 2){
						break;
					}
		        	counter++;
			    }
		        
		    path = path.substring(i-1);
		    link.setPath(path);
		    model.addAttribute("link", link);
		    return "fragments/modifyLinkByApp";
			
		}
		
		String path = "";
		Application application = applicationRepo.findByAppId(link.getAppId());
		path = application.getContext()+link.getPath();
	    path = path.trim();
	    link.setPath(path);
	    repo.save(link);
	    AppUtil.reloadApiGateway(env.getProperty("url.api.gateway"));
		return "fragments/ok";
	}
	
	
	@RequestMapping(value="/addForm",method=RequestMethod.GET)
	public String addForm(Model model){
			model.addAttribute("application", new Application());
			List<AuthenticationProvider> listAuthenticationProvider = (List<AuthenticationProvider>)authenticationProviderRepository.findAll();
			model.addAttribute("listProviders",listAuthenticationProvider);		
			List<RoleCategory> listCategories = (List<RoleCategory>)roleCategories.findAll();
			model.addAttribute("listCategories",listCategories);
			return "fragments/addapplication";
	};
	
	
	@RequestMapping(value="/addForm/submit",method=RequestMethod.POST)
	public String addFormSubmit(@Valid @ModelAttribute("application")  Application application, BindingResult bindingResult, Model model){
			if (bindingResult.hasErrors()) {
				model.addAttribute("application", application);
				List<AuthenticationProvider> listAuthenticationProvider = (List<AuthenticationProvider>)authenticationProviderRepository.findAll();
				model.addAttribute("listProviders",listAuthenticationProvider);		
				List<RoleCategory> listCategories = (List<RoleCategory>)roleCategories.findAll();
				model.addAttribute("listCategories",listCategories);
				return "fragments/addapplication";
			};
			
			
			
			
			
			
			
			
			
			
			applicationRepo.save(application);
			int providerId1 = application.getProviderId();
			if(providerId1>0){
				Optional opt =	authenticationProviderRepository.findById(Long.parseLong(Integer.toString(providerId1)));
				AuthenticationProvider prov = (AuthenticationProvider)opt.get();
			    String url = prov.getUrl();
			    String token = "oauth/check_token";
			    String host = url.trim().substring(0,url.trim().indexOf(token));
			    Link li = new Link();
			    UUID uiid = UUID.randomUUID();
				String uiidString = uiid.toString();
				String context = application.getApplicationName()+"-"+uiidString;
				li.setContext(context);
				String serviceId = application.getApplicationName()+"-"+uiidString;
				System.out.println("uuid:"+serviceId);
				li.setServiceId(serviceId);
				li.setActive(true);
				li.setPermitAll(true);
				int providerId = application.getProviderId();
				li.setProviderId(Long.parseLong(Integer.toString(providerId)));
				int categoryId = application.getRoleCategoryId();
				li.setCategoryId(Long.parseLong(Integer.toString(categoryId)));
				String tokenPath = application.getContext()+"/api/token/**";
				li.setPath(tokenPath);
				li.setStripPrefix(true);
				li.setAppId(application.getAppId());
				li.setPermitAll(true);
				List<String> sensitiveHeaders = new ArrayList<String>();
				sensitiveHeaders.add("Cookie");
				sensitiveHeaders.add("Set-Cookie");
				li.setSensitiveHeaders(sensitiveHeaders);
				li.setUrl(host+"oauth/token");
				Link t = repo.findByUrlContainingAndAppId(li.getUrl().trim(), application.getAppId());
				if(t==null){
					System.out.println("di create nih");
				repo.save(li);
				}else{
					System.out.println("udah ada oii");
				}
				
			}else{
				String tokenPath = application.getContext()+"/api/token";
				Link t = repo.findByPathContainingAndAppId(tokenPath, application.getAppId());
				repo.delete(t);
			}
			
			
			
			

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
		
		AppUtil.reloadApiGateway(env.getProperty("url.api.gateway"));
		
		return "fragments/ok";
	}
	
	
	@RequestMapping(value="/modifyapplication/submit",method=RequestMethod.POST)
	public String modifyFormSubmit(@Valid @ModelAttribute("application")  Application application, BindingResult bindingResult, Model model){
			System.out.println("patar submit");			
			if (bindingResult.hasErrors()) {
			
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

			

			
			
			

			int providerId1 = application.getProviderId();
			if(providerId1>0){
				Optional opt =	authenticationProviderRepository.findById(Long.parseLong(Integer.toString(providerId1)));
				AuthenticationProvider prov = (AuthenticationProvider)opt.get();
			    String url = prov.getUrl();
			    String token = "oauth/check_token";
			    String host = url.trim().substring(0,url.trim().indexOf(token));
			    Link li = new Link();
			    UUID uiid = UUID.randomUUID();
				String uiidString = uiid.toString();
				String context = application.getApplicationName()+"-"+uiidString;
				li.setContext(context);
				String serviceId = application.getApplicationName()+"-"+uiidString;
				System.out.println("uuid:"+serviceId);
				li.setServiceId(serviceId);
				li.setActive(true);
				li.setPermitAll(true);
				int providerId = application.getProviderId();
				li.setProviderId(Long.parseLong(Integer.toString(providerId)));
				int categoryId = application.getRoleCategoryId();
				li.setCategoryId(Long.parseLong(Integer.toString(categoryId)));
				String tokenPath = application.getContext()+"/api/token/**";
				li.setPath(tokenPath);
				li.setStripPrefix(true);
				li.setAppId(application.getAppId());
				li.setPermitAll(true);
				List<String> sensitiveHeaders = new ArrayList<String>();
				sensitiveHeaders.add("Cookie");
				sensitiveHeaders.add("Set-Cookie");
				li.setSensitiveHeaders(sensitiveHeaders);
				li.setUrl(host+"oauth/token");
				Link t = repo.findByUrlContainingAndAppId(li.getUrl().trim(), application.getAppId());
				if(t==null){
					System.out.println("di create nih");
				repo.save(li);
				}else{
					System.out.println("udah ada oii");
				}
				
			}else{
				String tokenPath = application.getContext()+"/api/token";
				Link t = repo.findByPathContainingAndAppId(tokenPath, application.getAppId());
				repo.delete(t);
			}
			
			
			
			
			AppUtil.reloadApiGateway(env.getProperty("url.api.gateway"));
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
	
	@RequestMapping(value="/removeapp",method=RequestMethod.GET)
	public String  removeapp(@RequestParam(name="removeId",required = false) Long removeId) {
		Optional<Application> app = applicationRepo.findById(removeId);
		List<Link> list = linkRepository.findByAppId(app.get().getAppId());
		for(Link l:list) {
			linkRepository.delete(l);
		}
		applicationRepo.delete(app.get());
		AppUtil.reloadApiGateway(env.getProperty("url.api.gateway"));
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
		widget.addHeader("Url");
		widget.addHeader("Path");
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
	
	
	@RequestMapping(value="/remove",method=RequestMethod.POST,produces="application/json")
	public @ResponseBody EntityResponse remove(@RequestBody  List<Link> list) {
		for(Link details:list){
			Link clientDetails = details;
			repo.deleteById(clientDetails.getLinkId());
		}
		EntityResponse response = new EntityResponse();

		AppUtil.reloadApiGateway(env.getProperty("url.api.gateway"));
		return response;
	}
	
	
	
	
}
