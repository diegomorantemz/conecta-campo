package com.conectacampo.model;

import com.conectacampo.model.enums.FairStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "fairs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fair extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farmer_id", nullable = false)
    private User farmer;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 150)
    private String locationName;

    private Double locationLat;

    private Double locationLng;

    @Column(nullable = false)
    private LocalDate fairDate;

    private LocalTime startTime;

    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    private FairStatus status = FairStatus.UPCOMING;
}