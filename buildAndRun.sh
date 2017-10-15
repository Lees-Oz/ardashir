#!/usr/bin/env bash
docker build -t lg/ardashir . && docker run -p 4567:4567 lg/ardashir