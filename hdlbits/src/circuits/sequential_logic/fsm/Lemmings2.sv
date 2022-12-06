module top_module(
  input clk,
  input areset,    // Freshly brainwashed Lemmings walk left.
  input bump_left,
  input bump_right,
  input ground,
  output walk_left,
  output walk_right,
  output aaah );

  parameter LEFT = 0, RIGHT = 1, FL = 2, FR = 3;

  reg [3:0] state;

  wire [3:0] next_state;
  assign next_state[LEFT] = ground && (
    (state[LEFT] && !bump_left) ||
    (state[RIGHT] && bump_right) ||
    state[FL]);
  assign next_state[RIGHT] = ground && (
    (state[LEFT] && bump_left) ||
    (state[RIGHT] && !bump_right) ||
    state[FR]);
  assign next_state[FL] = !ground && (state[LEFT] || state[FL]);
  assign next_state[FR] = !ground && (state[RIGHT] || state[FR]);

  always_ff@(posedge clk or posedge areset)
    if (areset)
      state <= 4'b1; // left
    else
      state <= next_state;

  assign walk_left = state[LEFT];
  assign walk_right = state[RIGHT];
  assign aaah = state[FL] | state[FR];

endmodule

