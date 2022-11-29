module top_module ( 
    input [15:0] a, b,
    input cin,
    output cout,
    output [15:0] sum );
  
  wire [3:0] carry;

  bcd_fadd a0(.a(a[3:0]), .b(b[3:0]), .cin(cin), .cout(carry[0]), .sum(sum[3:0]));

  genvar i;
  generate
    for (i = 1; i < 4; i = i + 1) begin:adder
      bcd_fadd ai(
        .a(a[i*4 +: 4]),
        .b(b[i*4 +: 4]),
        .cin(carry[i - 1]),
        .cout(carry[i]),
        .sum(sum[i*4 +: 4]));
    end
  endgenerate

  assign cout = carry[3];
endmodule

