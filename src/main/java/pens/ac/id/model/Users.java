package pens.ac.id.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Users {
	
	@Id
	@GeneratedValue
	private long id;
	
	@GeneratedValue
	private long idDataDiri;
	
	@Column(length=50)
	private String username;
	
	@Column(length=50)
	private String nama_depan;
	
	@Column(length=50)
	private String nama_belakang;
	
	@Column(length=50, unique = true)
	private String email;
	
	@Column(length=50)
	private String password;
	
}
