#include <iostream>
#include <stdio.h>
#include <stack>

const int n_nodos = 10;
using namespace std;

//SE DECLARA LA PROFUNDIDAD MAXIMA
int prof_max;


//SE DECLARA LA PILA QUE LAMACENA LA RUTA
stack <int> pila;

// SE INICIALIZAN LOS NODOS
int nodos[10][10] = {
    {0,1,1,0,0,1,0,1,0,0},//1
    {1,0,1,1,0,0,0,0,0,1},//2
    {1,1,0,0,1,1,0,0,0,0},//3
    {0,1,0,0,1,0,0,0,0,0},//4
    {0,0,1,1,0,1,0,0,0,0},//5
    {1,0,1,0,1,0,1,1,0,0},//6
    {0,0,0,0,0,1,0,0,1,0},//7
    {1,0,0,0,0,1,0,0,1,1},//8
    {0,0,0,0,0,0,1,1,0,1},//9
    {0,1,0,0,0,0,0,1,1,0} //10
};

//IMPRIME LA PILA RECURSIVAMENTE
void printRuta(stack < int > ruta){
    if(!ruta.empty()){
        int n = ruta.top();
        ruta.pop();
        printRuta(ruta);
        printf("%d -> ",n);
    }
}

/**
EL METODO DE BUSQUEDA RECIBE EL NODO INICIAL,
EL ESTADO OBJETIVO Y LA PROFUNDIDAD
**/
bool busqueda(int nodo, int estado_obj, int profundidad){
    pila.push(nodo+1);
    if(nodo != estado_obj){
        if(profundidad < prof_max){
            for(int col=0; col<n_nodos; col++){
                if(nodos[nodo][col] == 1){
                    if(busqueda(col,estado_obj,profundidad+1))
                       return true;
                }
            }
        }else{
            pila.pop();
            return false;
        }
    }
    else{
        return true;
    }
    pila.pop();
    return false;
}


int main(){
    /*
    SE DECLARA E INICIALIZA EL ESTADO INICIAL Y FINAL
    POSTERIORMENTE SE CAMBIAN EN EL CICLO
    */
    int estado_inicial = 0;
    int estado_final = 0;
    int estado = 0;
    //SE INICIALIZA LA PROFUNDIDAD MAXIMA
    prof_max = 5;
    //AQUI PONEMOS ESTADO INCIAL Y SERÁ EL FINAL TAMBIÉN
    estado = 3;

    for(int i=0; i<n_nodos; i++){
        estado_inicial = estado + i;
        estado_final = estado + i + 1;

        //SE RESTA EL NUMERO DE NODOS PARA QUE NO SE EXCEDA DE LA CAPACIDAD
        if(estado_final > 10) estado_final = estado_final -  n_nodos;
        if(estado_inicial > 10) estado_inicial = estado_inicial - n_nodos;

        //MUESTA EL CAMINO QUE SE SIGUE
        printf("INICIAL: %d  FINAL: %d \n",estado_inicial,estado_final);

        /*
        SE ENVIA A BUSCAR LA SOLUCIÓN CON -1
        YA QUE LA MATRIZ COMIENZA DE 0
        POR EJEMPLO SI SOLICITO QUE VAYA DE 3 a 3
        EL VALOR DEL NODO 3 EN LA MATRIZ ES 2
        */
        busqueda(estado_inicial-1,estado_final-1,0);

        /*
        SE HACE UN POP AL FINAL DE CADA BUSQUEDA
        YA QUE VUELVE A COMENZAR EN EL MISMO NODO
        Y SALIA REPETIDO*/
        if(i < n_nodos - 1) pila.pop();
        //SE REINICIA LA PROFUNIDAD CADA ITERACION
    }
    //SE IMPRIME LA PILA
    printf("\nRUTA\n");
    printRuta(pila);
    return 0;
}
