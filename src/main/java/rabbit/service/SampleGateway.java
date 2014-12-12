package rabbit.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface SampleGateway {
    @Gateway(requestChannel = "message.input")
    void sendMessage(String message);
}
