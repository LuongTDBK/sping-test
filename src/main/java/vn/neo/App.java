package vn.neo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableScheduling
//@ImportResource({ "file:config/beans.xml", "file:config/schedule-conf.xml" })
public class App {
	private static final Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		try {
			logger.info("init DB-Compare");
			SpringApplication.run(App.class, args);
			System.out.println("Server is ready");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
