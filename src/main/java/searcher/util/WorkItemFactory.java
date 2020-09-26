package searcher.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import searcher.model.Work;
import searcher.service.TransferService;

@Service
public class WorkItemFactory {
	private final UILoader uiLoader;
	private final TransferService transferService;
	
	private final Set<String> types = new HashSet<>();
	private static final String ANIME_VIEW = "/Anime.fxml";
	private static final String MANGA_VIEW = "/Manga.fxml";
	 
	public WorkItemFactory(UILoader uiLoader, TransferService transferService) {
		this.uiLoader = uiLoader;
		this.transferService = transferService;
		types.addAll(List.of("Manga", "Novel", "One-shot", "Manhwa"));
	}
	
	
	public Text createTextNode(String textString, Font...fonts) {
		Text text = new Text(textString);
		text.getStyleClass().add("my-text");
		if (fonts.length > 0)
			text.setFont(fonts[0]);
		return text;
	}
	
	public Text newLine() {return new Text(System.lineSeparator());}
	

	private final Insets rootInsets = new Insets(0d, 0d, 0d, 3d);
	
	public Node getWorkItemView(Work work, double fontSize) {
		var boldFont = new Font("Comic Sans MS Bold", fontSize);
		var usualFont = new Font("Comic Sans MS System", fontSize -1);
		Label title = createLabel(work.getTitle(), boldFont);
		Label score = createLabel(String.format("score: %d", work.getScore()), usualFont);
		VBox imgBox = createImgBox(score, work);
		StackPane stack = createTextStack(work, usualFont);
		HBox hbox = new HBox(5d, imgBox, stack);
		VBox root = new VBox(2d, title, hbox, new Label());
		root.setPadding(rootInsets);
		root.getStyleClass().add("work");
		return root;
	}
	
	private Label createLabel(String contents, Font font) {
		var label  = new Label(contents);
		label.setFont(font);
		label.setMaxWidth(550d);
		label.getStyleClass().add("my-text");
		return label;
	}
	
	private VBox createImgBox(Label score, Work work) {
		ImageView iv = new ImageView(work.getImage_url());
		iv.setFitHeight(290d);
		iv.setFitWidth(250d);
		iv.setPreserveRatio(true);
		iv.setSmooth(true);
		StackPane stack = new StackPane(iv);
		stack.getStyleClass().add("img-holder");
		
		var imgBox = new VBox(2d, score, stack);
		imgBox.setFillWidth(true);
		imgBox.setCursor(Cursor.HAND);
		imgBox.setOnMouseClicked(event -> mouseClikedHandler(event, work));
		return imgBox;
	}
	
	private void mouseClikedHandler(MouseEvent event, Work work) {
		if (event.getButton().compareTo(MouseButton.PRIMARY) == 0) {
			this.transferService.setCurrentId(work.getMal_id());
			String type = this.types.contains(work.getType()) ? MANGA_VIEW : ANIME_VIEW;
			final var root = this.uiLoader.load(type);
			
			Platform.runLater(() -> {
				Stage stage = new Stage();
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setScene(new Scene(root));
				stage.show();
			});
		}
	}
	
	private final Insets stackInsets = new Insets(175d, 0d, 0d, 0d);
	
	private StackPane createTextStack(Work work, Font font) {
		Text text = new Text(work.getSynopsis());
		text.getStyleClass().add("my-text");
		text.setFont(font);
		TextFlow flow = new TextFlow(text);
		flow.setTextAlignment(TextAlignment.CENTER);
		flow.setMaxWidth(300d);
		
		StackPane stack = new StackPane(flow);
		HBox.setMargin(stack, stackInsets);
		return stack;
	}
}