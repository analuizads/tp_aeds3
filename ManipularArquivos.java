import java.io.RandomAccessFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ManipularArquivos {
    
    public static int ProcuraId(int id) throws IOException, ParseException{

        RandomAccessFile arq = new RandomAccessFile("arquivo_db/hexa.db", "rw");
        SimpleDateFormat formato = new SimpleDateFormat("yyyy");

        Filmes f1;
		int tam;
		int identificador = -1;
		int idArq = -1;
        String titulo;
        float imdb;
        String genero;
        Date date;
		long pos = 0;

        //Mover o ponteiro para o início do arquivo
		arq.seek(0);
        //Ler os bytes e identificar como inteiro
		arq.readInt();

		while (true) {

            //Verificando que se a posição do ID está no arquivo
            if(arq.getFilePointer() < arq.length()){

				//Extrair a lápide do objeto
				byte lapide = arq.readByte();
                
				//Verificar se a lápide é válida
                if (lapide != '*') {
					
                    //Extrair o objeto do registro
                    tam = arq.readInt();
                    idArq = arq.readInt();
                    titulo = arq.readUTF();
					imdb = arq.readFloat();
                    genero = arq.readUTF();
                    date = formato.parse(arq.readUTF());

                    //Verificar se o id procurado é igual ao id do objeto atual 
                    if (id == idArq) {
                        identificador = idArq;
                        f1 = new Filmes(idArq, titulo, imdb, genero, date);
                        break;
                    }

                } else {
					arq.readInt();
					arq.readInt();
					arq.readUTF();
					arq.readFloat();
					arq.readUTF();
					arq.readUTF();
                }
            }
            else {

                arq.seek(0);
				int primeiro = arq.readInt();
				System.out.println("Este ID não existe ou foi excluido. \nid maximo é = " + primeiro);
				identificador = -1;
				break;
	
            }
		}
		arq.close();
		return identificador;
    }

	public static long retornaPos(int id, int op) throws IOException, Exception {

		RandomAccessFile arq = new RandomAccessFile("arquivo_db/hexa.db", "rw");
		SimpleDateFormat formato = new SimpleDateFormat("yyyy");

		long ponteiro = 0;
		long lapidePont, tamanho, idPont, titulo, imdb, genero, date, idArq;

		arq.seek(0);
		arq.readInt();
		Filmes f1;
		while (true) {
			if (arq.getFilePointer() < arq.length()) {

				lapidePont = arq.getFilePointer();
				byte lapide = arq.readByte();

				if (lapide != '*') {

					tamanho = arq.getFilePointer();
					arq.readInt();
					idPont = arq.getFilePointer();
					idArq = arq.readInt();
					titulo = arq.getFilePointer();
					arq.readUTF();
					imdb = arq.getFilePointer();
					arq.readFloat();
					genero = arq.getFilePointer();
					arq.readUTF();
					date = arq.getFilePointer();
					formato.parse(arq.readUTF());

					if (id == idArq) {

						switch (op) {
						case 1:
						ponteiro = lapidePont;
							break;
						case 2:
							ponteiro = tamanho;
							break;
						case 3:
							ponteiro = idPont;
							break;
						case 4:
							ponteiro = titulo;
							break;
						case 5:
							ponteiro = imdb;
							break;
						case 6:
							ponteiro = genero;
							break;
						case 7:
							ponteiro = date;
							break;
						}

						break;

					}

				} else {
					
					arq.readInt();
					arq.readInt();
					arq.readUTF();
					arq.readFloat();
					arq.readUTF();
					arq.readUTF();

				}
			} else {

				break;
			}
		}
		arq.close();
		return ponteiro;
	}

	public static Filmes fromByteArrayPosicao(byte[] ba, long pos) throws IOException, ParseException {

		SimpleDateFormat formato = new SimpleDateFormat("yyyy");
		RandomAccessFile arq = new RandomAccessFile("arquivo_db/hexa.db", "rw");
		Filmes filme = new Filmes();

		arq.seek(pos);
		arq.readInt();
		filme.setId(arq.readInt());
		filme.setTitulo(arq.readUTF());
		filme.setImdb(arq.readFloat());
		filme.setGenero(arq.readUTF());
		filme.setStartdate(formato.parse(arq.readUTF()));

		arq.close();
		return filme;
	}

}
