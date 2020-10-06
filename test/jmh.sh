#!/bin/bash
source common.sh
for jit in "${jit_strarr[@]}";
do
    echo ${msg_mine} jit: -${jit}
    if [ $jit == 'graal' ]; then
        benchmark=${inline_benchmarks}
    fi

    declare -A modes
    modes="-Inline +Inline"
    IFS=' '
    read -ra mode_strarr <<< "$modes"
    for mode in "${mode_strarr[@]}";
    do
        echo ${msg_mine} mode: -XX:${mode}
        out_name="${jit}${mode}.txt"
        benchmark=$notinline_benchmarks 
        if [ $mode == '+Inline' ]; then
            benchmark=${inline_benchmarks}
        fi
        echo ${msg_mine} benchmarks: ${benchmark}
        echo ${msg_mine} log: ${logs_dir}/${out_name}
	jit_selection=${jit}
	if [ $jit == 'graal' ]; then
            jit_selection=$graal_options
        fi      
	if [ $jit == 'aot' ]; then
            jit_selection="-XX:AOTLibrary=./${benchmark}"
            IFS=' '
            read -ra bm_strarr <<< "$benchmark"
            for bm in "${bm_strarr[@]}";
            do
                jaotc --output ${bm}.so ${bm}.class
            done
        fi            
        java -XX:${mode} -${jit_selection} ${jit_options} -jar target/benchmarks.jar $benchmark_options -rf text -rff $results_dir/$out_name -o $logs_dir/$out_name ${benchmark}
        echo ${msg_mine} results: ${results_dir}/${out_name}
    done
done