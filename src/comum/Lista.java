package comum;


public class Lista<T extends Comparable<T>>{
    private No inicio;
    private int size;
    
    public boolean add(T element, int index){
        if(!verifyIndex(index)){
            return false;
        }else{
            if(size == 0){
                inicio = new No(element);
            }else{
                No previousNo = getPreviousNo(index);

                if(previousNo.getProximo() == null){
                    previousNo.setProximo(new No(element));
                }else{
                    previousNo.setProximo(new No(element, previousNo.getProximo()));
                }
            }
            
            size++;
            return true;
        }
        
    }
    
    public boolean addLast(T element){
        return add(element, size);
    }

    public void addSort(T element){
        if(size == 0){
            inicio = new No(element);
            
        }else{
            No previousLowerNo = getLastLowerNo(element);

            if(previousLowerNo == null){
                No novoInicio = new No(element, inicio);
                inicio = novoInicio;
            }else{

                if(previousLowerNo.getProximo() == null){
                    previousLowerNo.setProximo(new No(element));
                }else{
                    previousLowerNo.setProximo(new No(element, previousLowerNo.getProximo()));
                }
            }
        }
        size++;
    }

    public T get(int index){
        if(!verifyIndex(index)){
            return null;
        }else{
            
            if(index == 0){
                return inicio.getInfo();
            }else{
                No PreviousNo = getPreviousNo(index);
                
                if(PreviousNo.getProximo() == null){
                    return null;
                }else{
                    return PreviousNo.getProximo().getInfo();
                }
            }
        }
    }

    public T remove(int index){
        if(!verifyIndex(index)){
            return null;
        }else{

            No PreviousNo = getPreviousNo(index);

            if(PreviousNo.getProximo() == null){
                return null;
            }else{
                No removeNo = PreviousNo.getProximo();

                if(removeNo.getProximo() != null){
                    PreviousNo.setProximo(removeNo.getProximo());
                    removeNo.setProximo(null);
                }
                return removeNo.getInfo();
            }
        }
    }
    
    private No getPreviousNo(int index){
        No aux = inicio;
        for (int i = 0; i < index-1; i++) {
            aux = aux.getProximo();
        }
        return aux;
    }

    private No getLastLowerNo(T element){
        No aux = inicio;

        if(element.compareTo(aux.getInfo()) < 0){
            return null;
        }else{
            while(aux.getProximo() != null){
                if(element.compareTo(aux.getProximo().getInfo()) < 0){//Se o próximo for maior
                    return aux;
                } 
                aux = aux.getProximo();
            }
            //Ultimo elemento da lista
            return aux;
        }
    }

    private boolean verifyIndex(int index){
        if(index < 0 || index > size){
            return false;
        }
        return true;
    }
    
    public int size(){
        return size;
    }
    
    @Override
    public String toString(){
        String str="";
        No local = inicio;
        while (local!= null){
            str += local.getInfo() + ", ";
            local = local.getProximo();
        }
        return str;
    }

    /////////////////////////////////////////////////////////
    public class No{
        private T info;
        private No proximo;
        //private No anterior;

        public No(T info){
            this.info = info;
            proximo = null;
        }
        public No(T info, No proximo){
            this.info = info;
            this.proximo = proximo;
        }

        public void setInfo(T info){
            this.info = info;
        }
        public void setProximo(No proximo){
            this.proximo = proximo;
        }
        /*
        public void setAnterior(No anterior){
            this.anterior = anterior;
        }
        */
        public T getInfo(){
            return info;
        }
        public No getProximo(){
            return proximo;
        }
        /*
        public No getAnterior(){
        return anterior;
    }
        */
    }


}