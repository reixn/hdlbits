module top_module (
    input clk,
    input reset,        // Synchronous active-high reset
    output [3:0] q);
  
  always_ff@(posedge clk)
    if (reset)
      q <= 4'd0;
    else
      q <= q == 4'd9 ? 4'd0 : q + 1;

endmodule

