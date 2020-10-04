#!/bin/bash
uname -s
msg_mine="[MSG]"
echo "$OSTYPE"
jdk_version(){
  local result
  local java_cmd
  if [[ -n $(type -p java) ]]
  then
    java_cmd=java
  elif [[ (-n "$JAVA_HOME") && (-x "$JAVA_HOME/bin/java") ]]
  then
    java_cmd="$JAVA_HOME/bin/java"
  fi
  local IFS=$'\n'
  # remove \r for Cygwin
  local lines=$("$java_cmd" -Xms32M -Xmx32M -version 2>&1 | tr '\r' '\n')
  if [[ -z $java_cmd ]]
  then
    result=no_java
  else
    for line in $lines; do
      if [[ (-z $result) && ($line = *"version \""*) ]]
      then
        local ver=$(echo $line | sed -e 's/.*version "\(.*\)"\(.*\)/\1/; 1q')
        # on macOS, sed doesn't support '?'
        if [[ $ver = "1."* ]]
        then
          result=$(echo $ver | sed -e 's/1\.\([0-9]*\)\(.*\)/\1/; 1q')
        else
          result=$(echo $ver | sed -e 's/\([0-9]*\)\(.*\)/\1/; 1q')
        fi
      fi
    done
  fi
  echo "$result"
}
v="$(jdk_version)"
echo $v
mvn clean install
echo "$msg_mine Testing is in progress, check results folder..."
echo "$msg_mine Cfg interpretator"
mkdir -p results
mkdir -p logs
echo "$msg_mine Testing with -Inline option..."
java -XX:-Inline  -jar target/benchmarks.jar -rf text -rff results/int_inline_off.txt -o logs/int_inline_off.txt JMHBenchmark_01_DummyInvoke
echo "$msg_mine done"
echo "$msg_mine Testing with +Inline option..."
java -XX:+Inline  -jar target/benchmarks.jar -rf text -rff results/int_inline_on.txt -o logs/int_inline_on.txt
echo "$msg_mine done"
echo "$msg_mine Cfg client"
echo "$msg_mine Testing with -Inline option..."
java -XX:-Inline -client -jar target/benchmarks.jar -rf text -rff results/client_inline_off.txt -o logs/client_inline_off.txt JMHBenchmark_01_DummyInvoke
echo "$msg_mine done"
echo "$msg_mine Testing with +Inline option..."
java -XX:+Inline -client -jar target/benchmarks.jar -rf text -rff results/client_inline_on.txt -o logs/client_inline_on.txt
echo "$msg_mine done"
echo "$msg_mine Cfg server"
echo "$msg_mine Testing with -Inline option..."
java -XX:-Inline -server -jar target/benchmarks.jar -rf text -rff results/server_inline_off.txt -o logs/server_inline_off.txt JMHBenchmark_01_DummyInvoke
echo "$msg_mine done"
echo "$msg_mine Testing with +Inline option..."
java -XX:+Inline -server -jar target/benchmarks.jar -rf text -rff results/server_inline_on.txt -o logs/server_inline_on.txt
echo "$msg_mine done"
echo "$msg_mine Testing completed"