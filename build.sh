#! /bin/sh
set -e

sbt "runMain adder.Adder_wrapper"
quartus_sh --flow compile Adder
