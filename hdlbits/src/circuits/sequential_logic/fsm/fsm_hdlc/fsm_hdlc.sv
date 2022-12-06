module top_module(
  input clk,
  input reset,    // Synchronous reset
  input in,
  output disc,
  output flag,
  output err);

  parameter Zero = 0, ZeroDisc = 1, ZeroFlag = 2, One = 3, Err = 4;
  parameter ZeroV = 1, ZeroDiscV = 2, ZeroFlagV = 4, OneV = 8, ErrV = 16;

  reg [4:0] state;
  reg [2:0] count; 

  wire [4:0] next_state;
  always_comb begin
    next_state = 5'b0;
    if (state[One]) begin
      case (count)
        3'd4 : begin
          next_state[ZeroDisc] = !in;
          next_state[One] = in;
        end
        3'd5 : begin
          next_state[ZeroFlag] = !in;
          next_state[Err] = in;
        end
        default: begin
          next_state[Zero] = !in;
          next_state[One] = in;
        end
      endcase
    end
    else begin
      if (in) begin
        next_state[One] = state[Zero] || state[ZeroDisc] || state[ZeroFlag];
        next_state[Err] = state[Err];
      end
      else begin
        next_state[Zero] = 1'b1;
      end
    end
  end

  always_ff@(posedge clk)
    if (reset)
      state <= ZeroV;
    else
      state <= next_state;

  always_ff@(posedge clk)
    if (reset)
      count <= 3'b0;
    else begin
      if (state[One])
        count <= count + 1;
      else
        count <= 3'b0;
    end
  
  assign disc = state[ZeroDisc];
  assign flag = state[ZeroFlag];
  assign err = state[Err];
endmodule
