void main()
{
  int a, b, 
    c, d;

   a = 10;
   b = '3';
   c = 3.14;
   d = "abacaxi";
   while (a!=0 && b>=c){
     c = (a+b)/2;
     a = a || (b | 1);
     b = b*b - 1;
   }
}
