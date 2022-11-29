module top_module(
    input [31:0] a,
    input [31:0] b,
    input sub,
    output [31:0] sum
);
  wire [31:0] b1 = b ^ {{32{sub}}};
  wire carry;

  add16 al(.a(a[15:0]), .b(b1[15:0]), .cin(sub), .cout(carry), .sum(sum[15:0]));
  add16 ah(.a(a[31:16]), .b(b1[31:16]), .cin(carry), .cout(), .sum(sum[31:16]));

endmodule

