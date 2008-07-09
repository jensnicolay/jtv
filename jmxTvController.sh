#!/bin/sh

CLASSPATH=
for i in `ls ./lib/*.jar`
do
  CLASSPATH=${CLASSPATH}:${i}
done

java -cp "./bin:${CLASSPATH}" org.jensiesoft.jtv.JmxTvController
