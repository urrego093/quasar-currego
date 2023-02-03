package com.mercadolibre.quasar.currego.infrastructure.adapaters.in.rest;

import com.mercadolibre.quasar.currego.infrastructure.adapaters.in.rest.data.request.TopSecretRequest;
import com.mercadolibre.quasar.currego.infrastructure.adapaters.in.rest.data.response.TopSecretResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/")
public class TopSecretRestAdapter {

    @PostMapping(value = "topSecret")
    TopSecretResponse findEnemySpaceShip(@RequestBody TopSecretRequest request) {
        return  null;
    }
}
