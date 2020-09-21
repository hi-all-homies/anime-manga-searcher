package searcher.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import javafx.scene.Scene;
import javafx.stage.Stage;
import searcher.SearcherApp.StageReadyEvent;
import searcher.util.UILoader;

@Service
public class AppRenderer {
	
	@Value("${app.title}")
	private String appTitle;
	
	private final UILoader uiLoader;
	
	public AppRenderer(UILoader uiLoader) {
		this.uiLoader = uiLoader;
	}


	@EventListener
	public void lookForStageIsReady(StageReadyEvent event) {
		Stage primaryStage = event.getStage();
		var root = this.uiLoader.load("/MainWindow.fxml");
		primaryStage.setTitle(appTitle);
		primaryStage.setScene(new Scene(root));
		primaryStage.setResizable(false);
		primaryStage.show();
	}
}
