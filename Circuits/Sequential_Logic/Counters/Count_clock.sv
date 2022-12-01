module top_module(
  input clk,
  input reset,
  input ena,
  output pm,
  output [7:0] hh,
  output [7:0] mm,
  output [7:0] ss); 

  logic enam, enah;

  bcd_time60 ts (.clk(clk), .inc(ena), .reset(reset), .overflow(enam), .q(ss));
  bcd_time60 tm (.clk(clk), .inc(enam), .reset(reset), .overflow(enah), .q(mm));
  
  always_ff@(posedge clk)
    if (reset)
      hh <= 8'h12;
    else if (enah) begin
      if (hh[3:0] == 8'd9)
        hh <= 8'h10;
      else if (hh == 8'h12)
        hh <= 8'h1;
      else
        hh[3:0] <= hh[3:0] + 1'b1;
    end
  wire ofh = enah && hh == 8'h11;
  
  always_ff@(posedge clk)
    if (reset)
      pm <= 1'b0;
    else if (ofh)
      pm <= !pm;
    
endmodule

module bcdcount #(
  parameter MAX = 4'd9
  )(
  input clk,
  input inc,
  input reset, // Synchronous active-high reset
  output overflow,
  output [3:0] q);
  
  wire q_max = q == MAX;

  always_ff@(posedge clk)
    if (reset)
      q <= 4'd0;
    else if (inc)
      q <= q_max ? 4'd0 : q + 1'b1;
  
  assign overflow = inc & q_max;
endmodule

module bcd_time60 (
  input clk,
  input inc,
  input reset,
  output overflow,
  output [7:0] q);
  
  logic of1;
  bcdcount c1(clk, inc, reset, of1, q[3:0]);

  bcdcount #(.MAX(4'd5)) c2(clk, of1, reset, overflow, q[7:4]);
endmodule
