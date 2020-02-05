package com.example.eatgo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private Long restaurantId;

    @NotEmpty
    private String email;

    @NotEmpty
    private String name;

    private Long level;

    private String password;

    public boolean isAdmin() {
        return this.level >= 100;
    }

    public boolean isActive() {
        return this.level > 0;
    }

    public void deactivate() {
        this.level = 0L;
    }

    public void setRestaurantId(Long restaurantId) {
        this.level = 50L;
        this.restaurantId = restaurantId;
    }

    public void update(String email, String name, Long level) {
        this.email = email;
        this.name = name;
        this.level = level;
    }

    public boolean isRestaurantOwner() {
        return this.level == 50L;
    }

}
