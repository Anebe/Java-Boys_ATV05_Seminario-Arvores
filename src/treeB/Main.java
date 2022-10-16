package treeB;


public class Main {
    public static void main(String[] args) {
        TreeB<Integer> teste = new TreeB<Integer>(4);

        teste.add(4);
        teste.imprimir();
        teste.add(51);
        teste.imprimir();
        teste.add(72);
        teste.imprimir();
        teste.add(-3);
        teste.imprimir();
        //teste.add(4100);
        //teste.imprimir();
        //teste.add(100);
        //teste.imprimir();
    }
    
}
