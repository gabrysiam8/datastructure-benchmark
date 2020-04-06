/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

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
public class MyBenchmark {

    @State(value = Scope.Benchmark)
    public static class MyState {
        public int listSizeForBegAddition = 100000;
        public int listSizeForEndAddition = 1000000;
        public int timesToAccess = 10000;
        public static final Random random = new Random();
        public List<Integer> arrayList = new ArrayList<>();
        public List<Integer> linkedList = new LinkedList<>();

        @Setup(Level.Invocation)
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
    @Fork(value = 2)
    @Measurement(iterations = 10, time = 1)
    @Warmup(iterations = 5, time = 1)
    public List<Integer> arrayListAddToTheEnd(MyState state) {
        List<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < state.listSizeForEndAddition; i++) {
            arrayList.add(i);
        }
        return arrayList;
    }

    @Benchmark
    @Fork(value = 2)
    @Measurement(iterations = 10, time = 1)
    @Warmup(iterations = 5, time = 1)
    public List<Integer> linkedListAddToTheEnd(MyState state) {
        List<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < state.listSizeForEndAddition; i++) {
            linkedList.add(i);
        }
        return linkedList;
    }

    @Benchmark
    @Fork(value = 2)
    @Measurement(iterations = 10, time = 1)
    @Warmup(iterations = 5, time = 1)
    public List<Integer> arrayListAddToTheBeginning(MyState state) {
        List<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < state.listSizeForBegAddition; i++) {
            arrayList.add(0,i);
        }
        return arrayList;
    }

    @Benchmark
    @Fork(value = 2)
    @Measurement(iterations = 10, time = 1)
    @Warmup(iterations = 5, time = 1)
    public List<Integer> linkedListAddToTheBeginning(MyState state) {
        List<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < state.listSizeForBegAddition; i++) {
            linkedList.add(0,i);
        }
        return linkedList;
    }

    @Benchmark
    @Fork(value = 2)
    @Measurement(iterations = 5, time = 1)
    @Warmup(iterations = 5, time = 1)
    public int arrayListRandomAccess(MyState state) {
        int result=0;
        for (int i = 0; i < state.timesToAccess; i++) {
            result = state.arrayList.get(MyState.random.nextInt( state.timesToAccess- 1));
        }
        return result;
    }

    @Benchmark
    @Fork(value = 2)
    @Measurement(iterations = 5, time = 1)
    @Warmup(iterations = 5, time = 1)
    public int linkedListRandomAccess(MyState state) {
        int result=0;
        for (int i = 0; i < state.timesToAccess; i++) {
            result = state.linkedList.get(MyState.random.nextInt( state.timesToAccess- 1));
        }
        return result;
    }

    @Benchmark
    @Warmup(iterations = 3, time=1)
    @Measurement(iterations = 5, time=1)
    public List<Integer> arrayListRemoveFromEnd(MyState state) {
        int size = state.arrayList.size();
        for (int i = size-1; i >=size-state.timesToAccess; i--) {
            state.arrayList.remove(i);
        }
        return state.arrayList;
    }

    @Benchmark
    @Warmup(iterations = 3, time=1)
    @Measurement(iterations = 5, time=1)
    public List<Integer> linkedListRemoveFromEnd(MyState state) {
        int size = state.linkedList.size();
        for (int i = size-1; i >=size-state.timesToAccess; i--) {
            state.linkedList.remove(i);
        }
        return state.linkedList;
    }

    @Benchmark
    @Warmup(iterations = 3, time=1)
    @Measurement(iterations = 5, time=1)
    public List<Integer> arrayListRemoveFromBeginning(MyState state) {
        for (int i = 0; i < state.timesToAccess; i++) {
            state.arrayList.remove(0);
        }
        return state.arrayList;
    }

    @Benchmark
    @Warmup(iterations = 3, time=1)
    @Measurement(iterations = 5, time=1)
    public List<Integer> linkedListRemoveFromBeginning(MyState state) {
        int size = state.linkedList.size();
        for (int i = 0; i < state.timesToAccess; i++) {
            state.linkedList.remove(0);
        }
        return state.linkedList;
    }
}
