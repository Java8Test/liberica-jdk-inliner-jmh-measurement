# liberica-jdk-inliner-jmh-measurement
Test task is to implement using JMH measure inliner performance for different 
JITs from Liberica JDK[1]

Task: Inlining is important option for optimization for any compiler. In 
Liberica JDK there are JITs: interpretator, C1, C2, Graal, AOT. Using JMH[2] it
is necessary to estimate enhancement of inlining method invocation for each
mode. There are samples  for JMH framework[3]. It is enough to do measurement
with one machine x86_64. Solution should consists of benchmarks source codes,
scripts to prepare host to execute JMH and results. Also expected analyze
reasons of the results using profiling data.
Addition task is to estimate inlining influence on memory usage and time of
compilers using tools. 

Suggestions: 
JMH (Java Microbenchmark Harness) is a Java harness for building, running, and
analyzing nano/micro/milli/macro benchmarks written in Java and other languages
targeting the JVM [4]. Benchmark should be implemented on Java. There is no way
in Java to specify method to be inline, and even more there is no way to 
provide a hint to compiler that method should be inline. However, Java language
specification [5] point that at run time, a machine-code generator or optimizer
can "inline" the body of a final method. At the same time the keyword final on
a method does not mean that the method can be safely inlined. Java compiler
cannot expand a method inline at compile time. In general, we suggest that
implementations use late-bound (run-time) code generation and optimization.
Also, it is known that native code accesses Java VM features by calling JNI
functions, and JNI functions are defined as inline member functions. 
There is good description of inlining. Inlining is a way to optimize compiled
source code at runtime by replacing the invocations of the most often executed
methods with its bodies [6]. It's the responsibility of the Just-In-Time (JIT)
compiler. 
There are options for compiler to attempt inline for specific method or prevent
inlining, enable and disable inlining, size for compiled methods that can be
inlined [7].
As summary we can consider that JIT inlines small static, private, or final
methods in general. And while public methods are also candidates for inlining,
not every public method will necessarily be inlined. The JVM needs to determine
that there's only a single implementation of such a method. Any additional
subclass would prevent inlining and the performance will inevitably decrease.

Benchmarks: 
1. Simple benchmark is to implement some static/final empty method that should
   be invoked many times from benchmark. And just measure such benchmark with
   enabled inlining and disable inlining.
2. The we can implement 2 benchmarks method where one invokes empty method with
   inline hint for compiler. And another benchmark method with invoke empty
   method that do not apply inlining.
3. Let’s try the same scenario but with method that returns some value.
4. Let’s try the same scenario but with method that returns some value and
  apply any parameter.

General options for executing benchmarks:
1. Let’s use flags -XX:+PrintCompilation -XX:+UnlockDiagnosticVMOptions 
   -XX:+PrintInlining, to see inlined methods.
2. It is good for inline methods to have a good warm up [8]. Let’s try huge
   number of iterations. 

JMH execute requirements:
- Install Liberica JDK [1]
- Install Apache Maven project [9]

Excepted results:
1. We should receive great improvements in performance with inline methods.

References:
[1] https://bell-sw.com/java.html
[2] https://github.com/openjdk/jmh
[3] https://github.com/openjdk/jmh/tree/master/jmh-samples/src/main/java/org/openjdk/jmh/samples
[4] https://openjdk.java.net/projects/code-tools/jmh/
[5] https://docs.oracle.com/javase/specs/jls/se15/jls15.pdf
[6] https://www.baeldung.com/jvm-method-inlining
[7] https://docs.oracle.com/en/java/javase/15/docs/specs/man/java.html
[8] https://wiki.openjdk.java.net/display/HotSpot/Inlining
[9] https://maven.apache.org