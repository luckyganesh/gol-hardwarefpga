#! /bin/bash

QUARTUS_PATH=~/intelFPGA_pro/18.1/quartus/bin
if [ ${1} == 'de10' ]
then
    QUARTUS_PATH=~/intelFPGA_lite/18.1/quartus/bin
fi
${QUARTUS_PATH}/quartus_pgm -m jtag -o "p;fpga/${1}/output_files/GameOfLife.sof@2"
