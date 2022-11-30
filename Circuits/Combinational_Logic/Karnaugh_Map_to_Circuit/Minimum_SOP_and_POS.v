module top_module (
    input a,
    input b,
    input c,
    input d,
    output out_sop,
    output out_pos
);
  assign out_sop = c & ((!a & !b & !d) | d);

  wire cd = !c | d;
  assign out_pos = c & (!b | cd) & (!a | cd); 
endmodule

