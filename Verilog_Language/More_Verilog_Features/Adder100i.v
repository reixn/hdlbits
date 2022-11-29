module top_module( 
    input [99:0] a, b,
    input cin,
    output [99:0] cout,
    output [99:0] sum );

  add a0(.a(a[0]), .b(b[0]), .cin(cin), .cout(cout[0]), .sum(sum[0]));

  genvar i;
  generate
    for (i = 1; i < 100; i = i + 1) begin: myadder
      add adder(.a(a[i]), .b(b[i]), .cin(cout[i - 1]), .cout(cout[i]), .sum(sum[i]));
    end
  endgenerate

endmodule

module add (
  input a, b, cin,
  output cout, sum);
  
  wire s1 = a ^ b;
  assign sum = s1 ^ cin;
  assign cout = (a & b) | (s1 & cin);
endmodule
