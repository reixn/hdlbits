module top_module (
    input clk,
    input reset,   // Synchronous active-high reset
    output [3:1] ena,
    output [15:0] q);
  
  bcdcount c0(.clk(clk), .inc(1'b1), .reset(reset), .overflow(ena[1]), .q(q[3:0]));
  bcdcount c1(.clk(clk), .inc(ena[1]), .reset(reset), .overflow(ena[2]), .q(q[7:4]));
  bcdcount c2(.clk(clk), .inc(ena[2]), .reset(reset), .overflow(ena[3]), .q(q[11:8]));
  bcdcount c4(.clk(clk), .inc(ena[3]), .reset(reset), .overflow(), .q(q[15:12]));
endmodule

module bcdcount (
    input clk,
    input inc,
    input reset, // Synchronous active-high reset
    output overflow,
    output [3:0] q);
  
  always_ff@(posedge clk)
    if (reset)
      q <= 4'd0;
    else if (inc)
      q <= q == 4'd9 ? 4'd0 : q + 1'b1;
  
  assign overflow = inc & q == 4'd9;
endmodule

