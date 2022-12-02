module top_module( 
    input a, b, cin,
    output cout, sum );
  
  wire s1 = a ^ b;
  assign sum = s1 ^ cin;
  assign cout = (a & b) | (s1 & cin);
endmodule

