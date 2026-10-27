package ec.edu.taller;

import javax.swing.*;

public class InterfazImpresiones extends JFrame {

    private final GestorImpresiones gestor = new GestorImpresiones(10);

    private JTextField txtCodigo, txtDocumento;
    private JComboBox<String> cmbSolicitante;
    private JSpinner spnPrioridad;

    private JLabel lblCodigo, lblSolicitante, lblDocumento, lblPrioridad, lblEstado;
    private JTextArea txtListado;
    private JLabel lblPendientes, lblDisponibles, lblCapacidad;

    public InterfazImpresiones() {
        cargarTrabajosIniciales();
        configurarVentana();
        actualizarContadores();
    }

    private void cargarTrabajosIniciales() {
        gestor.registrarTrabajo(new TrabajoImpresion("IMP-01", "DOCENTE", "Examen.pdf", 1));
        gestor.registrarTrabajo(new TrabajoImpresion("IMP-02", "ESTUDIANTE", "Informe.pdf", 5));
        gestor.registrarTrabajo(new TrabajoImpresion("IMP-03", "ADMINISTRATIVO", "Acta.pdf", 3));
        gestor.registrarTrabajo(new TrabajoImpresion("IMP-04", "ESTUDIANTE", "Tarea.pdf", 7));
        gestor.registrarTrabajo(new TrabajoImpresion("IMP-05", "DOCENTE", "Silabo.pdf", 2));
        gestor.registrarTrabajo(new TrabajoImpresion("IMP-06", "ADMINISTRATIVO", "Oficio.pdf", 4));
        gestor.registrarTrabajo(new TrabajoImpresion("IMP-07", "ESTUDIANTE", "Ensayo.pdf", 9));
        gestor.registrarTrabajo(new TrabajoImpresion("IMP-08", "DOCENTE", "Taller.pdf", 6));
    }

    private void configurarVentana() {
        setTitle("Centro de impresión universitario");
        setSize(560, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTabbedPane pestanas = new JTabbedPane();
        pestanas.addTab("REGISTRAR TRABAJO", crearPanelRegistro());
        pestanas.addTab("GESTIONAR TRABAJOS", crearPanelGestion());
        add(pestanas);
    }

    private JPanel crearPanelRegistro() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblCod = new JLabel("CODIGO");
        lblCod.setBounds(40, 40, 120, 25);
        panel.add(lblCod);

        txtCodigo = new JTextField();
        txtCodigo.setBounds(170, 40, 250, 25);
        panel.add(txtCodigo);

        JLabel lblSol = new JLabel("SOLICITANTE");
        lblSol.setBounds(40, 80, 120, 25);
        panel.add(lblSol);

        cmbSolicitante = new JComboBox<>(new String[]{"ESTUDIANTE", "DOCENTE", "ADMINISTRATIVO"});
        cmbSolicitante.setBounds(170, 80, 250, 25);
        panel.add(cmbSolicitante);

        JLabel lblDoc = new JLabel("DOCUMENTO");
        lblDoc.setBounds(40, 120, 120, 25);
        panel.add(lblDoc);

        txtDocumento = new JTextField();
        txtDocumento.setBounds(170, 120, 250, 25);
        panel.add(txtDocumento);

        JLabel lblPri = new JLabel("PRIORIDAD");
        lblPri.setBounds(40, 160, 120, 25);
        panel.add(lblPri);

        spnPrioridad = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        spnPrioridad.setBounds(170, 160, 80, 25);
        panel.add(spnPrioridad);

        JButton btnRegistrar = new JButton("REGISTRAR TRABAJO");
        btnRegistrar.setBounds(40, 240, 200, 35);
        btnRegistrar.addActionListener(e -> registrar());
        panel.add(btnRegistrar);

        JButton btnLimpiar = new JButton("LIMPIAR ELEMENTOS");
        btnLimpiar.setBounds(270, 240, 200, 35);
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        panel.add(btnLimpiar);

        return panel;
    }

    private JPanel crearPanelGestion() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        String[] titulos = {"CODIGO", "SOLICITANTE", "DOCUMENTO", "PRIORIDAD", "ESTADO"};
        for (int i = 0; i < titulos.length; i++) {
            JLabel titulo = new JLabel(titulos[i]);
            titulo.setBounds(30, 20 + i * 25, 120, 25);
            panel.add(titulo);
        }

        lblCodigo = new JLabel("-");
        lblCodigo.setBounds(160, 20, 300, 25);
        panel.add(lblCodigo);

        lblSolicitante = new JLabel("-");
        lblSolicitante.setBounds(160, 45, 300, 25);
        panel.add(lblSolicitante);

        lblDocumento = new JLabel("-");
        lblDocumento.setBounds(160, 70, 300, 25);
        panel.add(lblDocumento);

        lblPrioridad = new JLabel("-");
        lblPrioridad.setBounds(160, 95, 300, 25);
        panel.add(lblPrioridad);

