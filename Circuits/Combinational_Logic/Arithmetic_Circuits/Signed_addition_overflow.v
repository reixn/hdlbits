module top_module (
    input [7:0] a,
    input [7:0] b,
    output [7:0] s,
    output overflow
); //
 
  wire [7:0] carry;

  fadd a0(.a(a[0]), .b(b[0]), .cin(0), .cout(carry[0]), .sum(s[0]));

  genvar i;
  generate
    for (i = 1; i < 8; i = i + 1) begin:adder
      fadd ai(
        .a(a[i]),
        .b(b[i]),
        .cin(carry[i - 1]),
        .cout(carry[i]),
        .sum(s[i]));
    end
  endgenerate

  assign overflow = carry[7] ^ carry[6];

endmodule

module fadd( 
    input a, b, cin,
    output cout, sum );
  
  wire s1 = a ^ b;
  assign sum = s1 ^ cin;
  assign cout = (a & b) | (s1 & cin);
endmodule

