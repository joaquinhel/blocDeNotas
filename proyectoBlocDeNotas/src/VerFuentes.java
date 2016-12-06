
/**
 *Clase que he utilizado para recorrer el array de Fuentes que pueden ser utilizadas en la aplicaci�n.
 * Si queremos incluir tipos de letra nuevos, con ella podriamos ver todas las opciones disponibles.
 * @author Joaqu�n
 */
import java.awt.Font;
import java.awt.GraphicsEnvironment;

/**
 * @author Joaqu�n
 */
public class VerFuentes {

    Font[] todasFuentes = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();

    void saberFuentes() {
        for (int i = 0; i < todasFuentes.length; i++) {
            System.out.print(todasFuentes[i].getFontName() + " : ");
            System.out.print(todasFuentes[i].getFamily() + " : ");
            System.out.print(todasFuentes[i].getName());
            System.out.println();
        }
        //System.out.print(Arrays.toString(todasFuentes));
    }

}
