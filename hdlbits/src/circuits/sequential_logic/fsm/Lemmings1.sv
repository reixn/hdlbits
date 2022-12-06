module top_module(
  input clk,
  input areset,    // Freshly brainwashed Lemmings walk left.
  input bump_left,
  input bump_right,
  output walk_left,
  output walk_right); //  

  parameter LEFT = 0, RIGHT = 1;
  
  reg [1:0] state;

  wire [1:0] next_state;
  assign next_state[LEFT] =
    (state[LEFT] && !bump_left) ||
    (state[RIGHT] && bump_right);
  assign next_state[RIGHT] =
    (state[LEFT] && bump_left) ||
    (state[RIGHT] && !bump_right);
  
  always_ff@(posedge clk or posedge areset)
    if (areset)
      state <= 2'b01; // left
    else
      state <= next_state;

  assign walk_left = state[LEFT];
  assign walk_right = state[RIGHT];
endmodule

