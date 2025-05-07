package com.example.onebrc.calculate;

import com.example.onebrc.data.AirTemperature2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CalculateAverage2 {
    public Map<String, AirTemperature2> getAverage(String path) throws IOException {
        Map<String, AirTemperature2> result = new HashMap<>();
        try (final var stream = Files.lines(Path.of(path))) {
            stream.forEach(line -> {
                final var array = line.split(";");
                final var key = array[0];

                final var val = Integer.valueOf(array[1].replace(".", ""));
                result.compute(key, (k, value) -> {
                    if (Objects.isNull(value)) {
                        // keyが存在しない場合
                        return new AirTemperature2(val);
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
