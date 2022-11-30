module top_module (
    input clk,
    input j,
    input k,
    output Q); 
  
  wire diff = j ^ k;
  always@(posedge clk)
    Q <= (diff & j) | (!diff & (j ^ Q));
endmodule

