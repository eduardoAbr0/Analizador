import java.util.ArrayList;

public class analisisSintactico extends analisisLexico{

    public void tokens(){
    
        ArrayList<ArrayList<ArrayList<String>>> listaTokens = analisisLex();

        System.out.println(listaTokens);

    }


}