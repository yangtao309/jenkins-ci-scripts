#!/bin/sh

#####   
# add variable to build.gradle
#####
sed -i '1iext.plugins = new File(rootDir, "plugins")' build.gradle

#####
# add apply from some of plugins in allprojects after line.
#####
sed -i '/allprojects/a\apply from: "$rootDir/plugins/jdepend.gradle"' build.gradle
sed -i '/allprojects/a\apply from: "$rootDir/plugins/pmd.gradle"' build.gradle
sed -i '/allprojects/a\apply from: "$rootDir/plugins/findbugs.gradle"' build.gradle
sed -i '/allprojects/a\apply from: "$rootDir/plugins/checkstyle.gradle"' build.gradle
