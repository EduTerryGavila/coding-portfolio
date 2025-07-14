void main(){

    var n = 20; // Calcularemos 20 primeros numeros de fibonnaci

    var fib = 1;
    var prevFib = 1;
    var i;
    var temp;

    if (n<=1) {
        print "1";
    }else{
        for i from 2 to n{
            temp = fib;
            fib = fib + prevFib;
            prevFib = temp;
            print fib,"\n";
        }
    }
    
}