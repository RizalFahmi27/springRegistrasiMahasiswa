package pens.ac.id.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControllerHome {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String loginPage( HttpServletRequest request){
		HttpSession session = request.getSession();
		if(session.getAttribute("is_loggedIn") == null){
			return "redirect:/login";  	
		}
		System.out.println("masuk root");
		
		return "redirect:/user/upload_berkas/";
	}
}
