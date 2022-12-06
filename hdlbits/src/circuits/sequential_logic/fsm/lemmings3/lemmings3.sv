module top_module(
  input clk,
  input areset,    // Freshly brainwashed Lemmings walk left.
  input bump_left,
  input bump_right,
  input ground,
  input dig,
  output walk_left,
  output walk_right,
  output aaah,
  output digging ); 
  
  parameter WL = 0, WR = 1, DL = 2, DR = 3, FL = 4, FR = 5;
  parameter WLv = 1, WRv = 2, DLv = 4, DRv = 8, FLv = 16, FRv = 32;

  reg [5:0] state;

  wire [5:0] next_state;
  always_comb
    case (state) // 
      WLv : begin
        if (ground) begin
          if (dig)
            next_state = DLv;
          else if (bump_left)
            next_state = WRv;
          else
            next_state = WLv;
        end
        else
          next_state = FLv;
      end
      WRv : begin
        if (ground) begin
          if (dig)
            next_state = DRv;
          else if (bump_right)
            next_state = WLv;
          else
            next_state = WRv;
        end
        else
          next_state = FRv;
      end
      DLv : next_state = ground ? DLv : FLv;
      DRv : next_state = ground ? DRv : FRv;
      FLv : next_state = ground ? WLv : FLv;
      FRv : next_state = ground ? WRv : FRv;
      default : next_state = 0;
    endcase

  always_ff@(posedge clk or posedge areset)
    if (areset)
      state <= 6'b1; // left
    else
      state <= next_state;

  assign walk_left = state[WL];
  assign walk_right = state[WR];
  assign aaah = state[FL] || state[FR];
  assign digging = state[DL] || state[DR];
endmodule

