/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectoscc;

/**
 *
 * @author Jointflower
 */
public class Transicao extends Evento {
    private final Servico serv;
    private final Servico serv1;
    private final Cliente c;

    Transicao(double i,Simulador s, Servico serv1, Servico serv2,Cliente c){
        super(i,s);
        this.serv = serv1;
        this.serv1 = serv2;
        this.c=c;
    }

    @Override
    void executa() {
        serv.removeServico();
        serv1.insereServico(c);
    }
    
    @Override
    public String toString(){
        return "Transicao em " + instante;
    }
    
       
}
