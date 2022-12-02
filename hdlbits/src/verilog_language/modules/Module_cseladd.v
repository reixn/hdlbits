module top_module(
    input [31:0] a,
    input [31:0] b,
    output [31:0] sum
);
  wire [15:0] sh0, sh1;
  wire carry;

  add16 al(.a(a[15:0]), .b(b[15:0]), .cin(0), .cout(carry), .sum(sum[15:0]));

  add16 ah0(.a(a[31:16]), .b(b[31:16]), .cin(0), .cout(), .sum(sh0));
  add16 ah1(.a(a[31:16]), .b(b[31:16]), .cin(1), .cout(), .sum(sh1));

  always@(*) begin
    if(carry)
      sum[31:16] = sh1;
    else
      sum[31:16] = sh0;
  end
endmodule

