package mngcp;

import java.util.Random;
import java.util.concurrent.TimeUnit;
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
    Publisher<Event<Dice>> index() {
        final Flowable<Long> tick = Flowable.interval(500, TimeUnit.MILLISECONDS);
        final Flowable<Dice> diceRolls = Flowable.generate(() -> 0, (i, emitter) -> { 
            emitter.onNext(new Dice());
            if (i == 100) emitter.onComplete();
            return ++i;
        });;
 
        return tick.zipWith(diceRolls, this::createEvent);
    }

    private Event<Dice> createEvent(Long counter, Dice diceRoll) {
        return Event.of(diceRoll)
                    .id(String.valueOf(counter));
    }
 }

 class Dice {
    private int value;
    private static transient Random rand = new Random();

    public Dice() {
        this.value = rand.nextInt(6) + 1;
    }

    public int getValue() {
        return this.value;
    }
 }