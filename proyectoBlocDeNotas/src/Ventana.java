//Informaci�n para la tarea:https://docs.oracle.com/javase/7/docs/api/allclasses-frame.html

import java.awt.BorderLayout;
import java.awt.Color;
import static java.awt.Color.BLUE;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author Joaqu�n
 */
public class Ventana {

    public static void main(String[] args) {
        //Creamos un objeto de la clase EditorTexto, usando su constructor, necesitamos un String
        EditorTexto v = new EditorTexto("Editor de texto. Pr�ctica 7 Programaci�n");
    }
}

//Clase que desarrolla la aplicaci�n de un peque�o editor de textos con funcionalidades b�sicas.
//Hereda(extends)de JFrame e Implementa la interfaz ActionListener.
//ActionListener--> Situa a la clase como oyente, debemos desarrollar el m�todo ActionPerformed.
class EditorTexto extends JFrame implements ActionListener {

//Lo primero es declarar las varibles que vamos a utilizar en el ejercicio
    JPanel panelPrincipal, panelSuperior, panelInferior;
    JScrollPane scroll;
    JTextPane panelTexto;
    JComboBox comboTamano, comboFuente, comboTipo, comboColor;
    JMenuBar barraMenus;
    JMenu menuArchivo, menuEditar;
    JMenuItem itemNuevo, itemAbrir, itemGuardar, itemGuardarComo, itemCerrar, itemSalir;
    JMenuItem itemCortar, itemCopiar, itemPegar;
    JToolBar barraHerramientas;
    JFileChooser ventanaChooser; //Sirve para que el usuario elija un archivo
    Integer[] arrayTamano = {12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 50};
    String[] arrayTiposLetra = {"Normal", "Negrita", "Cursiva", "Negrita+cursiva"};
    String[] arrayFuentes = {"Arial", "Times New Roman", "Courier New", "Serif", "Blackadder ITC"};
    String[] arrayColor1 = {"Negro", "Azul", "Rojo", "Cyan", "Naranaja"};
    Object[] arrayColor = {Color.BLACK, BLUE, Color.RED, Color.CYAN, Color.ORANGE};
    FileNameExtensionFilter extensionesAdmitidas;
    File archivo;
    FileReader leerArchivo;
    BufferedReader textoEnBuffer;
    FileWriter escribirEnDisco;
    Font fuente; //Crea una nueva fuente con  el nombre, estilo y tama�o determinados.
    Color color;

    //M�todo constructor de la clase EditorTexto
    EditorTexto(String titulo) {//El constructor de la subclase EditorTexto recibe como par�metro un String
        //La llamada al constructor de la superclase(JFRAME), que viene definido con un par�metro tipo String.
        //El parametro de la clase super ser� el que hemos definido en el constructor de la clase EditarTexto
        super(titulo); 
        iniciarComponentes();
        a�adirListeners();
    }

    //M�todo privado que llama a los configuradores de todos los componentes y elementos de la aplicaci�n.
    private void iniciarComponentes() {
        configurarFuente();
        configurarFrame();
        configurarPanelSuperior();
        configurarPanelInferior();
        configurarFileChooser();
        configurarAtajosDeTeclado();
        setVisible(true);
    }

