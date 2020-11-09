package myProject.WebSocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * This class acts as the WebSocket Configuration.
 * The WebSocket endpoint handler is registered with SPRING.
 * 
 * @author Christian Lisle
 * Credit: Vamsi Krishna Calpakkam
 *
 */
@Configuration
public class WebSocketConfig {
	@Bean
	public ServerEndpointExporter serverEndpointExporter()	{
		return new ServerEndpointExporter();
	}
}
