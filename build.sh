#! /bin/bash
set -e

sbt "runMain GameOfLifeWrapper"

QUARTUS_PATH=~/intelFPGA_pro/18.1/quartus/bin
if [ ${1} == 'de10' ]
then
    QUARTUS_PATH=~/intelFPGA_lite/18.1/quartus/bin
fi

cd fpga/${1}
:
${QUARTUS_PATH}/quartus_sh --flow compile GameOfLife
