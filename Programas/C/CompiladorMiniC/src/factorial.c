main() {
    var n = 10; // Factorial de 10

    var factorial = 1;
    var i = 1;
    while(i<=n){
        factorial = factorial * i;
        i=i+1;
    }

    print ("Factorial(",n,") = ", factorial);
}
