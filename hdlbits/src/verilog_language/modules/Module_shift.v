module top_module ( input clk, input d, output q );
  wire o1, o2;

  my_dff m1(.clk(clk), .d(d), .q(o1));
  my_dff m2(.clk(clk), .d(o1), .q(o2));
  my_dff m3(.clk(clk), .d(o2), .q(q));
endmodule
