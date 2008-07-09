#!/bin/sh

CLASSPATH=
for i in `ls ./lib/*.jar`
do
  CLASSPATH=${CLASSPATH}:${i}
done

java -cp "./bin:${CLASSPATH}" -Dcom.sun.management.jmxremote.port=9999 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false org.jtv.backend.SpringTvBackend