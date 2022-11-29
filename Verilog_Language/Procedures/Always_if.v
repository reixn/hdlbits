// synthesis verilog_input_version verilog_2001
module top_module(
    input a,
    input b,
    input sel_b1,
    input sel_b2,
    output wire out_assign,
    output reg out_always   ); 
  
  wire sel = sel_b1 & sel_b2;

  assign out_assign = sel ? b : a;

  always@(*) begin
    if (sel)
      out_always = b;
    else
      out_always = a;
  end
endmodule

