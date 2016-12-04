package pens.ac.id.model;

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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DataSekolah {
//	@OneToOne(fetch = FetchType.LAZY)
//	@PrimaryKeyJoinColumn
	//private Users users;
	
	@GenericGenerator(name="generator", strategy="foreign",
			parameters = @Parameter(name="property", value="users"))
	@Id
	@GeneratedValue
	private long id_dataSekolah;
	
	@OneToOne(mappedBy="dataSekolah")
	private Users user;
	
	@Column(length=255)
	private String nama_sekolah;
	
	@Column(length=255)
	private String jenis_sekolah;
	
	@Column(length=255)
	private String alamat_sekolah;
	
	@Column(length=255)
	private String kota;
	
	@Column(length=255)
	private String provinsi;
	
	@Column(length=255)
	private String status_sekolah;
	
	@Column(length=255)
	private String jurusan;
	
	@Column(length=255)
	private String nilai_UN;
	
	@Column(length=255)
	private String tahun_lulus;
	
	@Column(length=255)
	private String kode_pos;
	
	@Column(length=255)
	private String nilai_rapor;
	
	
}
