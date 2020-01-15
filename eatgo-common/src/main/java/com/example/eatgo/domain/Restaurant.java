package com.example.eatgo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Restaurant {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String address;

    @Builder.Default
    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<MenuItem> menuItems = new ArrayList<>();

    @Builder.Default
    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Review> reviews = new ArrayList<>();

    public Restaurant(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        for (MenuItem menuItem : menuItems) {
            addMenuItem(menuItem);
        }
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void updateInformation(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
