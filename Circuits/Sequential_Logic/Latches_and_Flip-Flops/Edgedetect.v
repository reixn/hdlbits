module top_module (
    input clk,
    input [7:0] in,
    output [7:0] pedge
);
  reg [7:0] last;
  always@(posedge clk) begin
    pedge <= (in ^ last) & ~last;
    last <= in;
  end
endmodule

