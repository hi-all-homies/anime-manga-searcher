package searcher.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

@Configuration
public class ChangeEventPublisherConfig {

	@Bean("publisher")
	public UnicastProcessor<String> publisher(){
		return UnicastProcessor.<String>create();
	}
	
	@Bean("source")
	public Flux<String> source(@Qualifier("publisher") UnicastProcessor<String> publisher){
		return publisher.publish()
				.autoConnect(1)
				.cache(0);
	}
}
