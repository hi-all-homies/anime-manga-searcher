package searcher.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import searcher.config.ViewTypes;
import searcher.controllers.FavItemController;
import searcher.model.FavWork;
import searcher.service.TransferService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URL;

@Service
public class UILoader {
    private final ApplicationContext context;
    private final URL favItemUrl;
	private final TransferService transferService;
	private final ViewTypes viewTypes;

    public UILoader(ApplicationContext context, TransferService transferService, ViewTypes viewTypes) {
        this.context = context;
        this.transferService = transferService;
        this.viewTypes = viewTypes;
        this.favItemUrl = getClass().getResource("/FavItem.fxml");
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
}
