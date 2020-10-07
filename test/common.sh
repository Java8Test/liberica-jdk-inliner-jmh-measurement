#!/bin/bash
msg_mine="[MSG]"

echo "${msg_mine} requirements: jdk mvn ld"

graal_options="XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI -XX:+UseJVMCICompiler"

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

jversion="$(jdk_version)"
echo "${msg_mine} jdk_version: ${jversion}"
mvn -v

source config
mvn clean install

mkdir -p $results_dir
mkdir -p $logs_dir

os_name="x"
profs_set=${general_profs}
liberica_jdk="${JAVA_HOME}/bin/"
case "$OSTYPE" in
  darwin*)  echo "${msg_mine} os:osx" 
            os_name="osx"
            profs_set="${general_profs} ${osx_profs}" ;;
  linux*)   echo "${msg_mine} os:linux"
            os_name="linux"
            profs_set="${general_profs} ${linux_profs}" ;;
  msys*)    echo "${msg_mine} os:win" 
            os_name="win"
            profs_set="${general_profs} ${win_profs}" 
            liberica_jdk="${JAVA_HOME}\\bin\\" ;;
  cygwin*)  echo "${msg_mine} os:cygwin" 
            os_name="win"
            profs_set="${general_profs} ${win_profs}"
#            liberica_jdk="$(cygpath -u ${liberica_jdk})" ;;
           liberica_jdk="" ;;
  *)        echo "${msg_mine} os:unknown: $OSTYPE" 
            os_name="u" ;;
esac

echo ${msg_mine} jdk:${liberica_jdk}

IFS=' '
read -ra jit_strarr <<< "${jit_selected}"
read -ra profs_strarr <<< "${profs_set}"