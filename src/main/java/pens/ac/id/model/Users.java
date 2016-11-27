package pens.ac.id.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
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
		@UniqueConstraint(columnNames = "email"),
		@UniqueConstraint(columnNames = "password") })
public class Users {
	
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Column(length=50, unique = true)
	private String email;
	
	@NotNull
	@Column(length=50)
	private String password;
	
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "users", cascade = CascadeType.ALL)
	@NotNull
	private DataDiri id_dataDiri;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "users", cascade = CascadeType.ALL)
	@NotNull
	private DataOrtu id_ortu;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "users", cascade = CascadeType.ALL)
	@NotNull
	private DataKeluarga id_keluarga;
	
	
}
