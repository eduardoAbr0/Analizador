import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;

/* Materia: Lenguajes y Autómatas
 * No. Programa: 2
 * Programa: Analizador Léxico
 * Alumno: Eduardo Alejo Bañuelos Ruiz
 * Fecha: 05/05/2025
 */


public class analisisLexico {
    //Proceso encargado de la captura del documento de texto
    public StringBuilder chkDocumento() {
        StringBuilder documento = new StringBuilder();

        try (BufferedReader br = new BufferedReader(
                new FileReader("/Users/ed308lejo/Documents/anL/anlexic.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                    documento.append(linea).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return documento;
    }

    //Analisis del documento en base expresiones regulares
    public  ArrayList<ArrayList<String>> analisisLex() {
        StringBuilder documento = chkDocumento();
        System.out.println("\n"+documento);
        

        Pattern patronesBusqueda = Pattern.compile("\\b(vis|neg|ver|cond|condsi|cil|nt|dc|bool|itr)\\b|[*-+/]|(\n)|[;]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = patronesBusqueda.matcher(documento);

        //ArrayList para guardar tokens e informacion de estos
        ArrayList<ArrayList<String>> filaToken = new ArrayList<>();
        ArrayList<ArrayList<String>> filaTipo = new ArrayList<>();
        ArrayList<String> token = new ArrayList<>();
        ArrayList<String> tipo = new ArrayList<>();
        ArrayList<ArrayList<String>> listaTokens = new ArrayList<>();

        //Contador para token validos
        int ctdd = 0;
        //Contador para conocer fila del archivo
        int linea = 1;
        //Variable para uso de funcion token invalido
        int indP = 0;
        while (matcher.find()) {
            ctdd++;
            
            //Verificar token invalido
            if (matcher.start() > indP) {  
                //System.out.println(indP+" Indice p");
                String tokeninv = documento.substring(indP, matcher.start());
                if (!tokeninv.isBlank()) {
                    //System.out.println("Token invalido: " + tokeninv + " en linea "+linea);

                    tipo.add("Token invalido");
                    token.add(tokeninv);
                    listaTokens.add(tipo);
                    listaTokens.add(token);
                }
            }

            if (!matcher.group().equals("\n")) {
                //System.out.println("Token valido nm." + ctdd + " : " + matcher.group() + " en linea "+linea);
                
                //Palabras reservadas
                if (matcher.group().equals("neg")||matcher.group().equals("vis")||matcher.group().equals("cond")
                || matcher.group().equals("condsi")||matcher.group().equals("cil")||matcher.group().equals("dc")
                || matcher.group().equals("itr")||matcher.group().equals("bool")) {
                    tipo.add("Palabra reservada");
                    token.add(matcher.group());
                }
                    
                //Palabras reservadas tipo
                else if(matcher.group().equals("nt") || matcher.group().equals("dc")|| matcher.group().equals("bool")|| matcher.group().equals("itr"))){
                    tipo.add("Tipo");
                    token.add(matcher.group());
                }
                //
                else if(){

                }
                //Operadores
                else if(matcher.group().equals("*")||matcher.group().equals("+")||matcher.group().equals("-")||matcher.group().equals("/")){
                    tipo.add("Operador");
                    token.add(matcher.group());
                }

                indP = matcher.end() + 1;
                    
            }else{
                indP = matcher.end();
                linea++;
                ctdd--;
            }    
        }

        //Lista bidimensional con tipos y tokens
        filaTipo.add(tipo);
        filaToken.add(token);
        listaTokens.add(tipo);
        listaTokens.add(token);

        //Impresion tokens
        for (int i = 0; i < listaTokens.size(); i++) {
                
                System.out.println("i "+i+ listaTokens.size());
                System.out.print("Tipo: "+listaTokens.get(0).get(i));
                System.out.println(". Token: "+listaTokens.get(1).get(i));
            }

        //Dar los tokens obtenidos al analizador sintactico
        return listaTokens;

    }
}