    // M�todo privado que configura los atajos de teclado. Define el comportamiento del itemMenu.   
    private void configurarAtajosDeTeclado() {
        /* setMnemonic()--> Establece el atajo de teclado para acceder a una opci�n, hereda
         de javax.swing.AbstractButton --> que define el comportamiento de los menuItems y botones.*/
        //KeyEvent --> Un evento que indica que se pulso un componente.
        //VK_A --> Obtiene el valor n�merico de la constante VK_A(tecla A).
        menuArchivo.setMnemonic(KeyEvent.VK_A);
        menuEditar.setMnemonic(KeyEvent.VK_E);
        itemNuevo.setMnemonic(KeyEvent.VK_N);
        //setAccelerator()--> Combinaci�n de teclas que invoca a los elementos oyentes. Su parametro es un KeyStroke. 
        //getKeyStroke-->Pulsaci�n de una tecla(apretar o soltar, puede ser cualquier de las dos acciones), sus par�mtros son:
        //(keyCode - El c�digo num�rico de la telca presionada y modifiers - a bitwise-onred combination of any modifiers*/
        //ActionEvent-->Indica que una acci�n esta ocurriendo como por ejempo:
        //ALT_MASK --> Indicador de que la tecla ALT permanece pulsada durante el evento
        //Por tanto la l�nea significa cuando se mantiene pulsada ALT y se pulsa N se actua sobre el itemNuevo.
        //No funciona si mantenemos pulsada la N y entonces le damos a ALT (accion-oyente).
        itemNuevo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
        itemAbrir.setMnemonic(KeyEvent.VK_A);
        itemAbrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
        itemGuardar.setMnemonic(KeyEvent.VK_G);
        itemGuardar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.ALT_MASK));
        itemGuardarComo.setMnemonic(KeyEvent.VK_Q);
        itemGuardarComo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        itemCerrar.setMnemonic(KeyEvent.VK_T);
        itemCerrar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.ALT_MASK));
        itemSalir.setMnemonic(KeyEvent.VK_C);
        itemSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
        itemCortar.setMnemonic(KeyEvent.VK_C);
        itemCortar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        itemCopiar.setMnemonic(KeyEvent.VK_O);
        itemCopiar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        itemPegar.setMnemonic(KeyEvent.VK_P);
        itemPegar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));

    }

    /* M�todo privado que a�ade los listeners (pone a la escucha) a cada uno de los componentes 
     fuente o de origen de eventos.
     Es decir ponemos como oyentes a distintos componentes de le interfaz creada.
     Como par�metro pasamos, en este caso this, es la propia clase Jframe la que hace de oyente y esta
     implementa la interfaz actionListener, que viene definido por el m�todo actionPerformed*/
    private void a�adirListeners() {
        itemNuevo.addActionListener(this); //Ponemos a la escucha de eventos a itemNuevo y asi con todo
        itemAbrir.addActionListener(this);
        itemGuardar.addActionListener(this);
        itemGuardarComo.addActionListener(this);
        itemCerrar.addActionListener(this);
        itemSalir.addActionListener(this);
        itemCopiar.addActionListener(this);
        itemCortar.addActionListener(this);
        itemPegar.addActionListener(this);
        comboFuente.addActionListener(this);
        comboTamano.addActionListener(this);
        comboTipo.addActionListener(this);
        comboColor.addActionListener(this);
    }

    /* M�todo que implementa la l�gica de la aplicaci�n mediante la gesti�n de los distintos tipos
     * de eventos que se pueden generar en la aplicaci�n. Usamos else if para ahorrar tiempo de computaci�n.*/
    //Cuando ocurre un actionEvent(hacer clic) llega a est� m�todo y se implementa la accion adecuada. 
    //Este caso se entender�a como: Est� ocurriendo la acci�n e.getSource(), es decir el flujo del programa 
    //est� en un determinado elemento(item o combo) y dependiendo de su nombre llamar�mos al m�todo que nos interese
    @Override //Reescribimos los m�todos de la interfaz ActionListener-en este caso actionPerformed
    //actionPerformed--> M�todo que marca que hacer cuando se recibe un evento por par�mentro
    public void actionPerformed(ActionEvent e) {
        if ((e.getSource() == itemNuevo)) { //Cuando hacemos clic en itemNuevo
            nuevoArchivo(); //Llamamos al m�todo nuevoArchivo (en todos los casos siguientes el funcionamiento es el mismo).
        } else if ((e.getSource() == itemAbrir)) {
            abrirArchivo();
        } else if ((e.getSource() == itemGuardar)) {
            guardarCambios();
        } else if ((e.getSource() == itemGuardarComo)) {
            guardarComo();
        } else if ((e.getSource() == itemCerrar)) {
            cerrarArchivo();
        } else if ((e.getSource() == itemSalir)) {
            salir();
        } else if ((e.getSource() == comboFuente) || (e.getSource() == comboTamano) || (e.getSource() == comboTipo) || (e.getSource() == comboColor)) {
            actualizarLetra();
        } else if ((e.getSource() == itemCortar)) {
            panelTexto.cut(); //Utilizamos el m�todo cortar de JTextpanel
        } else if ((e.getSource() == itemPegar)) {
            panelTexto.paste(); //Utilizamos el m�todo pegar de JTextpanel
        } else if ((e.getSource() == itemCopiar)) {
            panelTexto.copy(); //Utilizamos el m�todo copiar de JTextpanel
        }
    }

    //M�todo al que se llamar� cuando el usuario quiera abrir un nuevo archivo.
    private void nuevoArchivo() {
        panelTexto.setEditable(true); //Hacemos que se pueda editar el jTextPane
        panelTexto.setBackground(Color.WHITE); //Definimos su color a blanco
        panelTexto.setText("");//Borramos todo su contenido
        visibilizarComponentesFuente(true); //Hacemos visibles los comboBox.
    }

    /* M�todo para configurar la forma de trabajar de la JFileChooser. Esta lase muestra 
     directamente una ventana que permite navegar por los directorios y elegir un fichero.*/
    private void configurarFileChooser() {
        /*Con "getProperty(String key)" al que paso como par�metro ya definido("user.dir") determinamos 
         el directorio de trabajo actual. Este m�todo tambi�n sirve para determinar otras caracteristicas 
         actuales del sistema*/
        String directorioActual = System.getProperty("user.dir");
        //Abrimos la ventana de seleccion de archivos, en el directorio que nos encontramos
        ventanaChooser = new JFileChooser(directorioActual);
        /*El constructor de la clase "FileNameExtensionFilter" admite una descripci�n (que aparece en el
         fileChooser) y el nombre de la extensi�n*/
        extensionesAdmitidas = new FileNameExtensionFilter("Texto plano (*.txt)", "txt");
        //asignamos el las extensiones al JFileChooser
        ventanaChooser.setFileFilter(extensionesAdmitidas);
    }

    // M�todo al que se llamar� cuando el usuario quiera abrir un archivo existente.
    private void abrirArchivo() {
        /*Si hay un archivo abierto (isEditable), se preguntar� al usuario si quiere 
         guardar los cambios antes de mostrarle el filechooser para abrir otro archivo.*/
        if (panelTexto.isEditable()) {
            guardarCambios();
        }
        /*Creamos un una variable tipo int, que viene determinada por la opcion elegida en la ventana emergente 
         (showDialog) de la clase JFileChooser*/
        int opcionAbrir = ventanaChooser.showDialog(this, "Abrir Fichero");
        //Si la opcion que ha escogido el usuario es Abrir Fichero
        if (opcionAbrir == JFileChooser.APPROVE_OPTION) {
            archivo = ventanaChooser.getSelectedFile();//Obtenemos el nombre del archivo que ha seleccionado el usuario
            try {
                leerArchivo = new FileReader(archivo); //Leemos el archivo abierto
                textoEnBuffer = new BufferedReader(leerArchivo); //Guardamos el archivo en el buffer
                StringBuffer sb = new StringBuffer();//Creamos un objeto para guardar el String del archivo.
                String line = "";
                while ((line = textoEnBuffer.readLine()) != null) {
                    sb.append(line); //Mientras existan l�neas en el buffer las vamos a�adiendo al final
                    sb.append("\n");
                }
                textoEnBuffer.close(); //Cerramos la clase BufferdReader
                leerArchivo.close(); //Cerramos la clase FileReader
                panelTexto.setText(sb.toString());//Introducimos el contenido del StringBuffer (sb) en el cuadro de texto
            } catch (Exception ex) {
            }
            panelTexto.setEditable(true); //El panel debe ser editable
            panelTexto.setBackground(Color.WHITE); //De color blanco
            visibilizarComponentesFuente(true); //Se deben visualizar los combos de la fuente
        }
    }

    //M�todo que se llamar� cuando el usuario quiera guardar los cambios del archivo activo.
    private void guardarCambios() {
        /*Si la caja de texto no es editable, no se ejecutar� el m�todo, puesto que no hay ning�n
         archivo abierto. La opci�n no es �til.*/
        if (!panelTexto.isEditable()) {
            return;
        }

        /*Guardamos el valor de la eleccion del usuario en la ventana emergente. Los par�metros son:
         Componente padre, mensaje a mostrar en el dialog, titulo de la ventana emergente y botones. */
        int opcionGuardar = JOptionPane.showConfirmDialog(this, "�Desea guardar los cambios?", "Guardar", JOptionPane.YES_NO_CANCEL_OPTION);
        //OK_OPTION recoge el valor de retorno de la clase al pulsar ok
        if (opcionGuardar == JOptionPane.OK_OPTION) {
            if (archivo == null) {//si no hay archivo, es la primera vez que se guarda y por tanto es necesario hacer guardarComo()
                guardarComo();
            } else {
                String texto = panelTexto.getText(); //Obtenemos el texto del panel
                try {
                    escribirEnDisco = new FileWriter(archivo); //Abrimos el fichero guardado
                    escribirEnDisco.write(texto); //Escribimos en el fichero
                    escribirEnDisco.close(); //Cerramos el fichero
                } catch (Exception ex) {
                }
            }
        }
    }

    // M�todo privado que realiza la acci�n de guardado por primera vez de un archivo.
    private void guardarComo() {
        /*Si la caja de texto no es editable, no se ejecutar� el m�todo, puesto que no hay ning�n
         archivo abierto. La opci�n no es �til.*/
        if (!panelTexto.isEditable()) {
            return;
        }
        //Guardamos en una variable tipo int la elecci�n del usuario en el showSaveDialog()
        int saveOption = ventanaChooser.showSaveDialog(this);
        //Si la opci�n elegida se identifica con el 0
        if (saveOption == 0) {
            String texto = panelTexto.getText(); //Obetenemos el texto del panel
            try {
                archivo = ventanaChooser.getSelectedFile(); //Guardamos en archivo el fichero seleccionado
                escribirEnDisco = new FileWriter(archivo);  //Abrimos el fichero para escribir en �l
                escribirEnDisco.write(texto); //Escribimos el texto en el fichero
                escribirEnDisco.close(); //Cerramos el objeto que hemos creado para guardar en el fichero
            } catch (Exception ex) {
            }
        }
    }

    // M�todo privado que se ejecuta cuando se quiere cerrar un archivo.
    private void cerrarArchivo() {
        //primero se guardan los cambios.
        guardarCambios();
        //Inahabilitamos el textBox para que no se pueda escribir en el.
        panelTexto.setEditable(false);
        panelTexto.setText("");
        panelTexto.setBackground(Color.LIGHT_GRAY);
        visibilizarComponentesFuente(false); //Ocultamos los comboBox
    }

    // M�todo que se ejecuta cuando el usario decide salir de la aplicaci�n
    private void salir() {
        guardarCambios(); //Primero se guardan los cambios
        System.exit(0); //Despu�s se sale
    }

    //M�todo que sirve para visualizar, ocultar y configurar los comboBox y los valores que muestran por defecto.
    private void visibilizarComponentesFuente(boolean b) {
        //Si los par�metros que reciben son true, muestran los combos
        comboFuente.setVisible(b);
        comboTipo.setVisible(b);
        comboTamano.setVisible(b);
        comboColor.setVisible(b);
        //Por defecto mostramos el valor 0 del array que guarda cada una de las variables
        comboTamano.setSelectedIndex(0);
        comboTipo.setSelectedIndex(0);
        comboFuente.setSelectedIndex(0);
        comboColor.setSelectedIndex(0);
    }

    //M�todo privado al que se llama cuando se actualiza alguno de los comboBox
    private void actualizarLetra() {
        //Primero obtenemos los valores actuales seleccionados en cada comboBox (tipo, fte y tama�o)
        int tipo = comboTipo.getSelectedIndex();
        String fte = (String) comboFuente.getSelectedItem(); //Debe ser String para que sirva en el constructor
        Integer tama�o = (Integer) comboTamano.getSelectedItem(); //Debe ser integer para que sirva en el constructor
        Color colorSel = (Color) comboColor.getSelectedItem();
        /* Ahora creamos una nueva fuente. Usamos el constructor de la clase font 
         que pide fuente, tipo y tama�o de letra*/
        fuente = new Font(fte, tipo, tama�o);
        panelTexto.setFont(fuente);  //Establecemos la fuente seleccionada para el panelTexto
        panelTexto.setForeground(colorSel); //El argumento son m�todos est�ticos de la clase color.
        //color.getColor(colorSel);
    }

    // Metodo privado que permite configurar la fuente con la que arranca la aplicaci�n.
    private void configurarFuente() {
        comboFuente = new JComboBox(arrayFuentes); //El comboFuentes muestra el arrayFuentes
        comboTipo = new JComboBox(arrayTiposLetra); //El comboTipo muestra el arrayTipos
        comboTamano = new JComboBox(arrayTamano); //El comboTamano muestra el arrayTamano
        comboColor = new JComboBox(arrayColor); //El comboTamano muestra el arrayColor
        visibilizarComponentesFuente(false); //Al llamar a este m�todo no se deben ver los comboBox
    }

    // M�todo que ajusta los par�metros de la ventana principal y sus componentes.
    private void configurarFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Sale de la ventana usando el m�todo System.exit(0)
        setSize(400, 400); //Tama�o de la ventana (JFrame)
        panelPrincipal = new JPanel();//Crear un JPanel para almacenar los componentes
        setContentPane(panelPrincipal);//Establecer nuestro JPanel como el panel por defecto de la ventana
        //Layout es la dstribuci�n de elementos en la ventana
        //BorderLayout--> Organiza y establece el tama�o y posici�n de los componentes
        panelPrincipal.setLayout(new BorderLayout());
        //Creamos dos paneles
        panelSuperior = new JPanel();
        panelInferior = new JPanel();
        //A�adimos los dos paneles creados al panel principal
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);//El panel superior lo colocamos arriba
        panelPrincipal.add(panelInferior, BorderLayout.CENTER); //El panel inferior lo colocamos en el centro
        panelSuperior.setLayout(new GridLayout(2, 1)); //Hacmos que la ventana(panelSuperior)se divida en rectangulos 2*1
        panelInferior.setLayout(new BorderLayout()); //Organiza los componenetes sin espacio entre ellos
    }

    // M�todo que ajusta los par�metros del panel inferior y sus componentes
    private void configurarPanelInferior() {
        scroll = new JScrollPane();
        panelInferior.add(scroll);//a�adimos el componente al scroll
        panelTexto = new JTextPane();//creamos el �rea de texto
        panelTexto.setEditable(false); //El panel no ser� 
        panelTexto.setBackground(Color.LIGHT_GRAY);
        panelTexto.setFont(fuente); //En el m�todo configurar fuente queda definido con fuente, tipo y tama�o
        scroll.setViewportView(panelTexto);
    }

    //M�todo que ajusta los par�metros del panel superior y sus componentes.
    private void configurarPanelSuperior() {
        barraMenus = new JMenuBar();//Creamos la barra de menus     
        panelSuperior.add(barraMenus);//A�adimos la barra de menu al panel susperior

        barraHerramientas = new JToolBar(); //Creamos una barra de herramientas
        panelSuperior.add(barraHerramientas);//A�adimos la barra de menu de menu al panel susperior
        barraHerramientas.setFloatable(true); //Permitimos que el usuario pueda mover la barra       
        //A�adimos a la barra de herramientas tres combos para configurar el texto
        barraHerramientas.add(comboFuente);
        barraHerramientas.add(comboTipo);
        barraHerramientas.add(comboTamano);
        barraHerramientas.add(comboColor);

        menuArchivo = new JMenu("Archivo");//Creamos el menu Archivo
        barraMenus.add(menuArchivo);//a�adimos el menu Archivo a la barra de herramientas

        //Creamos todas las opciones del men� archivo y le damos nombre
        itemNuevo = new JMenuItem("Nuevo");
        itemAbrir = new JMenuItem("Abrir");
        itemGuardar = new JMenuItem("Guardar");
        itemGuardarComo = new JMenuItem("Guardar como...");
        itemCerrar = new JMenuItem("Cerrar");
        itemSalir = new JMenuItem("Salir");

        //A�adimos los item que cuelgan de la opci�n Archivo del Men�
        menuArchivo.add(itemNuevo);
        menuArchivo.add(itemAbrir);
        menuArchivo.add(itemGuardar);
        menuArchivo.add(itemGuardarComo);
        menuArchivo.add(itemCerrar);
        menuArchivo.add(itemSalir);

        menuEditar = new JMenu("Editar");//Creamos el menu Editar
        barraMenus.add(menuEditar);//A�adimos el menu Editar a la barra de herramienta

        //Creamos todas las opciones del men� opciones y le damos nombre
        itemCortar = new JMenuItem("Cortar");
        itemCopiar = new JMenuItem("Copiar");
        itemPegar = new JMenuItem("Pegar");

        //A�adimos los item que cuelgan de la opci�n Editar del Men�
        menuEditar.add(itemCortar);
        menuEditar.add(itemCopiar);
        menuEditar.add(itemPegar);
    }
}
