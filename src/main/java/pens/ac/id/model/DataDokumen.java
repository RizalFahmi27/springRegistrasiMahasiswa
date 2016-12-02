package pens.ac.id.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DataDokumen {
	
//	@OneToOne(fetch = FetchType.LAZY)
//	@PrimaryKeyJoinColumn
//	private Users users;
	
//	@GenericGenerator(name="generator", strategy="foreign",
//			parameters = @Parameter(name="property", value="users"))
	@Id
	@GeneratedValue
	private long id_dataDokumen;
	
	@Column(length=255)
	private String namaFileFoto;
	
	@Column(length=255)
	private String namaFileSKL;
	
	@Column(length=255)
	private String namaFileKK;
	
	@OneToOne(mappedBy= "dataDokumen")
	private Users user;
	
	public String statusFoto;
	public String statusKK;
	public String statusSKL;
}
