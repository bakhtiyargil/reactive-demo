package az.baxtiyargil.reactivedemo.controller;

import az.baxtiyargil.reactivedemo.model.request.BerrySearchDto;
import az.baxtiyargil.reactivedemo.model.response.BerryView;
import az.baxtiyargil.reactivedemo.service.ReactiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reactive")
public class ReactiveController {

    private final ReactiveService reactiveService;

    @GetMapping("/react")
    public List<BerryView> react(BerrySearchDto searchDto, Pageable pageable) {
        return reactiveService.react(searchDto, pageable);
    }

}
