`include "../chisel_output/GameOfLifeWrapper.v"

module GameOfLifeTop (

    //////////// CLOCK //////////
    input FPGA_CLK1_100,

    //////////// LED //////////
    output [7:0] LED
);


	 
    wire hps_fpga_reset_n;

//=======================================================
//  Structural coding
//=======================================================
    soc_system u0(
        .clk_clk(FPGA_CLK1_100),
        .led_pio_export(LED),
        .reset_reset(hps_fpga_reset_n)
    );

endmodule : GameOfLifeTop

