module top_module (
  input clk,
  input areset,
  input x,
  output z
);

  reg carry;
  wire negX = !x;
  
  always_ff@(posedge clk or posedge areset)
    if (areset)
      carry <= 1'b1;
    else
      carry <= carry & negX;

  assign z = carry ^ negX;
endmodule