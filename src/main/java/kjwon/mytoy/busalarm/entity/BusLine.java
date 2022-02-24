package kjwon.mytoy.busalarm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class BusLine{

    @Id
    private Long busRouteId;
    private String busName;
    private boolean direction;
}