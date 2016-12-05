package pens.ac.id.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import pens.ac.id.model.LokasiUjian;

public interface DaoLokasi extends JpaRepository<LokasiUjian, Long> {

}
