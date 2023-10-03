package az.baxtiyargil.reactivedemo.service;

import az.baxtiyargil.reactivedemo.client.move.PokemonMoveClient;
import az.baxtiyargil.reactivedemo.configuration.properties.ApplicationProperties;
import az.baxtiyargil.reactivedemo.model.request.BerrySearchDto;
import az.baxtiyargil.reactivedemo.model.response.BerryView;
import az.baxtiyargil.reactivedemo.utility.ExecutorServiceUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static reactor.core.scheduler.Schedulers.fromExecutorService;


@Service
@RequiredArgsConstructor
public class ReactiveService {

    private final BerryService berryService;
    private final PokemonMoveClient pokemonMoveClient;
    private final ApplicationProperties applicationProperties;

    public List<BerryView> react(BerrySearchDto searchDto, Pageable pageable) {
        if (Objects.nonNull(searchDto.getMoveFilter())) {
            return getBerriesForOneCustomer(searchDto, pageable);
        } else {
            return getBerriesForMultipleCustomer(searchDto, pageable);
        }
    }

    private List<BerryView> getBerriesForMultipleCustomer(BerrySearchDto searchDto, Pageable pageable) {
        AtomicInteger counter = new AtomicInteger(0);
        var berryMap = berryService.search(searchDto.getBerryFilter(), pageable)
                .stream()
                .collect(Collectors.groupingBy(berryView -> berryView.getMoveResponse().getId()));

        //TODO: divide, generic, consumers
        Flux<Long> flow = Flux.fromIterable(berryMap.keySet());
        flow
                .parallel()
                .runOn(fromExecutorService(
                        ExecutorServiceUtility.customizeAndGetThreadPoolExecutor(applicationProperties, berryMap.size())
                ))
                .flatMap(moveId -> Mono.fromCallable(() ->
                                {
                                    var move = pokemonMoveClient.getMoveInfo(moveId);
                                    berryMap.get(moveId).forEach(berry -> berry.setMoveResponse(move));
                                    return moveId;
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

    private List<BerryView> getBerriesForOneCustomer(BerrySearchDto searchDto, Pageable pageable) {
        var moveResponse = pokemonMoveClient.getMoveInfo(searchDto.getMoveFilter().getId());
        return berryService.search(searchDto.getBerryFilter(), pageable)
                .stream()
                .peek(berryView -> berryView.setMoveResponse(moveResponse))
                .collect(Collectors.toList());
    }

}
