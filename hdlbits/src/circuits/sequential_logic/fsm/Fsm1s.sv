module top_module(
  input clk,
  input reset,    // Asynchronous reset to state B
  input in,
  output out);//  

  parameter A=0, B=1; 
  reg state, next_state;

  always_comb
    case (state)
      A:begin
        case (in)
          1'b0: next_state = B;
          1'b1: next_state = A;
        endcase
      end
      B: begin
        case (in)
          1'b0: next_state = A;
          1'b1: next_state = B;
        endcase
      end
    endcase


  always_ff @(posedge clk)
    if (reset)
      state <= B;
    else
      state <= next_state;
  
  assign out = state == B ? 1 : 0;

endmodule

