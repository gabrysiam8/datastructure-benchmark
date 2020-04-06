package com.gmiedlar;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
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
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 1, jvmArgs = {"-Xms2G", "-Xmx2G"})
public class ListRandomAccessBenchmark {

    @State(value = Scope.Benchmark)
    public static class MyState {
        public int timesToAccess = 10000;
        public static final Random random = new Random();
        public List<Integer> arrayList = new ArrayList<>();
        public List<Integer> linkedList = new LinkedList<>();

        @Setup(Level.Trial)
        public void doSetup() {
            for (int i = 0; i < timesToAccess+1; i++) {
                arrayList.add(i);
            }
            for (int i = 0; i < timesToAccess+1; i++) {
                linkedList.add(i);
            }
        }
    }

    @Benchmark
    @Warmup(iterations = 5, time = 1)
    @Measurement(iterations = 10, time = 1)
    public int arrayListRandomAccess(MyState state) {
        int result=0;
        for (int i = 0; i < state.timesToAccess; i++) {
            result = state.arrayList.get(MyState.random.nextInt( state.timesToAccess- 1));
        }
        return result;
    }

    @Benchmark
    @Warmup(iterations = 5, time = 1)
    @Measurement(iterations = 10, time = 1)
    public int linkedListRandomAccess(MyState state) {
        int result=0;
        for (int i = 0; i < state.timesToAccess; i++) {
            result = state.linkedList.get(MyState.random.nextInt( state.timesToAccess- 1));
        }
        return result;
    }
}
