#include <iostream>
#include <stdio.h>
#include <vector>
#include <stack>

using namespace std;

///DECLARO PROFUNDIDAD MAXIMA
int prof_max;
///DECLARO MI PILA DE VECTORES PARA GUARDAR LA RUTA
stack < vector < char > > pila;
///DECLARO EL ESTADO FINAL
vector < char > est_f;
///DECLARO EL ESTADO INICIAL
vector < char > est_i;

///DECLARO REGLAS PARA EVALUAR
char reglas [8][4] = {
    //P   M   Z   G
    {'I',' ',' ','I'},//GANJERO POLLO IZQUIERDA  0
    {' ','I',' ','I'},//GANJERO MAIZ IZQUIERDA   1
    {' ',' ','I','I'},//GANJERO ZORRO IZQUIERDA  2
    {' ',' ',' ','I'},//GANJERO IZQUIERDA        3
    {'D',' ',' ','D'},//GANJERO POLLO DERECHA    4
    {' ','D',' ','D'},//GANJERO MAIZ DERECHA     5
    {' ',' ','D','D'},//GANJERO ZORRO DERECHA    6
    {' ',' ',' ','D'} //GANJERO DERECHA          7
};

///IMPRIME EL VECTOR DE ESTADO
void printEstado(vector < char > est){
    for(int i=0; i<4; i++) printf("%c ",est[i]);
    printf("\n");
}

/**
EVALUA EL ESTADO QUE RECIBE Y CHECA SI SON POSIBLES
LAS REGLAS EJ.  [I,I,D,I]  NO PUEDE ESTAR POLLO Y MAIZ SOLOS
**/
bool evaluaEstado(vector < char > est){
    if(est[0] == est[1] && est[0] != est[3]) return false;
    else if(est[0] == est[2] && est[0] != est[3]) return false;
    else if(est[3] != est[0] && est[3] != est[1] && est[3] != est[2]) return false;
    else return true;
}

/**
EVALUA SI ES POSIBLE APLICAR LA REGLA(POS)
CON EL VECTOR DE REGLAS

EJ. NO PUEDES ENVIAR AL GRANJERO A LA DERECHA SI YA
ESTA EN LA DERECHA
**/
bool evaluaRegla(vector < char > estado, int pos){
    for(int i=0; i<4; i++)
        if(reglas[pos][i] != ' ')
            if(estado[i] != reglas[pos][i]) return false;

    return true;
}

///CAMBIA EL ESTADO EN BASE A LA REGLA QUE HAYA SIDO APLICADA
vector<char> cambiaEstado(vector < char > estado, int pos){
    for(int i=0; i<estado.size(); i++){
        if(reglas[pos][i] != ' '){
            if(estado[i] == 'I')
                estado[i] = 'D';
            else if(estado[i] == 'D')
                estado[i] = 'I';
        }
    }
    return estado;
}

///IMPRIME LA PILA "NORMAL" TOP TO BOTTOM
void printPila(stack < vector < char > > p){
    while(!p.empty()){
        printEstado(p.top());
        p.pop();
    }
}

///IMPRIME LA PILA "INVERSAMENTE" BOTTOM TO TOP
void printPilaRecursivo(stack < vector < char > > p){
    if(!p.empty()){
        vector < char > temp = p.top();
        p.pop();
        printPilaRecursivo(p);
        printEstado(temp);
    }
}

/**
EL METODO DE BUSQUEDA ES SIMILAR AL DE LAS CIUDADES
SOLO QUE EN LUGAR DE IR NODO A NODO VA REGLA A REGLA
CON CADA ESTADO
**/
bool busqueda(vector < char > est_a, int profundidad){
    pila.push(est_a);
    printf("METE ");
    printEstado(est_a);
    if(est_a == est_f) return true;
    if(profundidad < prof_max){
        if(evaluaEstado(est_a)){
            for(int col=0; col<8; col++)
                if(evaluaRegla(est_a, col))
                    if(busqueda(cambiaEstado(est_a,col),profundidad+1)) return true;
        }else{
            printf("SACA ");
            printEstado(est_a);
            pila.pop();
            return false;
        }
    }
    else{
        printf("SACA ");
        printEstado(est_a);
        pila.pop();
        return false;
    }
    printf("SACA ");
    printEstado(est_a);
    pila.pop();
    return false;
}

int main()
{
    ///SE INSTANCIA LA PROFUNDIDAD (LA MINIMA ES 7)
    prof_max =7;
    ///SE INSTANCIA EL ESTADO INICIAL (TODOS A LA IZQUIERDA)
    est_i = {'I','I','I','I'};
    ///SE INSTANCIA EL ESTADO FINAL (TODOS A LA DERECHA)
    est_f = {'D','D','D','D'};

    printf("--> PROCEDIMIENTO <--\n");
    if(busqueda(est_i,0)){
        printf("\nP = POLLO\nM = MAIZ\nZ = ZORRO\nG = GRANJERO\n");
        printf("\n--> R U T A <--\n");
        printf("P M Z G\n");
        printPilaRecursivo(pila);
    }else{
        printf("\nNO ENCONTRE LA SOLUCION PRUEBA MAS PROFUNDIDAD\n");
    }
}
