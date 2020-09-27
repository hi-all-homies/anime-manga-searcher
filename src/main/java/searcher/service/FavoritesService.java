package searcher.service;

import java.io.IOException;
import java.util.Map;
import org.springframework.stereotype.Service;
import searcher.model.FavWork;
import searcher.util.FileStorage;

@Service
public class FavoritesService {
	private final FileStorage fieStorage;
	private Map<Integer, FavWork> favorites;
	
	public FavoritesService(FileStorage fieStorage) {
		this.fieStorage = fieStorage;
	}
	
	public void initFavs() {
		try {
			this.favorites = this.fieStorage.loadFavorites();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public FavWork addFav(FavWork fav) {
		return this.favorites.put(fav.getMal_id(), fav);
	}
	
	public FavWork removeFav(FavWork fav) {
		return this.favorites.remove(fav.getMal_id());
	}
	
	public boolean isFavorite(int mal_id) {
		return this.favorites.containsKey(mal_id);
	}
	
	public void saveFavs() {
		this.fieStorage.saveFavorites(this.favorites);
	}
}
