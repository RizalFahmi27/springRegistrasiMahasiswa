package pens.ac.id.service;

import org.springframework.stereotype.Service;

import pens.ac.id.dao.DaoDokumen;
import pens.ac.id.model.DataDokumen;

import pens.ac.id.model.Users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


@Service
public class ServiceDokumen {
	
	@Autowired
	private DaoDokumen daoDokumen;
	
	public DaoDokumen getdaoDokumen(){
		return daoDokumen;
	}
	
	public DataDokumen save(DataDokumen dataDokumen){
		return daoDokumen.save(dataDokumen);
		
	}
	
	public List<DataDokumen> getAllItem(){
		List<DataDokumen> list = daoDokumen.findAll();
		return list;
	}
	
	public DataDokumen getById(Long id){
		return daoDokumen.findOne(id);
	}
	
	public boolean updateDokumen(DataDokumen dataDokumen){
		DataDokumen oldDataDokumen = daoDokumen.findOne(dataDokumen.getId_dataDokumen());
		if(oldDataDokumen!=null){
			oldDataDokumen.setNamaFileFoto(dataDokumen.getNamaFileFoto());
			oldDataDokumen.setNamaFileKK(dataDokumen.getNamaFileKK());
			oldDataDokumen.setNamaFileSKL(dataDokumen.getNamaFileSKL());
			daoDokumen.save(oldDataDokumen);
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean deleteDocument(DataDokumen dataDokumen, String type){
		DataDokumen oldData = daoDokumen.findOne(dataDokumen.getId_dataDokumen());
		if(oldData!=null){
			if(type.equalsIgnoreCase("foto"))
				oldData.setNamaFileFoto(null);
			else if(type.equalsIgnoreCase("KK"))
				oldData.setNamaFileKK(null);
			else if(type.equalsIgnoreCase("SKL"))
				oldData.setNamaFileSKL(null);
			else if(type.equalsIgnoreCase("bukti"))
				oldData.setNamaFileBuktiPendaftaran(null);
			daoDokumen.save(oldData);
			return true;
		}
		return false;
	}
	
//	public void updateIdDokumen(DataDokumen dataDokumen){
//		DataDokumen olddataDokumen = daoDokumen.findOne(dataDokumen.getId());
//		if(oldUser!=null){
//			oldUser.setId_dataDokumen(oldUser.getId());
//			daoUsers.save(oldUser);
//		}
//	}
	
//	public Users getByEmail(String email){
//		return daoUsers.getByEmail(email);
//	}

}
