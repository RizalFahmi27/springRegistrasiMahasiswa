package pens.ac.id.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", catalog = "registrasi_mahasiswa_baru", uniqueConstraints = {
		@UniqueConstraint(columnNames = "KAP"), })
public class Users {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Column(length=255, unique = true)
	private String kap;
	
	@NotNull
	@Column(length=255, unique=true)
	private String pin;
	
	@NotNull
	@Column(length=255)
	private String namalengkap;
	
	@Column
	private boolean status;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@NotNull
	private Date tanggallahir;
	
	@NotNull
	@Column(length=200, unique=true)
	private String no_pendaftaran;
	
	@NotNull
	@Column(length=200, unique=true)
	private String kodePembayaran;
	
	@Column(length=200, unique=true)
	private String noUjian;
	
	private long lokasiUjian;
	
//	@Column(length=20)
//	private long id_dataDiri;
//	
//	@Column(length=20)
//	private long id_dataOrtu;
//	
//	@Column(length=20)
//	private long id_dataKeluarga;
//	
//	@Column(length=20)
//	private long id_dataDokumen;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="id_data_dokumen")
	private DataDokumen dataDokumen;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="id_dataSekolah")
	private DataSekolah dataSekolah;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="id_data_ortu")
	private DataOrtu dataOrtu;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="id_data_diri")
	private DataDiri dataDiri;
	
//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name="id_lokasi_ujian")
//	private LokasiUjian lokasiUjian;

	
	
}
