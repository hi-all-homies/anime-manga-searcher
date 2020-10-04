package searcher.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextFlow;
import searcher.model.Work;
import searcher.util.WorkItemFactory;

public class WorkItemController {
	@FXML private Label score;
	@FXML private ImageView img;
    @FXML private TextFlow synopsis;
    @FXML private Label title;
    
    private final Work work;
    private final WorkItemFactory itmFactory;
    
    public WorkItemController(Work work, WorkItemFactory itmFactory) {
		this.work = work;
		this.itmFactory = itmFactory;
	}

	public void initialize() {
		this.title.setText(this.work.getTitle());
		this.score.setText(String.format("score: %d", this.work.getScore()));
		this.img.setImage(new Image(this.work.getImage_url()));
		this.synopsis.getChildren().add(this.itmFactory.createTextNode(this.work.getSynopsis()));
	}
}
