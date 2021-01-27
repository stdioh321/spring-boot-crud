package com.stdioh321.crud.seeder;

import com.stdioh321.crud.model.*;
import com.stdioh321.crud.repository.JwtCheckListRepository;
import com.stdioh321.crud.repository.StateRepository;
import com.stdioh321.crud.service.CityService;
import com.stdioh321.crud.service.JwtCheckListService;
import com.stdioh321.crud.service.RoleService;
import com.stdioh321.crud.service.UserService;
import com.stdioh321.crud.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class Db1Seeder {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CityService cityService;
    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Autowired
    private JwtCheckListService jwtCheckListService;

    @Autowired
    private JwtCheckListRepository jwtCheckListRepository;

    @EventListener
    public void seedDb1(ContextRefreshedEvent event) {
        System.out.println("--- Db1Seeder Start ---");
        if (roleService.count() < 1 && userService.count() < 1) {
            /*
            JwtCheckList jcl = new JwtCheckList();
            jcl.setToken("sadasd");
            jcl.setBlacklist(true);
            jwtCheckListService.post(jcl);
            */

            Role r1 = new Role();
            r1.setName("ADMIN");
            roleService.post(r1);
            Role r2 = new Role();
            r2.setName("USER");
            roleService.post(r2);


            User tmpUser = new User();
            tmpUser.setName("root");
            tmpUser.setEmail("root@test.com");
            tmpUser.setUsername("root");
            tmpUser.setPassword(passwordEncoder.encode("Root@4231"));
            tmpUser.setRoles(new HashSet<Role>() {
                {
                    add(r1);
                    add(r2);
                }
            });
            userService.post(tmpUser);

            User tmpUser2 = new User();
            tmpUser2.setName("test");
            tmpUser2.setEmail("test@test.com");
            tmpUser2.setUsername("test");
            String tmpPass2 = passwordEncoder.encode("Test@4231");
            tmpUser2.setPassword(tmpPass2);
            tmpUser2.setRoles(new HashSet<Role>() {
                {
                    add(r2);
                }
            });
            userService.post(tmpUser2);
        }
        if (cityService.count() < 1 && stateRepository.count() < 1) {
            for (int i = 0; i < 5; i++) {
                State s = new State();
                s.setName("State " + Utils.getRandomString(5));
                s.setInitial(Utils.getRandomString(2));
                s = stateRepository.save(s);
                City c = new City();
                c.setName("City " + Utils.getRandomString(5));
                c.setState(s);
                cityService.post(c);
            }
        }
        System.out.println("--- Db1Seeder End ---");
    }
}
