package az.baxtiyargil.reactivedemo.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "berry", schema = "reactive")
public class Berry {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "size")
    private Integer size;

    @Column(name = "growth_time")
    private Integer growthTime;


    @Column(name = "soil_dryness")
    private Integer soilDryness;

    @Column(name = "move_id")
    private Long moveId;

}
