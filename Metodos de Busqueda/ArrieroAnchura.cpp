#include <iostream>
#include <stdio.h>
#include <vector>
#include <queue>

using namespace std;

///DECLARO COLA DONDE SE VAN A GUARDAR LAS RUTAS
queue < vector < vector < char > > > rutas;
///DECLARO ESTADO FINAL
vector < char > est_f;
///DECLARO ESTADO INICIAL
vector < char > est_i;

///DECLARO REGLAS PARA EVALUAR
char reglas [8][4] = {
    //P   M   Z   G
    {'I',' ',' ','I'},//GANJERO POLLO   IZQUIERDA   0
    {' ','I',' ','I'},//GANJERO MAIZ    IZQUIERDA   1
    {' ',' ','I','I'},//GANJERO ZORRO   IZQUIERDA   2
    {' ',' ',' ','I'},//GANJERO         IZQUIERDA   3
    {'D',' ',' ','D'},//GANJERO POLLO   DERECHA     4
    {' ','D',' ','D'},//GANJERO MAIZ    DERECHA     5
    {' ',' ','D','D'},//GANJERO ZORRO   DERECHA     6
    {' ',' ',' ','D'} //GANJERO         DERECHA     7
};

/**
EVALUA EL ESTADO QUE RECIBE Y CHECA SI SON POSIBLES
LAS REGLAS EJ.  [IIDI]  NO PUEDE ESTAR POLLO Y MAIZ SOLOS
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

///IMPRIME EL VECTOR DE ESTADO
void printEstado(vector < char > est){
    for(int i=0; i<4; i++) printf("%c ",est[i]);
}

///IMPRIME EL VECTOR DE VECTORES DE ESTADO (UNA RUTA)
void printRuta(vector < vector < char > > ruta){
    printf("| ");
    for(int i=0; i<ruta.size(); i++){
        printEstado(ruta[i]);
        printf("| ");
    }
    printf("\n");
}

/**
EL METODO DE BUSCA RETORNA UNA RUTA EN CADO DE QUE LA ENCUENTRE
SE OCUPA LA COLA ANTERIORMENTE DECLARADA PARA IR ALMANCENADO
LAS RUTAS Y SUS POSIBLES HIJOS
**/
vector < vector < char > > busqueda(vector < char > estado_ini, vector < char > estado_fin){
    vector < vector < char > > ruta0 = {estado_ini};
    rutas.push(ruta0);
    while(!rutas.empty()){
        vector < vector < char > > temp_ruta = rutas.front();
        vector < char > temp_estado = temp_ruta[temp_ruta.size()-1];
        rutas.pop();
        if(temp_estado == estado_fin){
            return temp_ruta;
        }
        else{
            for(int i=0; i<8; i++){
                if(evaluaRegla(temp_estado,i)){
                    vector < char > temp_2 = cambiaEstado(temp_estado,i);
                    if(evaluaEstado(temp_2)){
                        vector < vector < char > > temp_r_f = temp_ruta;
                        temp_r_f.push_back(temp_2);
                        rutas.push(temp_r_f);
                        printRuta(temp_r_f);
                    }
                }
            }
        }
    }
}

int main(){
    ///SE INICIALIZA EL ESTADO INICIAL
    est_i = {'I','I','I','I'};
    ///SE INICIALIZA EL ESTADO FINAL
    est_f = {'D','D','D','D'};

    printf("--> PROCEDIMIENTO <--\n");
    vector < vector < char > >  ruta = busqueda(est_i, est_f);
    if(ruta.size()>0){
        printf("\n--> RUTA <---\n");
        printf("P = POLLO\nM = MAIZ\nZ = ZORRO\nG = GRANJERO\n");
        printf("  ");
        for(int i=0; i<ruta.size(); i++){
            printEstado({'P','M','Z','G'});
            printf("   ");
        }
        printf("\n");
        printRuta(ruta);
    }
}
