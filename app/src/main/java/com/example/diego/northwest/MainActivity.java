package com.example.diego.northwest;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //<--Dialogo-->
    public TextView dialogo;
    Dialog dialog;
    //
    int disc=0,demc=0;
    int disp[];
    int dem[];
    int mat[][],n,m;
    EditText et1,et2,et3,et4;
    TextView tv1,tv2;
    Button bt1,bt2,bt3,mosb,solb,solbb;
    int yeasf=0,yeasc=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1=(EditText)findViewById(R.id.et1);
        et2=(EditText)findViewById(R.id.et2);
        et3=(EditText)findViewById(R.id.et3);
        tv1=(TextView)findViewById(R.id.tv1);
        bt1=(Button)findViewById(R.id.bt1);
        bt2=(Button)findViewById(R.id.bt2);
        et4=(EditText)findViewById(R.id.et4);
        tv2=(TextView)findViewById(R.id.tv2);
        bt3=(Button)findViewById(R.id.bt3);
        mosb=(Button)findViewById(R.id.mosb);
        solb=(Button)findViewById(R.id.solb);
        solbb=(Button)findViewById(R.id.solbb);
        dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialogo=(TextView)dialog.findViewById(R.id.dialog);

    }
    public void CreateMat(View view)
    {
        try{
            n=Integer.parseInt(et1.getText()+"");
            m=Integer.parseInt(et2.getText()+"");
            mat=new int[n][m];
            disp=new int[n];
            dem=new int[m];
            bt1.setEnabled(false);
            et1.setEnabled(false);
            et2.setEnabled(false);
            et3.setEnabled(true);
            bt2.setEnabled(true);
            tv1.setText("Posicion [0][0]");
        }
        catch(Exception e)
        {
            Log.e("Error",""+e.getMessage());
        }
    }
    public void setInput(View view)
    {
        try {
            if (yeasf < n && yeasc < m)
            {
                int x=Integer.parseInt(et3.getText()+"");
                mat[yeasf][yeasc]=x;
                yeasc++;
                if(yeasc==m)
                {
                    yeasc=0;
                    yeasf++;
                }
                tv1.setText("Posicion ["+yeasf+"]["+yeasc+"]");
                et3.setText("");
            }
            else
            {
                bt2.setEnabled(false);
                et3.setEnabled(false);
                Toast.makeText(this, "Matriz Llena, Presione en Continuar", Toast.LENGTH_SHORT).show();
                et4.setEnabled(true);
                bt3.setEnabled(true);
            }

        }
        catch(Exception e)
        {
            Log.e("Error",""+e.getMessage());
        }
    }
    public void setOutsiders(View view)
    {
        try{
            if(disc<(n))
            {
                if(disc==0)
                    bt3.setText("Ingrese Dato");
                Log.e("DISC:","Disponibilidad: "+disc);
                int x=Integer.parseInt(et4.getText()+"");
                disp[disc]=x;
                disc++;
                tv2.setText("Disponibilidad Fila: "+(disc-1));
                et4.setText("");
            }
            else if(demc<(m))
            {
                Log.e("DEMC:","Demanda: "+demc);
                int x=Integer.parseInt(et4.getText()+"");
                dem[demc]=x;
                demc++;
                tv2.setText("Demanda Columna: "+(demc-1));
                et4.setText("");
            }
            else
            {
                bt3.setEnabled(false);
                et4.setEnabled(false);
                tv2.setEnabled(false);
                Toast.makeText(this, "Todo Listo", Toast.LENGTH_SHORT).show();
                mosb.setEnabled(true);
                solb.setEnabled(true);
                solbb.setEnabled(true);
            }
        }
        catch(Exception e)
        {
            Log.e("Error",e.getMessage()+"");
        }
    }
    public void showArray(View view)
    {
            String aux = "";

            for(int i = 0;i<n;i++)
            {
                aux=aux+"\n";
                for (int j = 0; j < m; j++)
                {
                    aux = aux + mat[i][j] +"\t\t\t";
                }
                aux=aux+"|"+disp[i];
            }
            aux=aux+"\n";
            for(int i=0;i<m;i++)
            {
                aux = aux +"___";
            }
            aux=aux+"\n";
            for(int i=0;i<m;i++)
            {
                aux = aux + dem[i] +"\t\t\t";
            }
            dialogo.setTextSize(20);
            dialogo.setText(aux);
            dialog.show();

    }
    public void max(View view) {
        String aux = "";
        int i = 0, j = 0;
        int sol[][] = new int[n][m];
        int copiadem[] = new int[m];
        pasarExtremos(dem, copiadem);
        int copiadisp[] = new int[n];
        pasarExtremos(disp, copiadisp);
        //<--Encontrando primera solucion factibler-->
        while (true) {
            if (copiadisp[i] < copiadem[j]) {
                sol[i][j] = copiadisp[i];
                copiadisp[i] -= sol[i][j];
                copiadem[j] -= sol[i][j];
                i++;
            } else if (copiadisp[i] > copiadem[j]) {
                sol[i][j] = copiadem[j];
                copiadisp[i] -= sol[i][j];
                copiadem[j] -= sol[i][j];
                j++;
            } else if (copiadisp[i] == copiadem[j]) {
                sol[i][j] = copiadem[j];
                break;
            }
        }
        //<--Sol Factible Encontrada-->
        //<--Asignando valores de las posiciones de las soluciones-->
        int Zeta[][] = new int[n][m];
        assignValPos(sol, Zeta);
        //<--Hecha la matriz con los valores de las posiciones de soluciones-->
        //<--Hallando el minimo valor-->
        int min = getMinAsignados(sol,Zeta);
        Log.e("MinimoFil: ",""+min);
        //<--Encontrado el minimo valor-->
        //<--Calculos fila-col para hallar matriz completada
        int filstinker[] = new int[n];
        int colstinker[] = new int[m];
        filstinker[0] = min;
        //<--Encontradas filas y cols-->
        aux="";
        getFilsCols(filstinker, colstinker, Zeta);
        for(int k=0;k<filstinker.length;k++)
        {
            aux=aux+filstinker[k]+"\t";
        }
        Log.e("Filas:",""+aux);
        aux="";
        for(int k=0;k<colstinker.length;k++)
        {
            aux=aux+colstinker[k]+"\t";
        }
        Log.e("Cols:",""+aux);
        //<--Calculo de la matriz completa-->
        completeMatrix(Zeta, filstinker, colstinker);
        //<--Matriz Completada-->
        //<--Comparacion para pararla-->
        int condicional[][] = new int[n][m];
        MatCond(condicional, Zeta);
        //<--Se ha comparado-->

        /*String aux="";
        for(int k=0;k<n;k++)
        {
            aux=aux+"\n";
            for(int l=0;l<m;l++)
            {
                aux=aux+condicional[k][l]+"\t";
            }
        }
        Log.e("BS",""+aux);*/
        /*<--Revisar si se llego al optimo-->*/

       while(!isMaximized(condicional)) {
        int posi = 0, posj = 0;
        min = getMin(condicional);
        for (int k = 0; k < n; k++) {
            for (int l = 0; l < m; l++) {
                if (condicional[k][l] == min) {
                    posi = k;
                    posj = l;
                    break;
                }
            }
        }
        Log.e("Posicion posible val: ", "" + posi + "," + posj);

        //Hacer una matriz de objetos para facilitar el trabajo
        Posicion matel[][] = new Posicion[n][m];
        pasarMatel(matel, sol);
           matel[posi][posj].setPivote(true);
           Log.e("Pivote ",""+matel[posi][posj].isPivote()+" Posicion: "+posi+","+posj);
        //Finalizada la asginacion
        //Preparación de variables
        String logic = "Fila";
        int dini = posi;
        int dinj = posj;
        int backi = 0, backj = 0;
        int tracker = 0;
        boolean circuito = false, found, StageOne = true, StageTwo = false;
        //Variables hechas
        while (!circuito) {
            found = false;
            if (StageOne) {
                dini = posi;
                dinj = posj;
                tracker = 0;
                if (logic.equals("Fila") && !found) {
                    for (int k = 0; k < m; k++) {
                        if (matel[dini][k].getValor() > 0 && !matel[dini][k].isUsado() && !matel[dini][k].isTrash() && k!=dinj) {
                            Log.e("Analizando:",""+matel[dini][k].getValor()+" Posicion: "+dini+","+k);
                            dinj = k;
                            matel[dini][k].setUsado(true);
                            logic = "Columna";
                            found = true;
                            matel[dini][k].setCrossedNeg(true);
                            tracker++;
                            StageTwo = true;
                            StageOne = false;
                            break;
                        }
                    }
                } else if (logic.equals("Columna") && !found) {
                    for (int k = 0; k < n; k++) {
                        if (matel[k][dinj].getValor() > 0 && !matel[k][dinj].isUsado() && !matel[k][dinj].isTrash() && k!=dini) {
                            Log.e("Analizando:",""+matel[k][dinj].getValor()+" Posicion: "+k+","+dinj);
                            matel[k][dinj].setUsado(true);
                            dini = k;
                            logic = "Columna";
                            found = true;
                            matel[dini][k].setCrossedNeg(true);
                            tracker++;
                            StageTwo = true;
                            StageOne = false;
                            break;
                        }
                    }
                }
                if (!found) {
                    if (logic.equals("Fila")) {
                        logic = "Columna";
                        dini = posi;
                        dinj = posj;
                    }
                    else
                    {
                        logic = "Fila";
                        dini = posi;
                        dinj = posj;
                    }

                }
            } else if (StageTwo) {
                found = false;
                boolean filyes=false,colyes=false;
                if (logic.equals("Fila") && !found) {
                    for (int k = 0; k < m; k++) {
                        if (matel[dini][k].getValor() > 0 && !matel[dini][k].isUsado() && !matel[dini][k].isTrash() && k!=dinj && !found) {
                            Log.e("Analizando:",""+matel[dini][k].getValor()+" Posicion: "+dini+","+k);
                            dinj = k;
                            matel[dini][k].setUsado(true);
                            Log.e("Tracker",""+tracker);
                            logic = "Columna";
                            found = true;
                            if (tracker % 2 == 0)
                            {
                                Log.e("Marca:","Negativo");
                                matel[dini][k].setCrossedNeg(true);
                            }
                            else
                            {
                                Log.e("Marca:","Positivo");
                                matel[dini][k].setCrossedPos(true);
                            }
                            tracker++;
                            break;
                        }
                    }
                } else if (logic.equals("Columna") && !found) {
                    for (int k = 0; k < n; k++) {
                        if (matel[k][dinj].getValor() > 0 && !matel[k][dinj].isUsado() && !matel[k][dinj].isTrash() && k!=dini && !found) {
                            Log.e("Analizando:",""+matel[k][dinj].getValor()+" Posicion: "+k+","+dinj);
                            matel[k][dinj].setUsado(true);
                            dini = k;
                            logic = "Fila";
                            found = true;
                            Log.e("Tracker",""+tracker);
                            if (tracker % 2 == 0)
                            {
                                Log.e("Marca:","Negativo");
                                matel[k][dinj].setCrossedNeg(true);
                            }
                            else
                            {
                                Log.e("Marca:","Positivo");
                                matel[k][dinj].setCrossedPos(true);
                            }
                            tracker++;
                            break;
                        }
                    }
                }
                for(int a=0;a<n;a++)
                {
                    if(matel[a][posj].isCrossedNeg())
                    {
                        filyes=true;
                        Log.e("Anulador Col",""+matel[a][posj].getValor()+" Posicion "+a+","+posj);
                        break;
                    }
                }
                for(int a=0;a<m;a++)
                {
                    if(matel[posi][a].isCrossedNeg())
                    {
                        colyes=true;
                        Log.e("Anulador Fil",""+matel[posi][a].getValor()+" Posicion "+posi+","+a);
                        break;
                    }
                }
                if(filyes && colyes)
                {
                    circuito=true;
                    Log.e("Completado: ",""+circuito);
                    break;
                }
                if (!found) {
                    StageOne = true;
                    StageTwo = false;
                    logic = "Fila";
                    for(int k=0;k<n;k++)
                    {
                        for(int l=0;l<m;l++)
                        {
                            matel[k][l].setCrossedNeg(false);
                            matel[k][l].setCrossedPos(false);
                        }
                    }
                    matel[dini][dinj].setTrash(true);
                    Log.e("Trash: ",""+matel[dini][dinj].getValor()+" Posicion: "+dini+","+dinj);
                }
            }
        }
        //Encontrar el menor de los marcados
        aux = "";
        int first = 0, ToRest = 0;
        for (int k = 0; k < n; k++) {
            for (int l = 0; l < m; l++) {
                if (matel[k][l].isUsado() && !matel[k][l].isTrash() && first == 0 && matel[k][l].isCrossedNeg()) {
                    aux = aux + matel[k][l].getValor() + ",";
                    ToRest = matel[k][l].getValor();
                    first++;
                } else if (matel[k][l].isUsado() && !matel[k][l].isTrash() && matel[k][l].isCrossedNeg()) {
                    aux = aux + matel[k][l].getValor() + ",";
                    if (matel[k][l].getValor() < ToRest)
                        ToRest = matel[k][l].getValor();
                }
            }
        }
        Log.e("MenorMarcado:", "" + ToRest);
        //Encontrado el menor de los marcados
        //Calcular la nueva matriz
        aux = "";
        for (int k = 0; k < n; k++) {
            aux = aux + "\n";
            for (int l = 0; l < m; l++) {
                if (matel[k][l].isUsado() && !matel[k][l].isTrash() && matel[k][l].isCrossedNeg()) {
                    int val=matel[k][l].getValor();
                    val-=ToRest;
                    matel[k][l].setValor(val);
                } else if (matel[k][l].isUsado() && !matel[k][l].isTrash() && matel[k][l].isCrossedPos()) {
                    int val=matel[k][l].getValor();
                    val+=ToRest;
                    matel[k][l].setValor(val);
                }
                if (k == posi && l == posj)
                    matel[k][l].setValor(ToRest);
                aux = aux + matel[k][l].getValor() + "\t";
            }
        }
        Log.e("Hecho? ", "" + aux);
        //<--Asignando valores de las posiciones de las soluciones-->
        //<--Transformando a la nueva solucion de la matriz de posicion-->
        volverDeMatel(sol, matel);
        //<--Solucion transformada-->
        for (int k = 0; k < n; k++) {
            for (int l = 0; l < m; l++) {
                Zeta[k][l] = 0;
            }
        }
        assignValPos(sol, Zeta);
        //<--Hecha la matriz con los valores de las posiciones de soluciones-->
        //<--Hallando el minimo valor-->
           min = getMinAsignados(sol,Zeta);
           Log.e("MinimoFil: ",""+min);
        //<--Encontrado el minimo valor-->
        //<--Calculos fila-col para hallar matriz completada
        for (int k = 0; k < filstinker.length; k++) {
            filstinker[k] = 0;
        }
        for (int k = 0; k < colstinker.length; k++) {
            colstinker[k] = 0;
        }
           filstinker[0] = min;
        //<--Encontradas filas y cols-->
        aux="";
        getFilsCols(filstinker, colstinker, Zeta);
        for(int k=0;k<filstinker.length;k++)
        {
            aux=aux+filstinker[k]+"\t";
        }
        Log.e("Filas:",""+aux);
        aux="";
        for(int k=0;k<colstinker.length;k++)
        {
            aux=aux+colstinker[k]+"\t";
        }
        Log.e("Cols:",""+aux);
        //<--Calculo de la matriz completa-->
        completeMatrix(Zeta, filstinker, colstinker);
        //<--Matriz Completada-->
        aux="";
        //<--Comparacion para pararla-->
        MatCond(condicional, Zeta);
        //<--Matriz lista para ser analizada-->
   }
        //<--Una vez llegado al optimo imprimir la solucion con la funcion maximizada-->
            aux = "";
            int funcion = 0;
            for (int k = 0; k < n; k++) {
                aux = aux + "\n";
                for (int l = 0; l < m; l++) {
                    aux = aux + sol[k][l] + "\t\t\t";
                    funcion += (sol[k][l] * mat[k][l]);
                }
            }
            dialogo.setTextSize(20);
            dialogo.setText(aux);
            dialog.show();
            Toast.makeText(this, "Maximo: " + funcion, Toast.LENGTH_SHORT).show();
    }

    public void min(View view) {
        String aux = "";
        int i = 0, j = 0;
        int sol[][] = new int[n][m];
        int copiadem[] = new int[m];
        pasarExtremos(dem, copiadem);
        int copiadisp[] = new int[n];
        pasarExtremos(disp, copiadisp);
        //<--Encontrando primera solucion factibler-->
        while (true) {
            if (copiadisp[i] < copiadem[j]) {
                sol[i][j] = copiadisp[i];
                copiadisp[i] -= sol[i][j];
                copiadem[j] -= sol[i][j];
                i++;
            } else if (copiadisp[i] > copiadem[j]) {
                sol[i][j] = copiadem[j];
                copiadisp[i] -= sol[i][j];
                copiadem[j] -= sol[i][j];
                j++;
            } else if (copiadisp[i] == copiadem[j]) {
                sol[i][j] = copiadem[j];
                break;
            }
        }
        //<--Sol Factible Encontrada-->
        //<--Asignando valores de las posiciones de las soluciones-->
        int Zeta[][] = new int[n][m];
        assignValPos(sol, Zeta);
        //<--Hecha la matriz con los valores de las posiciones de soluciones-->
        //<--Hallando el minimo valor-->
        int min = getMinAsignados(sol,Zeta);
        Log.e("MinimoFil: ",""+min);
        //<--Encontrado el minimo valor-->
        //<--Calculos fila-col para hallar matriz completada
        int filstinker[] = new int[n];
        int colstinker[] = new int[m];
        filstinker[0] = min;
        //<--Encontradas filas y cols-->
        aux="";
        getFilsCols(filstinker, colstinker, Zeta);
        for(int k=0;k<filstinker.length;k++)
        {
            aux=aux+filstinker[k]+"\t";
        }
        Log.e("Filas:",""+aux);
        aux="";
        for(int k=0;k<colstinker.length;k++)
        {
            aux=aux+colstinker[k]+"\t";
        }
        Log.e("Cols:",""+aux);
        //<--Calculo de la matriz completa-->
        completeMatrix(Zeta, filstinker, colstinker);
        //<--Matriz Completada-->
        //<--Comparacion para pararla-->
        int condicional[][] = new int[n][m];
        MatCond(condicional, Zeta);
        //<--Se ha comparado-->

        /*String aux="";
        for(int k=0;k<n;k++)
        {
            aux=aux+"\n";
            for(int l=0;l<m;l++)
            {
                aux=aux+condicional[k][l]+"\t";
            }
        }
        Log.e("BS",""+aux);*/
        /*<--Revisar si se llego al optimo-->*/

        while(!isMinimized(condicional)) {
            int posi = 0, posj = 0;
            min = getMax(condicional);
            for (int k = 0; k < n; k++) {
                for (int l = 0; l < m; l++) {
                    if (condicional[k][l] == min) {
                        posi = k;
                        posj = l;
                        break;
                    }
                }
            }
            Log.e("Posicion posible val: ", "" + posi + "," + posj);

            //Hacer una matriz de objetos para facilitar el trabajo
            Posicion matel[][] = new Posicion[n][m];
            pasarMatel(matel, sol);
            matel[posi][posj].setPivote(true);
            Log.e("Pivote ",""+matel[posi][posj].isPivote()+" Posicion: "+posi+","+posj);
            //Finalizada la asginacion
            //Preparación de variables
            String logic = "Fila";
            int dini = posi;
            int dinj = posj;
            int backi = 0, backj = 0;
            int tracker = 0;
            boolean circuito = false, found, StageOne = true, StageTwo = false;
            //Variables hechas
            while (!circuito) {
                found = false;
                if (StageOne) {
                    dini = posi;
                    dinj = posj;
                    tracker = 0;
                    if (logic.equals("Fila") && !found) {
                        for (int k = 0; k < m; k++) {
                            if (matel[dini][k].getValor() > 0 && !matel[dini][k].isUsado() && !matel[dini][k].isTrash() && k!=dinj) {
                                Log.e("Analizando:",""+matel[dini][k].getValor()+" Posicion: "+dini+","+k);
                                dinj = k;
                                matel[dini][k].setUsado(true);
                                logic = "Columna";
                                found = true;
                                matel[dini][k].setCrossedNeg(true);
                                tracker++;
                                StageTwo = true;
                                StageOne = false;
                                break;
                            }
                        }
                    } else if (logic.equals("Columna") && !found) {
                        for (int k = 0; k < n; k++) {
                            if (matel[k][dinj].getValor() > 0 && !matel[k][dinj].isUsado() && !matel[k][dinj].isTrash() && k!=dini) {
                                Log.e("Analizando:",""+matel[k][dinj].getValor()+" Posicion: "+k+","+dinj);
                                matel[k][dinj].setUsado(true);
                                dini = k;
                                logic = "Columna";
                                found = true;
                                matel[dini][k].setCrossedNeg(true);
                                tracker++;
                                StageTwo = true;
                                StageOne = false;
                                break;
                            }
                        }
                    }
                    if (!found) {
                        if (logic.equals("Fila")) {
                            logic = "Columna";
                            dini = posi;
                            dinj = posj;
                        }
                        else
                        {
                            logic = "Fila";
                            dini = posi;
                            dinj = posj;
                        }

                    }
                } else if (StageTwo) {
                    found = false;
                    boolean filyes=false,colyes=false;
                    if (logic.equals("Fila") && !found) {
                        for (int k = 0; k < m; k++) {
                            if (matel[dini][k].getValor() > 0 && !matel[dini][k].isUsado() && !matel[dini][k].isTrash() && k!=dinj && !found) {
                                Log.e("Analizando:",""+matel[dini][k].getValor()+" Posicion: "+dini+","+k);
                                dinj = k;
                                matel[dini][k].setUsado(true);
                                Log.e("Tracker",""+tracker);
                                logic = "Columna";
                                found = true;
                                if (tracker % 2 == 0)
                                {
                                    Log.e("Marca:","Negativo");
                                    matel[dini][k].setCrossedNeg(true);
                                }
                                else
                                {
                                    Log.e("Marca:","Positivo");
                                    matel[dini][k].setCrossedPos(true);
                                }
                                tracker++;
                                break;
                            }
                        }
                    } else if (logic.equals("Columna") && !found) {
                        for (int k = 0; k < n; k++) {
                            if (matel[k][dinj].getValor() > 0 && !matel[k][dinj].isUsado() && !matel[k][dinj].isTrash() && k!=dini && !found) {
                                Log.e("Analizando:",""+matel[k][dinj].getValor()+" Posicion: "+k+","+dinj);
                                matel[k][dinj].setUsado(true);
                                dini = k;
                                logic = "Fila";
                                found = true;
                                Log.e("Tracker",""+tracker);
                                if (tracker % 2 == 0)
                                {
                                    Log.e("Marca:","Negativo");
                                    matel[k][dinj].setCrossedNeg(true);
                                }
                                else
                                {
                                    Log.e("Marca:","Positivo");
                                    matel[k][dinj].setCrossedPos(true);
                                }
                                tracker++;
                                break;
                            }
                        }
                    }
                    for(int a=0;a<n;a++)
                    {
                        if(matel[a][posj].isCrossedNeg())
                        {
                            filyes=true;
                            Log.e("Anulador Col",""+matel[a][posj].getValor()+" Posicion "+a+","+posj);
                            break;
                        }
                    }
                    for(int a=0;a<m;a++)
                    {
                        if(matel[posi][a].isCrossedNeg())
                        {
                            colyes=true;
                            Log.e("Anulador Fil",""+matel[posi][a].getValor()+" Posicion "+posi+","+a);
                            break;
                        }
                    }
                    if(filyes && colyes)
                    {
                        circuito=true;
                        Log.e("Completado: ",""+circuito);
                        break;
                    }
                    if (!found) {
                        StageOne = true;
                        StageTwo = false;
                        logic = "Fila";
                        for(int k=0;k<n;k++)
                        {
                            for(int l=0;l<m;l++)
                            {
                                matel[k][l].setCrossedNeg(false);
                                matel[k][l].setCrossedPos(false);
                            }
                        }
                        matel[dini][dinj].setTrash(true);
                        Log.e("Trash: ",""+matel[dini][dinj].getValor()+" Posicion: "+dini+","+dinj);
                    }
                }
            }
            //Encontrar el menor de los marcados
            aux = "";
            int first = 0, ToRest = 0;
            for (int k = 0; k < n; k++) {
                for (int l = 0; l < m; l++) {
                    if (matel[k][l].isUsado() && !matel[k][l].isTrash() && first == 0 && matel[k][l].isCrossedNeg()) {
                        aux = aux + matel[k][l].getValor() + ",";
                        ToRest = matel[k][l].getValor();
                        first++;
                    } else if (matel[k][l].isUsado() && !matel[k][l].isTrash() && matel[k][l].isCrossedNeg()) {
                        aux = aux + matel[k][l].getValor() + ",";
                        if (matel[k][l].getValor() < ToRest)
                            ToRest = matel[k][l].getValor();
                    }
                }
            }
            Log.e("MenorMarcado:", "" + ToRest);
            //Encontrado el menor de los marcados
            //Calcular la nueva matriz
            aux = "";
            for (int k = 0; k < n; k++) {
                aux = aux + "\n";
                for (int l = 0; l < m; l++) {
                    if (matel[k][l].isUsado() && !matel[k][l].isTrash() && matel[k][l].isCrossedNeg()) {
                        int val=matel[k][l].getValor();
                        val-=ToRest;
                        matel[k][l].setValor(val);
                    } else if (matel[k][l].isUsado() && !matel[k][l].isTrash() && matel[k][l].isCrossedPos()) {
                        int val=matel[k][l].getValor();
                        val+=ToRest;
                        matel[k][l].setValor(val);
                    }
                    if (k == posi && l == posj)
                        matel[k][l].setValor(ToRest);
                    aux = aux + matel[k][l].getValor() + "\t";
                }
            }
            Log.e("Hecho? ", "" + aux);
            //<--Asignando valores de las posiciones de las soluciones-->
            //<--Transformando a la nueva solucion de la matriz de posicion-->
            volverDeMatel(sol, matel);
            //<--Solucion transformada-->
            for (int k = 0; k < n; k++) {
                for (int l = 0; l < m; l++) {
                    Zeta[k][l] = 0;
                }
            }
            assignValPos(sol, Zeta);
            //<--Hecha la matriz con los valores de las posiciones de soluciones-->
            //<--Hallando el minimo valor-->
            min = getMinAsignados(sol,Zeta);
            Log.e("MinimoFil: ",""+min);
            //<--Encontrado el minimo valor-->
            //<--Calculos fila-col para hallar matriz completada
            for (int k = 0; k < filstinker.length; k++) {
                filstinker[k] = 0;
            }
            for (int k = 0; k < colstinker.length; k++) {
                colstinker[k] = 0;
            }
            filstinker[0] = min;
            //<--Encontradas filas y cols-->
            aux="";
            getFilsCols(filstinker, colstinker, Zeta);
            for(int k=0;k<filstinker.length;k++)
            {
                aux=aux+filstinker[k]+"\t";
            }
            Log.e("Filas:",""+aux);
            aux="";
            for(int k=0;k<colstinker.length;k++)
            {
                aux=aux+colstinker[k]+"\t";
            }
            Log.e("Cols:",""+aux);
            //<--Calculo de la matriz completa-->
            completeMatrix(Zeta, filstinker, colstinker);
            //<--Matriz Completada-->
            aux="";
            //<--Comparacion para pararla-->
            MatCond(condicional, Zeta);
            //<--Matriz lista para ser analizada-->
        }
        //<--Una vez llegado al optimo imprimir la solucion con la funcion maximizada-->
        aux = "";
        int funcion = 0;
        for (int k = 0; k < n; k++) {
            aux = aux + "\n";
            for (int l = 0; l < m; l++) {
                aux = aux + sol[k][l] + "\t\t\t";
                funcion += (sol[k][l] * mat[k][l]);
            }
        }
        dialogo.setTextSize(20);
        dialogo.setText(aux);
        dialog.show();
        Toast.makeText(this, "Maximo: " + funcion, Toast.LENGTH_SHORT).show();
    }






    public void volverDeMatel(int sol[][],Posicion matel[][])
    {
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<m;j++)
            {
                sol[i][j]=matel[i][j].getValor();
            }
        }
    }
    public boolean isMaximized(int mat[][])
    {
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<m;j++)
            {
                if(mat[i][j]<0)
                    return false;
            }
        }
        return true;
    }
    public boolean isMinimized(int mat[][])
    {
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<m;j++)
            {
                if(mat[i][j]>0)
                    return false;
            }
        }
        return true;
    }
    public int getMin(int mat[][])
    {
        int c=0;
        int min=0;
        for(int k=0;k<n;k++)
        {
            for(int l=0;l<m;l++)
            {
                if(mat[k][l]!=0 && c==0)
                {
                    min=mat[k][l];
                    c++;
                }
                else if(mat[k][l]<min && mat[k][l]!=0)
                {
                    min=mat[k][l];
                }
            }
        }
        return min;
    }
    public int getMax(int mat[][])
    {
        int c=0;
        int min=0;
        for(int k=0;k<n;k++)
        {
            for(int l=0;l<m;l++)
            {
                if(mat[k][l]!=0 && c==0)
                {
                    min=mat[k][l];
                    c++;
                }
                else if(mat[k][l]>min && mat[k][l]!=0)
                {
                    min=mat[k][l];
                }
            }
        }
        return min;
    }
    public void getFilsCols(int filstinker[],int colstinker[],int Zeta[][])
    {
        String aux="";
        for(int k=0;k<n;k++)
        {
            aux=aux+"\n";
            for(int l=0;l<m;l++)
            {
                if(Zeta[k][l]>0)
                {
                    if(filstinker[k]!=0 && colstinker[l]==0)
                    {
                        colstinker[l]=Zeta[k][l]-filstinker[k];
                        for(int a=0;a<n;a++)
                        {
                            if(Zeta[a][l]>0)
                                filstinker[a]=Zeta[a][l]-colstinker[l];
                        }
                    }
                    else if(filstinker[k]==0 && colstinker[l]!=0)
                    {
                        filstinker[k]=Zeta[k][l]-colstinker[l];
                        for(int a=0;a<m;a++)
                        {
                            if(Zeta[k][a]>0)
                                colstinker[a]=Zeta[k][a]-filstinker[k];
                        }
                    }
                    else if(filstinker[k]==0 && colstinker[l]==0)
                    {
                        filstinker[k]=Zeta[k][l];
                        colstinker[l]=Zeta[k][l]-filstinker[k];
                    }
                }
                aux=aux+Zeta[k][l]+"\t";
            }
        }
        Log.e("EncontrarConSols: ",""+aux);
    }
    public void completeMatrix(int Zeta[][],int filstinker[],int colstinker[])
    {
        String aux="";
        for(int k=0;k<n;k++)
        {
            aux=aux+"\n";
            for(int l=0;l<m;l++)
            {
                Zeta[k][l]=filstinker[k]+colstinker[l];
                aux=aux+Zeta[k][l]+"\t";
            }
        }
        Log.e("Completed",""+aux);
    }
    public void MatCond(int condicional[][],int Zeta[][])
    {
        String aux="";
        for(int k=0;k<n;k++)
        {
            aux=aux+"\n";
            for(int l=0;l<m;l++)
            {
                condicional[k][l]=Zeta[k][l]-mat[k][l];
                aux=aux+condicional[k][l]+"\t";
            }
        }
        Log.e("La Condicional",""+aux);
    }
    public void pasarMatel(Posicion matel[][],int sol[][])
    {

        for(int k=0;k<n;k++)
        {

            for(int l=0;l<m;l++)
            {
                matel[k][l]=new Posicion(sol[k][l],false,false,false,false);

            }
        }

    }
    public void assignValPos(int sol[][],int Zeta[][])
    {
        String aux="";
        for(int k=0;k<n;k++)
        {
            aux=aux+"\n";
            for(int l=0;l<m;l++)
            {
                if(sol[k][l]!=0)
                {
                    Zeta[k][l]=mat[k][l];
                }
                aux=aux+Zeta[k][l]+"\t";
            }
        }
        Log.e("Asignados",""+aux);
    }
    public int getMinAsignados(int sol[][], int Zeta[][])
    {
        int min=0,c=0;
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<m;j++)
            {
                if(sol[i][j]!=0)
                {
                    if(c==0)
                    {
                        min=Zeta[i][j];
                        c++;
                    }
                    else
                    if(Zeta[i][j]<min)
                        min=Zeta[i][j];
                }
            }
        }
        return min;
    }
    public void pasarExtremos(int org[],int copia[])
    {
        for(int i=0;i<org.length;i++)
        {
            copia[i]=org[i];
        }
    }
}
