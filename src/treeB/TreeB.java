package treeB;

import comum.Lista;

public class TreeB <T extends Comparable<T>>{
    private Pagina<T> raiz;
    private static int ordem;

    public TreeB(int ordem){
        TreeB.ordem = ordem;
    }

    
    public void imprimir(int tip){
        if(raiz != null){
            if(tip == 0)
                imprimirByPage(raiz, "");
            else if(tip == 1)
                imprimirByElement(raiz, "");

        }
    }

    private void imprimirByPage(Pagina<T> imprimir, String tab){
        System.out.println(tab + imprimir.chaves);
        for (int i = 0; i < imprimir.filhos.size(); i++) {
            imprimirByPage(imprimir.filhos.get(i), tab.concat("\t"));
        }
        
    } 
    
    private void imprimirByElement(Pagina<T> imprimir, String tab){
        for (int i = 0; i < imprimir.chaves.size(); i++) {
            if(imprimir.hasFilhos())
                imprimirByPage(imprimir.filhos.get(i), tab.concat("\t"));

            System.out.println(tab + imprimir.chaves.get(i));
        }
        if(imprimir.hasFilhos())
            imprimirByPage(imprimir.filhos.get(imprimir.chaves.size()), tab.concat("\t"));
    }
    
    public boolean search(T element){
        if(searchNode(element, raiz) != null){
            return true;
        }
        return false;
    }
    
    public void add(T elemento){
        if(raiz == null){//ARVORE VAZIA
            raiz = new Pagina<T>(null);
            raiz.addChave(elemento);
        }else{
            Pagina<T> aux = getFolha(elemento);
            T atingiuLimite = aux.addChave(elemento);

            while(atingiuLimite != null){//MELHORAR FUNCÇÃO ADD FILHO (ADDSORT)
                if(aux.pai == null){//SPLIT NA RAIZ
                    
                    Pagina<T> novoPai = new Pagina<>(null);
                    //novoPai.filhos.addLast(aux);
                    novoPai.addFilho(aux);
                    aux.pai = novoPai;
                    raiz = novoPai;

                }
                
                //System.out.println("PAI: " + aux.pai.chaves);
                //System.out.println("NODELIMITE: " + aux.chaves);

                atingiuLimite = split(aux.pai, aux, atingiuLimite);

                aux = aux.pai;
            }
        }
    }

    public void remove(T elemento){
        if(raiz != null){
            Pagina<T> aux = searchNode(elemento, raiz);
            
            if(!aux.hasFilhos()){
                //removeOfFolha(aux,elemento);

                aux.removeChave(elemento);

                //aux = aux.pai;
                while(aux.chaves.size() < ordemMinChave() && aux.pai != null){
                    if(!redistribution(aux)){
                        merge(aux);
                    }

                    aux = aux.pai;
                }

                if(aux == raiz && aux.chaves.size() == 0){
                    raiz = aux.filhos.get(0);
                    aux.pai = null;
                }
            }else{
                aux = replaceByFolha(aux, elemento);

                while(aux.chaves.size() < ordemMinChave() && aux.pai != null){
                    if(!redistribution(aux)){
                        merge(aux);
                    }

                    aux = aux.pai;
                }

                if(aux == raiz && aux.chaves.size() == 0){
                    raiz = aux.filhos.get(0);
                    aux.pai = null;
                }
            }
        }
    }

    private boolean redistribution(Pagina<T> node){

        int indexNode = searchIndexFilho(node, node.pai);

        Pagina<T> vizinhoEsq = node.pai.filhos.get(indexNode-1);
        Pagina<T> vizinhoDir = node.pai.filhos.get(indexNode+1);

        if(vizinhoEsq != null && vizinhoEsq.chaves.size() > ordemMinChave()){

            node.addChave(node.pai.chaves.remove(indexNode-1));
            node.pai.addChave(vizinhoEsq.chaves.remove(vizinhoEsq.chaves.size()-1));

            if(vizinhoEsq.hasFilhos()){
                Pagina<T> novoFilho = vizinhoEsq.filhos.remove(vizinhoEsq.filhos.size()-1);
                novoFilho.pai = node;
                node.addFilho(novoFilho);
            }
        }
        else if(vizinhoDir != null && vizinhoDir.chaves.size() > ordemMinChave()){
            
            node.addChave(node.pai.chaves.remove(indexNode));
            node.pai.addChave(vizinhoDir.chaves.remove(0));

            if(vizinhoDir.hasFilhos()){
                Pagina<T> novoFilho = vizinhoDir.filhos.remove(0);
                novoFilho.pai = node;
                node.addFilho(novoFilho);
            }
        }
        else{
            return false;
        }
        return true;
    }

