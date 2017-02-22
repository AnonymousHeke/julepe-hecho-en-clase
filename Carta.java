
/**
 * Write a description of class Carta here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Carta
{
    // Valor de la carta; de 1 a 7 y de 10 a 12
    private int valor;
    // Valor del palo: 0 es oros, 1 es copas, 2 es espadas y 3 es bastos
    private int palo;

    /**
     * Constructor for objects of class Carta
     */
    public Carta(int valor, int palo)
    {
        this.valor = valor;
        this.palo = palo;
    }

    public int getValor()
    {
        return valor;
    }

    public int getPalo()
    {
        return palo;
    }

    public String toString()
    {
        String textoADevolver = "";

        String textoValor = Integer.toString(valor);
        String textoPalo = "";

        switch (palo) {
            case 0:
            textoPalo = "oros";
            break;
            case 1:
            textoPalo = "copas";
            break;
            case 2:
            textoPalo = "espadas";
            break;
            case 3:
            textoPalo = "bastos";
            break;
        }

        switch (valor) {
            case 1:
            textoValor = "as";
            break;
            case 10:
            textoValor = "sota";
            break;
            case 11:
            textoValor = "caballo";
            break;
            case 12:
            textoValor = "rey";
            break;
        }

        textoADevolver = textoValor + " de " + textoPalo;

        return textoADevolver;
    }
  
  public boolean ganaA(Carta cartaACompararPreviamenteTirada, int paloQuePinta)
  {
  	boolean gana = false;
    
    if (palo == cartaACompararPreviamenteTirada.getPalo()) {
      // En caso de que tengan el mismo palo...
      if (getPosicionEscalaTute() > cartaACompararPreviamenteTirada.getPosicionEscalaTute()) {
        gana = true;
      }
    }
    else {
      // En caso de que tengan distinto palo...
      if (palo == paloQuePinta) {
        gana = true;
      }
    } 
    
    return gana;
  }
  
  
  public int getPosicionEscalaTute() 
  {
    int posicion = valor;
    
    if (valor == 3) {
      posicion = 13;
    }
    else if (valor == 1) {
      posicion = 14;
    }

    return posicion;    
  }
  
  
  
  
  
}



















