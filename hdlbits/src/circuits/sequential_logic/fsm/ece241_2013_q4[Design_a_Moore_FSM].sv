module top_module (
  input clk,
  input reset,
  input [3:1] s,
  output fr3,
  output fr2,
  output fr1,
  output dfr
);

  parameter LS = 0, Ht1 = 1, Lt1 = 2, Ht2 = 3, Lt2 = 4, Ht3 = 5;

  reg [5:0] state;

  wire [5:0] next_state;
  wire L1 = state[LS] | state[Ht1];
  wire H2 = state[Lt2] | state[Ht3];
  always_comb begin
    next_state = 0;
    case (s)
      3'b000 : next_state[LS] = 1'b1;
      3'b001 : begin
        next_state[Ht1] = L1;
        next_state[Lt1] = !L1;
      end
      3'b011 : begin
        next_state[Ht2] = !H2;
        next_state[Lt2] = H2;
      end
      3'b111 : next_state[Ht3] = 1'b1;
    endcase
  end

  always_ff@(posedge clk)
    if (reset)
      state <= 6'b1;
    else
      state <= next_state;
  
  wire S1 = state[Ht1] | state[Lt1];
  wire S2 = state[Ht2] | state[Lt2];
  assign fr1 = state[LS] | S1 | S2;
  assign fr2 = state[LS] | S1;
  assign fr3 = state[LS];
  assign dfr = state[LS] | state[Lt1] | state[Lt2];

endmodule

