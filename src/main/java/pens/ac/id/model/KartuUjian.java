package pens.ac.id.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KartuUjian {
	String no_peserta="";
	String nama="";
	String nama_lembar_ujian="";
	String tahun_lulus="";
	String alamat="";
	String foto="";
	String pilihan_jurusan_1="";
	String pilihan_jurusan_2="";
	String lokasi_ujian="";
	String no_telepon="";
	
	public KartuUjian(){
		
	}
	
	public KartuUjian(String no_peserta, String nama, String nama_lembar_ujian, String tahun_lulus, String alamat,
			String foto, String pilihan_jurusan_1, String pilihan_jurusan_2, String lokasi_ujian, String no_telp) {
		this.no_peserta = no_peserta;
		this.nama = nama;
		this.nama_lembar_ujian = nama_lembar_ujian;
		this.tahun_lulus = tahun_lulus;
		this.alamat = alamat;
		this.foto = foto;
		this.pilihan_jurusan_1 = pilihan_jurusan_1;
		this.pilihan_jurusan_2 = pilihan_jurusan_2;
		this.lokasi_ujian = lokasi_ujian;
		this.no_telepon = no_telp;
	}
	
	
}
