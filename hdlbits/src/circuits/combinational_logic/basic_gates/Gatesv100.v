module top_module( 
    input [99:0] in,
    output [98:0] out_both,
    output [99:1] out_any,
    output [99:0] out_different );

  always@(*) begin
    for (int i = 0; i < 99; i = i + 1)
      out_both[i] = in[i] & in[i + 1];
  end

  always@(*) begin
    for (int i = 1; i < 100; i = i + 1)
      out_any[i] = in[i] | in[i - 1];
  end
  
  always@(*) begin
    for (int i = 0; i < 99; i = i + 1)
      out_different[i] = in[i] ^ in[i + 1];
    out_different[99] = in[99] ^ in[0];
  end

endmodule

