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
public class JMHBenchmark_09_NumberOfSumCalcWithCompileHint {

    static final long ITERATIONS = 100;

    @Benchmark
    public void baseline() {
        // do nothing, this is a baseline
    }

    @Benchmark
    public long SumCalcWithInline() {
	long sumValue = 0;
	for (long i = 0; i < ITERATIONS; ++i)
        {
            sumValue += _hintInlineMethod_1(i);
            sumValue += _hintInlineMethod_2(i);
            sumValue += _hintInlineMethod_3(i);
            sumValue += _hintInlineMethod_4(i);
            sumValue += _hintInlineMethod_5(i);
        }
        return sumValue;
    }

    @Benchmark
    public long SumCalcWithDontInline() {
	long sumValue = 0;
	for (long i = 0; i < ITERATIONS; ++i)
        {
            sumValue += _hintNotInlineMethod_1(i);
            sumValue += _hintNotInlineMethod_2(i);
            sumValue += _hintNotInlineMethod_3(i);
            sumValue += _hintNotInlineMethod_4(i);
            sumValue += _hintNotInlineMethod_5(i);
        }
        return sumValue;
    }

    @CompilerControl(CompilerControl.Mode.INLINE)
    private long _hintInlineMethod_1(long value) {
	return (value/3) + (value/2);
    }
    @CompilerControl(CompilerControl.Mode.INLINE)
    private long _hintInlineMethod_2(long value) {
	return (value/4) + (value/3);
    }
    @CompilerControl(CompilerControl.Mode.INLINE)
    private long _hintInlineMethod_3(long value) {
	return (value/5) + (value/4);
    }
    @CompilerControl(CompilerControl.Mode.INLINE)
    private long _hintInlineMethod_4(long value) {
	return (value/6) + (value/5);
    }
    @CompilerControl(CompilerControl.Mode.INLINE)
    private long _hintInlineMethod_5(long value) {
	return (value/7) + (value/6);
    }
    @CompilerControl(CompilerControl.Mode.INLINE)
    private long _longInlineMethod(long value) {
	long sumValue = 0;
	for (long j = 0; j < value; j++ )
            sumValue += j;
	return sumValue;
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    private long _hintNotInlineMethod_1(long value) {
	return (value/3) + (value/2);
    }
    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    private long _hintNotInlineMethod_2(long value) {
	return (value/4) + (value/3);
    }
    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    private long _hintNotInlineMethod_3(long value) {
	return (value/5) + (value/4);
    }
    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    private long _hintNotInlineMethod_4(long value) {
	return (value/6) + (value/5);
    }
    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    private long _hintNotInlineMethod_5(long value) {
	return (value/7) + (value/6);
    }
    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    private long _longNotInlineMethod(long value) {
	long sumValue = 0;
	for (long j = 0; j < value; j++ )
            sumValue += j;
	return sumValue;
    }
}