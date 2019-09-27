package view;

import config.koneksi;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import config.UserID;

/**
 *
 * @author Reza Azmi
 */
public class kasir extends javax.swing.JFrame {
    String id, kode_barang, nama, harga, kategori, unit;    
    koneksi koneksi = new koneksi();
    private int subtotal, grandtotal;
    java.util.Date invoice = new java.util.Date();
    java.util.Date tglhariini = new java.util.Date();
    private DefaultTableModel model;

    /**
     * Creates new form kasir
     */
    public kasir() {
        initComponents();
        tampilItem();
        tglhariini();
        invoice();
        tampil_comboSupplier();
    }
    
    public void invoice(){
        try {
            Connection connection = koneksi.SambungKeDatabase();
            Statement statement = connection.createStatement();
            String sql = "select * from no_invoice order by invoice desc";
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                String nofak = rs.getString("invoice").substring(1);
                String AN = "" + (Integer.parseInt(nofak) + 1);
                String Nol = "";

                if(AN.length()==1)
                {Nol = "000";}
                else if(AN.length()==2)
                {Nol = "00";}
                else if(AN.length()==3)
                {Nol = "0";}
                else if(AN.length()==4)
                {Nol = "";}

               lblinvoice.setText("F" + Nol + AN);
            } else {
               lblinvoice.setText("F0001");
            }

           }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
           }
    }
    
    public void tglhariini(){
        SimpleDateFormat format= new SimpleDateFormat("dd MMM YYYY");
        String tgl = format.format(tglhariini);
        lbltglhariini.setText(format.format(tglhariini));
        lblnamakasir.setText(UserID.getUserLogin());
    }
    
    public void tampilItem(){
        Object[]baris = {"Id","Kode Barang","Nama Barang", "Harga"};
        model = new DefaultTableModel(baris,0);
        tbproduk.setModel(model);
        String sql = "select * from item";
        Connection con;
        Statement st;
        try {
            con = koneksi.SambungKeDatabase();
            st = con.createStatement();
            ResultSet hasil = st.executeQuery(sql);
            while (hasil.next()){
                String id = hasil.getString("item_id");
                String kode_barang = hasil.getString("kode_barang");
                String nama = hasil.getString("nama");
                String harga = hasil.getString("harga");
                Object[]data = {id,kode_barang, nama, harga};
                model.addRow(data);    
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "menampilkan data gagal", "informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void pilih(){
        lblkode_barang.setText(kode_barang);
        lblNama_barang.setText(nama);
        lblharga.setText(harga);
    }
    
    public void livetotal()
    {
        int sum = 0;
        for(int i = 0; i < tbkeranjang.getRowCount(); i++)
        {
            sum = sum + Integer.parseInt(tbkeranjang.getValueAt(i, 4).toString());
        }
        lbltotal_belanja.setText(Integer.toString(sum));
    }
    
    public void tampil_comboSupplier()
    {
        
        try {
            Connection connection = koneksi.SambungKeDatabase();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM customer";
            ResultSet rs = statement.executeQuery(  sql);
            while (rs.next()) {
            cmbcustomer.addItem(rs.getString("nama"));
            }
        rs.last();
        int jumlahdata = rs.getRow();
        rs.first();
        } catch (Exception e) {
        }
    }
    
    public void reset(){
        
    }
    
    public void no_invoice(){
        if (cmbcustomer.getSelectedIndex() == 0 ) {
                JOptionPane.showMessageDialog(rootPane, "Customer masih kosong harap diisi !");
                cmbcustomer.requestFocus();
            }
        else{
            try {
                Connection connection = koneksi.SambungKeDatabase();
                Statement statement = connection.createStatement();
                if (cmbcustomer.getSelectedIndex() == 0 ) {
                    JOptionPane.showMessageDialog(rootPane, "Customer masih kosong harap diisi !");
                    cmbcustomer.requestFocus();
                }
                int diskon = Integer.parseInt(txtdiskon.getText());
                String sql = "insert into no_invoice (invoice, kasir, tanggal, customer, total_belanjaan, uang_bayar, potongan, kembalian)values('"+lblinvoice.getText()+"','"+lblnamakasir.getText()+"','"+lbltglhariini.getText()+"','"+cmbcustomer.getSelectedItem()+"','"+lbltotal_belanja.getText()+"','"+txtbayar.getText()+"','"+diskon+"','"+lblkembali.getText()+"')";
                statement.executeUpdate(sql);
                int jumlah_baris = tbkeranjang.getRowCount();
                    if (jumlah_baris == 0) {
                        JOptionPane.showMessageDialog(rootPane, "Table masih kosong");
                    }else{
                        int i=0;
                        while(i < jumlah_baris){
                            statement.executeUpdate("insert into detail_belanjaan (invoice, tgl_transaksi, kode_barang, harga, jumlah, sub_total) values('"+lblinvoice.getText()+"','"+lbltglhariini.getText()+"','"+tbkeranjang.getValueAt(i, 1)+"','"+tbkeranjang.getValueAt(i, 2)+"','"+tbkeranjang.getValueAt(i, 3)+"','"+tbkeranjang.getValueAt(i, 4)+"')");
                            i++;
                            }
                        JOptionPane.showMessageDialog(rootPane, "Berhasil menyimpan");
                        kasir log = new kasir();
                        log.setExtendedState(JFrame.MAXIMIZED_BOTH);
                        log.setVisible(true);
                        dispose();
                    }
            } catch (SQLException |HeadlessException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbproduk = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtjumlah = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblharga = new javax.swing.JLabel();
        lblkode_barang = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbkeranjang = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblinvoice = new javax.swing.JLabel();
        lbltglhariini = new javax.swing.JLabel();
        lbltotal_belanja = new javax.swing.JLabel();
        lblnamakasir = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cmbcustomer = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtbayar = new javax.swing.JTextField();
        txtdiskon = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        lblkembali = new javax.swing.JLabel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jSeparator1 = new javax.swing.JSeparator();
        jButton2 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        lblNama_barang = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Leelawadee", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SISTEM KASIR");

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Malgun Gothic Semilight", 1, 18)); // NOI18N
        jButton3.setText("Home");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(387, 387, 387)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(550, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        tbproduk.setAutoCreateRowSorter(true);
        tbproduk.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        tbproduk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbproduk.setGridColor(new java.awt.Color(204, 204, 204));
        tbproduk.setSelectionBackground(new java.awt.Color(51, 102, 255));
        tbproduk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbprodukMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbproduk);

        jPanel5.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 142, 540, 264));

        jLabel2.setFont(new java.awt.Font("Malgun Gothic Semilight", 1, 14)); // NOI18N
        jLabel2.setText("Kode Barang");
        jPanel5.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 130, 140, 26));

        jLabel3.setFont(new java.awt.Font("Malgun Gothic Semilight", 1, 14)); // NOI18N
        jLabel3.setText("Jumlah");
        jPanel5.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 340, 140, 26));
        jPanel5.add(txtjumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 370, 210, 30));

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setFont(new java.awt.Font("Malgun Gothic Semilight", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Tambah");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 420, 90, 32));

        jLabel4.setFont(new java.awt.Font("Leelawadee", 1, 24)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Daftar Produk");
        jPanel5.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 110, 168, 33));

        jLabel5.setFont(new java.awt.Font("Leelawadee", 1, 24)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Keranjang produk");
        jPanel5.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 420, 229, 33));

        jLabel6.setFont(new java.awt.Font("Malgun Gothic Semilight", 1, 14)); // NOI18N
        jLabel6.setText("Harga");
        jPanel5.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 280, 140, 21));

        lblharga.setFont(new java.awt.Font("Malgun Gothic Semilight", 1, 18)); // NOI18N
        jPanel5.add(lblharga, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 300, 210, 34));

        lblkode_barang.setFont(new java.awt.Font("Malgun Gothic Semilight", 1, 18)); // NOI18N
        jPanel5.add(lblkode_barang, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 160, 210, 34));

        tbkeranjang.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        tbkeranjang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Kode Barang", "Harga", "Jumlah", "Sub Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tbkeranjang);

        jPanel5.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 620, 264));

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel7.setFont(new java.awt.Font("Malgun Gothic Semilight", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Tanggal ");

        jLabel8.setFont(new java.awt.Font("Malgun Gothic Semilight", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Nomor Invoice");

        jLabel9.setFont(new java.awt.Font("Malgun Gothic Semilight", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Nama Kasir ");

        lblinvoice.setFont(new java.awt.Font("OCR-B 10 BT", 0, 18)); // NOI18N
        lblinvoice.setForeground(new java.awt.Color(255, 255, 255));

        lbltglhariini.setFont(new java.awt.Font("OCR-B 10 BT", 0, 18)); // NOI18N
        lbltglhariini.setForeground(new java.awt.Color(255, 255, 255));

        lbltotal_belanja.setFont(new java.awt.Font("Malgun Gothic Semilight", 1, 48)); // NOI18N
        lbltotal_belanja.setForeground(new java.awt.Color(255, 255, 255));
        lbltotal_belanja.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbltotal_belanja.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                lbltotal_belanjaInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        lbltotal_belanja.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                lbltotal_belanjaPropertyChange(evt);
            }
        });

        lblnamakasir.setFont(new java.awt.Font("OCR-B 10 BT", 0, 18)); // NOI18N
        lblnamakasir.setForeground(new java.awt.Color(255, 255, 255));

        jLabel11.setFont(new java.awt.Font("Malgun Gothic Semilight", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Customer ");

        jLabel14.setFont(new java.awt.Font("Malgun Gothic Semilight", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Total Belanjaan");

        cmbcustomer.setFont(new java.awt.Font("Malgun Gothic Semilight", 1, 14)); // NOI18N
        cmbcustomer.setForeground(new java.awt.Color(255, 255, 255));
        cmbcustomer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih Customer --", "Umum" }));
        cmbcustomer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel13.setBackground(new java.awt.Color(0, 0, 0));
        jLabel13.setFont(new java.awt.Font("Malgun Gothic Semilight", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Uang Bayar");

        jLabel15.setBackground(new java.awt.Color(0, 0, 0));
        jLabel15.setFont(new java.awt.Font("Malgun Gothic Semilight", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Diskon");

        txtbayar.setFont(new java.awt.Font("Malgun Gothic Semilight", 1, 18)); // NOI18N
        txtbayar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        txtdiskon.setFont(new java.awt.Font("Malgun Gothic Semilight", 1, 18)); // NOI18N
        txtdiskon.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtdiskon.setText("0");

        jLabel16.setBackground(new java.awt.Color(0, 0, 0));
        jLabel16.setFont(new java.awt.Font("Malgun Gothic Semilight", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Kembali");

        lblkembali.setBackground(new java.awt.Color(0, 0, 0));
        lblkembali.setFont(new java.awt.Font("Malgun Gothic Semilight", 1, 24)); // NOI18N
        lblkembali.setForeground(new java.awt.Color(255, 255, 255));
        lblkembali.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jToggleButton1.setBackground(new java.awt.Color(255, 255, 255));
        jToggleButton1.setFont(new java.awt.Font("Malgun Gothic Semilight", 1, 14)); // NOI18N
        jToggleButton1.setText("Hitung");
        jToggleButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Malgun Gothic Semilight", 1, 14)); // NOI18N
        jButton2.setText("Simpan");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblinvoice, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                            .addComponent(lbltglhariini, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblnamakasir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbcustomer, 0, 225, Short.MAX_VALUE))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(txtbayar, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtdiskon, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addComponent(lbltotal_belanja, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 546, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(137, 137, 137)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(176, 176, 176))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(216, 216, 216))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lblkembali, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(107, 107, 107))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(221, 221, 221))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(lblinvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblnamakasir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbltglhariini, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbcustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbltotal_belanja, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtbayar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtdiskon, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblkembali, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel5.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 130, 550, 590));

        jLabel10.setFont(new java.awt.Font("Malgun Gothic Semilight", 1, 14)); // NOI18N
        jLabel10.setText("Nama Barang");
        jPanel5.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 200, 130, 20));

        lblNama_barang.setFont(new java.awt.Font("Malgun Gothic Semilight", 1, 18)); // NOI18N
        jPanel5.add(lblNama_barang, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 230, 200, 40));

        jButton4.setBackground(new java.awt.Color(0, 0, 0));
        jButton4.setFont(new java.awt.Font("Malgun Gothic Semilight", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Reset");
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 570, 100, 30));

        jButton5.setBackground(new java.awt.Color(0, 0, 0));
        jButton5.setFont(new java.awt.Font("Malgun Gothic Semilight", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Checkout");
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 620, 100, 30));

        getContentPane().add(jPanel5);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        DefaultTableModel model2 = (DefaultTableModel) tbkeranjang.getModel();
        if (txtjumlah.getText().isEmpty() ) {
            JOptionPane.showMessageDialog(rootPane, "Jumlah masih kosong harap diisi !");
            txtjumlah.requestFocus();
        }else{
            subtotal = Integer.parseInt(lblharga.getText())*Integer.parseInt(txtjumlah.getText());
            model2.addRow(new Object[]{
                tbkeranjang.getRowCount()+1, 
                lblkode_barang.getText(), 
                lblharga.getText(), 
                txtjumlah.getText(),
                subtotal
            });
            lblkode_barang.setText("");
            lblNama_barang.setText("");
            lblharga.setText("");
            txtjumlah.setText("");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        livetotal();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void tbprodukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbprodukMouseClicked
        int ubahdata = tbproduk.getSelectedRow();
        if (ubahdata >= 0 ) {
            id = tbproduk.getValueAt(ubahdata,0).toString();
            kode_barang = tbproduk.getValueAt(ubahdata,1).toString();
            nama = tbproduk.getValueAt(ubahdata,2).toString();
            harga = tbproduk.getValueAt(ubahdata, 3).toString();
            pilih();
        }else{
            JOptionPane.showMessageDialog(rootPane, "Belum ada data yang terpilih");
        }
        tbproduk.clearSelection();
    }//GEN-LAST:event_tbprodukMouseClicked

    private void lbltotal_belanjaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_lbltotal_belanjaPropertyChange
        
    }//GEN-LAST:event_lbltotal_belanjaPropertyChange

    private void lbltotal_belanjaInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_lbltotal_belanjaInputMethodTextChanged
        
    }//GEN-LAST:event_lbltotal_belanjaInputMethodTextChanged

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        if (txtdiskon.getText().equals("0")) {
            grandtotal = Integer.parseInt(txtbayar.getText())-Integer.parseInt(lbltotal_belanja.getText());
        }else{
            int harga_diskon = (Integer.parseInt(lbltotal_belanja.getText())*Integer.parseInt(txtdiskon.getText())/100);
            int total2 = (Integer.parseInt(lbltotal_belanja.getText())-harga_diskon);
            grandtotal = Integer.parseInt(txtbayar.getText())-total2;
        }
        lblkembali.setText(Integer.toString(grandtotal));
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        no_invoice();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        kasir log = new kasir();
        log.setExtendedState(JFrame.MAXIMIZED_BOTH);
        log.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        menu log = new menu();
        log.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new kasir().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbcustomer;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JLabel lblNama_barang;
    private javax.swing.JLabel lblharga;
    private javax.swing.JLabel lblinvoice;
    private javax.swing.JLabel lblkembali;
    private javax.swing.JLabel lblkode_barang;
    private javax.swing.JLabel lblnamakasir;
    private javax.swing.JLabel lbltglhariini;
    private javax.swing.JLabel lbltotal_belanja;
    private javax.swing.JTable tbkeranjang;
    private javax.swing.JTable tbproduk;
    private javax.swing.JTextField txtbayar;
    private javax.swing.JTextField txtdiskon;
    private javax.swing.JTextField txtjumlah;
    // End of variables declaration//GEN-END:variables
}
