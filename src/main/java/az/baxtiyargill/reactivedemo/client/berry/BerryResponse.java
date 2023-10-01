package az.baxtiyargill.reactivedemo.client.berry;

import az.baxtiyargill.reactivedemo.client.move.MoveResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BerryResponse {

    Long id;
    String name;
    String size;
    Firmness firmness;
    @JsonProperty("growth_time")
    String growthTime;
    @JsonProperty("max_harvest")
    String maxHarvest;
    @JsonProperty("natural_gift_power")
    String naturalGiftPower;
    @JsonProperty("soil_dryness")
    String soilDryness;
    MoveResponse moveResponse;

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Firmness {
        String name;
        String url;
    }

}
