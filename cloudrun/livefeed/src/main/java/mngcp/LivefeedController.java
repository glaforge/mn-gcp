package mngcp;

import java.util.Random;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.sse.Event;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.reactivex.Flowable;
import org.reactivestreams.Publisher;

@Controller("/livefeed")
public class LivefeedController {
    @ExecuteOn(TaskExecutors.IO)
    @Get(produces = MediaType.TEXT_EVENT_STREAM)
    public Publisher<Event> index() { 
        Random rand = new Random();
        return Flowable.generate(() -> 0, (i, emitter) -> { 
            if (i < 20) {
                emitter.onNext( 
                    Event.of("Dice: " + (rand.nextInt(6) + 1))
                );
                try { Thread.sleep(1000); } catch (Throwable t) {}
            } else {
                emitter.onComplete(); 
            }
            return ++i;
        });
    }
 }