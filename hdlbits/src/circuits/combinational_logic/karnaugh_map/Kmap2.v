module top_module(
    input a,
    input b,
    input c,
    input d,
    output out  ); 
  wire cd = c & d;

  assign out = ((a | b) & cd) | (!a & !d) | (!b & !c);
endmodule

