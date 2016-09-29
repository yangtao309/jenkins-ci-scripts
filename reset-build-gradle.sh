#!/bin/sh

#####   
# add variable to build.gradle
#####
sed -i '1iext.plugins = new File(rootDir, "plugins")'

#####
# add apply from some of plugins in allprojects after line.
#####
sed -i '/allprojects/a\apply from: "$rootDir/plugins/jdepend.gradle"'
sed -i '/allprojects/a\apply from: "$rootDir/plugins/pmd.gradle"'
sed -i '/allprojects/a\apply from: "$rootDir/plugins/findbugs.gradle"'
sed -i '/allprojects/a\apply from: "$rootDir/plugins/checkstyle.gradle"'



