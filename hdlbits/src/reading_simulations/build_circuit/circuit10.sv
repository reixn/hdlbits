module top_module (
  input clk,
  input a,
  input b,
  output q,
  output reg state  );

  always_ff@(posedge clk)
    if (state)
      state <= a || b;
    else
      state <= a && b;
    
  assign q = a ^ b ^ state;

endmodule
