package treeB;

import comum.Lista;

public class TreeB <T extends Comparable<T>>{
    private Pagina<T> raiz;
    private static int ordem;

    public TreeB(int ordem){
        TreeB.ordem = ordem;
    }

    public void imprimir(){
        System.out.println(raiz.chaves);
    }

    public void add(T elemento){
        if(raiz == null){
            raiz = new Pagina<T>(null);
            raiz.addChave(elemento);
        }else{
            Pagina<T> aux = getFolha(elemento);
            aux.addChave(elemento);
        }
    }

    private Pagina<T> getFolha(T elemento){
        Pagina<T> aux = raiz;
        
        while(aux.hasFilhos()){
            T chaveRaizAtual;
            for (int i = 0; i < aux.chaves.size(); i++) {
                chaveRaizAtual = aux.chaves.get(i);

                if(elemento.compareTo(chaveRaizAtual) < 0){
                    aux = aux.filhos.get(i);
                    break;
                }else if(i+1 == aux.chaves.size()){
                    aux = aux.filhos.get(i+1);
                }
            }
        }

        return aux;
    }

    public class Pagina<U extends Comparable<U>> implements Comparable<Pagina<U>>{
        
        private Lista<U> chaves;
        //private Lista<U> filhos;
        private Pagina<U> pai;
        private Lista<Pagina<U>> filhos;


        public Pagina(Pagina<U> pai){
            this.pai = pai;
            chaves = new Lista<U>();
            filhos = new Lista<Pagina<U>>();
        }

        public Pagina<U> getPai() {
            return pai;
        }
        public void setPai(Pagina<U> pai) {
            this.pai = pai;
        }

        public Lista<Pagina<U>> getFilhos() {
            return filhos;
        }

        public U addChave(U elemento){
            if(chaves.size() < ordem-1){
                chaves.addSort(elemento);
                return null;
            }else{//Retorna chave do meio
                chaves.addSort(elemento);
                return chaves.remove((ordem-1)/2);
            }
        }

        @Override
        public int compareTo(Pagina<U> o) {
            return 0;
        }

        
        public boolean hasFilhos() {
            return (filhos.size() > 0);
        }
    }
}

