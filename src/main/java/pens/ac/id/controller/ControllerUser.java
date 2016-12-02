package pens.ac.id.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pens.ac.id.model.DataDiri;
import pens.ac.id.model.DataDokumen;
import pens.ac.id.model.DataKeluarga;
import pens.ac.id.model.DataOrtu;
import pens.ac.id.model.LogInData;
import pens.ac.id.model.Users;
import pens.ac.id.service.ServiceUsers;


@Controller
public class ControllerUser {
	

	@Autowired
	private ServiceUsers serviceUsers;
	
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(Model newModel, HttpServletRequest request, RedirectAttributes redirectAttributes){
	HttpSession session = request.getSession();
	if(session.getAttribute("is_loggedIn") == null){
			Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
			if(inputFlashMap!=null){
				String msg = (String) inputFlashMap.get("message");
				if(msg!=null){				
				newModel.addAttribute("msg",msg);
				System.out.println(msg);
				}
				else {				
					newModel.addAttribute("msg", "Masukkan password dan ID User Anda");
				}
			}
		//String msg = (String) session.getAttribute("msg");
		System.out.println("duar");
		newModel.addAttribute("userData", new Users());	
		return "/login/login";  
	}
		
	long id = (Long) session.getAttribute("email");
	System.out.println(""+id);
	return "redirect:/dashboard/user/"+id;
}
	

	@RequestMapping(value="/login/masuk", method = RequestMethod.POST)
	public String login(@ModelAttribute("userData")Users user, HttpServletRequest request, RedirectAttributes redirect){
		HttpSession session = request.getSession();
//		model.addAttribute("userData", new Users());
//		return "/login/login";
//		if(session.getAttribute("is_loggedIn") == null){
			
			Users userData = serviceUsers.getByEmail(user.getEmail());
			System.out.println("idnya : " + serviceUsers.getById((long) 4).getId());
			if(userData == null){		
				redirect.addFlashAttribute("message", "User ID atau password salah");
				//model.addObject("msg", "User ID atau password salah");
				//model.addObject("userData", new Users());
				session.setAttribute("msg", "User ID atau password salah");
				System.out.println("email tidak ketemu");
				//model.addAttribute("userData", new Users());
				return "redirect:/login";
			}
			else {				
				System.out.println("\n" + userData.getPassword());
				System.out.println(user.getPassword());
				
				if(BCrypt.checkpw(user.getPassword(), userData.getPassword())){
					
					
					session.setAttribute("is_loggedIn", true);
					session.setAttribute("id", userData.getId());
					session.setAttribute("email", userData.getEmail());
					System.out.println("boom");
					return "redirect:/dashboard/user/"+userData.getEmail();
					
				}
				else {
					redirect.addFlashAttribute("message", "User ID atau password salah");
					session.setAttribute("msg", "User ID atau password salah");
					return "redirect:/login";
				}
			}
//		}
//			System.out.println(session.getAttribute("is_loggedIn").toString());
//			long id = (Long) session.getAttribute("id");
//			return "redirect:/dashboard/user/"+id;
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.removeAttribute("is_loggedIn");
		session.removeAttribute("id");
		session.removeAttribute("email");
		return "redirect:/login";
	}
//	
//	
//	@RequestMapping(value="login/daftar", method = RequestMethod.POST)
//	@ResponseBody
//	public String register(@ModelAttribute("usersData") Users user, HttpServletRequest request){
//		HttpSession session = request.getSession();
//		
//		if(session.getAttribute("is_loggedIn") == null){
//			Users users = serviceUsers.getByEmail(user.getEmail());
//			if(users != null){
//				return "Data sebelumnya ditemukan, gunakan menu log in"; 
//			}
//			else {
//					
//				serviceUsers.save(user);
//				return "data berhasil disimpan";
//			}
//		}
//		return "redirect:" + "/";
	

	
	
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
	

}
