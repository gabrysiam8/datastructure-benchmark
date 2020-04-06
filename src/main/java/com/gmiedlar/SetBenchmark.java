package com.gmiedlar;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 1, jvmArgs = {"-Xms2G", "-Xmx2G"})
public class SetBenchmark {

    enum Direction {
        NORTH, SOUTH, EAST, WEST;
    }

    @State(value = Scope.Benchmark)
    public static class MyState {
        Set<Direction> directionsHashSet = new HashSet<>();
        Set<Direction> directionsEnumSet = EnumSet.noneOf(Direction.class);

        @Setup(Level.Invocation)
        public void doSetup() {
            directionsHashSet.addAll(Arrays.asList(Direction.values()));
            directionsEnumSet.addAll(Arrays.asList(Direction.values()));
        }
    }

    @Benchmark
    @Measurement(iterations = 10, time = 1)
    @Warmup(iterations = 5, time = 1)
    public Set<Direction> hashSetContains(MyState state) {
        for (int i = 0; i < 10000000; i++) {
            state.directionsHashSet.contains(Direction.NORTH);
            state.directionsHashSet.contains(Direction.SOUTH);
            state.directionsHashSet.contains(Direction.EAST);
            state.directionsHashSet.contains(Direction.WEST);
        }
        return state.directionsHashSet;
    }

    @Benchmark
    @Measurement(iterations = 10, time = 1)
    @Warmup(iterations = 5, time = 1)
    public Set<Direction> enumSetContains(MyState state) {
        for (int i = 0; i < 10000000; i++) {
            state.directionsEnumSet.contains(Direction.NORTH);
            state.directionsEnumSet.contains(Direction.SOUTH);
            state.directionsEnumSet.contains(Direction.EAST);
            state.directionsEnumSet.contains(Direction.WEST);
        }
        return state.directionsEnumSet;
    }
}
