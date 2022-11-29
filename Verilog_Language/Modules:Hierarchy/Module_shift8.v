module top_module ( 
    input clk, 
    input [7:0] d, 
    input [1:0] sel, 
    output [7:0] q 
);
  wire [7:0] d1, d2, d3;

  my_dff8 dff1(.clk(clk), .d(d), .q(d1));
  my_dff8 dff2(.clk(clk), .d(d1), .q(d2));
  my_dff8 dff3(.clk(clk), .d(d2), .q(d3));

  always@(*) begin
    case(sel)
      0: q = d;
      1: q = d1;
      2: q= d2;
      3: q= d3;
    endcase
  end
endmodule

