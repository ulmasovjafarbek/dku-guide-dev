package net.duke.dkuguide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class DkuGuideApplication {

	public static void main(String[] args) {
		SpringApplication.run(DkuGuideApplication.class, args);
	}

}

@Controller
@RequestMapping("/")
class Welcome {
	@GetMapping
	private String goHome() {
		return "index";
	}

	@GetMapping("/premium")
	private String premium() {
		return "premium";
	}
}
