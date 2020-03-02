#include <iostream>
#include <vector>
#include <stdio.h>
#include <stack>
#include <math.h>
using namespace std;

vector < char > est_inicial;
vector < char > est_final;
stack < vector < char > > pila;
int prof_max;
int n_ranas;

//INICIALIZA LOS VECTORES INICIAL Y FINAL CON LA CANTIDAD DE RANAS POR LADOS
void iniciaRanas(){
    for(int i=0; i<(n_ranas * 2) +1 ; i++){
        if(i<n_ranas)
            est_inicial.push_back('I');
        else if (i==n_ranas)
            est_inicial.push_back(' ');
        else
            est_inicial.push_back('D');
    }

    for(int i=0; i<(n_ranas * 2) +1 ; i++){
        if(i<n_ranas)
            est_final.push_back('D');
        else if (i==n_ranas)
            est_final.push_back(' ');
        else
            est_final.push_back('I');
    }
}

//CAMBIA EL ESPACIO POR LA RANA, RECIBE LA POSCICION DEL ESPACIO Y LA RANA
vector <char> cambiaEspacioRana(vector< char > est,int x,int y){
	int aux= est[x];
	est[x]=est[y];
	est[y]=aux;
	return est;
}

//EVALUA SI LOS MOVIMIENTOS SON POSIBLES
bool evaluaMovimiento(vector <char> estado,int pos_a,int salto){
    int pos_abs = pos_a + salto;
    if(pos_abs < 0 || pos_abs >= estado.size())
        return false;
	else if(estado[pos_abs]==' ')
		return true;
	else
		return false;
}

//IMPRIME EL ESTADO
void printEstado( vector < char > est){
    printf("|");
    for(int i=0; i<est.size(); i++){
        printf(" %c",est[i]);
    }
    printf(" |");
}

//IMPRIME LA PILA EN FORMA TOP TO BOTTOM
void printPila(stack < vector < char > > p){
    while(!p.empty()){
        printEstado(p.top());
        p.pop();
    }
}

//IMPRIME LA PILA EN FORMA BOTTOM TO TOP
void printPilaRecursivo(stack < vector < char > > p){
    if(!p.empty()){
        vector < char > temp = p.top();
        p.pop();
        printPilaRecursivo(p);
        printEstado(temp);
        printf("\n");
    }
}


/*
EL METODO BUSQUEDA RECIBE COMO PARAMETOS, ESTADO INICIAR, FINAL Y LA PROFUNDIDAD
EN SEGUIDA ITERA LA COLA Y GENERA LAS RUTAS EN  BASE AL ULTIMO HIJO ALMACENADO
CUANDO SE ENCUENTRA EL ESTADO Y RETORNA LA RUTA
EVALUA SI LA RANA ESTA EN EL LADO IZQWUIERDO O DERECHO
*/
bool busqueda(vector <char> est_a, int profundidad){
    pila.push(est_a);
    if(est_a == est_final){
        return true;
    }
    if(profundidad < prof_max){
        for(int i=0; i <est_a.size();i++){
            if(est_a[i] == 'I'){
                if(evaluaMovimiento(est_a,i,1))
                    if(busqueda(cambiaEspacioRana(est_a,i,i+1),profundidad+1))
                        return true;
                if(evaluaMovimiento(est_a,i,2))
                    if(busqueda(cambiaEspacioRana(est_a,i,i+2),profundidad+1))
                        return true;
            }
            else if(est_a[i] == 'D'){
                if(evaluaMovimiento(est_a,i,-1))
                    if(busqueda(cambiaEspacioRana(est_a,i,i-1),profundidad+1))
                        return true;
                if(evaluaMovimiento(est_a,i,-2) )
                    if(busqueda(cambiaEspacioRana(est_a,i,i-2),profundidad+1))
                        return true;
            }
        }
    }
    pila.pop();
    return false;
}

int main(){
    //SE SOLICITA EL NUMERO DE RANAS POR LADO
    printf("Numero de ranas por lado: ");
    scanf("%d", &n_ranas);

    //SE HACE UNA VALIDACION PARA LA PROFUNDIDAD
    if(n_ranas > 9)
        prof_max = pow(10,9);
    else
        prof_max = pow(n_ranas,n_ranas);

    printf("\nProfundidad maxima :  %d",prof_max);

    //SE INICIALIZAN LAS RANAS
    iniciaRanas();
    if(busqueda(est_inicial,0)){
        printf("\n--> RUTA <---\n");
        printPilaRecursivo(pila);
    }
    else{
        printf("NO SE ENCONTRO  \n");
    }
    return 0;
}
