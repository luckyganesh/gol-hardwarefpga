// (C) 2001-2018 Intel Corporation. All rights reserved.
// Your use of Intel Corporation's design tools, logic functions and other 
// software and tools, and its AMPP partner logic functions, and any output 
// files from any of the foregoing (including device programming or simulation 
// files), and any associated documentation or information are expressly subject 
// to the terms and conditions of the Intel Program License Subscription 
// Agreement, Intel FPGA IP License Agreement, or other applicable 
// license agreement, including, without limitation, that your use is for the 
// sole purpose of programming logic devices manufactured by Intel and sold by 
// Intel or its authorized distributors.  Please refer to the applicable 
// agreement for further details.


module soc_system_hps_0_hps_io_border(
// memory
  output wire [15 - 1 : 0 ] mem_a
 ,output wire [3 - 1 : 0 ] mem_ba
 ,output wire [1 - 1 : 0 ] mem_ck
 ,output wire [1 - 1 : 0 ] mem_ck_n
 ,output wire [1 - 1 : 0 ] mem_cke
 ,output wire [1 - 1 : 0 ] mem_cs_n
 ,output wire [1 - 1 : 0 ] mem_ras_n
 ,output wire [1 - 1 : 0 ] mem_cas_n
 ,output wire [1 - 1 : 0 ] mem_we_n
 ,output wire [1 - 1 : 0 ] mem_reset_n
 ,inout wire [32 - 1 : 0 ] mem_dq
 ,inout wire [4 - 1 : 0 ] mem_dqs
 ,inout wire [4 - 1 : 0 ] mem_dqs_n
 ,output wire [1 - 1 : 0 ] mem_odt
 ,output wire [4 - 1 : 0 ] mem_dm
 ,input wire [1 - 1 : 0 ] oct_rzqin
// hps_io
 ,inout wire [1 - 1 : 0 ] hps_io_gpio_inst_GPIO09
 ,inout wire [1 - 1 : 0 ] hps_io_gpio_inst_GPIO35
 ,inout wire [1 - 1 : 0 ] hps_io_gpio_inst_GPIO40
 ,inout wire [1 - 1 : 0 ] hps_io_gpio_inst_GPIO53
 ,inout wire [1 - 1 : 0 ] hps_io_gpio_inst_GPIO54
 ,inout wire [1 - 1 : 0 ] hps_io_gpio_inst_GPIO61
);

assign hps_io_gpio_inst_GPIO09 = intermediate[1] ? intermediate[0] : 'z;
assign hps_io_gpio_inst_GPIO35 = intermediate[3] ? intermediate[2] : 'z;
assign hps_io_gpio_inst_GPIO40 = intermediate[5] ? intermediate[4] : 'z;
assign hps_io_gpio_inst_GPIO53 = intermediate[7] ? intermediate[6] : 'z;
assign hps_io_gpio_inst_GPIO54 = intermediate[9] ? intermediate[8] : 'z;
assign hps_io_gpio_inst_GPIO61 = intermediate[11] ? intermediate[10] : 'z;

wire [12 - 1 : 0] intermediate;

wire [102 - 1 : 0] floating;

cyclonev_hps_peripheral_gpio gpio_inst(
 .GPIO1_PORTA_I({
    hps_io_gpio_inst_GPIO54[0:0] // 25:25
   ,hps_io_gpio_inst_GPIO53[0:0] // 24:24
   ,floating[11:0] // 23:12
   ,hps_io_gpio_inst_GPIO40[0:0] // 11:11
   ,floating[15:12] // 10:7
   ,hps_io_gpio_inst_GPIO35[0:0] // 6:6
   ,floating[21:16] // 5:0
  })
,.GPIO1_PORTA_OE({
    intermediate[9:9] // 25:25
   ,intermediate[7:7] // 24:24
   ,floating[33:22] // 23:12
   ,intermediate[5:5] // 11:11
   ,floating[37:34] // 10:7
   ,intermediate[3:3] // 6:6
   ,floating[43:38] // 5:0
  })
,.GPIO2_PORTA_O({
    intermediate[10:10] // 3:3
   ,floating[46:44] // 2:0
  })
,.GPIO0_PORTA_O({
    intermediate[0:0] // 9:9
   ,floating[55:47] // 8:0
  })
,.GPIO2_PORTA_I({
    hps_io_gpio_inst_GPIO61[0:0] // 3:3
   ,floating[58:56] // 2:0
  })
,.GPIO2_PORTA_OE({
    intermediate[11:11] // 3:3
   ,floating[61:59] // 2:0
  })
,.GPIO0_PORTA_I({
    hps_io_gpio_inst_GPIO09[0:0] // 9:9
   ,floating[70:62] // 8:0
  })
,.GPIO0_PORTA_OE({
    intermediate[1:1] // 9:9
   ,floating[79:71] // 8:0
  })
,.GPIO1_PORTA_O({
    intermediate[8:8] // 25:25
   ,intermediate[6:6] // 24:24
   ,floating[91:80] // 23:12
   ,intermediate[4:4] // 11:11
   ,floating[95:92] // 10:7
   ,intermediate[2:2] // 6:6
   ,floating[101:96] // 5:0
  })
);


hps_sdram hps_sdram_inst(
 .mem_dq({
    mem_dq[31:0] // 31:0
  })
,.mem_odt({
    mem_odt[0:0] // 0:0
  })
,.mem_ras_n({
    mem_ras_n[0:0] // 0:0
  })
,.mem_dqs_n({
    mem_dqs_n[3:0] // 3:0
  })
,.mem_dqs({
    mem_dqs[3:0] // 3:0
  })
,.mem_dm({
    mem_dm[3:0] // 3:0
  })
,.mem_we_n({
    mem_we_n[0:0] // 0:0
  })
,.mem_cas_n({
    mem_cas_n[0:0] // 0:0
  })
,.mem_ba({
    mem_ba[2:0] // 2:0
  })
,.mem_a({
    mem_a[14:0] // 14:0
  })
,.mem_cs_n({
    mem_cs_n[0:0] // 0:0
  })
,.mem_ck({
    mem_ck[0:0] // 0:0
  })
,.mem_cke({
    mem_cke[0:0] // 0:0
  })
,.oct_rzqin({
    oct_rzqin[0:0] // 0:0
  })
,.mem_reset_n({
    mem_reset_n[0:0] // 0:0
  })
,.mem_ck_n({
    mem_ck_n[0:0] // 0:0
  })
);

endmodule

