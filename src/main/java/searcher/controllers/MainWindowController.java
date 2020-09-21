package searcher.controllers;

import org.springframework.stereotype.Component;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

@Component
public class MainWindowController {
	
	@FXML private Label anime_label;
    @FXML private TextField search;
    @FXML private Label manga_label;
    @FXML private Slider toggler;
    @FXML private VBox content_vbox;

	public void initialize() {
search.textProperty().addListener((obs, ov, nv) -> {
			content_vbox.getChildren().add(new Button(nv));
		});
	}
}
