package comum;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;

public class Arquivo {
    File arquivo;
    FileWriter fw;
    BufferedWriter bw;
    FileReader fr;
    BufferedReader br;

    public Arquivo(){}
    public Arquivo(String path){
        loadFile(path);
    }

    public void loadFile(String path){
        try{
            arquivo = new File(path);
            
            if(!arquivo.exists()){
                File diretorio = new File(arquivo.getParent());
                diretorio.mkdirs();
                arquivo.createNewFile();

                throw new IOException("Arquivo não encontrado. Novo arquivo criado!");
            }
        }
        catch(SecurityException e){
            System.err.println(e + ": Programa não tem acesso para esse arquivo!");
        }
        catch(NullPointerException e){
            System.err.println(e + ": Parâmetro vazio!");
        }
        catch(IOException e){
            System.err.println(e);
        }
        catch(Exception e){
            System.err.println(e);
        }
    }

//ESCREVER--------------------------------------------------
    public void write(String[] dado){
        try {

            fw = new FileWriter(arquivo);
            bw = new BufferedWriter(fw);

            for (String gravar : dado) {
                bw.write(gravar.toString());
                bw.newLine();
            }
            
            bw.close();
            fw.close();
        }
        catch (IOException e) {
            System.err.println(e);
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

    public void write(String dado){
        try {

            fw = new FileWriter(arquivo);
            bw = new BufferedWriter(fw);

            bw.write(dado.toString());
            bw.newLine();

            bw.close();
            fw.close();

        }
        catch (IOException e) {
            System.err.println(e);
        }
        catch (Exception e) {
            System.err.println(e);
        } 
    }

//LER--------------------------------------------------
    public Object readNLine(int line){
        Object result = null;
        try {
            fr = new FileReader( arquivo );
            br = new BufferedReader(fr);

            for (int i = 1; i < line && br.ready(); i++) {
                br.readLine();
            }
            result = br.readLine();
            
            br.close();
            fr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Object[] readAll(){
        ArrayList<Object> result = new ArrayList<Object>();

        try {
            FileReader fr = new FileReader( arquivo );
            BufferedReader br = new BufferedReader(fr);

            while(br.ready()){
                result.add(br.readLine());
            }
            
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toArray();
    }

    public Integer[] readAllIntegers(){
        ArrayList<Integer> result = new ArrayList<Integer>();

        try {
            FileReader fr = new FileReader( arquivo );
            BufferedReader br = new BufferedReader(fr);

            while(br.ready()){
                result.add(Integer.parseInt(br.readLine()));
            } 
            
            br.close();
            fr.close();
        } catch (Exception e) {
            System.err.println(e);
        }

        return result.toArray(new Integer[result.size()]);
    }

    public String[] readAllStrings(){
        ArrayList<String> result = new ArrayList<String>();

        try {
            FileReader fr = new FileReader( arquivo );
            BufferedReader br = new BufferedReader(fr);

            while(br.ready()){
                result.add(br.readLine());
            }
            
            
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toArray(new String[result.size()]);
    }

}
