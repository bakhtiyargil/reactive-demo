package az.baxtiyargil.reactivedemo.service;

import az.baxtiyargil.reactivedemo.mapper.BerryMapper;
import az.baxtiyargil.reactivedemo.repository.predicate.filter.BerryPredicateBuilder;
import az.baxtiyargil.reactivedemo.model.response.BerryView;
import az.baxtiyargil.reactivedemo.repository.BerryRepository;
import az.baxtiyargil.reactivedemo.repository.predicate.filter.BerryFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BerryService {

    private final BerryMapper berryMapper;
    private final BerryRepository berryRepository;

    public List<BerryView> search(BerryFilter filter, Pageable page) {
        return berryRepository.findAll(BerryPredicateBuilder.build(filter), page)
                .stream()
                .map(berryMapper::mapToBerryView)
                .collect(Collectors.toList());
    }

}
