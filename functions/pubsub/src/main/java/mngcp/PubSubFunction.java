package mngcp;

import com.google.cloud.functions.*;
import io.micronaut.gcp.function.GoogleFunctionInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.inject.*;
import java.util.*;

public class PubSubFunction extends GoogleFunctionInitializer 
        implements BackgroundFunction<PubSubMessage> { 

    @Inject LoggingService loggingService; 

    @Override
    public void accept(PubSubMessage message, Context context) {
        loggingService.logMessage(message);
    }
}

class PubSubMessage {
    String data;
    Map<String, String> attributes;
    String messageId;
    String publishTime;
}

@Singleton
class LoggingService {
    private static final Logger LOG = LoggerFactory.getLogger(LoggingService.class);

    void logMessage(PubSubMessage message) {
        String base64encoded = message.data;
        String decoded = new String(Base64.getDecoder().decode(base64encoded));
        LOG.info("Message: {}", decoded);
    }
}