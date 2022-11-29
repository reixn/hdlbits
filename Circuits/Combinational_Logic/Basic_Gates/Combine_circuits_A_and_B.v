module top_module (input x, input y, output z);
  
  wire oa1, ob1, oa2, ob2;

  ca a1(.x(x), .y(y), .z(oa1));
  cb b1(.x(x), .y(y), .z(ob1));
  ca a2(.x(x), .y(y), .z(oa2));
  cb b2(.x(x), .y(y), .z(ob2));

  assign z = (oa1 | ob1) ^ (oa2 & ob2);

endmodule

module ca (input x, input y, output z);
  assign z = (x ^ y) & x;
endmodule

module cb (input x, input y, output z);
  assign z = x ~^ y;
endmodule
