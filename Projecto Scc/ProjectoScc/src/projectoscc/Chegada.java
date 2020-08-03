package projectoscc;

public class Chegada extends Evento {
    
    private final Servico serv;

    //Construtor
    Chegada (double i, Simulador s, Servico serv){
        super (i, s);
        this.serv=serv;
    }

    @Override
    void executa (){
	serv.insereServico (new Cliente());
        if(serv.getTipo()==1)
            s.insereEvento (new Chegada(s.getInstante()+Aleatorio.exponencial(s.getMedia_chegA(),1), s, serv));
        else if(serv.getTipo()==2)
            s.insereEvento (new Chegada(s.getInstante()+Aleatorio.exponencial(s.getMedia_chegB(),2), s, serv));
    }


    @Override
    public String toString(){
        return "Chegada em " + instante;
    }
}