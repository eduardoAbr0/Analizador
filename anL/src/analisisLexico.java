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
                new FileReader("C:\\Users\\ed308\\Documents\\Analizador\\Analizador\\anL\\anlexic.txt"))) {
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
        

        Pattern patronesBusqueda = Pattern.compile(""
        + "(?<Tipo>\\b(nt|dc|bool|itr)\\b)"
        + "|(?<Booleano>\\b(neg|ver)\\b)"
        + "|(?<NumeroDecimal>\\b\\d+(\\.\\d{2})\\b)"
        + "|(?<NumeroEntero>\\b\\d+\\b)"
        + "|(?<Nombre>\\b([a-zA-Z]([a-zA-Z]|\\d)*)\\b)"
        + "|(?<Operador>[*+\\-/])"
        + "|(?<Especial>[=;()])"
        + "|(?<Texto>\"[^\"]+\")"
        + "|(?<Salto>\n)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = patronesBusqueda.matcher(documento);

        //ArrayList para guardar tokens e informacion de estos
        ArrayList<String> token = new ArrayList<>();
        ArrayList<String> tipo = new ArrayList<>();
        ArrayList<String> lin = new ArrayList<>();
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
                String tokeninv = documento.substring(indP, matcher.start());
                if (!tokeninv.isBlank()) {
                    System.out.println("Token invalido: " + tokeninv + " en linea "+linea);

                    tipo.add("Token invalido");
                    token.add(tokeninv);
                    lin.add("Linea: "+linea);
                }
            }
            if (matcher.group("Salto")==null) {
                //Tipo
                if (matcher.group("Tipo") != null) {
                    tipo.add("Tipo");
                    token.add(matcher.group());
                    lin.add("Linea: "+linea);
                }    
                //Numero entero
                else if(matcher.group("NumeroEntero")!=null){
                    tipo.add("NumeroEntero");
                    token.add(matcher.group());
                    lin.add("Linea: "+linea);
                }
                //Numero decimal
                else if(matcher.group("NumeroDecimal")!=null){
                    tipo.add("NumeroDecimal");
                    token.add(matcher.group());
                    lin.add("Linea: "+linea);
                }
                //Nombre
                else if(matcher.group("Nombre")!=null){
                    tipo.add("Nombre");
                    token.add(matcher.group());
                    lin.add("Linea: "+linea);
                }
                //Operadores
                else if(matcher.group("Operador") != null){
                    tipo.add("Operador");
                    token.add(matcher.group());
                    lin.add("Linea: "+linea);
                }
                //Caracteres especiales
                else if(matcher.group("Especial") != null){
                    tipo.add("Especial");
                    token.add(matcher.group());
                    lin.add("Linea: "+linea);
                }
                //Texto
                else if(matcher.group("Texto") != null){
                    tipo.add("Texto");
                    token.add(matcher.group());
                    lin.add("Linea: "+linea);
                }
                //Caracteres especiales
                else if(matcher.group("Especial") != null){
                    tipo.add("Especial");
                    token.add(matcher.group());
                    lin.add("Linea: "+linea);
                }
                //Booleano
                else if(matcher.group("Booleano") != null){
                    tipo.add("Booleano");
                    token.add(matcher.group());
                    lin.add("Linea: "+linea);
                }
                indP = matcher.end() + 1;   
            }else{
                indP = matcher.end();
                linea++;
                ctdd--;
            }    
        }

        //Lista bidimensional con tipos y tokens
        listaTokens.add(tipo);
        listaTokens.add(token);
        listaTokens.add(lin);
        //Impresion tokens
        for (int i = 0; i < listaTokens.get(0).size(); i++) {
                System.out.print("Tipo: "+listaTokens.get(0).get(i));
                System.out.print(". Token: "+listaTokens.get(1).get(i));
                System.out.println(". Linea: "+listaTokens.get(2).get(i));
            }

        //Dar los tokens obtenidos al analizador sintactico
        return listaTokens;

    }
}
