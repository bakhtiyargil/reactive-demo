package az.baxtiyargil.reactivedemo.service;

import az.baxtiyargil.reactivedemo.utility.ExecutorServiceUtility;
import az.baxtiyargil.reactivedemo.client.berry.BerryResponse;
import az.baxtiyargil.reactivedemo.client.move.PokemonMoveClient;
import az.baxtiyargil.reactivedemo.configuration.properties.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static reactor.core.scheduler.Schedulers.fromExecutorService;


@Service
@RequiredArgsConstructor
public class ReactiveService {

    private final PokemonMoveClient pokemonMoveClient;
    private final ApplicationProperties applicationProperties;

    public List<BerryResponse> react() {
        AtomicInteger counter = new AtomicInteger(0);
        var berryMap = dummyBerriesWithSuccess();

        Flux<Long> flow = Flux.fromIterable(berryMap.keySet());
        flow
                .parallel()
                .runOn(fromExecutorService(
                        ExecutorServiceUtility.customizeAndGetThreadPoolExecutor(applicationProperties, berryMap.size())
                ))
                .flatMap(berryId -> Mono.fromCallable(() ->
                                {
                                    var move = pokemonMoveClient.getMoveInfo(berryId);
                                    berryMap.get(berryId).forEach(berry -> berry.setMoveResponse(move));
                                    return berryId;
                                })
                                .retryWhen(Retry.backoff(2, Duration.ofMillis(100)))
                                .log("error.client.mono.", Level.INFO, SignalType.ON_ERROR)
                                .doOnError((t) -> counter.getAndIncrement())
                                .onErrorComplete()
                )
                .collectSortedList(Comparator.comparing(Long::longValue))
                .block(Duration.ofSeconds(5));

        if (berryMap.size() == counter.get()) {
            throw new RuntimeException("Failed to load customers data!");
        }

        return berryMap.values().stream()
                .flatMap(List::stream)
                .collect(ArrayList::new, List::add, List::addAll);
    }

    /**
     * A method that returns dummy data resulting in an error response for the MoveClient.
     *
     * @return Map of berries with id as the key.
     */
    private Map<Long, List<BerryResponse>> dummyBerriesWithError() {
        //TODO: This method should be replaced with the service method that
        // retrieves the corresponding data from the database.
        var berry1 = BerryResponse.builder().id(1000L).build();
        var berry2 = BerryResponse.builder().id(1002L).build();
        var berry3 = BerryResponse.builder().id(1003L).build();
        var berry4 = BerryResponse.builder().id(1003L).build();
        var berry5 = BerryResponse.builder().id(1005L).build();
        var berry6 = BerryResponse.builder().id(1006L).build();
        var berry7 = BerryResponse.builder().id(1007L).build();
        var berry8 = BerryResponse.builder().id(1008L).build();
        var berry9 = BerryResponse.builder().id(1009L).build();
        var berry10 = BerryResponse.builder().id(999L).build();
        return Stream.of(berry1, berry2, berry3, berry4, berry5, berry6, berry7, berry8, berry9, berry10)
                .collect(Collectors.groupingBy(BerryResponse::getId));
    }

    /**
     * A method that returns dummy data resulting in a successful response for the MoveClient.
     *
     * @return Map of berries with id as the key.
     */
    private Map<Long, List<BerryResponse>> dummyBerriesWithSuccess() {
        //TODO: This method should be replaced with the service method that
        // retrieves the corresponding data from the database.
        var berry1 = BerryResponse.builder().id(1L).build();
        var berry2 = BerryResponse.builder().id(2L).build();
        var berry3 = BerryResponse.builder().id(3L).build();
        var berry4 = BerryResponse.builder().id(3L).build();
        var berry5 = BerryResponse.builder().id(5L).build();
        var berry6 = BerryResponse.builder().id(6L).build();
        var berry7 = BerryResponse.builder().id(7L).build();
        var berry8 = BerryResponse.builder().id(8L).build();
        var berry9 = BerryResponse.builder().id(9L).build();
        var berry10 = BerryResponse.builder().id(999L).build();
        return Stream.of(berry1, berry2, berry3, berry4, berry5, berry6, berry7, berry8, berry9, berry10)
                .collect(Collectors.groupingBy(BerryResponse::getId));
    }

}
