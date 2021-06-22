package mngcp;

import io.micronaut.http.annotation.*;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/webhook")
public class WebhookController {

    private static final Logger LOG = LoggerFactory.getLogger(WebhookController.class);

    @Produces(MediaType.TEXT_PLAIN)
    @Get
    public String index() {
        return "Webhook controller ready!";
    }

    @Post
    public void postMethod(@Body String rawInput) {
        LOG.info("Webhook payload: {}", rawInput);
    }
}