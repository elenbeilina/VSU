package com.aqualen.vsu.entity;

import com.aqualen.vsu.enums.TechnologyName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@Entity
@Table(name = "technology",schema="vsu")
@DynamicUpdate
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Technology {
    @Id
    @Column(name = "tournament_id")
    private Long id;
    private TechnologyName technology;
    private Integer percent;

    @ManyToOne
    @MapsId
    @JoinColumn(name = "tournament_id")
    @JsonIgnore
    private Tournament tournament;
}
