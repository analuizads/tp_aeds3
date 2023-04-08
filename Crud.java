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

        int verificar = ManipularArquivos.ProcuraId(id);

        //Verificar se o ID é válido, se retornar -1 é porque não é válido
        if(verificar == -1) {

            System.out.println("ID inválido!!");
            System.out.println("Digite outro ID: ");
            id = sc.nextInt();
            read(id);
        }
        else {
            //Retornar a posição, enviando a opção do tamanho
            long pos = ManipularArquivos.retornaPos(id, 2);
            arq.seek(pos);
            int tamanho = arq.readInt();
            byte[] ba = new byte[tamanho];

            Filmes f1 = ManipularArquivos.fromByteArrayPosicao(ba, pos);

            System.out.println(f1);
        }
        arq.close();
    }
        
    public static void update(int id) throws IOException, Exception {

        RandomAccessFile arq = new RandomAccessFile("arquivo_db/hexa.db", "rw");
        Scanner sc = new Scanner(System.in);
		SimpleDateFormat formato = new SimpleDateFormat("yyyy");

        Filmes filme = new Filmes();

        int verificar = ManipularArquivos.ProcuraId(id);

        if(verificar != -1) {

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
            byte[] novoRegistro = Filmes.toByteArray(filme);

            long posicao = ManipularArquivos.retornaPos(id, 2);
            arq.seek(posicao);
            int tam = arq.readInt();
            byte lapide = '*';
            if (novoRegistro.length <= tam) {
                posicao = ManipularArquivos.retornaPos(id, 1);
                arq.write(novoRegistro);
    
            } else {
                arq.seek(ManipularArquivos.retornaPos(id, 2));
                arq.writeByte(lapide);
                arq.seek(arq.length());
                arq.write(novoRegistro);
            }
            System.out.println(filme);
        }

    }

    public static void delete(int id) throws Exception {

        int verificar = ManipularArquivos.ProcuraId(id);

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
