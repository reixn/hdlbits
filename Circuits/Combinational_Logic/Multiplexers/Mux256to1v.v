module top_module( 
    input [1023:0] in,
    input [7:0] sel,
    output [3:0] out );
  
  always@(*) begin
    out = 0;
    for (int i = 0; i < 256; i = i + 1)
      out = sel == i ? in[i * 4 +: 4] : out;
  end
endmodule

