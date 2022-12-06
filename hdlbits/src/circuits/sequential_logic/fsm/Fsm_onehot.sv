module top_module(
    input in,
    input [9:0] state,
    output [9:0] next_state,
    output out1,
    output out2);

  always_comb begin
    next_state = 10'b0;
    if (in) begin
      next_state[1] = state[0] || state[8] || state[9];
      next_state[2] = state[1];
      next_state[3] = state[2];
      next_state[4] = state[3];
      next_state[5] = state[4];
      next_state[6] = state[5];
      next_state[7] = state[6] || state[7];
    end
    else begin
      next_state[0] = | {state[9:7], state[4:0]};
      next_state[8] = state[5];
      next_state[9] = state[6];
    end
  end

  assign out1 = state[8] || state[9];
  assign out2 = state[7] || state[9];
  
endmodule