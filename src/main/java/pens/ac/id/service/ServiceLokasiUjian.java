package pens.ac.id.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pens.ac.id.dao.DaoLokasi;
import pens.ac.id.dao.DaoUsers;
import pens.ac.id.model.DataDokumen;
import pens.ac.id.model.DataSekolah;
import pens.ac.id.model.LokasiUjian;
import pens.ac.id.model.Users;

@Service
public class ServiceLokasiUjian {
	
	@Autowired
	DaoLokasi daoLokasi;

	
	public DaoLokasi getDaoLokasi(){
		return daoLokasi;
	}
	
	
	public List<LokasiUjian> getAllItem(){
		List<LokasiUjian> list = daoLokasi.findAll();
		
		return list;
	}
	
	public LokasiUjian getById(Long id){
		//System.out.println("id user : " +id);
		return daoLokasi.findOne(id);
	}
	
	
}
