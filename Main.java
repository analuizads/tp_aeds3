import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;


public class Main {

    private static void clearBuffer(Scanner scanner) {
		if (scanner.hasNextLine()) {
			scanner.nextLine();
		}
	}
    public static void main(String[] args) throws IOException, ParseException, Exception {

        RandomAccessFile arq = new RandomAccessFile("arquivo_db/hexa.db", "rw");
        System.out.println("PASSOU AQ");
        
        if(arq.length() <= 0) {
            try {
                Leitor_csv.leitor();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        Scanner sc = new Scanner(System.in);
        int opcao;
        String resp = "s";

        while(resp.equals("s")|| resp.equals("S")) {

            System.out.println("Selecione a operação que deseja realizar: \n(1)Create \n(2)Read \n(3)Update \n(4)Delete \n(5) Compressao  \n(6) Descompressao\n");
            opcao = sc.nextInt();

            while (opcao < 1 || opcao > 6) {
                System.out.println("Número inválido!!");
                System.out.println("Selecione a operação que deseja realizar:\n(1)Create \n(2)Read \n(3)Update \n(4)Delete \n(5) Compressao  \n(6) Descompressao\n");
                opcao = sc.nextInt();
            }

            switch (opcao) {

                case 1 :
                //Create

                SimpleDateFormat formato = new SimpleDateFormat("yyyy");

                Filmes filme = new Filmes();
                
		        System.out.println("Digite o título do filme:  ");
		        filme.setTitulo(sc.next());

                System.out.println("Digite a nota do IMDB:  ");
		        filme.setImdb(sc.nextFloat());

		        System.out.println("Digite o gênero do filme:  ");
		        filme.setGenero(sc.next());

		        System.out.println("digite o ano de lançamento do filme:  ");
                clearBuffer(sc);
                filme.setStartdate(formato.parse(sc.nextLine()));

                Crud.create(filme);

                break;

                case 2 :
                //Read
                System.out.println("digite o id para ser procurado: ");
			    Crud.read(sc.nextInt());

                break;

                case 3 :
                //Update
                System.out.println("Digite o ID a ser modificado: ");
                Crud.update(sc.nextInt());

                break; 

                case 4 : 
                //Delete
                System.out.println("Digite o ID a ser deletado: ");
                Crud.delete(sc.nextInt());
                
                break;

                case 5:
                String opcaocd="COMPRIMIR";
                Lzw.algoritmo(opcaocd);
                break;
                
                case 6:
                opcaocd="DESCOMPRIMIR";
                Lzw.algoritmo(opcaocd);
    
                break;
            }

            System.out.println("Deseja realizar outra operação?");
            resp = sc.next();
        }

        sc.close();
    }


    
}
