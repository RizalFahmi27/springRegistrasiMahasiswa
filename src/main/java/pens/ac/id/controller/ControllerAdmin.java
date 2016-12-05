package pens.ac.id.controller;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pens.ac.id.model.LokasiUjian;
import pens.ac.id.model.Users;
import pens.ac.id.service.ServiceLokasiUjian;
import pens.ac.id.service.ServiceUsers;

@Controller
public class ControllerAdmin {
	
	@Autowired
	ServiceUsers serviceUsers;
	
	@Autowired
	ServiceLokasiUjian serviceLokasiUjian;
	
	@RequestMapping(value="/admin", method=RequestMethod.GET)
	public String adminHome(Model model, HttpServletRequest request){
		List<Users> list = (List<Users>)serviceUsers.getAllItem();
		Collections.sort(list, new Comparator<Users>() {

			@Override
			public int compare(Users o1, Users o2) {
				// TODO Auto-generated method stub
				return o1.getNamalengkap().compareTo(o2.getNamalengkap());
			}
		});
		model.addAttribute("list",list);
		return "/admin/administrator";
	}
	
	@RequestMapping(value="/admin/ubah_status/221339912/", method=RequestMethod.POST)
	@ResponseBody
	public String changeStatus(@RequestParam("id") Long id){
		
		Random rn = new Random();
			
		System.out.println("id status : "+id);
		Users user = serviceUsers.getById(id);
		
		int lokasiUjianCount = serviceLokasiUjian.getAllItem().size();
		int idLokasi = rn.nextInt(lokasiUjianCount) + 1; 
		System.out.println("id lokasi ujian : "+idLokasi);
		
		user.setLokasiUjian(idLokasi);
		user.setNoUjian(ControllerUser.generateNumber(8));
		user.setStatus(true);
		serviceUsers.save(user);
		return "done";
	}
	
}
