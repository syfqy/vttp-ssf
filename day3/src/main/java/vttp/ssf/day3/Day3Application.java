package vttp.ssf.day3;

import static vttp.ssf.day3.utils.IOUtil.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Day3Application {
	
	private static final Logger logger = LoggerFactory.getLogger(Day3Application.class);

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(Day3Application.class);
		DefaultApplicationArguments appArgs = new DefaultApplicationArguments(args);
		List<String> argOptions = appArgs.getOptionValues("dataDir");
		String dataDir;
		
		// check data dir was provided
		if (argOptions == null) {
			logger.warn("No data directory provided");
			System.exit(1);
		}

		dataDir = argOptions.get(0);
		logger.info("Data directory > " + dataDir);
		createDir(dataDir);

		app.run(args);
	}

}
