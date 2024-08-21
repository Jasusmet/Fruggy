package com.eoi.Fruggy;



import com.eoi.Fruggy.entidades.Descuento;
import com.eoi.Fruggy.repositorios.RepoDescuento;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
class DescuentoTest {

    @Autowired
    RepoDescuento repoDescuento;

    @Test
public void test() {
        assertNotNull(repoDescuento);

    }
}