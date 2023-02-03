package com.mercadolibre.quasar.currego.infrastructure.adapaters.out;

import com.mercadolibre.quasar.currego.application.ports.out.SateliteRepository;
import com.mercadolibre.quasar.currego.domain.model.Satellite;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class SateliteRepositoryAdapter  implements SateliteRepository {
    @Override
    public List<Satellite> getTestSatellites() {
        Satellite kenobi = Satellite.builder()
                .name("kenobi")
                .positions(new double[]{-500, -200})
                .message(new String[] {"este", "", "", "mensaje", ""})
                .build();
        Satellite skyWalker = Satellite.builder()
                .name("skywalker")
                .positions(new double[]{100, -100})
                .message(new String[] {"", "es", "", "", "secreto"})
                .build();
        Satellite sato = Satellite.builder().name("sato")
                .positions(new double[]{500, 100})
                .message(new String[] {"este", "", "un", "", ""})
                .build();

        return Arrays.asList(kenobi, skyWalker, sato);
    }
}
