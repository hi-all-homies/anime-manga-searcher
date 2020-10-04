package searcher.model;

public class ErrorWork extends Work {
	
	public ErrorWork(String errorUrl) {
		super(-1, errorUrl, ":(", "error", 666, "server is unavailable, try later...");
	}
}
