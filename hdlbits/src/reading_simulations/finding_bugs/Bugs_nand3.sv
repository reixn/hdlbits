module top_module (input a, input b, input c, output out);//

  wire an;
  andgate inst1(an, a, b, c, 1'b1, 1'b1);

  assign out = !an;

endmodule