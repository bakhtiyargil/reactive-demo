package az.baxtiyargil.reactivedemo.client.berry;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "pokemon-berry-client",
        url = "${application.client.pokemon-berry.url}",
        primary = false
)
public interface PokemonBerryClient {

    @GetMapping("/v2/berry/{id}")
    BerryResponse getBerryInfo(@PathVariable Integer id);


}
