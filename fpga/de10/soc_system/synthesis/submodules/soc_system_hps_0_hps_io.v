// soc_system_hps_0_hps_io.v

// This file was auto-generated from altera_hps_io_hw.tcl.  If you edit it your changes
// will probably be lost.
// 
// Generated using ACDS version 18.1 625

`timescale 1 ps / 1 ps
module soc_system_hps_0_hps_io (
		output wire [14:0] mem_a,                   // memory.mem_a
		output wire [2:0]  mem_ba,                  //       .mem_ba
		output wire        mem_ck,                  //       .mem_ck
		output wire        mem_ck_n,                //       .mem_ck_n
		output wire        mem_cke,                 //       .mem_cke
		output wire        mem_cs_n,                //       .mem_cs_n
		output wire        mem_ras_n,               //       .mem_ras_n
		output wire        mem_cas_n,               //       .mem_cas_n
		output wire        mem_we_n,                //       .mem_we_n
		output wire        mem_reset_n,             //       .mem_reset_n
		inout  wire [31:0] mem_dq,                  //       .mem_dq
		inout  wire [3:0]  mem_dqs,                 //       .mem_dqs
		inout  wire [3:0]  mem_dqs_n,               //       .mem_dqs_n
		output wire        mem_odt,                 //       .mem_odt
		output wire [3:0]  mem_dm,                  //       .mem_dm
		input  wire        oct_rzqin,               //       .oct_rzqin
		inout  wire        hps_io_gpio_inst_GPIO09, // hps_io.hps_io_gpio_inst_GPIO09
		inout  wire        hps_io_gpio_inst_GPIO35, //       .hps_io_gpio_inst_GPIO35
		inout  wire        hps_io_gpio_inst_GPIO40, //       .hps_io_gpio_inst_GPIO40
		inout  wire        hps_io_gpio_inst_GPIO53, //       .hps_io_gpio_inst_GPIO53
		inout  wire        hps_io_gpio_inst_GPIO54, //       .hps_io_gpio_inst_GPIO54
		inout  wire        hps_io_gpio_inst_GPIO61  //       .hps_io_gpio_inst_GPIO61
	);

	soc_system_hps_0_hps_io_border border (
		.mem_a                   (mem_a),                   // memory.mem_a
		.mem_ba                  (mem_ba),                  //       .mem_ba
		.mem_ck                  (mem_ck),                  //       .mem_ck
		.mem_ck_n                (mem_ck_n),                //       .mem_ck_n
		.mem_cke                 (mem_cke),                 //       .mem_cke
		.mem_cs_n                (mem_cs_n),                //       .mem_cs_n
		.mem_ras_n               (mem_ras_n),               //       .mem_ras_n
		.mem_cas_n               (mem_cas_n),               //       .mem_cas_n
		.mem_we_n                (mem_we_n),                //       .mem_we_n
		.mem_reset_n             (mem_reset_n),             //       .mem_reset_n
		.mem_dq                  (mem_dq),                  //       .mem_dq
		.mem_dqs                 (mem_dqs),                 //       .mem_dqs
		.mem_dqs_n               (mem_dqs_n),               //       .mem_dqs_n
		.mem_odt                 (mem_odt),                 //       .mem_odt
		.mem_dm                  (mem_dm),                  //       .mem_dm
		.oct_rzqin               (oct_rzqin),               //       .oct_rzqin
		.hps_io_gpio_inst_GPIO09 (hps_io_gpio_inst_GPIO09), // hps_io.hps_io_gpio_inst_GPIO09
		.hps_io_gpio_inst_GPIO35 (hps_io_gpio_inst_GPIO35), //       .hps_io_gpio_inst_GPIO35
		.hps_io_gpio_inst_GPIO40 (hps_io_gpio_inst_GPIO40), //       .hps_io_gpio_inst_GPIO40
		.hps_io_gpio_inst_GPIO53 (hps_io_gpio_inst_GPIO53), //       .hps_io_gpio_inst_GPIO53
		.hps_io_gpio_inst_GPIO54 (hps_io_gpio_inst_GPIO54), //       .hps_io_gpio_inst_GPIO54
		.hps_io_gpio_inst_GPIO61 (hps_io_gpio_inst_GPIO61)  //       .hps_io_gpio_inst_GPIO61
	);

endmodule
