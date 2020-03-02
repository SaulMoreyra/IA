#include <iostream>
#include <stdio.h>
#include <queue>
#include <vector>

using namespace std;
//SE DECLARA LA CANTIDAD DE NODOS
const int n_nodos = 10;
// LA COLA QUE SERVIRÁ PARA ALMACENAR LAS RUTAS
queue < vector < int > > rutas;

// SE DECLARA LA PROFUNDIDAD
int prof_max;

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

//IMPRIME EL VECTOR DE VECTORES DE ESTADO (UNA RUTA)
void printRuta(vector < int > ruta){
    printf("| ");
    for(int i=0; i<ruta.size(); i++){
        printf("%d | ",ruta[i]+1);
    }
    printf("\n");
}

/*
EL METODO BISQUEDA RECIBE COMO PARAMETOS, ESTADO INICIAR, FINAL Y LA PROFUNDIDAD
EN SEGUIDA ITERA LA COLA Y GENERA LAS RUTAS EN  BASE AL ULTIMO HIJO ALMACENADO
CUANDO SE ENCUENTRA EL ESTADO Y RETORNA LA RUTA
*/
vector < int > busqueda(int estado_ini, int estado_fin, int profundidad){
    vector < int > ruta0 = {estado_ini};
    rutas.push(ruta0);
    while(!rutas.empty()){
        vector < int > temp_ruta = rutas.front();
        int temp_estado = temp_ruta[temp_ruta.size()-1];
        rutas.pop();
        if(temp_estado == estado_fin){
            return temp_ruta;
        }
        else{
            for(int col=0; col<n_nodos; col++){
                if(nodos[temp_estado][col] == 1){
                    vector < int > temp_r_f = temp_ruta;
                    temp_r_f.push_back(col);
                    rutas.push(temp_r_f);
                }
            }
        }
    }
}

// ESTE METODO MUEVE A OTRO VECTOR LA RUTA
vector <int> move_ruta(vector <int> ruta_p, vector <int> ruta_f){
    for(int i=0; i<ruta_p.size(); i++){
        ruta_f.push_back(ruta_p[i]);
    }
    return ruta_f;
}

//ESTE METODO VACIA LA COLA DE RUTAS
queue <vector <int>> vaciaRutas(queue <vector <int>> r){
    while(!r.empty()) r.pop();
    return r;
}

int main()
{
    // SE DECLARA LA RUTA QUE FUNGUIRA COMO TEMPORAL PARA LAS RUTAS PARCIALES
    vector < int > ruta_temp;
    // SE DECLARA ESTADO INICIAL Y FINAL
    int estado_inicial, estado_final;
    // SE INICIALIZA EL ESTADO DONDE VA A ACABAR Y TERMINAR
    int estado = 3;

    printf("PROCEDIMIENTO\n");
    for(int i=0; i<n_nodos; i++){
        estado_inicial = estado + i;
        estado_final = estado + i + 1;

        if(estado_final > 10) estado_final = estado_final -  n_nodos;
        if(estado_inicial > 10) estado_inicial = estado_inicial - n_nodos;
        printf("INICIAL: %d  FINAL: %d \n",estado_inicial,estado_final);
        vector < int > de_a = busqueda(estado_inicial-1,estado_final-1,0);
        printRuta(de_a);
        ruta_temp = move_ruta(de_a,ruta_temp);
        if(i<n_nodos-1)
            ruta_temp.pop_back();

        rutas = vaciaRutas(rutas);
    }

    printf("\nRUTA COMPLETA\n");
    printRuta(ruta_temp);
    return 0;
}
