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
                System.out.println("- " + cartaActual);
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
                        
                        // Se muestran por pantalla las cartas que le quedan 
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
     * Método que tira una carta aleatoria 
     */
    public Carta tirarCartaAleatoria() 
    {
        Carta cartaTirada = null;
        
        
        if (numeroCartasEnLaMano > 0) {
            Random aleatorio = new Random();
            
            boolean elJugadorHaTiradoUnaCarta = false;
            while (elJugadorHaTiradoUnaCarta == false) {
                int posicionAleatoria = aleatorio.nextInt(5);
                if (cartasQueTieneEnLaMano[posicionAleatoria] != null) {
                    cartaTirada = tirarCarta(cartasQueTieneEnLaMano[posicionAleatoria].toString());
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
        Carta cartaTirada = null;
        Carta cartaATirar = null;
        
        // Si puedo asistir y ganar, lo hago
        int i = 0;
        while (i < cartasQueTieneEnLaMano.length && cartaATirar == null) {
            if (cartasQueTieneEnLaMano[i] != null) {
                if (cartasQueTieneEnLaMano[i].getPalo() == paloPrimeraCartaDeLaBaza) {
                    if (cartasQueTieneEnLaMano[i].ganaA(cartaQueVaGanando, paloQuePinta)) {
                        cartaATirar = cartasQueTieneEnLaMano[i];
                    }
                }
            }
            i++;
        }
       
        // Si no
        if (cartaATirar == null) {
            
            // Si puedo asistir, lo hago
            i = 0;
            while (i < cartasQueTieneEnLaMano.length && cartaATirar == null) {
                if (cartasQueTieneEnLaMano[i] != null) {                
                    if (cartasQueTieneEnLaMano[i].getPalo() == paloPrimeraCartaDeLaBaza) {
                        cartaATirar = cartasQueTieneEnLaMano[i];
                    }
                }    
                i++;                
            }
            
            // Si no
            if (cartaATirar == null) {
                
                // Si puedo fallar y ganar, lo hago
                i = 0;
                while (i < cartasQueTieneEnLaMano.length && cartaATirar == null) {
                    if (cartasQueTieneEnLaMano[i] != null) {                    
                        if (cartasQueTieneEnLaMano[i].ganaA(cartaQueVaGanando, paloQuePinta)) {
                            cartaATirar = cartasQueTieneEnLaMano[i];
                        }
                    }   
                    i++;                    
                }
                
                // Si no, tiro una aleatoria
                if (cartaATirar == null) {
                    cartaTirada = tirarCartaAleatoria();
                }
            }
            
        }
        
        
        if (cartaATirar != null) {
            cartaTirada = tirarCarta(cartaATirar.toString());
        }
                             
        return cartaTirada;
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
    
    
    
}




























