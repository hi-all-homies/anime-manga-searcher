package searcher.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import searcher.SearcherApp.StageReadyEvent;
import searcher.util.UILoader;

@Service
public class AppRenderer {
	
	@Value("${app.title}")
	private String appTitle;
	
	private final UILoader uiLoader;
	private final FavoritesService favService;
	
	public AppRenderer(UILoader uiLoader, FavoritesService favService) {
		this.uiLoader = uiLoader;
		this.favService = favService;
	}


	@EventListener
	public void lookForStageIsReady(StageReadyEvent event) {
		this.favService.initFavs();
		
		Stage primaryStage = event.getStage();
		var root = this.uiLoader.load("/MainWindow.fxml");
		primaryStage.setTitle(appTitle);
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
		primaryStage.setScene(scene);
		
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/icon.png")));
		primaryStage.setResizable(false);
		primaryStage.show();
	}
}
