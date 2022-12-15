module top_module (
  input a,
  input b,
  input c,
  input d,
  output q );//

  wire obc = b || c;
  assign q = (a || obc) && (!a || obc); // Fix me

endmodule
