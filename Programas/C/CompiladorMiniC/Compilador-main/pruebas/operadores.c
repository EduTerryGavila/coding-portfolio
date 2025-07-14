main(){

//operaciones
    var a = 4;
    var b = 3;
    print ("Suma: ", a + b, "\n");
    print ("Resta: ", a - b, "\n");
    print ("Multiplicacion: ", a * b, "\n");
    print ("Division: ", a / b, "\n");
    
//uso de operadores logicos
    if (a!=2){
        if(a<5){
            while(a<2){
                print (a,"\n");
                a=a+1;
            }
        }else if(a>2){
            while(a>5){
                print (a,"\n");
                a=a-1;
            }
        }
    }else{
        print ("a==2");
    }

    print ("Valor final de a: ",a);
    
}

