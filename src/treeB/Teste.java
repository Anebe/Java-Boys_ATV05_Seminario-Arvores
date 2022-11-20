package treeB;

import java.util.StringTokenizer;

import comum.Arquivo;

public class Teste {
    public static void main(String[] args) {
        Arquivo arqBaseDedadosInicial = new Arquivo("entrada/base de dados inicial.txt");
        Arquivo arqComandos = new Arquivo("entrada/comandos.txt");

        TreeB<Integer> arvore;
        if(args.length > 0){
            arvore = new TreeB<Integer>(Integer.parseInt(args[0]));
        }else{
            arvore = new TreeB<Integer>(5);
        }
        
        Integer[] dadosIniciais = arqBaseDedadosInicial.readAllIntegers();

        for(int i=0; i < dadosIniciais.length; i++){
            arvore.add(dadosIniciais[i]);
        }

        String[] comandos = arqComandos.readAllStrings();

        for (String comando : comandos) {

            StringTokenizer operacao = new StringTokenizer(comando," ");
            String op = operacao.nextToken();
            Integer num = Integer.parseInt(operacao.nextToken());
            switch(op){
                case "criar":
                    System.out.println("Elemento " + num + " adicionado");
                    arvore.add(num);
                    break;
                case "remover":
                    System.out.println("Elemento " + num + " removido");
                    arvore.remove(num);
                    break;
                case "procurar":
                    if(arvore.search(num)){
                        System.out.println("O elemento " + num + " se encontra na árvore B");
                    }else{
                        System.out.println("O elemento " + num + " NÃO se encontra na árvore B");
                    }
                    break;
                case "imprimir":
                    arvore.imprimir(num);
                    break;
                default:
                    System.out.println("Inválido");
            }
        }
    }
}
