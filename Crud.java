import java.io.RandomAccessFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Crud {

    static SimpleDateFormat formato = new SimpleDateFormat("yyyy");
	static Scanner sc = new Scanner(System.in);

    public static void create(Filmes filme) throws IOException, ParseException, Exception {

        RandomAccessFile arq = new RandomAccessFile("arquivo_db/hexa.db", "rw");

        //Mover o ponteiro para o início do arquivo
        arq.seek(0);
        //Ler os bytes e identificar como inteiro e atribuir o valor à variável ultimoID
		int ultimoId = arq.readInt();
		ultimoId++;
        //Fazer com que o ID do novo objeto seja igual ao valor contido na váriavel ultimoId
		filme.setId(ultimoId);
        //Mover o ponteiro para o final do arquivo
		arq.seek(arq.length());
        //Escrever o novo objeto em vetor de bytes 
		Escrita_db.escrita(filme);
        //Mostrar o novo objeto
        read(ultimoId++);
        arq.close();

    }

    public static void read(int id) throws IOException, Exception, ParseException {

        RandomAccessFile arq = new RandomAccessFile("arquivo_db/hexa.db", "rw");

        long verificar = ManipularArquivos.pesquisarNoIndex(id);

        //Verificar se o ID é válido, se retornar -1 é porque não é válido
        if(verificar != -1) {

            arq.seek(verificar);
            byte lapide = arq.readByte();
            int tam = arq.readInt();
			if (lapide != '*') {
				byte[] ba = new byte[tam];
				arq.read(ba);
				System.out.println(Filmes.fromByteArray(ba));
			}
			else {
				System.out.println("Arquivo excluido");
				System.out.println("Digite novo ID valido: ");
				read(sc.nextInt());
			}
            
        }
        else {
            System.out.print("Arquivo excluido ou não exite, ID maximo é: ");
			arq.seek(0);
			System.out.println(arq.readInt());
			System.out.println("digite novo ID valido: ");
			read(sc.nextInt());
        }
        arq.close();
    }
        
    public static void update(int id) throws IOException, Exception {

		SimpleDateFormat formato = new SimpleDateFormat("yyyy");

        Filmes filme = new Filmes();

        long verificar = ManipularArquivos.pesquisarNoIndex(id);

        if(verificar != -1) {

            //Ler dados
            System.out.println("Digite o título do filme:  ");
		    filme.setTitulo(sc.nextLine());

		    System.out.println("Digite o gênero do filme:  ");
		    filme.setGenero(sc.nextLine());

		    System.out.println("Digite a nota do IMDB:  ");
		    filme.setImdb(sc.nextFloat());

		    System.out.println("Digite o ano de lançamento do filme:  ");
            clearBuffer(sc);
            filme.setStartdate(formato.parse(sc.nextLine()));

            filme.setId(id);
            //Testar tamanho do novo objeto e escrever
            byte[] novoRegistro = Filmes.toByteArray(filme);
            ManipularArquivos.testarTamanho(id, novoRegistro, verificar);
            System.out.println("ID atualizado ");
            System.out.println(filme);
        }

    }

    public static void delete(int id) throws Exception {

        long verificar = ManipularArquivos.pesquisarNoIndex(id);

        //Verificar se o ID é válido, se retornar -1 é porque não é válido
        if(verificar == -1) {

            System.out.println("ID inválido!!");
            System.out.println("Digite outro ID: ");
            id = sc.nextInt();
            delete(id);
        }
        else {

            long pos = ManipularArquivos.retornaPos(id, 1);

            RandomAccessFile arq = new RandomAccessFile("arquivo_db/hexa.db", "rw");
		    arq.seek(pos);
		    arq.writeByte('*');
            System.out.println("ID removido.");

		    arq.close();
        }
    }

    private static void clearBuffer(Scanner scanner) {
	    if (scanner.hasNextLine()) {
			scanner.nextLine();
		}
	}
}
