package searcher.controllers;

import java.time.Duration;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.UnicastProcessor;
import reactor.core.scheduler.Schedulers;
import searcher.model.Work;
import searcher.service.WorkService;
import searcher.util.WorkItemFactory;

@Component
public class MainWindowController {
	
	 @FXML private TextField search;
	 @FXML private ToggleGroup toggles;
	 @FXML private ToggleButton manga;
	 @FXML private ToggleButton anime;
	 @FXML private VBox content_vbox;
	 private ProgressBar progress;
	 
	 private final WorkService workService;
	 private final UnicastProcessor<String> publisher;
	 private final Flux<String> source;
	 private final ChangeSearchStateEventListener eventListener;
	 private final WorkItemFactory workItemFactory; 
	 
	public MainWindowController(
			@Qualifier("anime") WorkService workService,
			@Qualifier("publisher") UnicastProcessor<String> publisher,
			@Qualifier("source") Flux<String> source,
			WorkItemFactory workItemFactory) {
		this.workService = workService;
		this.publisher = publisher;
		this.source = source;
		this.workItemFactory = workItemFactory;
		this.eventListener = new ChangeSearchStateEventListener();
	}
	
	public void initialize() {
		this.initProgressBar();
		anime.setOnAction(this::toggleHandler);
		manga.setOnAction(this::toggleHandler);
		
		this.search.textProperty().addListener((obs, oldVal, newVal) -> this.publisher.onNext(newVal));
		
		this.eventListener.subscribeForEvents();
	}
	
	private void toggleHandler(ActionEvent event) {
		var togl = (ToggleButton) event.getSource();
		if (!togl.isSelected())
			togl.setSelected(true);
	}
	
	private void initProgressBar() {
		this.progress = new ProgressBar();
		this.progress.setPrefSize(400d, 20d);
		VBox.setMargin(this.progress, new Insets(150d, 50d, 0d, 50d));
	}
	
	
	private class ChangeSearchStateEventListener {
		
		private void subscribeForEvents() {
			source.subscribeOn(Schedulers.single())
				.sampleTimeout(searchValue -> Mono.delay(Duration.ofMillis(2000l)))
				.map(searchValue -> searchValue.trim())
				.filter(checkSearchValue)
				.doOnNext(removeLastResults)
				.flatMap(findWorks)
				.doOnNext(works -> Platform.runLater(() -> content_vbox.getChildren().clear()))
				.flatMap(works -> Flux.fromIterable(works))
				.delayElements(Duration.ofMillis(150l))
				.subscribe(addWork);
		}
		
		
		private final Predicate<String> checkSearchValue = searchValue -> 
			searchValue.length() > 3 && StringUtils.hasText(searchValue);
			
			
		private final Consumer<String> removeLastResults =
				searchValue -> Platform.runLater(() -> {
					content_vbox.getChildren().clear();
					content_vbox.getChildren().add(progress);});
		
			
		private final Function<String, Mono<List<Work>>> findWorks = searchValue ->
			workService.findWorkByItsTitle(searchValue,
					((ToggleButton) toggles.getSelectedToggle()).getText());
			
			
		private final Consumer<Work> addWork = work -> {
			var guiElem = workItemFactory.getWorkItemView(work, 15d);
			Platform.runLater(() -> {
				content_vbox.getChildren().add(guiElem);});
		};
	}
}
