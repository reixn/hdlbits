module top_module (
    input [3:0] x,
    input [3:0] y, 
    output [4:0] sum);
  
  wire [3:0] carry;
  
  fadd a0(.a(x[0]), .b(y[0]), .cin(0), .cout(carry[0]), .sum(sum[0]));

  genvar i;
  generate
    for (i = 1; i < 4; i = i + 1) begin: adder
      fadd a(
        .a(x[i]),
        .b(y[i]),
        .cin(carry[i - 1]),
        .cout(carry[i]),
        .sum(sum[i]));
    end
  endgenerate

  assign sum[4] = carry[3];

endmodule

module fadd( 
    input a, b, cin,
    output cout, sum );
  
  wire s1 = a ^ b;
  assign sum = s1 ^ cin;
  assign cout = (a & b) | (s1 & cin);
endmodule

