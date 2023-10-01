package az.baxtiyargil.reactivedemo.client.move;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "pokemon-move-client",
        url = "${application.client.pokemon-move.url}",
        primary = false
)
public interface PokemonMoveClient {

    @GetMapping("/v2/move/{id}")
    MoveResponse getMoveInfo(@PathVariable Long id);

}
