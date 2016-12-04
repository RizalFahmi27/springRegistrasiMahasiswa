package pens.ac.id.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import pens.ac.id.model.Users;

public interface DaoUsers extends JpaRepository<Users, Long> {
	public Users getByKap(String kap);	
	public Users getByTanggallahir(Date tanggallahir);
	public Users getByNamalengkap(String namalengkap);
}
