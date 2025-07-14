void main(){

    var a = 4;

    if (a!=5){
        if(a<5){
            while(a<5){
                print a,"\n";
                a=a+1;
            }
        }else if(a>5){
            while(a>5){
                print a,"\n";
                a=a-1;
            }
        }
    }else{
        print "a es cinco";
    }

    print "Valor final de a:",a;

}