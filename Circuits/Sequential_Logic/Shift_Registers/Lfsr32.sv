module top_module(
    input clk,
    input reset,    // Active-high synchronous reset to 32'h1
    output [31:0] q
); 

  always_ff@(posedge clk)
    if (reset)
      q <= 32'h1;
    else begin
      q[31] <= 0 ^ q[0];
      for (int i = 0; i < 31; i = i + 1)
        if (i == 21 || i == 1 || i == 0)
          q[i] <= q[i + 1] ^ q[0];
        else
          q[i] <= q[i + 1];
    end
  
endmodule

