package com.gmiedlar;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
public class ListRemovalBenchmark {

    @State(value = Scope.Benchmark)
    public static class MyState {
        public int timesToAccess = 10000;
        public List<Integer> arrayList = new ArrayList<>();
        public List<Integer> linkedList = new LinkedList<>();

        @Setup(Level.Invocation)
        public void doSetup() {
            for (int i = 0; i < timesToAccess + 1; i++) {
                arrayList.add(i);
            }
            for (int i = 0; i < timesToAccess + 1; i++) {
                linkedList.add(i);
            }
        }
    }

    @Benchmark
    @Warmup(iterations = 3, time = 1)
    @Measurement(iterations = 7, time = 1)
    public List<Integer> arrayListRemoveFromEnd(MyState state) {
        int size = state.arrayList.size();
        for (int i = size - 1; i >= size - state.timesToAccess; i--) {
            state.arrayList.remove(i);
        }
        return state.arrayList;
    }

    @Benchmark
    @Warmup(iterations = 3, time = 1)
    @Measurement(iterations = 7, time = 1)
    public List<Integer> linkedListRemoveFromEnd(MyState state) {
        int size = state.linkedList.size();
        for (int i = size - 1; i >= size - state.timesToAccess; i--) {
            state.linkedList.remove(i);
        }
        return state.linkedList;
    }

    @Benchmark
    @Warmup(iterations = 3, time = 1)
    @Measurement(iterations = 7, time = 1)
    public List<Integer> arrayListRemoveFromBeginning(MyState state) {
        for (int i = 0; i < state.timesToAccess; i++) {
            state.arrayList.remove(0);
        }
        return state.arrayList;
    }

    @Benchmark
    @Warmup(iterations = 3, time = 1)
    @Measurement(iterations = 7, time = 1)
    public List<Integer> linkedListRemoveFromBeginning(MyState state) {
        for (int i = 0; i < state.timesToAccess; i++) {
            state.linkedList.remove(0);
        }
        return state.linkedList;
    }
}
