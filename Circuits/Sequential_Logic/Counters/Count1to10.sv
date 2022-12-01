module top_module (
    input clk,
    input reset,
    output [3:0] q);
  
  always_ff@(posedge clk)
    if (reset)
      q <= 4'd1;
    else
      q <= q == 4'd10 ? 4'd1 : q + 1;
endmodule

