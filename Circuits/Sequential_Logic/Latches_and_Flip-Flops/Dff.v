module top_module (
    input clk,    // Clocks are used in sequential circuits
    input d,
    output reg q );//

  always@(posedge clk)
    q <= d;

endmodule

