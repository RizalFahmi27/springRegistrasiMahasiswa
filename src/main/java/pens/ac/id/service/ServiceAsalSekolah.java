package pens.ac.id.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pens.ac.id.dao.DaoAsalSekolah;

import pens.ac.id.model.DataSekolah;

@Service
public class ServiceAsalSekolah  {
	
	@Autowired
	DaoAsalSekolah daoAsalSekolah;
	
	public DaoAsalSekolah getdaoDokumen(){
		return daoAsalSekolah;
	}
	
	public DataSekolah save(DataSekolah dataSekolah){
		return daoAsalSekolah.save(dataSekolah);
		
	}
	
	public List<DataSekolah> getAllItem(){
		List<DataSekolah> list = daoAsalSekolah.findAll();
		return list;
	}
	
	public DataSekolah getById(Long id){
		return daoAsalSekolah.findOne(id);
	}
	
	public boolean updateDataSekolah(DataSekolah dataSekolah){
		DataSekolah oldDataSekolah = daoAsalSekolah.findOne(dataSekolah.getId_dataSekolah());
		if(oldDataSekolah!=null){
			System.out.println("masuk update");
			oldDataSekolah.setAlamat_sekolah(dataSekolah.getAlamat_sekolah());
			oldDataSekolah.setJenis_sekolah(dataSekolah.getJenis_sekolah());
			oldDataSekolah.setJurusan(dataSekolah.getJurusan());
			oldDataSekolah.setKota(dataSekolah.getKota());
			oldDataSekolah.setNama_sekolah(dataSekolah.getNama_sekolah());
			oldDataSekolah.setNilai_rapor(dataSekolah.getNilai_rapor());
			oldDataSekolah.setNilai_UN(dataSekolah.getNilai_UN());
			oldDataSekolah.setProvinsi(dataSekolah.getProvinsi());
			oldDataSekolah.setStatus_sekolah(dataSekolah.getStatus_sekolah());
			oldDataSekolah.setTahun_lulus(dataSekolah.getTahun_lulus());
			oldDataSekolah.setKode_pos(dataSekolah.getKode_pos());
			daoAsalSekolah.save(oldDataSekolah);
			return true;
		}
		else {
			return false;
		}
	}
	
	
	
}
