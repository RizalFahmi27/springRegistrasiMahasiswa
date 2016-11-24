package pens.ac.id;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MySpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(MySpringApplication.class, args);
		//AppContainer.getInstance().getServiceMahasiswa().add("Rizal", "Teknik Informatika");
		//AppContainer.getInstance().getServiceMahasiswa().add("Bey", "Hubungan Internasional");
	}
}
