package projectoscc;


public class Saida extends Evento {
    private final Servico serv;
    
    Saida (double i, Simulador s, Servico serv){
        super(i, s);
        this.serv=serv;
    }

    
    @Override
    void executa (){
        serv.removeServico();
    }


    @Override
    public String toString(){
        return "Saida em " + instante;
    }

}