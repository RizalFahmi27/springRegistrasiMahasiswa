package pens.ac.id.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import pens.ac.id.model.DataDiri;
import pens.ac.id.model.DataKeluarga;
import pens.ac.id.model.DataOrtu;
import pens.ac.id.model.LogInData;
import pens.ac.id.model.Users;
import pens.ac.id.service.ServiceUsers;


@Controller
public class ControllerUser {
	
	private DataTemp dataTemp;
	@Autowired
	private ServiceUsers serviceUsers;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String loginPage( HttpServletRequest request){
		HttpSession session = request.getSession();
		if(session.getAttribute("is_loggedIn") == null){
			return "redirect:/login";  	
		}
		return "redirect:/dashboard/user";	
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request){
	HttpSession session = request.getSession();
	ModelAndView model = new ModelAndView("/login/login");
	if(session.getAttribute("is_loggedIn") == null){
		Users logInData = new Users();
		model.addObject("userData", logInData);
		model.addObject("msg", "Masukkan password dan ID User Anda");
		System.out.println("went here");
		return model;  
	}
	
	return new ModelAndView("redirect:/dashboard/user");
}
	
	@RequestMapping(value="/dashboard/user", method = RequestMethod.GET)
	public String home(Model model, HttpServletRequest request){
		HttpSession session = request.getSession();
		if(session.getAttribute("is_loggedIn") == null)
			return "redirect:/";
		model.addAttribute("form", new DataDiri());
		return "dashboard/dashboard";
	}
	
	
	@RequestMapping(value="/login/masuk", method = RequestMethod.POST)
	public ModelAndView login(@ModelAttribute("userData")Users user, HttpServletRequest request){
		HttpSession session = request.getSession();
		ModelAndView model = new ModelAndView();
		if(session.getAttribute("is_loggedIn") == null){
			
			Users userData = serviceUsers.getByEmail(user.getEmail());
			if(userData == null){
				
				model.addObject("msg", "User ID atau password salah");
				model.addObject("userData", new Users());
				model.setViewName("/login/login");
				return model;
			}
			else {				
				System.out.println("\n" + userData.getPassword());
				System.out.println(user.getPassword());
				
				if(BCrypt.checkpw(user.getPassword(), userData.getPassword())){
					
					session.setAttribute("is_loggedIn", true);
					session.setAttribute("id", userData.getId());
					session.setAttribute("email", userData.getEmail());
					return new ModelAndView("redirect:/dashboard/user");
					
				}
				else {
					model.setViewName("/login/login");
					model.addObject("msg", "User ID atau password salah");
					model.addObject("userData", new Users());
					return model;
				}
			}
		}
			System.out.println(session.getAttribute("is_loggedIn").toString());
			return new ModelAndView("redirect:/dashboard/user");
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
					
				serviceUsers.save(user);
				return "data berhasil disimpan";
			}
		}
		return "redirect:" + "/";
	}
	
	
	private class DataTemp{
		private DataDiri dataDiri;
		private DataOrtu dataOrtu;
		private DataKeluarga dataKeluarga;
		
		public DataDiri getDataDiri() {
			return dataDiri;
		}
		public void setDataDiri(DataDiri dataDiri) {
			this.dataDiri = dataDiri;
		}
		public DataOrtu getDataOrtu() {
			return dataOrtu;
		}
		public void setDataOrtu(DataOrtu dataOrtu) {
			this.dataOrtu = dataOrtu;
		}
		public DataKeluarga getDataKeluarga() {
			return dataKeluarga;
		}
		public void setDataKeluarga(DataKeluarga dataKeluarga) {
			this.dataKeluarga = dataKeluarga;
		}
		
		
	}
	
	
	
//	@RequestMapping(value="/login/masuk", method = RequestMethod.POST)
//	public String login(@ModelAttribute Users user){
//		
//	}
}
