struct x{
  int a;
  double b;
  char c;
  struct x *next;
};
void main()
{
  int a, b[100], 
    c, d;

  struct x aa, *p;

  aa.a = 10;
  p =&aa;
  p->a=1;
   a = 10;
   b[1] = '3';
   c = 3.14;
   d = "abacaxi";
   while (--a!=0 && !(b>=!c) || b==0){
     c = (a+b[a])/2;
     a>0?a || c: (b[0] | 1);
     b[0] = b[0]*b[0] - 1;
     c++;
   }
}
