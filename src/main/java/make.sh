#!/bin/sh

# shellcheck disable=SC2038
# shellcheck disable=SC2185
find -name '*.java' | xargs javac
java com.codecool.polishdraughts.PolishDraughts