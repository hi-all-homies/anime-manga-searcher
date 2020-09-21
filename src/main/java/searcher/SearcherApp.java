package searcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

@SpringBootApplication
public class SearcherApp extends Application {
	private ConfigurableApplicationContext context;
	
	@Override
	public void init() throws Exception {
		this.context = SpringApplication.run(SearcherApp.class);
	}
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.context.publishEvent(new StageReadyEvent(primaryStage));
	}
	

	@Override
	public void stop() throws Exception {
		this.context.close();
		Platform.exit();
	}
	
	
	
	@SuppressWarnings("serial")
	public class StageReadyEvent extends ApplicationEvent {

		public StageReadyEvent(Stage source) {
			super(source);
		}
		
		public Stage getStage() {
			return (Stage) getSource();
		}
	}
}
