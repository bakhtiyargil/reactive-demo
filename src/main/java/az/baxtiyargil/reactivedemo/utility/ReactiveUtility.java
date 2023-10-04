package az.baxtiyargil.reactivedemo.utility;

import az.baxtiyargil.reactivedemo.configuration.properties.ApplicationProperties;
import az.baxtiyargil.reactivedemo.model.reactive.ReactiveResponse;
import lombok.experimental.UtilityClass;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;
import reactor.core.publisher.SignalType;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Collection;
import java.util.function.Function;
import java.util.logging.Level;

@UtilityClass
public class ReactiveUtility {

    private final static String MONO_ERROR_CATEGORY = "error.client.mono.";

    public <T extends Number> ParallelFlux<T> createParallelFlux(Collection<T> collection,
                                                                 ApplicationProperties properties) {
        Flux<T> flux = Flux.fromIterable(collection);
        return flux
                .parallel()
                .runOn(Schedulers.fromExecutorService(
                                ExecutorServiceUtility.customizeAndGetThreadPoolExecutor(
                                        properties, collection.size())
                        )
                );
    }

    public <T extends Number, R extends ReactiveResponse>
    Function<? super T, ? extends Publisher<R>> monoFunction(Function<T, R> function) {
        return (id) -> Mono.fromCallable(() -> function.apply(id))
                .retryWhen(Retry.backoff(2, Duration.ofMillis(500)))
                .log(MONO_ERROR_CATEGORY, Level.INFO, SignalType.ON_ERROR)
                .onErrorComplete();
    }

}
