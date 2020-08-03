package projectoscc;

import javax.swing.JTextField;

public class Simulador {


    private double instante;
    private final double media_chegA;//Media de chegada A
    private final double media_chegB;//Media de chegada B
    private final double mediaA1,mediaA2,mediaB1,mediaB2,media3;//Medias das maquinas para a Gaussiana
    private final double desvioA1,desvioA2,desvioB1,desvioB2,desvio3;//Desvio das maquinas para a Gaussiana
    private final int maquinasA1, maquinasA2, maquinasB1, maquinasB2, maquinas3;//N de maquinas
    private final int n_tempo;
    private final Servico A1,A2,B1,B2,Fim3;//Secoes, 1-Perfuracao,2-Polimento,3Envernizamento
    private final ListaEventos lista;

    // Construtor
    public Simulador(double mcA,double mcB,double mA1,double dA1,double mA2,double dA2,double mB1,
            double dB1,double mB2,double dB2,double m3,double d3,int maqA1,int maqA2,int maqB1,
            int maqB2,int maq3, int tempo) 
    {
	n_tempo = tempo;
	instante = 0;
        media_chegA=mcA;
        media_chegB=mcB;
        mediaA1=mA1;
        mediaA2=mA2;
        mediaB1=mB1;
        mediaB2=mB2;
        media3=m3;
        desvioA1=dA1;
        desvioA2=dA2;
        desvioB1=dB1;
        desvioB2=dB2;
        desvio3=d3;
        maquinasA1=maqA1;
        maquinasA2=maqA2;
        maquinasB1=maqB1;
        maquinasB2=maqB2;
        maquinas3=maq3;
	Fim3= new Servico(this,media3,desvio3,7,null,maquinas3,0);
        A2= new Servico(this,mediaA2,desvioA2,5,Fim3,maquinasA2,1);
        B2= new Servico(this,mediaB2,desvioB2,6,Fim3,maquinasB2,2);
        A1= new Servico(this,mediaA1,desvioA1,3,A2,maquinasA1,1);
        B1= new Servico(this,mediaB1,desvioB1,4,B2,maquinasB1,2);
	lista = new ListaEventos(this);
	insereEvento (new Chegada(instante, this,A1));
        insereEvento (new Chegada(instante, this,B1));
    }





	void insereEvento (Evento e1){
            lista.insereEvento (e1);
	}

	private void act_stats(){
            A1.act_stats();
            B1.act_stats();
            A2.act_stats();
            B2.act_stats();
            Fim3.act_stats();
	}

	private String[] relat (){
            String[] rel = new String[5];
            System.out.println();
            System.out.println("------- Resultados finais -------");
            System.out.println();
            System.out.println("-------PerfuracaoA---------------");
            rel[0]=A1.relat();
            System.out.println("-------PerfuracaoB---------------");
            rel[1]=B1.relat();
            System.out.println("------PolimentoA-----------------");
            rel[2]=A2.relat();
            System.out.println("-------PolimentoB----------------");
            rel[3]=B2.relat();
            System.out.println("-------Envernizamento-------------");
            rel[4]=Fim3.relat();
            
            return rel;
	}

	public String[] executa (){
		Evento e1;
		while (instante < n_tempo){
                    //lista.print(); 
                    e1 = (Evento)(lista.removeFirst()); 
                    this.instante = e1.getInstante();         
                    act_stats();                         
                    e1.executa();
                }
		relat();
                return relat();
	}


    public double getInstante() {
        return instante;
    }


    public double getMedia_chegA() {
        return media_chegA;
    }
    
    public double getMedia_chegB() {
        return media_chegB;
    }




}