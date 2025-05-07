package com.example.onebrc.calculate;

import com.example.onebrc.data.AirTemperature;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CalculateAverage {
    public Map<String, AirTemperature> getAverage(String path) throws IOException {
        Map<String, AirTemperature> result = new HashMap<>();
        try (final var stream = Files.lines(Path.of(path))) {
            stream.forEach(line -> {
                final var array = line.split(";");
                final var key = array[0];

                final var val = new BigDecimal(array[1]);
                result.compute(key, (k, value) -> {
                    if (Objects.isNull(value)) {
                        // keyが存在しない場合
                        return new AirTemperature(val);
                    } else {
                        // keyが存在する場合　更新
                        return value.add(val);
                    }
                });
            });
        }
        return result;
    }
}
