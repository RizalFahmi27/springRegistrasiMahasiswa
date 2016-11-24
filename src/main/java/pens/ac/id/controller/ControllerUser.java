package pens.ac.id.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pens.ac.id.model.LogInData;
import pens.ac.id.model.Users;
import pens.ac.id.service.ServiceUsers;

@Controller
public class ControllerUser {
	
	@Autowired
	private ServiceUsers serviceUsers;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String loginPage(Model model, HttpServletRequest request){
		HttpSession session = request.getSession();
		if(session.getAttribute("is_loggedIn") == null){
			Users logInData = new Users();
			model.addAttribute("usersData", logInData);
			return "/login/login";
			
		}
		return "redirect:" + "/mahasiswa/mahasiswa-input";
		
	}
	
	
	@RequestMapping(value="login/daftar", method = RequestMethod.POST)
	@ResponseBody
	public String register(@ModelAttribute("usersData") Users user, HttpServletRequest request){
		HttpSession session = request.getSession();
		
		if(session.getAttribute("is_loggedIn") == null){
			Users users = serviceUsers.getByEmail(user.getEmail());
			if(users != null){
				return "Data sebelumnya ditemukan, gunakan menu log in"; 
			}
			else {
				return "hai";
			}
		}
		return "redirect:" + "/";
	}
	
	
	
//	@RequestMapping(value="/login/masuk", method = RequestMethod.POST)
//	public String login(@ModelAttribute Users user){
//		
//	}
}
