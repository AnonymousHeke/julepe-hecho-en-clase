import java.util.Random;
import java.util.ArrayList;
/**
 * Write a description of class Jugador here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Jugador
{
    private String nombre;
    private Carta[] cartasQueTieneEnLaMano;
    private int numeroCartasEnLaMano;
    private ArrayList<Baza> bazasGanadas;

    /**
     * Constructor for objects of class Jugador
     */
    public Jugador(String nombreJugador)
    {
        nombre = nombreJugador;
        cartasQueTieneEnLaMano = new Carta[5];
        numeroCartasEnLaMano = 0;   
        bazasGanadas = new ArrayList<Baza>();
    }

    /**
     * Metodo que hace que el jugador reciba una carta
     */
    public void recibirCarta(Carta cartaRecibida)
    {
        if (numeroCartasEnLaMano < 5) {
            cartasQueTieneEnLaMano[numeroCartasEnLaMano] = cartaRecibida;
            numeroCartasEnLaMano++;
        }
    }

    /**
     * Metodo que muestra las cartas del jugador por pantalla
     */
    public void verCartasJugador()
    {
        for (Carta cartaActual : cartasQueTieneEnLaMano) {
            if (cartaActual != null) {
                System.out.println(cartaActual);
            }
        }
    }

    /**
     * Metodo que devuelve el nombre del jugador
     */
    public String getNombre()
    {
        return nombre;
    }

    /**
     * Metodo que devuelve la carta especificada como parametro si
     * el jugador dispone de ella y simula que se lanza a la mesa
     */    
    public Carta tirarCarta(String nombreCarta)
    {
        Carta cartaTirada = null;

        if (numeroCartasEnLaMano > 0) {

            int cartaActual = 0;
            boolean buscando = true;
            while (cartaActual < cartasQueTieneEnLaMano.length && buscando) {
                if (cartasQueTieneEnLaMano[cartaActual] != null) {                                 
                    if (nombreCarta.equals(cartasQueTieneEnLaMano[cartaActual].toString())) {
                        buscando = false;
                        cartaTirada = cartasQueTieneEnLaMano[cartaActual];
                        cartasQueTieneEnLaMano[cartaActual] = null;
                        numeroCartasEnLaMano--;
                        System.out.print(nombre + " ha tirado " + cartaTirada);
                        String cartasQueLeQuedan = "";
                        for (Carta carta : cartasQueTieneEnLaMano) {
                            if (carta != null) {
                                cartasQueLeQuedan += carta + " - ";
                            }
                        }
                        
                        if (cartasQueLeQuedan.length() > 0) {
                            System.out.println(" (le quedan: " + cartasQueLeQuedan.substring(0, cartasQueLeQuedan.length() - 3) + ")");
                        }
                        else {
                            System.out.println();
                        }
                    }
                }
                cartaActual++;
            }

            
        }

        return cartaTirada;
    }

    
    /**
     * Método que elige una carta aleatoria 
     */
    public Carta elegirCartaAleatoria() 
    {
        Carta cartaTirada = null;

        if (numeroCartasEnLaMano > 0) {
            Random aleatorio = new Random();

            boolean elJugadorHaTiradoUnaCarta = false;
            while (elJugadorHaTiradoUnaCarta == false) {
                int posicionAleatoria = aleatorio.nextInt(5);
                if (cartasQueTieneEnLaMano[posicionAleatoria] != null) {
                    cartaTirada = cartasQueTieneEnLaMano[posicionAleatoria];
                    elJugadorHaTiradoUnaCarta = true;
                }
            }

        }

        return cartaTirada;
    }

    /**
     * Método que tira una carta "inteligentemente"
     */
    public Carta tirarCartaInteligentemente(Palo paloPrimeraCartaDeLaBaza, 
                                            Carta cartaQueVaGanando,
                                            Palo paloQuePinta)
    {        
        Carta cartaATirar = null;

        // Puede ganar asistiendo?
        cartaATirar = puedeAsistirGanando(paloPrimeraCartaDeLaBaza, cartaQueVaGanando);
        if (cartaATirar == null) {        
            // Puede asistir aunque no gane?
            cartaATirar = puedeAsistirSinGanar(paloPrimeraCartaDeLaBaza);
            if (cartaATirar == null) {
                // Puede ganar fallando?
                cartaATirar = puedeGanarFallando(cartaQueVaGanando, paloQuePinta);
                if (cartaATirar == null) {                    
                    // Toca contrafallar (tirar una aleatoria)  
                    cartaATirar = elegirCartaAleatoria();   
                }
            }
            
         
        }

        tirarCarta(cartaATirar.toString());
        
        return cartaATirar;
    }

    /**
     * Metodo que hace que jugador recoja una baza ganada
     */
    public void addBaza(Baza bazaGanada)
    {
        bazasGanadas.add(bazaGanada);
    }

    /**
     * Metodo que devuelve el numero de bazas ganadas por el jugador hasta
     * el momento
     */
    public int getNumeroBazasGanadas() 
    {
        return bazasGanadas.size();
    }

    
    /**
     * Comprueba si un jugador puede ganar la baza asistiendo. Si es asi devuelve la
     * primer carta con la que es capaz de ganar la baza; si no puede, devuelve null.
     *
     * @param paloPrimeraCartaDeLaBaza El palo de inicio de la baza
     * @param cartaQueVaGanando La carta que va ganando hasta ese momento
     * @return La primera carta de las que tiene en la mano que gana asistiendo
     */
    public Carta puedeAsistirGanando(Palo paloPrimeraCartaDeLaBaza, Carta cartaQueVaGanando) 
    {
        Carta cartaADevolver = null;
        
        if (paloPrimeraCartaDeLaBaza == cartaQueVaGanando.getPalo()) {
            int i = 0;
            while (cartaADevolver == null && i < cartasQueTieneEnLaMano.length) {
                if (cartasQueTieneEnLaMano[i] != null) {
                    if ((cartasQueTieneEnLaMano[i].getPalo() == paloPrimeraCartaDeLaBaza) && 
                        (cartasQueTieneEnLaMano[i].getPosicionEscalaTute() > cartaQueVaGanando.getPosicionEscalaTute())) {
                            cartaADevolver = cartasQueTieneEnLaMano[i];
                        }
                }
                i++;
            }            
        }
                
        return cartaADevolver;
    }
    

    /**
     * Comprueba si un jugador tiene una carta al menos con la que asistir. Si la tiene,
     * devuelve la primera que encuentra; si no la tiene, devuelve null.
     *
     * @param paloPrimeraCartaDeLaBaza El palo con el que se inicio la baza
     * @return La primera carta encontrada que sirve para asistir
     */
    public Carta puedeAsistirSinGanar(Palo paloPrimeraCartaDeLaBaza) 
    {
        Carta cartaADevolver = null;
        
        int i = 0;
        while (cartaADevolver == null && i < cartasQueTieneEnLaMano.length) {
            if (cartasQueTieneEnLaMano[i] != null) {
                if ((cartasQueTieneEnLaMano[i].getPalo() == paloPrimeraCartaDeLaBaza)) {
                    cartaADevolver = cartasQueTieneEnLaMano[i];                 
                }
            }
            i++;             
        }
                
        return cartaADevolver;
    }
    
    
    public Carta puedeGanarFallando(Carta cartaQueVaGanando, Palo paloQuePinta) 
    {
        Carta cartaADevolver = null;
        
        int i = 0;
        while (cartaADevolver == null && i < cartasQueTieneEnLaMano.length) {
            if (cartasQueTieneEnLaMano[i] != null) {
                if ((cartasQueTieneEnLaMano[i].ganaA(cartaQueVaGanando, paloQuePinta))) {
                        cartaADevolver = cartasQueTieneEnLaMano[i];                 
                }
            }
            i++;             
        }
                
        return cartaADevolver;
    }    
    
}














