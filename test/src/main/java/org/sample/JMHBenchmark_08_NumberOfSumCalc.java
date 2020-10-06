/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.sample;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.CompilerControl;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Timeout;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;

@Timeout(time = 1, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class JMHBenchmark_08_NumberOfSumCalc {

    static final long ITERATIONS = 100;

    @Benchmark
    public void baseline() {
        // do nothing, this is a baseline
    }

    @Benchmark
    public long noInvocationNumberSumCalc() {
	long sumValue = 0;
	for (long value = 0; value < ITERATIONS; ++value)
        {
            sumValue += (value/3) + (value*2);
            sumValue += (value+4) * (value-3);
            sumValue += (value*5) - (value/4);
            sumValue += (value-6) / (value+5);
            sumValue += (value/7) + (value*6);
        }
        return sumValue;
    }

    @Benchmark
    public long numberSumCalcInvoke() {
	long sumValue = 0;
	for (long i = 0; i < ITERATIONS; ++i)
        {
            sumValue += _sumMethod_1(i);
            sumValue += _sumMethod_2(i);
            sumValue += _sumMethod_3(i);
            sumValue += _sumMethod_4(i);
            sumValue += _sumMethod_5(i);
        }
        return sumValue;
    }

    @Benchmark
    public long numberSumCalcInvokeNested() {
	long sumValue = 0;
	for (long i = 0; i < ITERATIONS; ++i)
        {
            sumValue += _sumNestedMethod_1(i);
        }
        return sumValue;
    }

    @Benchmark
    public long longSumCalcInvoke() {
	long sumValue = 0;
	for (long i = 0; i < ITERATIONS; ++i)
        {
            sumValue += _longSumMethod(i);
        }
        return sumValue;
    }

    private long _sumMethod_1(long value) {
	return (value/3) + (value*2);
    }

    private long _sumMethod_2(long value) {
	return (value+4) * (value-3);
    }

    private long _sumMethod_3(long value) {
	return (value*5) - (value/4);
    }

    private long _sumMethod_4(long value) {
	return (value-6) / (value+5);
    }

    private long _sumMethod_5(long value) {
	return (value/7) + (value*6);
    }

    private long _sumNestedMethod_1(long value) {
	return (value/3) + (value*2) + _sumNestedMethod_2(value);
    }

    private long _sumNestedMethod_2(long value) {
	return (value+4) * (value-3) + _sumNestedMethod_3(value);
    }

    private long _sumNestedMethod_3(long value) {
	return (value*5) - (value/4) + _sumNestedMethod_4(value);
    }

    private long _sumNestedMethod_4(long value) {
	return (value-6) / (value+5) + _sumMethod_5(value);
    }

    private long _longSumMethod(long value) {
        long res = (value/3) + (value*2);
	res += (value+4) * (value-3);
        res += (value*5) - (value/4);
        res += (value-6) / (value+5);
        res += (value/7) + (value*6);
        return res;
    }
}