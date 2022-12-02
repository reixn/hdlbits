module top_module (
    input clk,
    input resetn,   // synchronous reset
    input in,
    output out);
  
  reg [3:0] q;
  
  always@(posedge clk)
    if (!resetn)
      q <= 4'b0;
    else begin
      q[0] <= in;
      q[1] <= q[0];
      q[2] <= q[1];
      q[3] <= q[2];
    end

  assign out = q[3];
endmodule

