
module soc_system (
	clk_clk,
	completed_pio_input_export,
	hps_0_h2f_reset_reset_n,
	hps_0_hps_io_hps_io_gpio_inst_GPIO09,
	hps_0_hps_io_hps_io_gpio_inst_GPIO35,
	hps_0_hps_io_hps_io_gpio_inst_GPIO40,
	hps_0_hps_io_hps_io_gpio_inst_GPIO53,
	hps_0_hps_io_hps_io_gpio_inst_GPIO54,
	hps_0_hps_io_hps_io_gpio_inst_GPIO61,
	initialize_pio_output_export,
	memory_mem_a,
	memory_mem_ba,
	memory_mem_ck,
	memory_mem_ck_n,
	memory_mem_cke,
	memory_mem_cs_n,
	memory_mem_ras_n,
	memory_mem_cas_n,
	memory_mem_we_n,
	memory_mem_reset_n,
	memory_mem_dq,
	memory_mem_dqs,
	memory_mem_dqs_n,
	memory_mem_odt,
	memory_mem_dm,
	memory_oct_rzqin,
	onchip_memory2_0_s2_address,
	onchip_memory2_0_s2_chipselect,
	onchip_memory2_0_s2_clken,
	onchip_memory2_0_s2_write,
	onchip_memory2_0_s2_readdata,
	onchip_memory2_0_s2_writedata,
	reset_reset_n,
	reset_pio_output_export,
	result_address_pio_output_export,
	start_address_pio_output_export,
	start_pio_output_export,
	rows_pio_output_export,
	columns_pio_output_export);	

	input		clk_clk;
	input		completed_pio_input_export;
	output		hps_0_h2f_reset_reset_n;
	inout		hps_0_hps_io_hps_io_gpio_inst_GPIO09;
	inout		hps_0_hps_io_hps_io_gpio_inst_GPIO35;
	inout		hps_0_hps_io_hps_io_gpio_inst_GPIO40;
	inout		hps_0_hps_io_hps_io_gpio_inst_GPIO53;
	inout		hps_0_hps_io_hps_io_gpio_inst_GPIO54;
	inout		hps_0_hps_io_hps_io_gpio_inst_GPIO61;
	output		initialize_pio_output_export;
	output	[14:0]	memory_mem_a;
	output	[2:0]	memory_mem_ba;
	output		memory_mem_ck;
	output		memory_mem_ck_n;
	output		memory_mem_cke;
	output		memory_mem_cs_n;
	output		memory_mem_ras_n;
	output		memory_mem_cas_n;
	output		memory_mem_we_n;
	output		memory_mem_reset_n;
	inout	[31:0]	memory_mem_dq;
	inout	[3:0]	memory_mem_dqs;
	inout	[3:0]	memory_mem_dqs_n;
	output		memory_mem_odt;
	output	[3:0]	memory_mem_dm;
	input		memory_oct_rzqin;
	input	[11:0]	onchip_memory2_0_s2_address;
	input		onchip_memory2_0_s2_chipselect;
	input		onchip_memory2_0_s2_clken;
	input		onchip_memory2_0_s2_write;
	output	[7:0]	onchip_memory2_0_s2_readdata;
	input	[7:0]	onchip_memory2_0_s2_writedata;
	input		reset_reset_n;
	output		reset_pio_output_export;
	output	[15:0]	result_address_pio_output_export;
	output	[15:0]	start_address_pio_output_export;
	output		start_pio_output_export;
	output	[7:0]	rows_pio_output_export;
	output	[7:0]	columns_pio_output_export;
endmodule
