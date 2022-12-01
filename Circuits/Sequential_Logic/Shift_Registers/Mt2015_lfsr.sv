module top_module (
	input [2:0] SW,      // R
	input [1:0] KEY,     // L and clk
	output [2:0] LEDR);  // Q

  wire clk = KEY[0];
  wire L = KEY[1];

  unit u0(.clk(clk), .l(L), .r(SW[0]), .q_in(LEDR[2]), .q(LEDR[0]));
  unit u1(.clk(clk), .l(L), .r(SW[1]), .q_in(LEDR[0]), .q(LEDR[1]));
  unit u2(.clk(clk), .l(L), .r(SW[2]), .q_in(LEDR[1] ^ LEDR[2]), .q(LEDR[2]));
endmodule

module unit (
  input clk, l, r, q_in,
  output reg q);

  always_ff@(posedge clk)
    q <= l ? r : q_in;
endmodule
