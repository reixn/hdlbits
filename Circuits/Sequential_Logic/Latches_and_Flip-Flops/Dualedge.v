module top_module (
    input clk,
    input d,
    output q
);
  reg pq, nq;
  
  always@(posedge clk) begin
    pq <= d;
  end

  always@(negedge clk) begin
    nq <= d;
  end

  assign q = clk ? pq : nq;
endmodule

