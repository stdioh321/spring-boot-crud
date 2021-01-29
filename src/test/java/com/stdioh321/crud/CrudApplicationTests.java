package com.stdioh321.crud;

import com.stdioh321.crud.model.State;
import com.stdioh321.crud.repository.StateCommonRepository;
import com.stdioh321.crud.repository.StateRepository;
import com.stdioh321.crud.service.StateService;
import com.stdioh321.crud.utils.Utils;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@Rollback(value = true)
class CrudApplicationTests {

    @BeforeEach
    public void beforeTest(){
        System.out.println("beforeTest-------------------------------------------------");
    }
    @AfterEach
    public void afterTest(){
        System.out.println("afterTest-------------------------------------------------");
    }

    @Autowired
    private StateService stateService;

    @Test
    @Transactional
    public void testAddState(){
        State tempState = new State();
        String tempStr = Utils.getRandomString(10);
        tempState.setName(tempStr);
        tempState.setInitial(tempStr.substring(0,2));
        tempState = stateService.post(tempState);
        System.out.println(tempState);

        Assert.assertNotNull(tempState);

    }
}
