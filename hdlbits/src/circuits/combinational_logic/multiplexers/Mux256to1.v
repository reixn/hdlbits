module top_module( 
    input [255:0] in,
    input [7:0] sel,
    output out );
  
  always@(*) begin
    out = 0;
    for (int i = 0; i < 256; i = i + 1)
      out = sel == i ? in[i] : out;
  end
endmodule

