package org.delivery.db.menu;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.delivery.db.BaseEntity;
import org.delivery.db.restaurant.RestaurantEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@SuperBuilder
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "menu")
@NoArgsConstructor
public class MenuEntity extends BaseEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private RestaurantEntity restaurant;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Version
    private Long version;

    @Column(nullable = false)
    private Integer stock;
}


