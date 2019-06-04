package com.kamelong.OuDia;

import com.kamelong.aodia.SDlog;
import com.kamelong.tool.ShiftJISBufferedReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SimpleOudia {
    public String name="";
    public ArrayList<String>stationName=new ArrayList<>();

    public SimpleOudia(File file)throws Exception{
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String version = br.readLine().split("=", -1)[1];
            double v = 1.02;
            try {
                v = Double.parseDouble(version.substring(version.indexOf(".") + 1));
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (version.startsWith("OuDia.") || v < 1.03) {
                loadShiftJis(file);
            } else {
                loadDiaFile(br);
            }
        }catch(Exception e){
            SDlog.log("ファイルの読み込みに失敗しました。"+file.getPath());
        }

    }
    private void loadShiftJis(File file)throws Exception{
        BufferedReader br = new ShiftJISBufferedReader(new InputStreamReader(new FileInputStream(file),"Shift-JIS"));
        String nouse=br.readLine();
        loadDiaFile(br);//version info
    }
    private void loadDiaFile(BufferedReader br)throws Exception{
        br.readLine();//Rosen.
        name=br.readLine().split("=",-1)[1];
        String line=br.readLine();
        while(line!=null){
            if(line.equals("Eki.")){
                line=br.readLine();
                stationName.add(line.split("=",-1)[1]);
            }
            if(line.equals("Ressyasyubetsu.")){
                br.close();
                return;
            }
            line=br.readLine();
        }

        br.close();
    }

}