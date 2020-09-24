package searcher.service;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Service;

@Service
public class TransferService {
	private final AtomicInteger current_mal_id = new AtomicInteger();
	
	
	public void setCurrentId(int mal_id) {
		this.current_mal_id.set(mal_id);
	}
	
	public int getCurrentId() {
		return this.current_mal_id.get();
	}
}
