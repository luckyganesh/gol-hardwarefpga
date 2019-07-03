#! /bin/sh
set -e

sbt "runMain GameOfLifeWrapper"
cd fpga/
:
quartus_sh --flow compile GameOfLife
