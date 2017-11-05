#!/usr/bin/env bash
cd .. &&
mkdir -p ci &&
rm -rf ci/workdir &&
mkdir ci/workdir &&
cd ci/workdir &&
git clone https://github.com/Lees-Oz/ardashir &&
cd ardashir &&
mvn verify
#docker build -t lg/ardashir . && docker run -p 4567:4567 lg/ardashir