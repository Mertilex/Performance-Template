#!/bin/bash
#

if [ -n "$JAVA_HOME" ]; then
    JAVA="$JAVA_HOME"/bin/java
else
    JAVA=java
fi

OLDDIR=$(pwd)
BIN_DIR=$(dirname "$0")
cd "${BIN_DIR}/.." && DEFAULT_GATLING_HOME=$(pwd) && cd "${OLDDIR}"

GATLING_HOME="${GATLING_HOME:=${DEFAULT_GATLING_HOME}}"

export GATLING_HOME

JAVA_OPTS="${JAVA_OPTS} -Xms32M -Xmx128M"

# Setup classpath
CLASSPATH="$GATLING_HOME/lib/*"

"$JAVA" $JAVA_OPTS -cp "$CLASSPATH" io.gatling.bundle.RecorderCLI "$@"