        lblEstado = new JLabel("-");
        lblEstado.setBounds(160, 120, 300, 25);
        panel.add(lblEstado);

        JLabel lblTitulo = new JLabel("TRABAJOS PENDIENTES (ORDENADOS MENOR VALOR MAYOR PRIORIDAD)");
        lblTitulo.setBounds(30, 155, 500, 25);
        panel.add(lblTitulo);

        txtListado = new JTextArea();
        txtListado.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtListado);
        scroll.setBounds(30, 185, 490, 120);
        panel.add(scroll);

        lblPendientes = new JLabel();
        lblPendientes.setBounds(30, 320, 180, 30);
        panel.add(lblPendientes);

        JButton btnProcesar = new JButton("PROCESAR");
        btnProcesar.setBounds(270, 320, 250, 30);
        btnProcesar.addActionListener(e -> procesar());
        panel.add(btnProcesar);

        lblDisponibles = new JLabel();
        lblDisponibles.setBounds(30, 360, 180, 30);
        panel.add(lblDisponibles);

        JButton btnConsultar = new JButton("CONSULTAR SIGUIENTE");
        btnConsultar.setBounds(270, 360, 250, 30);
        btnConsultar.addActionListener(e -> consultar());
        panel.add(btnConsultar);

        lblCapacidad = new JLabel();
        lblCapacidad.setBounds(30, 400, 180, 30);
        panel.add(lblCapacidad);

        JButton btnListar = new JButton("LISTAR");
        btnListar.setBounds(270, 400, 250, 30);
        btnListar.addActionListener(e -> listar());
        panel.add(btnListar);

        return panel;
    }

    private void registrar() {
        String codigo = txtCodigo.getText();
        String documento = txtDocumento.getText().trim();

        if (codigo.trim().isEmpty() || documento.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe completar el código y el documento.");
            return;
        }
        if (gestor.consultarEspaciosDisponibles() == 0) {
            JOptionPane.showMessageDialog(this, "La cola está llena. Procese un trabajo para liberar espacio.");
            return;
        }
        if (gestor.existeTrabajo(codigo)) {
            JOptionPane.showMessageDialog(this, "Ya existe un trabajo pendiente con ese código.");
            return;
        }

        TrabajoImpresion trabajo = new TrabajoImpresion(
                codigo,
                (String) cmbSolicitante.getSelectedItem(),
                documento,
                (Integer) spnPrioridad.getValue());

        gestor.registrarTrabajo(trabajo);
        JOptionPane.showMessageDialog(this, "Trabajo registrado correctamente.");
        limpiarFormulario();
        actualizarContadores();
    }

    private void limpiarFormulario() {
        txtCodigo.setText("");
        txtDocumento.setText("");
        cmbSolicitante.setSelectedIndex(0);
        spnPrioridad.setValue(1);
    }

    private void consultar() {
        TrabajoImpresion trabajo = gestor.consultarSiguienteTrabajo();
        if (trabajo == null) {
            avisarColaVacia();
            return;
        }
        mostrarDetalle(trabajo);
    }

    private void procesar() {
        TrabajoImpresion trabajo = gestor.procesarSiguienteTrabajo();
        if (trabajo == null) {
            avisarColaVacia();
            return;
        }
        mostrarDetalle(trabajo);
        actualizarContadores();
        listar();
    }

    private void listar() {
        String texto = "";
        int orden = 1;
        for (TrabajoImpresion trabajo : gestor.listarTrabajos()) {
            texto += orden + ". " + trabajo + "\n";
            orden++;
        }
        txtListado.setText(texto);
    }

    private void avisarColaVacia() {
        mostrarDetalle(null);
        JOptionPane.showMessageDialog(this, "No hay trabajos pendientes en la cola.");
    }

    private void mostrarDetalle(TrabajoImpresion trabajo) {
        if (trabajo == null) {
            lblCodigo.setText("-");
            lblSolicitante.setText("-");
            lblDocumento.setText("-");
            lblPrioridad.setText("-");
            lblEstado.setText("-");
            return;
        }
        lblCodigo.setText(trabajo.getCodigo());
        lblSolicitante.setText(trabajo.getSolicitante());
        lblDocumento.setText(trabajo.getDocumento());
        lblPrioridad.setText(String.valueOf(trabajo.getPrioridad()));
        lblEstado.setText(trabajo.getEstado());
    }

    private void actualizarContadores() {
        lblPendientes.setText("PENDIENTES " + gestor.contarTrabajosPendientes());
        lblDisponibles.setText("DISPONIBLES " + gestor.consultarEspaciosDisponibles());
        lblCapacidad.setText("CAPACIDAD " + gestor.getCapacidadMaxima());
    }

    public static void main(String[] args) {
        new InterfazImpresiones().setVisible(true);
    }
}