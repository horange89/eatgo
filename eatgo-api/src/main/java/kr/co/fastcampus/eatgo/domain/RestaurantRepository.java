package kr.co.fastcampus.eatgo.domain;

import jdk.internal.vm.compiler.word.LocationIdentity;

import java.util.List;

public interface RestaurantRepository {
    List<Restaurant> findAll();

    Restaurant findById(Long id);

    Restaurant save(Restaurant restaurant);
}
