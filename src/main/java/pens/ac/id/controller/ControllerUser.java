package pens.ac.id.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

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
import pens.ac.id.model.DataSekolah;
import pens.ac.id.model.DataOrtu;
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
				String msg2 = (String) inputFlashMap.get("messageRegist");
				if(msg!=null){				
				newModel.addAttribute("msg",msg);
				System.out.println(msg);
				}
				else {				
					newModel.addAttribute("msg", "Masukkan password dan ID User Anda");
				}
				if(msg2!=null){
					newModel.addAttribute("msgRegist",msg2);
					System.out.println(msg2);
				}
				
			}
		//String msg = (String) session.getAttribute("msg");
		System.out.println("duar");
		newModel.addAttribute("userData", new Users());	
		return "/login/login";  
	}
		

	return "redirect:/user/upload_berkas/";
}
	

	@RequestMapping(value="/login/masuk", method = RequestMethod.POST)
	public String login(@ModelAttribute("userData")Users user, HttpServletRequest request, RedirectAttributes redirect){
		HttpSession session = request.getSession();
//		model.addAttribute("userData", new Users());
//		return "/login/login";
//		if(session.getAttribute("is_loggedIn") == null){
			
			Users userData = serviceUsers.getByKAP(user.getKap());
			//System.out.println("idnya : " + serviceUsers.getById((long) 4).getId());
			if(userData == null){		
				redirect.addFlashAttribute("message", "User ID atau password salah");
				//model.addObject("msg", "User ID atau password salah");
				//model.addObject("userData", new Users());
				session.setAttribute("msg", "User ID atau password salah");
				System.out.println("kap tidak ketemu");
				//model.addAttribute("userData", new Users());
				return "redirect:/login";
			}
			else {				
				System.out.println("\n" + userData.getPin());
				System.out.println(user.getPin());
				
				if(BCrypt.checkpw(user.getPin(), userData.getPin())){
					
					
					session.setAttribute("is_loggedIn", true);
					session.setAttribute("id", userData.getId());
					session.setAttribute("KAP", userData.getKap());
					System.out.println("boom");
					return "redirect:/user/upload_berkas/";
					
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
		session.removeAttribute("KAP");
		return "redirect:/login";
	}
	
	@RequestMapping(value="/registrasi", method = RequestMethod.POST)
	public String register(Model model, @ModelAttribute("usersData") Users user, HttpServletRequest request, RedirectAttributes redirect){
		HttpSession session = request.getSession();		
		if(session.getAttribute("is_loggedIn") == null){
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			Users users = serviceUsers.getByNamalengkap(user.getNamalengkap());
			
			String formatUser = formatter.format(user.getTanggallahir());
			if(users!=null){
					
			String formatUsers = formatter.format(users.getTanggallahir());
			
			System.out.println("hbd terdaftar : "+formatUsers);
			System.out.println("hbd mau daftar : "+formatUser);
			if(formatUser.equalsIgnoreCase(formatUsers)){
				System.out.println(users.getNamalengkap());
				redirect.addFlashAttribute("messageRegist", "Anda sudah terdaftar, gunakan menu Log In");
				session.setAttribute("msgRegist", "Anda sudah terdaftar, gunakan menu Log In");
				return "redirect:/login";
			}
			else {
				model = insertNewUser(formatUser, user, model);	
				return "/verifikasi";
			}
			}
			else {
				model = insertNewUser(formatUser, user, model);
				return "/verifikasi";
			}
		}
		return "redirect:/login";
	}
	
	private Model insertNewUser(String formatUser, Users user, Model model){
		Users newUser = new Users();
		
		Date date = new Date();
		try {
			 date = new SimpleDateFormat("yyyy-MM-dd").parse(formatUser);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("tanggal user : "+user.getNamalengkap());
		
		String bcryptPin = generateNumber(8);
		
		String uniqueKAP = UUID.randomUUID().toString().toUpperCase(); 
		newUser.setKap(uniqueKAP);
		newUser.setPin(BCrypt.hashpw(bcryptPin, BCrypt.gensalt()) );
		newUser.setStatus(false);
		newUser.setKodePembayaran(generateNumber(8));
		newUser.setNo_pendaftaran(generateNumber(10));
		newUser.setTanggallahir(date);
		newUser.setNamalengkap(user.getNamalengkap());
		newUser.setDataDiri(new DataDiri());
		newUser.setDataDokumen(new DataDokumen());
		newUser.setDataOrtu(new DataOrtu());
		newUser.setDataSekolah(new DataSekolah());
		serviceUsers.save(newUser);
		
		model.addAttribute("kap",newUser.getKap());
		model.addAttribute("pin",bcryptPin);
		model.addAttribute("nama",newUser.getNamalengkap());
		model.addAttribute("tgl", newUser.getTanggallahir());
		model.addAttribute("kodePembayaran",newUser.getKodePembayaran());
		return model;
		
	}
	
	
	
	public String generateNumber(int n) {
        String s = "";
        double d;
        for (int i = 1; i <= n; i++) {
            d = Math.random() * 10;
            s = s + ((int)d);       
        }
        System.out.println(s);
        return s;
    }
}
