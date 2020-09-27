package searcher.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import searcher.model.FavWork;

@Service
public class FileStorage {
	private static final String PATH = "favorites.ser";

	public void saveFavorites(Map<Integer, FavWork> favs) {
		File favorites = new File(PATH);
		try(
				FileOutputStream out = new FileOutputStream(favorites);
				ObjectOutputStream writer = new ObjectOutputStream(out);
		){
			writer.writeObject(favs);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	public Map<Integer, FavWork> loadFavorites() throws IOException{
		File favorites = new File(PATH);
		if (!favorites.isFile()) {
			favorites = Files.createFile(Path.of(PATH)).toFile();
			this.saveFavorites(new HashMap<Integer, FavWork>());
		}
		
		try(
			FileInputStream in = new FileInputStream(favorites);
			ObjectInputStream reader = new ObjectInputStream(in);
		){
			return (Map<Integer, FavWork>) reader.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
}
