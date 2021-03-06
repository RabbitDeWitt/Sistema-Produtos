import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class App {
    public static void limparTela() throws IOException, InterruptedException{
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }
    public static void main(String[] args) throws Exception{
        HashMap<Integer, String> produtos = new HashMap<Integer ,String>();
        HashMap<Integer , Double> precos = new HashMap<Integer , Double>();
        Scanner scan = new Scanner(System.in);
        int opcaoMenu = 0;
        
        do{
            limparTela();
            mostrarMenu();
            opcaoMenu = lerOpcaoMenu(scan);
            if(!processarOpcaoMenu(scan, opcaoMenu, produtos, precos)){
                System.out.println("Opção Inválida, tente novamente...");
                Thread.sleep(2000);
            }
        }while(opcaoMenu != 5);

        scan.close();
    }

    private static boolean processarOpcaoMenu(Scanner scan, int opcaoMenu, HashMap<Integer, String> produtos, HashMap<Integer, Double> precos) throws IOException, InterruptedException{
        switch (opcaoMenu) {
            case 1:
            inserirProduto(scan, produtos, precos);
            return true;
            case 2:
            alterarProduto(scan, produtos, precos);
            return true;
            case 3:
            removerProduto(scan, produtos, precos);
            return true;
            case 4:
            listarProduto(produtos, precos);
            return true;
            case 5:
            sairDoPrograma();
            return true;
            default:
            return false;
        }               
    }

    

    
    private static void inserirProduto(Scanner scan, HashMap<Integer, String> produtos,
            HashMap<Integer, Double> precos) throws IOException, InterruptedException{
                limparTela();
                System.out.println("INSERÇÃO DE PRODUTOS");
                System.out.println("--------------------");
                System.out.print(">> Nome: ");
                String nome = scan.nextLine();
                System.out.print(">> Preço: ");
                Double preco = scan.nextDouble();
                int proximaChave = obterProximaChave(produtos.keySet());
                produtos.put(proximaChave, nome);
                precos.put(proximaChave, preco);
                System.out.println("--------------------");
                System.out.println("Produto inserido com sucesso!");
                System.out.println(">> Pressione ENTER para voltar ao menu...");
                System.in.read();
    }

    private static int obterProximaChave(Set<Integer> keySet) {
        if(keySet.size() > 0){
            return Collections.max(keySet) + 1;
        }else return 1;
    }
    private static void alterarProduto(Scanner scan, HashMap<Integer, String> produtos,
            HashMap<Integer, Double> precos) throws IOException, InterruptedException {
                limparTela();
                System.out.println("ALTERAÇÃO DE PRODUTO");
                System.out.println("------------------");
                System.out.print(">> Código: ");
                int codigo = scan.nextInt();
                scan.nextLine();
                if(produtos.containsKey(codigo)){
                    System.out.format("Valores originais:%n%s, R$ %.2f%n", produtos.get(codigo), precos.get(codigo));                                       
                    System.out.println("(Deixe em branco para manter o valor origina)");
                    System.out.print(">> Novo nome: ");
                    String novoNome = scan.nextLine().trim();
                    novoNome = novoNome.isEmpty() ? produtos.get(codigo) : novoNome;                    
                    System.out.print(">> Novo preço: ");
                    String novoPrecoStr = scan.nextLine().trim();
                    Double novoPreco = novoPrecoStr.isEmpty() ? precos.get(codigo) : Double.parseDouble(novoPrecoStr.replace(",", "."));
                    produtos.put(codigo, novoNome);
                    precos.put(codigo, novoPreco);
                    System.out.println("------------------");
                    System.out.println("Produto alterado com sucesso!");
                }else{
                    System.out.println("Produto não encontrado!");                    
                }
                System.out.println(">> Pressione ENTER para voltar ao menu...");
                System.in.read();
    }

    private static void removerProduto(Scanner scan, HashMap<Integer, String> produtos,
            HashMap<Integer, Double> precos) throws IOException, InterruptedException {
                limparTela();
                System.out.println("REMOÇÃO DE PRODUTO");
                System.out.println("------------------");
                System.out.print(">> Código: ");
                int codigo = scan.nextInt();
                scan.nextLine();
                if(produtos.containsKey(codigo)){
                    System.out.format("Valores originais:%n%s, R$ %.2f%n", produtos.get(codigo), precos.get(codigo));
                    System.out.print(">> Confirmar a exclusão (S/N)? ");
                    String resposta = scan.nextLine().trim();
                    if(resposta.toUpperCase().equals("S")){
                        produtos.remove(codigo);
                        precos.remove(codigo);
                        System.out.println("--------------------");
                        System.out.println("Produto removido com sucesso!");
                    }else{
                        System.out.println("Remoção não confirmada.");                        
                    }
                }else{
                    System.out.println("Produto não encontrado!");                    
                }
                System.out.println(">> Pressione ENTER para voltar ao menu...");
                System.in.read();
    }

    private static void listarProduto(HashMap<Integer, String> produtos, HashMap<Integer, Double> precos) throws IOException, InterruptedException {
        limparTela();
        System.out.println("PRODUTOS CADASTRADOS");
        System.out.println("-------------------------------");
        System.out.format("%6s | %-10s | %6s%n","Código", "Nome", "Preço");
        for(int chave: produtos.keySet()){
         System.out.format("%06d | %-10s | R$%6.2f%n", chave, produtos.get(chave), precos.get(chave));
        }
        System.out.println("-------------------------------");
        System.out.println(">> Pressione ENTER para voltar ao menu...");
        System.in.read();
    }

    private static void sairDoPrograma() throws IOException, InterruptedException {
        System.out.println("Saindo do programa...");
        Thread.sleep(1000);
        limparTela();
    }

    private static int lerOpcaoMenu(Scanner scan){
        System.out.println(">> Opção desejada: ");
        int opcaoEscolhida = scan.nextInt();
        scan.nextLine();
        return opcaoEscolhida;
    }

    private static void mostrarMenu(){
    System.out.println("CADASTRO DE PRODUTOS");
    System.out.println("--------------------");
    System.out.println("1 - Adicionar");
    System.out.println("2 - Alterar");
    System.out.println("3 - Remover");
    System.out.println("4 - Listar");
    System.out.println("5 - Sair");
    System.out.println("--------------------");
    }
}
