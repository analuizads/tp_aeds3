import java.util.Scanner;

public class main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int opcao;
        String resp = "s";

        while(resp.equals("s")|| resp.equals("S")) {

            System.out.println("Selecione a operação que deseja realizar: \n(1)Ler um registro \n(2)Create \n(3)Read \n(4)Update \n(5)Delete \n(6)Ordenação externa do arquino \n");
            opcao = sc.nextInt();

            while (opcao < 1 || opcao > 5) {
                System.out.println("Número inválido!!");
                System.out.println("Selecione a operação que deseja realizar: \n(1)Ler um registro \n(2)Create \n(3)Read \n(4)Update \n(5)Delete \n(6)Ordenação externa do arquino \n");
                opcao = sc.nextInt();
            }

            switch (opcao) {

                case 1 :
                // Ler registro
                break;

                case 2 :
                //Create
                break;

                case 3 :
                //Read
                break;

                case 4 :
                //Update
                break; 

                case 5 : 
                //Delete
                break;

                case 6:
                //Ordenação externa do arquivo
                break;
            }

            System.out.println("Deseja realizar outra operação?");
            resp = sc.next();

        }

        sc.close();
    }


    
}