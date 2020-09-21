package searcher.util;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import searcher.model.Work;

public class WorkItemFactory {

	public static Node getWorkItemView(Work work, double fontSize) {
		var boldFont = new Font("Comic Sans MS Bold", fontSize);
		var usualFont = new Font("Comic Sans MS System", fontSize -1);
		Label title = new Label(work.getTitle());
		title.setFont(boldFont);
		Label score = new Label(String.format("rating: %d", work.getScore()));
		score.setFont(usualFont);
		
		ImageView iv = new ImageView(work.getImage_url());
		iv.setFitHeight(290d);
		iv.setFitWidth(250d);
		iv.setPreserveRatio(true);
		iv.setSmooth(true);
		
		VBox imgBox = new VBox(2d, title, score, iv);
		imgBox.setFillWidth(true);
		
		Text text = new Text(work.getSynopsis());
		text.setFont(usualFont);
		TextFlow flow = new TextFlow(text);
		flow.setMaxHeight(250d);
		StackPane stack = new StackPane(flow);
		
		AnchorPane root = new AnchorPane(imgBox, stack);
		AnchorPane.setTopAnchor(imgBox, 0d);
		AnchorPane.setLeftAnchor(imgBox, 0d);
		AnchorPane.setBottomAnchor(stack, 15d);
		AnchorPane.setRightAnchor(stack, 10d);
		AnchorPane.setLeftAnchor(stack, 250d);
		
		return root;
	}
}
