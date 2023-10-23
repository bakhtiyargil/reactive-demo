package az.baxtiyargil.reactivedemo.service;

import az.baxtiyargil.reactivedemo.client.move.MoveResponse;
import az.baxtiyargil.reactivedemo.client.move.PokemonMoveClient;
import az.baxtiyargil.reactivedemo.configuration.properties.ApplicationProperties;
import az.baxtiyargil.reactivedemo.model.reactive.ReactiveResponse;
import az.baxtiyargil.reactivedemo.model.request.BerrySearchDto;
import az.baxtiyargil.reactivedemo.model.response.BerryView;
import az.baxtiyargil.reactivedemo.utility.ReactiveUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ReactiveService {

    private final BerryService berryService;
    private final PokemonMoveClient pokemonMoveClient;
    private final ApplicationProperties applicationProperties;

    public List<BerryView> react(BerrySearchDto searchDto, Pageable pageable) {
        if (Objects.nonNull(searchDto.getMoveFilter())) {
            return getBerriesForOneMove(searchDto, pageable);
        } else {
            return getBerriesForMultipleMove(searchDto, pageable);
        }
    }

    private List<BerryView> getBerriesForMultipleMove(BerrySearchDto searchDto, Pageable pageable) {
        var berryMap = berryService.search(searchDto.getBerryFilter(), pageable)
                .stream()
                .collect(Collectors.groupingBy(berryView -> berryView.getMoveResponse().getId()));

        var parallelFlux = ReactiveUtility.createParallelFlux(berryMap.keySet(), applicationProperties);
        parallelFlux
                .flatMap(ReactiveUtility.toMonoFunction(pokemonMoveClient::getMoveInfo))
                .map(mapperFunction(berryMap))
                .collectSortedList(Comparator.comparing(ReactiveResponse::getId))
                .block(Duration.ofSeconds(5));

        return berryMap.values().stream()
                .flatMap(List::stream)
                .collect(ArrayList::new, List::add, List::addAll);
    }

    private List<BerryView> getBerriesForOneMove(BerrySearchDto searchDto, Pageable pageable) {
        var moveResponse = pokemonMoveClient.getMoveInfo(searchDto.getMoveFilter().getId());
        return berryService.search(searchDto.getBerryFilter(), pageable)
                .stream()
                .peek(berryView -> berryView.setMoveResponse(moveResponse))
                .collect(Collectors.toList());
    }

    private Function<MoveResponse, MoveResponse> mapperFunction(Map<Long, List<BerryView>> berryMap) {
        return (moveResponse) -> {
            berryMap.get(moveResponse.getId())
                    .forEach(berryView -> berryView.setMoveResponse(moveResponse));
            return moveResponse;
        };
    }

}
