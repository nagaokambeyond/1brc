package com.example.onebrc.calculate;

import com.example.onebrc.data.AirTemperature4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class MyCallable4 implements Callable<Map<String, AirTemperature4>> {
    private final String[] lines;

    public MyCallable4(String[] lines) {
        this.lines = lines;
    }

    @Override
    public Map<String, AirTemperature4> call() {
        Map<String, AirTemperature4> result = new HashMap<>();
        for (var line : lines) {
            final var array = line.split(";");
            final var key = array[0];

            final var val = Integer.parseInt(array[1].replace(".", ""));
            result.compute(key, (k, value) -> {
                if (Objects.isNull(value)) {
                    return new AirTemperature4(val);
                } else {
                    return value.add(val);
                }
            });
        }
        return result;
    }
}

public class CalculateAverage4 {
    public Map<String, AirTemperature4> getAverage(String path) throws ExecutionException, IOException, InterruptedException {
        final var MAX_THREADS = 4;
        Map<String, AirTemperature4> result = new HashMap<>();

        try (final var exec = Executors.newFixedThreadPool(MAX_THREADS)) {
            final var CHUNK_SIZE = 100_000;

            List<Future<Map<String, AirTemperature4>>> futures = new ArrayList<>();

            try (final var stream = Files.lines(Path.of(path))) {
                List<String> chunk = new ArrayList<>(CHUNK_SIZE);     // サイズを固定するとはやい

                stream.forEach(line -> {
                    if (chunk.size() >= CHUNK_SIZE) {
                        // ある程度の行数を１まとまりとして実行する
                        final var future = exec.submit(new MyCallable4(chunk.toArray(new String[CHUNK_SIZE])));
                        futures.add(future);
                        chunk.clear();
                    }
                    chunk.add(line);
                });

                if (!chunk.isEmpty()) {
                    final var future = exec.submit(new MyCallable4(chunk.toArray(new String[CHUNK_SIZE])));
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
