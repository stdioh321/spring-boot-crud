package com.stdioh321.crud.seeder;

import com.stdioh321.crud.model.City;
import com.stdioh321.crud.service.CityService;
import com.stdioh321.crud.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@Configuration
public class Db1Seeder {
    @Autowired
    private CityService cityService;

    @EventListener
    public void seedDb1(ContextRefreshedEvent event) {
        System.out.println("--- Db1Seeder Start ---");
        if (cityService.count() < 1) {
            for (int i = 0; i < 5; i++) {
                City c = new City();
                c.setName("City " + Utils.getRandomString(5));
                cityService.post(c);
            }
        }
        System.out.println("--- Db1Seeder End ---");
    }
}
