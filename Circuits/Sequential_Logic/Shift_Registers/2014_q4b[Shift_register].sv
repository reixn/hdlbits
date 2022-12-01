module top_module (
    input [3:0] SW,
    input [3:0] KEY,
    output [3:0] LEDR
); //
  
  wire clk = KEY[0];
  wire E = KEY[1];
  wire L = KEY[2];
  wire w = KEY[3];

  MUXDFF md3 (.clk(clk), .w(w), .R(SW[3]), .E(E), .L(L), .Q(LEDR[3]));

  genvar i;
  generate
    for (i = 0; i < 3; i = i + 1) begin:muxdff
      MUXDFF md(
        .clk(clk),
        .w(LEDR[i + 1]),
        .R(SW[i]),
        .E(E),
        .L(L),
        .Q(LEDR[i]));
    end
  endgenerate
endmodule

module MUXDFF (
    input clk,
    input w, R, E, L,
    output Q
);

  always@(posedge clk)
    Q <= L ? R : (E ? w : Q);
endmodule
