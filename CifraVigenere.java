public class CifraVigenere {

    // Gera a chave para criptografia baseada na palavra-chave e no texto original
    public static String gerarChave(String str, String chave) {
        StringBuilder chaveGerada = new StringBuilder();
        int tamanhoStr = str.length();
        int tamanhoChave = chave.length();

        for (int i = 0; i < tamanhoStr; i++) {
            char caractereChave = chave.charAt(i % tamanhoChave);

            // Verifica se o caractere da chave é uma letra
            // Se sim, adiciona o caractere à chave gerada
            // Se não, mantém o caractere original do texto
            if (Character.isLetter(caractereChave)) {
                chaveGerada.append(caractereChave);
            } else {
                chaveGerada.append(str.charAt(i));
            }
        }

        return chaveGerada.toString();
    }

    // Criptografa o texto original usando a chave gerada
    public static String cifrarTexto(String str, String chave) {
        StringBuilder textoCifrado = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            char caractereAtual = str.charAt(i);

            // Verifica se o caractere atual é uma letra
            // Se sim, realiza a criptografia utilizando a fórmula de César
            // Se não, mantém o caractere original
            if (Character.isLetter(caractereAtual)) {
                int valorCaractere = caractereAtual - 'A';
                int valorChave = chave.charAt(i) - 'A';

                int caractereCifrado = (valorCaractere + valorChave) % 26;
                textoCifrado.append((char) (caractereCifrado + 'A'));
            } else {
                textoCifrado.append(caractereAtual);
            }
        }

        return textoCifrado.toString();
    }

    // Descriptografa o texto cifrado usando a chave gerada
    public static String textoOriginal(String textoCifrado) {
        String palavraChave = "AER";
        StringBuilder textoOriginal = new StringBuilder();

        // Converte o texto cifrado e a palavra-chave para maiúsculas
        String textoCifradoMaiusculo = textoCifrado.toUpperCase();
        String palavraChaveMaiusculo = palavraChave.toUpperCase();

        // Gera a chave com base no texto cifrado e na palavra-chave
        String chave = gerarChave(textoCifradoMaiusculo, palavraChaveMaiusculo);

        for (int i = 0; i < textoCifrado.length() && i < chave.length(); i++) {
            char caractereAtual = textoCifrado.charAt(i);

            // Verifica se o caractere atual é uma letra
            // Se sim, realiza a descriptografia utilizando a fórmula de Vigenere inversa
            // Se não, mantém o caractere original
            if (Character.isLetter(caractereAtual)) {
                int valorCaractere = caractereAtual - 'A';
                int valorChave = chave.charAt(i) - 'A';

                int caractereDecifrado = (valorCaractere - valorChave + 26) % 26;
                textoOriginal.append((char) (caractereDecifrado + 'A'));
            } else {
                textoOriginal.append(caractereAtual);
            }
        }

        return textoOriginal.toString();
    }

    // Método principal para criptografar o texto
    public static String criptografiaGFG(String textoCriptografado) {
        String texto = textoCriptografado;
        String palavraChave = "AER";

        // Gera a chave com base no texto original e na palavra-chave
        String chave = gerarChave(texto.toUpperCase(), palavraChave.toUpperCase());

        // Cifra o texto original utilizando a chave gerada
        String textoCifrado = cifrarTexto(texto.toUpperCase(), chave);

        return textoCifrado;
    }
}
