module top_module (
    input clk,
    input slowena,
    input reset,
    output [3:0] q);
  
  always_ff@(posedge clk)
    if (reset)
      q <= 4'd0;
    else if (slowena)
      q <= q == 4'd9 ? 4'd0 : q + 4'd1;
endmodule

