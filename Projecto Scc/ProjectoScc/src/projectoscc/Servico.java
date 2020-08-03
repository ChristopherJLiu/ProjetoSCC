package projectoscc;

import java.util.*;


public class Servico {
        private final int G_P;//Tipo de peça do serviço
	private int estado;
        private int atendidos;
        private final int stream;//seed para a normal
	private double temp_ult, soma_temp_esp, soma_temp_serv;
	private final Vector<Cliente> fila; 
	private final Simulador s;
        private final int n_maquinas;//N de máquinas no Servico
        private final double media;//Media
        private final double desvio;//Desvio
        private final Servico next;//Proximo Servico no caso de haver transicao caso contrário este parâmetro é Null
        private double[] norm;//Array que contém os 2 números gerados pela normal
        private boolean haveNextNextGaussian;//Para a escolha entre os 2 número gerado pela Normal

    // Construtor
    Servico (Simulador s, double media, double desvio,int stream, Servico next, int n, int gp){
    	this.s = s;
        this.fila = new Vector <Cliente>(); 
	this.estado = 0; 
	this.temp_ult = s.getInstante();
	this.atendidos = 0;
	this.soma_temp_esp = 0;
	this.soma_temp_serv = 0;
        this.media=media;
        this.desvio=desvio;
        this.stream=stream;
        this.n_maquinas=n;
        this.next=next;
        this.G_P=gp;
        this.norm= new double[2];
        this.haveNextNextGaussian=false;
    }


    public void insereServico (Cliente c){
	if (estado < n_maquinas) { 
            estado ++;
            if(next==null) 
                s.insereEvento (new Saida(s.getInstante()+getNormal(),s,this));
            else
                s.insereEvento (new Transicao(s.getInstante()+getNormal(),s,this,next,c));
	}
	else
            fila.addElement(c);
}

    
    public void removeServico (){
        atendidos++;
        if (fila.isEmpty()) 
            estado --;
        else { 
            Cliente c = (Cliente)fila.firstElement();
            fila.removeElementAt(0);
            if(next==null) 
                s.insereEvento (new Saida(s.getInstante()+getNormal(),s,this));
            else
                this.s.insereEvento (new Transicao(this.s.getInstante()+getNormal(),s,this,next,c));
        }
    }

	
    public void act_stats(){
	double temp_desde_ult = s.getInstante() - temp_ult;
	temp_ult = s.getInstante();
	soma_temp_esp += fila.size() * temp_desde_ult;
	soma_temp_serv += estado * temp_desde_ult;
    }

            
    public String relat (){
        String rel = new String();
	double temp_med_fila = soma_temp_esp / (atendidos+fila.size());
	double comp_med_fila = soma_temp_esp / s.getInstante();
	double utilizacao_serv = (soma_temp_serv / s.getInstante())/ n_maquinas;
	rel+="Tempo medio de espera "+temp_med_fila;
	rel+="\nComp. medio da fila "+comp_med_fila;
	rel+="\nUtilizacao do servico "+utilizacao_serv;
	rel+="\nTempo de simulacao "+s.getInstante(); // Valor actual
	rel+="\nNumero de pecas processadas "+atendidos;
	rel+="\nNumero de pecas na fila de espera "+fila.size(); // Valor actual
        return rel;
    }

    
    public int getAtendidos() {
        return atendidos;
    }
    
    public int getTipo(){
        return G_P;
    }
    
    public double getNormal(){
        if (haveNextNextGaussian) {
            haveNextNextGaussian = false;
            return Math.abs(norm[1]);
        }
        else{
            norm = Aleatorio.normal(media,desvio,stream);
            haveNextNextGaussian = true;
            return Math.abs(norm[0]);
        }
    }

}