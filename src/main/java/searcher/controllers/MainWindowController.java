package searcher.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;
import searcher.service.WorkService;
import searcher.util.UILoader;

@Component
public class MainWindowController {
	
	 @FXML private TextField search;
	 @FXML private ToggleGroup toggles;
	 @FXML private ToggleButton manga;
	 @FXML private ToggleButton anime;
	 @FXML private VBox content_vbox;
	 
	 private final WorkService workService;
	 private final UILoader uiLoader;
	 private final UnicastProcessor<String> publisher;
	 private final Flux<String> source;
	 
	  
	public MainWindowController(
			@Qualifier("anime") WorkService workService,
			@Qualifier("publisher") UnicastProcessor<String> publisher,
			@Qualifier("source") Flux<String> source,
			UILoader uiLoader) {
		this.workService = workService;
		this.publisher = publisher;
		this.source = source;
		this.uiLoader = uiLoader;
	}


	public void initialize() {
		anime.setOnAction(this::toggleHandler);
		manga.setOnAction(this::toggleHandler);
	}
	
	
	private void toggleHandler(ActionEvent event) {
		var togl = (ToggleButton) event.getSource();
		if (!togl.isSelected())
			togl.setSelected(true);
	}
}
