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
public class DataKeluarga {
//	@OneToOne(fetch = FetchType.LAZY)
//	@PrimaryKeyJoinColumn
	//private Users users;
	
	@GenericGenerator(name="generator", strategy="foreign",
			parameters = @Parameter(name="property", value="users"))
	@Id
	@GeneratedValue
	private long id_keluarga;
	
	@OneToOne(mappedBy="dataKeluarga")
	private Users user;
	
}
