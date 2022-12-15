module top_module (
  input clock,
  input a,
  output p,
  output q);

  always_latch
    if (clock)
      p <= a;
  
  always_ff@(negedge clock)
    q <= p;

endmodule
