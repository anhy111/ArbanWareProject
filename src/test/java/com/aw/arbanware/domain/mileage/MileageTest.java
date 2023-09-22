package com.aw.arbanware.domain.mileage;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class MileageTest {

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("MileageTest")
    public void mileageTest() throws Exception{
        new MileageHistory();
    }

}