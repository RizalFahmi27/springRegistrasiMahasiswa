package pens.ac.id.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class LokasiUjian {
	
	@GenericGenerator(name="generator", strategy="foreign",
			parameters = @Parameter(name="property", value="users"))
	@Id
	@GeneratedValue
	private long id_lokasi_ujian;
	
	@Column(length=255)
	private String nama_lokasi;
	
	@Column(length=255)
	private String alamat;
	
	@OneToOne(mappedBy="lokasiUjian")
	private Users user;
}
