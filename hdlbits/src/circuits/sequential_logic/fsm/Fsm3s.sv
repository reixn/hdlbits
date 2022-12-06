module top_module(
  input clk,
  input in,
  input reset,
  output out); //

  parameter A = 0, B = 1, C = 2, D = 3;
  
  reg [3:0] state;
  wire [3:0] next_state;

  // State transition logic
  assign next_state[A] = !in & (state[A] | state[C]);
  assign next_state[B] = in & (state[A] | state[B] | state[D]);
  assign next_state[C] = !in & (state[B] | state[D]);
  assign next_state[D] = in & state[C];

  always_ff@(posedge clk)
    if (reset)
      state <= 4'b1;
    else
      state <= next_state;

  assign out = state[D];

endmodule
