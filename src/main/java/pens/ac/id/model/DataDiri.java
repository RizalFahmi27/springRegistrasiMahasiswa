package pens.ac.id.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DataDiri {
	
//	@OneToOne(fetch = FetchType.LAZY)
//	@PrimaryKeyJoinColumn
//	private Users users;
	
//	@GenericGenerator(name="generator", strategy="foreign",
//			parameters = @Parameter(name="property", value="users"))
	@Id
	@GeneratedValue
	private long id_dataDiri;
	
	@OneToOne(mappedBy="dataDiri")
	private Users user;
	
	@Column(length=255)
	private String nama_lengkap;
	
	@Column(length=50)
	private String jenis_kelamin;
	
	@Column(length=50)
	private String agama;
	
	@Column(length=50)
	private String tempat_lahir;
	
	@Column(length=255)
	private String alamat_asal;
	
	@Column(length=50)
	private String provinsi;
	
	@Column(length=100)
	private String no_telepon;
	
	@Column(length=50)
	private String email;
	
	@Column(length=50)
	private String jurusan1;
	
	@Column(length=50)
	private String jurusan2;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(length=50)
	private Date tanggal_lahir;
	
	@Column(length=50)
	private String kode_pos;
	
	
}
