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

public class DataOrtu {
//	@OneToOne(fetch = FetchType.LAZY)
//	@PrimaryKeyJoinColumn
//	private Users users;
	
//	@GenericGenerator(name="generator", strategy="foreign",
//			parameters = @Parameter(name="property", value="users"))
	@Id
	@GeneratedValue
	private long id_ortu;
	
	@OneToOne(mappedBy="dataOrtu")
	private Users user;
	
	@Column(length=255)
	private String nama_ayah;
	
	@Column(length=50)
	private String jenis_kelamin_ayah;
	
	@Column(length=50)
	private String tempat_lahir_ayah;
	
	@Column(length=255)
	private String alamat_asal_ayah;
	
	@Column(length=50)
	private String kota_ayah;
	
	@Column(length=50)
	private String provinsi_ayah;
	
	@Column(length=50)
	private String no_telepon_ayah;
	
	@Column(length=50)
	private String email_ayah;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date tanggal_lahir_ayah;
	
	@Column(length=50)
	private String kode_pos_ayah;
	
	@Column(length=255)
	private String pekerjaan_ayah;
	
	@Column(length=255)
	private String nama_ibu;
	
	@Column(length=50)
	private String jenis_kelamin_ibu;
	
	@Column(length=50)
	private String tempat_lahir_ibu;
	
	@Column(length=255)
	private String alamat_asal_ibu;
	
	@Column(length=50)
	private String kota_ibu;
	
	@Column(length=50)
	private String provinsi_ibu;
	
	@Column(length=50)
	private String no_telepon_ibu;
	
	@Column(length=50)
	private String email_ibu;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date tanggal_lahir_ibu;
	
	@Column(length=50)
	private String kode_pos_ibu;
	
	@Column(length=255)
	private String pekerjaan_ibu;
}
