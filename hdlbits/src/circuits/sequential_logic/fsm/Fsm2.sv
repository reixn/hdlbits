module top_module(
  input clk,
  input areset,    // Asynchronous reset to OFF
  input j,
  input k,
  output out); //  

  parameter OFF=0, ON=1; 
  reg state, next_state;
    
  always_comb
    case (state)
      ON: next_state = k ? OFF : ON;
      OFF: next_state = j ? ON : OFF;
    endcase
  
  always_ff@(posedge clk or posedge areset)
    if (areset)
      state <= OFF;
    else
      state <= next_state;
  
  assign out = state == ON ? 1'b1 : 1'b0;

endmodule

