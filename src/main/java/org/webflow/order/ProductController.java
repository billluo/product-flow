package org.webflow.order;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;
import org.webflow.admin.UserRepository;
import org.webflow.domain.OrderItem;
import org.webflow.domain.Product;
import org.webflow.domain.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;


@Controller
public class ProductController {
	
	@Autowired
    private OrderService orderService;

	@Autowired
    private UserRepository newUser;

	@Autowired
	@Qualifier("authMgr")
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private FlowExecutor flowExecutor;
	
	@Autowired
    private ServletContext context;

	@Autowired
	FlowHandlerAdapter flowHandlerAdapter;

	public ProductController() {}

    public ProductController(OrderService orderService) {
	this.orderService = orderService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
 
    @RequestMapping(value = "/products/search", method = RequestMethod.GET)
    public void search(ProductCriteria productCriteria, Principal currentUser, Model model) {
    		if (currentUser != null) {
    			List<OrderItem> orderItems = orderService.findOrderItems(currentUser.getName());
    			model.addAttribute("orderItems",orderItems);

    		}
    }
 
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public String list(ProductCriteria criteria, Model model) {

	List<Product> products = orderService.findProducts(criteria);
	model.addAttribute("products",products);
	return "products/list";
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public String show(@PathVariable Long id, Model model, HttpServletRequest request, HttpServletResponse response) {
	model.addAttribute("product",orderService.findProductById(id));
	
	return "products/show";
    }

    @RequestMapping(value = "/orderItems/{id}", method = RequestMethod.DELETE)
    public String deleteOrder(@PathVariable Long id) {
    	orderService.cancelOrderItem(id);
	return "redirect:../products/search";
    }


    
	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response,Model model) {
		  	
    	return "login";
    }

    @RequestMapping(value = "/registrationForm", method = RequestMethod.GET)
    public void registrationForm(User user, Model model) {

    }
    
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(User user, BindingResult result, HttpServletRequest request,
		HttpServletResponse response, ModelMap model) throws Exception {
    
    	//Add user to database table
    	newUser.save(user);
    	//apply registration credential to log on
    	authenticateUserAndSetSession(user, request);    	
 			
    	return "redirect:/products/search";   
    }  

    private void authenticateUserAndSetSession(User user, HttpServletRequest request) {
    	   		
        String username = user.getUsername();
        String password = user.getPassword();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        request.getSession();

        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);
        
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }  
    
    
}
