package az.baxtiyargill.reactivedemo.controller;

import az.baxtiyargill.reactivedemo.client.berry.BerryResponse;
import az.baxtiyargill.reactivedemo.service.ReactiveService;
import lombok.RequiredArgsConstructor;
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
    public List<BerryResponse> react() {
        return reactiveService.react();
    }

}
