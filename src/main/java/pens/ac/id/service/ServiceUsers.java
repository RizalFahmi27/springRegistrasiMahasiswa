package pens.ac.id.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pens.ac.id.dao.DaoUsers;
import pens.ac.id.model.DataDokumen;
import pens.ac.id.model.Users;

@Service
public class ServiceUsers  {
	
	@Autowired
	private DaoUsers daoUsers;
	
	public DaoUsers geteDaoUsers(){
		return daoUsers;
	}
	
	public void save(Users users){
		daoUsers.save(users);
	}
	
	public List<Users> getAllItem(){
		List<Users> list = daoUsers.findAll();
		
		return list;
	}
	
	public Users getById(Long id){
		//System.out.println("id user : " +id);
		return daoUsers.findOne(id);
	}
	
	public boolean updateIdDokumen(Users user){
		Users oldUser = daoUsers.findOne(user.getId());
		if(oldUser!=null){
			oldUser.setDataDokumen(new DataDokumen());
			daoUsers.save(oldUser);
			return true;
		}
		else {
			return false;
		}
	}
	
	public Users getByEmail(String email){
		return daoUsers.getByEmail(email);
	}
	
	
}