    private boolean merge(Pagina<T> node){

        int indexNode = searchIndexFilho(node, node.pai);

        Pagina<T> vizinhoEsq = node.pai.filhos.get(indexNode-1);
        Pagina<T> vizinhoDir = node.pai.filhos.get(indexNode+1);

        if(vizinhoEsq != null){
            node.addChave(node.pai.chaves.remove(indexNode-1));
            //for (int i = 0; i < vizinhoEsq.chaves.size(); i++) {
            while(vizinhoEsq.chaves.size() > 0){
                
                node.addChave(vizinhoEsq.chaves.remove(0));

                if(vizinhoEsq.hasFilhos()){ 
                    Pagina<T> novoFilho = vizinhoEsq.filhos.remove(0);
                    novoFilho.pai = node;
                    node.addFilho(novoFilho);

                    if(vizinhoEsq.chaves.size() == 0){
                        novoFilho = vizinhoEsq.filhos.remove(0);
                        novoFilho.pai = node;
                        node.addFilho(novoFilho);
                    }
                }
            }

            node.pai.filhos.remove(indexNode-1);

        }else if(vizinhoDir != null){
            node.addChave(node.pai.chaves.remove(indexNode));

            //for (int i = 0; i < vizinhoDir.chaves.size(); i++) {
            while(vizinhoDir.chaves.size() > 0){
                node.addChave(vizinhoDir.chaves.remove(0));
                
                if(vizinhoDir.hasFilhos()){ 
                    Pagina<T> novoFilho = vizinhoDir.filhos.remove(0);
                    novoFilho.pai = node;
                    node.addFilho(novoFilho);

                    if(vizinhoDir.chaves.size() == 0){
                        novoFilho = vizinhoDir.filhos.remove(0);
                        novoFilho.pai = node;
                        node.addFilho(novoFilho);
                    }
                }
            }
            
            node.pai.filhos.remove(indexNode+1);

        }else{
            return false;
        }
        
        return true;
    }

    private Pagina<T> replaceByFolha(Pagina<T> node, T element){
        int indexElement = node.chaves.search(element);

        Pagina<T> aux = node.filhos.get(indexElement);
        while(aux.hasFilhos()){
            aux = aux.filhos.get(aux.filhos.size()-1);
        }

        node.removeChave(element);
        node.addChave(aux.chaves.remove(aux.chaves.size()-1));

        return aux;
    }

    private Pagina<T> searchNode(T elemento, Pagina<T> aux){
        T chaveRaizAtual;

        for (int i = 0; i < aux.chaves.size(); i++) {
            chaveRaizAtual = aux.chaves.get(i);

            if(elemento.compareTo(chaveRaizAtual) < 0){
                //aux = aux.filhos.get(i);
                //break;
                if(aux.hasFilhos())
                    return searchNode(elemento, aux.filhos.get(i));
                else
                    return null;
            }else if(elemento.compareTo(chaveRaizAtual) == 0){
                return aux;
            }else if(i+1 == aux.chaves.size()){//aqui ajeitar
                //aux = aux.filhos.get(aux.chaves.size());
                //break;
                if(aux.hasFilhos())
                    return searchNode(elemento, aux.filhos.get(aux.chaves.size()));
                else
                    return null;
            }
        }
        return null;
    }

    private int searchIndexFilho(Pagina<T> filho, Pagina<T> pai){
        for (int i = 0; i < pai.filhos.size(); i++) {
            if(pai.filhos.get(i) == filho){
                return i;
            }
        }
        return -1;
    }

    private T split(Pagina<T> paiParaAdcionar, Pagina<T> filhoParaSplit, T limiteExcedido){
        Pagina<T> novoFilho = filhoParaSplit.prepareSplit();

        paiParaAdcionar.addFilho(novoFilho);
        
        return paiParaAdcionar.addChave(limiteExcedido);
    }

    private Pagina<T> getFolha(T elemento){

        Pagina<T> aux = raiz;
        
        while(aux.hasFilhos()){
            //System.out.println("Entrou   " + elemento);
            T chaveRaizAtual;
            for (int i = 0; i < aux.chaves.size(); i++) {
                chaveRaizAtual = aux.chaves.get(i);

                if(elemento.compareTo(chaveRaizAtual) < 0){
                    aux = aux.filhos.get(i);
                    break;
                }else if(i+1 == aux.chaves.size()){//aqui ajeitar
                    aux = aux.filhos.get(aux.chaves.size());
                    break;
                }
            }
        }

        return aux;
    }

    private int ordemMinChave(){
        return ordem/2-1;
    }

    private int ordemMaxChave(){
        return ordem-1;
    }

    public class Pagina<U extends Comparable<U>> implements Comparable<Pagina<U>>{
        
        private Lista<U> chaves;
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
            if(chaves.size() < ordemMaxChave()){
                chaves.addSort(elemento);
                return null;
            }else{//Retorna chave do meio
                chaves.addSort(elemento);
                //return chaves.remove(ordem/2-1);
                return chaves.remove(chaves.size()/2 + (chaves.size()%2 - 1));
                //return chaves.remove(chaves.size()/2);
            }
        }

        public Pagina<U> addFilho(Pagina<U> filho){
            filhos.addSort(filho);
            return null;
        }

        @Override
        public int compareTo(Pagina<U> o) {
            return  chaves.get(0).compareTo(o.chaves.get(o.chaves.size()-1));
        }
        
        public boolean hasFilhos() {
            return (filhos.size() > 0);
        }
    
        public Pagina<U> prepareSplit(){//MELHORAR FILHO

            Pagina<U> metade = new Pagina<>(pai);

            int indexMetade = chaves.size()/2-1;
            metade.chaves = chaves.split(0, indexMetade);

            if(filhos.size() > 0)
                metade.filhos = filhos.split(0, indexMetade+1);
                
            
            for (int i = 0; i <= indexMetade; i++) {
                chaves.remove(0);
                
            }
            if(filhos.size() > 0){ 
                for (int i = 0; i <= indexMetade+1; i++) {
                    filhos.remove(0).pai = metade;
                }
            }

            return metade;
        }
    
        public U removeChave(U elemento){
            int index = chaves.search(elemento);
            if(index != -1){
                return chaves.remove(index);
            }
            return null;
        }
    }
}