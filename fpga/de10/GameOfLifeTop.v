`include "../chisel_output/GameOfLifeWrapper.v"

module GameOfLifeTop (

    //////////// CLOCK //////////
    input FPGA_CLK1_50,

    //////////// HPS //////////
    inout HPS_CONV_USB_N,
    output [14:0] HPS_DDR3_ADDR,
    output [2:0] HPS_DDR3_BA,
    output HPS_DDR3_CAS_N,
    output HPS_DDR3_CK_N,
    output HPS_DDR3_CK_P,
    output HPS_DDR3_CKE,
    output HPS_DDR3_CS_N,
    output [3:0] HPS_DDR3_DM,
    inout [31:0] HPS_DDR3_DQ,
    inout [3:0] HPS_DDR3_DQS_N,
    inout [3:0] HPS_DDR3_DQS_P,
    output HPS_DDR3_ODT,
    output HPS_DDR3_RAS_N,
    output HPS_DDR3_RESET_N,
    input HPS_DDR3_RZQ,
    output HPS_DDR3_WE_N,

    inout HPS_ENET_INT_N,
    inout HPS_GSENSOR_INT,
    inout HPS_KEY,
    inout HPS_LED,
    inout HPS_LTC_GPIO,


    //////////// LED //////////
    output [7:0] LED
);


    wire [11:0] onchip_memory2_0_address;         // onchip_memory2_0_s2.address
    wire onchip_memory2_0_clken;            //                    .clken
    wire onchip_memory2_0_write;            //                    .write
    wire [7:0] onchip_memory2_0_readdata;              //                    .readdata
    wire [7:0] onchip_memory2_0_writedata; //                    .writedata

//=======================================================
//  REG/WIRE declarations
//=======================================================

    wire completed_pio_input_export;
    wire initialize_pio_output_export;
    wire [11:0] result_address_pio_output_export;
    wire [11:0] start_address_pio_output_export;
    wire start_pio_output_export;
    wire write_enable;	
    wire reset_pio_output_export;
	 
    wire hps_fpga_reset_n;
    wire fpga_clk_50;
// connection of internal logics
    assign fpga_clk_50 = FPGA_CLK1_50;

//=======================================================
//  Structural coding
//=======================================================
    soc_system u0(
        //Clock&Reset
        .clk_clk(fpga_clk_50),                                      //                            clk.clk
        .reset_reset_n(hps_fpga_reset_n),                            //                          reset.reset_n
        //HPS ddr3
        .memory_mem_a(HPS_DDR3_ADDR),                                //                         memory.mem_a
        .memory_mem_ba(HPS_DDR3_BA),                                 //                               .mem_ba
        .memory_mem_ck(HPS_DDR3_CK_P),                               //                               .mem_ck
        .memory_mem_ck_n(HPS_DDR3_CK_N),                             //                               .mem_ck_n
        .memory_mem_cke(HPS_DDR3_CKE),                               //                               .mem_cke
        .memory_mem_cs_n(HPS_DDR3_CS_N),                             //                               .mem_cs_n
        .memory_mem_ras_n(HPS_DDR3_RAS_N),                           //                               .mem_ras_n
        .memory_mem_cas_n(HPS_DDR3_CAS_N),                           //                               .mem_cas_n
        .memory_mem_we_n(HPS_DDR3_WE_N),                             //                               .mem_we_n
        .memory_mem_reset_n(HPS_DDR3_RESET_N),                       //                               .mem_reset_n
        .memory_mem_dq(HPS_DDR3_DQ),                                 //                               .mem_dq
        .memory_mem_dqs(HPS_DDR3_DQS_P),                             //                               .mem_dqs
        .memory_mem_dqs_n(HPS_DDR3_DQS_N),                           //                               .mem_dqs_n
        .memory_mem_odt(HPS_DDR3_ODT),                               //                               .mem_odt
        .memory_mem_dm(HPS_DDR3_DM),                                 //                               .mem_dm
        .memory_oct_rzqin(HPS_DDR3_RZQ),                             //                               .oct_rzqin

        //GPIO
        .hps_0_hps_io_hps_io_gpio_inst_GPIO09(HPS_CONV_USB_N),       //                               .hps_io_gpio_inst_GPIO09
        .hps_0_hps_io_hps_io_gpio_inst_GPIO35(HPS_ENET_INT_N),       //           s                     .hps_io_gpio_inst_GPIO35
        .hps_0_hps_io_hps_io_gpio_inst_GPIO40(HPS_LTC_GPIO),         //                               .hps_io_gpio_inst_GPIO40
        .hps_0_hps_io_hps_io_gpio_inst_GPIO53(HPS_LED),              //                               .hps_io_gpio_inst_GPIO53
        .hps_0_hps_io_hps_io_gpio_inst_GPIO54(HPS_KEY),              //                               .hps_io_gpio_inst_GPIO54
        .hps_0_hps_io_hps_io_gpio_inst_GPIO61(HPS_GSENSOR_INT),      //                               .hps_io_gpio_inst_GPIO61
        //FPGA Partion

        .hps_0_h2f_reset_reset_n(hps_fpga_reset_n),                  //                hps_0_h2f_reset.reset_n

        .onchip_memory2_0_s2_address(onchip_memory2_0_address),
        .onchip_memory2_0_s2_chipselect(1'b1),
        .onchip_memory2_0_s2_write(write_enable),
        .onchip_memory2_0_s2_clken(1'b1),
        .onchip_memory2_0_s2_readdata(onchip_memory2_0_readdata),
        .onchip_memory2_0_s2_writedata(onchip_memory2_0_writedata),

        .completed_pio_input_export(completed_pio_input_export),
        .initialize_pio_output_export(initialize_pio_output_export),
        .result_address_pio_output_export(result_address_pio_output_export),
        .start_address_pio_output_export(start_address_pio_output_export),
        .start_pio_output_export(start_pio_output_export),
        .reset_pio_output_export(reset_pio_output_export)
    );

    GameOfLifeWrapper GameOfLifeWrapper0(
        .io_address_to_access(onchip_memory2_0_address),
        .io_starting_address(start_address_pio_output_export),
        .io_result_address(result_address_pio_output_export),
        .io_initialize(initialize_pio_output_export),
        .io_completed(completed_pio_input_export),
        .io_write_data(onchip_memory2_0_writedata),
        .io_read_data(onchip_memory2_0_readdata),
        .io_start(start_pio_output_export),
        .clock(fpga_clk_50),
        .reset(reset_pio_output_export),
        .io_write_enable(write_enable)
    );

    reg [25:0] counter;
    reg led_level;
    always @(posedge fpga_clk_50 or negedge hps_fpga_reset_n) begin
        if (~hps_fpga_reset_n) begin
            counter <= 0;
            led_level <= 0;
        end

        else if (counter == 24999999) begin
            counter <= 0;
            led_level <= ~led_level;
        end
        else
            counter <= counter+1'b1;
    end

    assign LED[0] = led_level;

endmodule

