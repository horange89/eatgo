package kr.co.fastcampus.eatgo.domain;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


class UserTests {

    @Test
    public void ceration() {
        User user = User.builder().email("tester@example.com").name("테스터").level(100L).build();

        assertThat(user.getName(), is("테스터"));
        assertThat(user.isAdmin(), is(true));
        assertThat(user.isActive(), is(true));

        user.deactive();

        assertThat(user.isActive(), is(false));
    }

    @Test
    public void restaurantOwner() {
        User user = User.builder().level(1L).build();

        assertThat(user.isRestaurantOwner(), is(false));

        user.setRestaurantId(1004L);

        assertThat(user.isRestaurantOwner(), is(true));
        assertThat(user.getRestaurantId(), is(1004L));
    }
}