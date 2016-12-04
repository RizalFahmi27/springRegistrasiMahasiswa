package pens.ac.id.controller;


import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pens.ac.id.model.DataSekolah;
import pens.ac.id.model.Users;
import pens.ac.id.service.ServiceAsalSekolah;
import pens.ac.id.service.ServiceUsers;

@Controller
public class ControllerAsalSekolah {
	
	@Autowired
	ServiceAsalSekolah serviceAsalSekolah;
	
	@Autowired
	ServiceUsers serviceUsers;

	public static String[] provinsi = {"Aceh","Sulawesi Tenggara","Jawa Timur","Jawa Tengah","DKI Jakarta", "Yogyakarta",
			"Jawa Barat","Jawa Tengah","Bali","Maluku","Sumatera Utara","Banten","Nusa Tenggara Timur","Papua","Papua Barat",
			"Sumatera Selatan","Lampung","Kalimantan Selatan","Kalimantan Timur","Gorontalo","Sulawesi Utara",
			"Nusa Tenggara Barat","Jambi","Bengkulu"};
	
	@RequestMapping(value="/user/data-asal-sekolah/", method=RequestMethod.GET)
	public String asalSekolah(Model model, HttpServletRequest request){
		HttpSession session = request.getSession();
		if(session.getAttribute("is_loggedIn")!=null){
		
		String KAP = (String) session.getAttribute("KAP");
		
		List<String> provinsiList = Arrays.asList(provinsi);
	
			
		Users user = serviceUsers.getByKAP(KAP);
		System.out.println("id : "+user.getId());
		if(user.getDataSekolah()==null){
			serviceUsers.updateIdDataSekolah(user);
			DataSekolah dataSekolah = serviceUsers.getByKAP(KAP).getDataSekolah();
			System.out.println("id asal sekolah : "+dataSekolah.getId_dataSekolah());
			model.addAttribute("provinsi",provinsiList);
			model.addAttribute("sekolah",dataSekolah);
			return "/dashboard/asal_sekolah";
		}
		else {
			DataSekolah dataSekolahFound = serviceAsalSekolah.getById(user.getDataSekolah().getId_dataSekolah());
			model.addAttribute("provinsi",provinsiList);
			model.addAttribute("sekolah",dataSekolahFound);
			return "/dashboard/asal_sekolah";
		}
		}
		else {
			return "redirect:/login";
		}
		
	}
	
	@RequestMapping(value="/user/data-asal-sekolah/29111120/input", method = RequestMethod.POST)
	@ResponseBody
	public String insertDataSekolah(@ModelAttribute("sekolah") DataSekolah dataSekolah, HttpServletRequest request){
		HttpSession session = request.getSession();		
		if(session.getAttribute("is_loggedIn")!=null){
			if(serviceAsalSekolah.updateDataSekolah(dataSekolah)){}
			else {serviceAsalSekolah.save(dataSekolah);}
			return "success";
		}
		else {
			return "/login";
		}
	}
	
}
