#include <stdio.h> // this is for standard input output functions.
#include <unistd.h> // this is for file closing.
#include <fcntl.h> // this is for open the file.
#include <sys/mman.h> // this is for mmap and munmap
#include <stdint.h> // this is for uint32_t

#define HW_REGS_BASE ( 0xff200000 )
#define HW_REGS_SPAN ( 0x00100000 )
#define HW_REGS_MASK ( HW_REGS_SPAN - 1 )

#define MEM_ADDR            ( 0x0000 )
#define START_DATA_ADDR     ( 0x1000 )
#define RESULT_DATA_ADDR    ( 0x2000 )
#define INITIALIZE_ADDR     ( 0x3000 )
#define COMPLETED_ADDR      ( 0x4000 )
#define RESET_SIGNAL_ADDR   ( 0x5000 )
#define START_SIGNAL_ADDR   ( 0x6000 )

#define ROWS 3
#define COLS 3
#define TOTAL ROWS*COLS

void gol_test(void *virtual_base, uint8_t *input) {
    void *reset_addr = virtual_base + RESET_SIGNAL_ADDR;
    void *start_data_addr = virtual_base + START_DATA_ADDR;
    void *result_data_addr = virtual_base + RESULT_DATA_ADDR;
    void *initialize_addr = virtual_base + INITIALIZE_ADDR;
    void *start_signal_addr = virtual_base + START_SIGNAL_ADDR;
    void *complete_signal_addr = virtual_base + COMPLETED_ADDR;


    *(uint16_t *)start_data_addr = 0x013;
    *(uint16_t *)result_data_addr = 0x023;

    *(uint8_t *) reset_addr = 1;
    *(uint8_t *) reset_addr = 0;

    for(int i=0; i<TOTAL; i++) {
        void *data_addr = virtual_base + MEM_ADDR + *(uint16_t *)start_data_addr + i;
        *(uint8_t *) data_addr = input[i];
    }

    *(uint8_t *) initialize_addr = 1;
    *(uint8_t *) start_signal_addr = 1;
    *(uint8_t *) start_signal_addr = 0;
    while(*(uint8_t *) complete_signal_addr != 1);
    *(uint8_t *) initialize_addr = 0;

    for(int i=0; i<ROWS; i++) {
        for(int j=0; j<COLS; j++) {
            void *data_addr = virtual_base + *(uint16_t *)start_data_addr + i*COLS + j;
            printf("%d ", *(uint8_t *) data_addr);
        }
        printf("\n");
    }
    printf("\n");

    int no_of_times = 7;
    while(no_of_times--) {
        *(uint8_t *) start_signal_addr = 1;
        *(uint8_t *) start_signal_addr = 0;
        while(*(uint8_t *) complete_signal_addr != 1);
        for(int i=0; i<ROWS; i++) {
            for(int j=0; j<COLS; j++) {
                void *data_addr = virtual_base + *(uint16_t *)result_data_addr + i*COLS + j;
                printf("%d ", *(uint8_t *) data_addr);
            }
            printf("\n");
        }
        printf("\n");
    }
}

int main() {

	void *virtual_base;
	int fd;

	fd = open( "/dev/mem", ( O_RDWR | O_SYNC ) );
	virtual_base = mmap( NULL , HW_REGS_SPAN, ( PROT_READ | PROT_WRITE ), MAP_SHARED, fd, HW_REGS_BASE );

    uint8_t input[ROWS*COLS] = {
        0,0,0,
        1,1,1,
        0,0,0
    };

	gol_test(virtual_base, input);

	if( munmap( virtual_base, HW_REGS_SPAN ) != 0 ) {
		printf( "ERROR: munmap() failed...\n" );
		close( fd );
		return( 1 );
	}

	close( fd );

	return( 0 );

}
