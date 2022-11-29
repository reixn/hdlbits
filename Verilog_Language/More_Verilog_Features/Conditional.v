module top_module (
    input [7:0] a, b, c, d,
    output [7:0] min);//

  wire [7:0] mab = a < b ? a : b;
  wire [7:0] mcd = c < d ? c : d;

  assign min = mab < mcd ? mab : mcd;

endmodule

