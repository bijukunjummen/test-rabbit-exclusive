package rabbit.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SampleQueuePopulator implements CommandLineRunner {

    @Autowired
    private SampleGateway sampleGateway;

    @Override
    public void run(String... strings) throws Exception {
        for (int i = 0; i < 10; i++) {
            this.sampleGateway.sendMessage("Test Message " + i);
        }
    }
}
