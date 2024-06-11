package ticketing.ticket;




import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;



@SpringBootApplication
@EnableJpaAuditing
public class TicketApplication {
	
	//ㅇ
	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(TicketApplication.class, args);

		
	}

}
