package com.example.onebrc.calculate;

import com.example.onebrc.data.AirTemperature3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class MyCallable3 implements Callable<Map<String, AirTemperature3>> {
    private final List<String> lines;

    public MyCallable3(List<String> lines) {
        this.lines = lines;
    }

    @Override
    public Map<String, AirTemperature3> call() {
        Map<String, AirTemperature3> result = new HashMap<>();
        lines.forEach(line -> {
            final var array = line.split(";");
            final var key = array[0];

            final var val = Integer.parseInt(array[1].replace(".", ""));
            result.compute(key, (k, value) -> {
                if (Objects.isNull(value)) {
                    return new AirTemperature3(val);
                } else {
                    return value.add(val);
                }
            });
        });
        return result;
    }
}

public class CalculateAverage3 {
    public Map<String, AirTemperature3> getAverage(String path) throws ExecutionException, IOException, InterruptedException {
        final var MAX_THREADS = 4;
        Map<String, AirTemperature3> result = new HashMap<>();

        try (final var exec = Executors.newFixedThreadPool(MAX_THREADS)) {
            final var CHUNK_SIZE = 100_000;

            List<Future<Map<String, AirTemperature3>>> futures = new ArrayList<>();

            try (final var stream = Files.lines(Path.of(path))) {
                List<String> chunk = new ArrayList<>(CHUNK_SIZE);     // サイズを固定するとはやい

                stream.forEach(line -> {
                    if (chunk.size() >= CHUNK_SIZE) {
                        // ある程度の行数を１まとまりとして実行する
                        final var future = exec.submit(new MyCallable3(new ArrayList<>(chunk)));
                        futures.add(future);
                        chunk.clear();
                    }
                    chunk.add(line);
                });

                if (!chunk.isEmpty()) {
                    final var future = exec.submit(new MyCallable3(new ArrayList<>(chunk)));
                    futures.add(future);
                }
            }

            for (final var future : futures) {
                final var futureResult = future.get();
                futureResult.keySet().forEach(key -> result.compute(key, (temp, resultValue) -> {
                    // 処理結果をマージする
                    final var val = futureResult.get(key);
                    if (Objects.isNull(resultValue)) {
                        return val;
                    } else {
                        return val.add(result.get(key));
                    }
                }));
            }

            exec.shutdown();
        }
        return result;
    }
}
