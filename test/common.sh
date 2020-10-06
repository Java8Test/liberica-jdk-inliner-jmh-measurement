#!/bin/bash
msg_mine="[MSG]"
graal_options="XX:+UnlockExperimentalVMOptions -XX:+UseJVMCICompiler -XX:-EnableJVMCI"

source config
mvn clean install

mkdir -p $results_dir
mkdir -p $logs_dir

IFS=' '
read -ra jit_strarr <<< "$jit_selected"