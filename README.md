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

References:
[1] https://bell-sw.com/java.html
[2] https://github.com/openjdk/jmh
[3] https://github.com/openjdk/jmh/tree/master/jmh-samples/src/main/java/org/openjdk/jmh/samples