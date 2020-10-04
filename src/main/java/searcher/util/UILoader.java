package searcher.util;

import java.io.IOException;
import java.net.URL;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import searcher.config.ViewTypes;
import searcher.controllers.FavItemController;
import searcher.controllers.WorkItemController;
import searcher.model.FavWork;
import searcher.model.Work;
import searcher.service.TransferService;

@Service
public class UILoader {
    private final ApplicationContext context;
    private final URL favItemUrl;
    private final URL workItemUrl;
	private final TransferService transferService;
	private final ViewTypes viewTypes;
	private final WorkItemFactory itemFactory;

    public UILoader(
    		ApplicationContext context,
    		TransferService transferService,
    		ViewTypes viewTypes,
    		WorkItemFactory itemFactory) {
        this.context = context;
        this.transferService = transferService;
        this.viewTypes = viewTypes;
        this.itemFactory = itemFactory;
        this.favItemUrl = getClass().getResource("/FavItem.fxml");
        this.workItemUrl = getClass().getResource("/WorkItem.fxml");
    }

    public Parent load(String sourceName) {
        try {
            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource(sourceName));
            loader.setControllerFactory(context::getBean);
            return loader.load();
        } catch (IOException e) {
        	e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
    
    public Parent loadFavItem(final FavWork favWork) {
    	try {
			FXMLLoader loader =
					new FXMLLoader(this.favItemUrl);
			loader.setControllerFactory(clazz ->
				new FavItemController(favWork, this, this.transferService, this.viewTypes));
			return loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
    }

	public Parent loadWorkItem(final Work work) {
		try {
			FXMLLoader loader =
					new FXMLLoader(this.workItemUrl);
			loader.setControllerFactory(clazz -> new WorkItemController(work, itemFactory));
			return loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
}
