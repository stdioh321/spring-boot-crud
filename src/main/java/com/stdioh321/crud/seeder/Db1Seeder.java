package com.stdioh321.crud.seeder;

import com.stdioh321.crud.model.City;
import com.stdioh321.crud.model.State;
import com.stdioh321.crud.model.User;
import com.stdioh321.crud.repository.StateRepository;
import com.stdioh321.crud.service.CityService;
import com.stdioh321.crud.service.UserService;
import com.stdioh321.crud.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@Configuration
public class Db1Seeder {
    @Autowired
    private CityService cityService;
    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private UserService userService;

    @EventListener
    public void seedDb1(ContextRefreshedEvent event) {
        System.out.println("--- Db1Seeder Start ---");

        if (userService.count() < 1) {
            User tmpUser = new User();
            tmpUser.setName("test");
            tmpUser.setEmail("test@gmail.com");
            tmpUser.setUsername("test");
            tmpUser.setPassword("Test@4231");
            userService.post(tmpUser);
        }
        if (cityService.count() < 1 && stateRepository.count() < 1) {
            for (int i = 0; i < 5; i++) {
                State s = new State();
                s.setName("State " + Utils.getRandomString(5));
                s.setInitial(Utils.getRandomString(2));
                s = stateRepository.saveAndFlush(s);
                City c = new City();
                c.setName("City " + Utils.getRandomString(5));
                c.setState(s);
                cityService.post(c);
            }
        }
        System.out.println("--- Db1Seeder End ---");
    }
}
