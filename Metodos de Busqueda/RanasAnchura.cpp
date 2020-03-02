#include <iostream>
#include <vector>
#include <stdio.h>
#include <queue>
#include <math.h>
using namespace std;

//SE DECLARA EL ESTADO INICIAL
vector < char > est_inicial;
//SE DECLARA EL ESTADO FINAL
vector < char > est_final;
//SE LA COLA QUE ALMACENARA LAS RUTAS
queue < vector < vector < char > > > rutas;


/*
ESTE METODO SE ENCARGA DE INICIALIZAR
LOS ESTADOS SEGUN EL NUMERO DE RANAS

III DDD INICIAL
DDD III FINAL
*/
void iniciaRanas(int n_ranas){
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

/*
ESTE METODO CAMBIA LA LA RANA POR EL ESPACIO
RECIBE EL ESTADO, LA POSICION DE LA RANA Y LA DEL ESPACIO
*/
vector <char> cambiaEspacioRana(vector< char > est,int x,int y){
	int aux= est[x];
	est[x]=est[y];
	est[y]=aux;
	return est;
}

/*
ESTE METODO SI ES POSIBLE REALIZAR
EL MOVIMIENTO SUGERIDO POR LA BUSQUEDA
*/
bool evaluaMovimiento(vector <char> estado,int pos_a,int salto){
    int pos_abs = pos_a + salto;
    if(pos_abs < 0 || pos_abs >= estado.size())
        return false;
	else if(estado[pos_abs]==' ')
		return true;
	else
		return false;
}

/*
IMPRIME EL ESTADO QUE SE LE ENVIE
*/
void printEstado( vector < char > est){
    printf("|");
    for(int i=0; i<est.size(); i++){
        printf(" %c",est[i]);
    }
    printf(" |");
}

/*
IMPRIME LA RUTA
*/
void printRuta(vector < vector < char > > ruta){

    for(int i=0; i<ruta.size(); i++){
        printEstado(ruta[i]);
        printf("\n");
    }
    printf("\n");
}

/*
EL METODO RECIBE UN ESTADO INICIAL Y FINAL
VA ENCOLANDO LOS HIJOS DE CADA POSIBLE MOVIMIENTO
Y LOS EVALUA POSTERIORMENTE
*/
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
            for(int i=0; i<temp_estado.size(); i++){
                if(temp_estado[i] == 'I'){
                    if(evaluaMovimiento(temp_estado,i,1)){
                        vector < char > temp_2 = cambiaEspacioRana(temp_estado,i,i+1);
                        vector < vector < char > > temp_r_f = temp_ruta;
                        temp_r_f.push_back(temp_2);
                        rutas.push(temp_r_f);
                        //printRuta(temp_r_f);
                    }
                    if(evaluaMovimiento(temp_estado,i,2)){
                        vector < char > temp_2 = cambiaEspacioRana(temp_estado,i,i+2);
                        vector < vector < char > > temp_r_f = temp_ruta;
                        temp_r_f.push_back(temp_2);
                        rutas.push(temp_r_f);
                        //printRuta(temp_r_f);
                    }
                }
                else if(temp_estado[i] == 'D'){
                    if(evaluaMovimiento(temp_estado,i,-1)){
                        vector < char > temp_2 = cambiaEspacioRana(temp_estado,i,i-1);
                        vector < vector < char > > temp_r_f = temp_ruta;
                        temp_r_f.push_back(temp_2);
                        rutas.push(temp_r_f);
                        //printRuta(temp_r_f);
                    }
                    if(evaluaMovimiento(temp_estado,i,-2)){
                        vector < char > temp_2 = cambiaEspacioRana(temp_estado,i,i-2);
                        vector < vector < char > > temp_r_f = temp_ruta;
                        temp_r_f.push_back(temp_2);
                        rutas.push(temp_r_f);
                        //printRuta(temp_r_f);
                    }
                }
            }
        }
    }
}


int main(){
    //SE DECLARA EL NUMERO DE RANAS
    int n_ranas;
    printf("Numero de ranas por lado: ");
    //SE INGRESA EL NUMERO DE RANAS
    scanf("%d", &n_ranas);
    //SE INICIALIZAN LAS RANAS
    iniciaRanas(n_ranas);
    //SE ENVIA A BUSCAR CON EL ESTADO INICIAL Y FINAL Y RETORNA LA RUTA
    vector < vector < char > >  ruta = busqueda(est_inicial, est_final);

    if(ruta.size()>0){
        printf("\n--> RUTA <---\n");
        printRuta(ruta);
    }
    return 0;
}
