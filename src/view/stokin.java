/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import config.koneksi;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Reza Azmi
 */
public class stokin extends javax.swing.JFrame {
    koneksi koneksi = new koneksi();
    Date skrg = new Date();
    String kode_barang;
    private DefaultTableModel model;

    /**
     * Creates new form stokin
     */
    public stokin() {
        initComponents();
        tampil_comboSupplier();
        tampil_comboKodeBarang();
        tglhariini();
        setForm();
        tampilData();
        
    }
    
    public void setForm(){
        txttanggal_masuk.setEnabled(false);
        txtnama_barang.setEnabled(false);
        txtunit.setEnabled(false);
        txtstok.setEnabled(false);
    }
    
    public void tglhariini(){
        SimpleDateFormat format= new SimpleDateFormat("dd MMMM yyyy");
        String tgl = format.format(skrg);
        txttanggal_masuk.setText(format.format(skrg));
    }
    
    public void getDataproduk() throws SQLException{
        kode_barang = (String) cmbkode_barang.getSelectedItem();
        try {
            Connection connection = koneksi.SambungKeDatabase();
            Statement statement = connection.createStatement();
            String sql="SELECT * FROM item WHERE kode_barang='"+kode_barang+"'";
            ResultSet rs = statement.executeQuery(  sql);
            while (rs.next()) {
            txtnama_barang.setText(rs.getString("nama"));
            txtunit.setText(rs.getString("unit_id"));
            txtstok.setText(rs.getString("stok"));
            }
        } catch (Exception e) {
        }
    }
    
    public void tampil_comboSupplier()
    {
        
        try {
            Connection connection = koneksi.SambungKeDatabase();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM supplier";
            ResultSet rs = statement.executeQuery(  sql);
            while (rs.next()) {
            cmbsupplier.addItem(rs.getString("nama"));
            }
        rs.last();
        int jumlahdata = rs.getRow();
        rs.first();
        } catch (Exception e) {
        }
    }
    
    public void tampil_comboKodeBarang()
    {
        
        try {
            Connection connection = koneksi.SambungKeDatabase();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM item";
            ResultSet rs = statement.executeQuery(  sql);
            while (rs.next()) {
                cmbkode_barang.addItem(rs.getString("kode_barang"));
            }
        rs.last();
        int jumlahdata = rs.getRow();
        rs.first();
        } catch (Exception e) {
        }
    }
    
    public void updateStok(){
        try {
            Connection connection = koneksi.SambungKeDatabase();
            Statement statement = connection.createStatement();
            String sql = "SELECT stok FROM item WHERE kode_barang='"+cmbkode_barang.getSelectedItem()+"'";
            statement.execute(sql);
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String stok = rs.getString("stok");
                int total_stok = Integer.parseInt(stok)+Integer.parseInt(txtjumlah.getText());
                String sql2 = "UPDATE item SET stok='"+total_stok+"'WHERE kode_barang='"+cmbkode_barang.getSelectedItem()+"'";
                statement.execute(sql2);
            }
            JOptionPane.showMessageDialog(null, "Data Berhasil simpan");
        } catch (SQLException |HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Data Berhasil simpan");
        }
    }
    
    public void tampilData(){
        Object[]baris = {"Id","Tanggal Masuk","Kode","Nama Barang", "Unit", "Stok Masuk", "Keterangan", "Supplier", "Harga", "Total"};
        model = new DefaultTableModel(baris,0);
        tbstok_in.setModel(model);
        String sql = "select * from stok_in";
        Connection con;
        Statement st;
        try {
            con = koneksi.SambungKeDatabase();
            st = con.createStatement();
            ResultSet hasil = st.executeQuery(sql);
            while (hasil.next()){
                String id = hasil.getString("id");
                String tgl_masuk = hasil.getString("tgl_masuk");
                String kode_barang2 = hasil.getString("kode_barang");
                String nama = hasil.getString("nama_barang");
                String unit = hasil.getString("unit");
                String stok = hasil.getString("stok_masuk");
                String keterangan = hasil.getString("keterangan");
                String supplier = hasil.getString("supplier");
                String harga_beli = hasil.getString("harga_beli");
                String total = hasil.getString("total_beli");
                Object[]data = {id,tgl_masuk,kode_barang2, nama, unit, stok, keterangan, supplier, harga_beli, total};
                model.addRow(data);    
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "menampilkan data gagal", "informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void tambah(){
        try {
            Connection connection = koneksi.SambungKeDatabase();
            Statement statement = connection.createStatement();
            int total = Integer.parseInt(txtharga.getText())*Integer.parseInt(txtjumlah.getText());
            String sql = "insert into stok_in (tgl_masuk, kode_barang, nama_barang, unit, stok_masuk, keterangan, supplier, harga_beli, total_beli)values('"+txttanggal_masuk.getText()+"','"+cmbkode_barang.getSelectedItem()+"','"+txtnama_barang.getText()+"','"+txtunit.getText()+"','"+txtjumlah.getText()+"','"+txtketerangan.getText()+"','"+cmbsupplier.getSelectedItem()+"','"+txtharga.getText()+"','"+total+"')";
            statement.executeUpdate(sql);
        } catch (SQLException |HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtnama_barang = new javax.swing.JTextField();
        txtstok = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtjumlah = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        cmbsupplier = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtketerangan = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        txttanggal_masuk = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtunit = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cmbkode_barang = new javax.swing.JComboBox<>();
        txtharga = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        lblstokin_id = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbstok_in = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        jButton25 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Leelawadee", 1, 36)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Data Master Stok Masuk");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(434, 434, 434)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(420, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setText("Nama Barang");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 90, 110, 25));

        txtnama_barang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnama_barangActionPerformed(evt);
            }
        });
        jPanel4.add(txtnama_barang, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 120, 250, 30));

        txtstok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtstokActionPerformed(evt);
            }
        });
        jPanel4.add(txtstok, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 190, 110, 35));

        jLabel5.setText("Stok");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 160, 110, 25));

        txtjumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtjumlahActionPerformed(evt);
            }
        });
        jPanel4.add(txtjumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 130, 110, 35));

        jLabel11.setText("Harga/unit");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 100, 110, 25));

        cmbsupplier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih Supplier --" }));
        cmbsupplier.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel4.add(cmbsupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 190, 250, 40));

        jLabel10.setText("Supplier");
        jPanel4.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 160, 238, 25));

        jLabel12.setText("Qty");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 100, 110, 25));

        txtketerangan.setColumns(20);
        txtketerangan.setRows(5);
        jScrollPane2.setViewportView(txtketerangan);

        jPanel4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 110, 250, 48));

        jLabel4.setText("Unit");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 160, 110, 25));

        txttanggal_masuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttanggal_masukActionPerformed(evt);
            }
        });
        jPanel4.add(txttanggal_masuk, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 250, 30));

        jLabel2.setText("Kode Barang");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 110, 25));

        txtunit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtunitActionPerformed(evt);
            }
        });
        jPanel4.add(txtunit, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 190, 110, 35));

        jLabel8.setBackground(new java.awt.Color(0, 0, 0));
        jLabel8.setFont(new java.awt.Font("Leelawadee", 1, 24)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Form Stok Masuk");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 241, 51));

        jLabel6.setText("Keterangan");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 80, 110, 25));

        cmbkode_barang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih Kode Barang --" }));
        cmbkode_barang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbkode_barang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbkode_barangItemStateChanged(evt);
            }
        });
        cmbkode_barang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbkode_barangMouseClicked(evt);
            }
        });
        cmbkode_barang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbkode_barangActionPerformed(evt);
            }
        });
        jPanel4.add(cmbkode_barang, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 250, 35));

        txtharga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txthargaActionPerformed(evt);
            }
        });
        jPanel4.add(txtharga, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 130, 110, 35));

        jLabel1.setText("Tanggal Masuk");
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 110, 25));

        lblstokin_id.setFont(new java.awt.Font("Tahoma", 0, 3)); // NOI18N
        jPanel4.add(lblstokin_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(182, 215, -1, -1));

        tbstok_in.setAutoCreateRowSorter(true);
        tbstok_in.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10"
            }
        ));
        jScrollPane1.setViewportView(tbstok_in);
        if (tbstok_in.getColumnModel().getColumnCount() > 0) {
            tbstok_in.getColumnModel().getColumn(0).setResizable(false);
            tbstok_in.getColumnModel().getColumn(0).setHeaderValue("Title 1");
            tbstok_in.getColumnModel().getColumn(1).setHeaderValue("Title 2");
            tbstok_in.getColumnModel().getColumn(2).setHeaderValue("Title 3");
            tbstok_in.getColumnModel().getColumn(3).setHeaderValue("Title 4");
            tbstok_in.getColumnModel().getColumn(4).setHeaderValue("Title 5");
            tbstok_in.getColumnModel().getColumn(5).setHeaderValue("Title 6");
            tbstok_in.getColumnModel().getColumn(6).setHeaderValue("Title 7");
            tbstok_in.getColumnModel().getColumn(7).setHeaderValue("Title 8");
            tbstok_in.getColumnModel().getColumn(8).setHeaderValue("Title 9");
            tbstok_in.getColumnModel().getColumn(9).setHeaderValue("Title 10");
        }

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 1110, 380));

        jButton3.setText("Update");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel4.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 190, 110, 35));

        jButton4.setText("Simpan");
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 190, 110, 35));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(216, 81, 1150, 660));

        jPanel5.setBackground(java.awt.Color.lightGray);
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton6.setBackground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Dashboard");
        jButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 160, 48));

        jButton7.setBackground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Stok In");
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 160, 48));

        jButton8.setBackground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Stok Out");
        jButton8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 160, 48));

        jButton9.setBackground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Produk");
        jButton9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 160, 48));

        jButton23.setBackground(new java.awt.Color(255, 255, 255));
        jButton23.setText("Kategori & Unit");
        jButton23.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton23, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, 160, 48));

        jButton24.setBackground(new java.awt.Color(255, 255, 255));
        jButton24.setText("Supplier");
        jButton24.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton24, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 460, 160, 48));

        jButton25.setBackground(new java.awt.Color(255, 255, 255));
        jButton25.setText("Karyawan");
        jButton25.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton25, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 510, 160, 48));

        jButton26.setBackground(new java.awt.Color(255, 255, 255));
        jButton26.setText("Log out");
        jButton26.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton26, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 590, 100, 40));

        jButton27.setBackground(new java.awt.Color(255, 255, 255));
        jButton27.setText("Customer");
        jButton27.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton27, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 410, 160, 48));

        jButton28.setBackground(new java.awt.Color(255, 255, 255));
        jButton28.setText("Kasir");
        jButton28.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton28, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 160, 48));

        jLabel9.setFont(new java.awt.Font("Leelawadee", 1, 36)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("POS");
        jPanel5.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 160, 60));
        jPanel5.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 210, 10));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(-8, 71, 230, 670));

        getContentPane().add(jPanel1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtnama_barangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnama_barangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnama_barangActionPerformed

    private void txtunitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtunitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtunitActionPerformed

    private void txtstokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtstokActionPerformed
        
    }//GEN-LAST:event_txtstokActionPerformed

    private void cmbkode_barangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbkode_barangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbkode_barangActionPerformed

    private void txthargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txthargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txthargaActionPerformed

    private void txttanggal_masukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttanggal_masukActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttanggal_masukActionPerformed

    private void cmbkode_barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbkode_barangMouseClicked
        
    }//GEN-LAST:event_cmbkode_barangMouseClicked

    private void cmbkode_barangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbkode_barangItemStateChanged
        try {
            getDataproduk();
        } catch (SQLException ex) {
            Logger.getLogger(stokin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cmbkode_barangItemStateChanged

    private void txtjumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtjumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtjumlahActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        tambah();
        updateStok();
        cmbkode_barang.setSelectedIndex(0);
        txtnama_barang.setText("");
        txtunit.setText("");
        txtstok.setText("");
        txtketerangan.setText("");
        cmbsupplier.setSelectedIndex(0);
        txtharga.setText("");
        txtjumlah.setText("");
        tampilData();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        menu log = new menu ();
        log.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        stokin log = new stokin();
        log.setExtendedState(JFrame.MAXIMIZED_BOTH);
        log.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        stokout log = new stokout();
        log.setExtendedState(JFrame.MAXIMIZED_BOTH);
        log.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        produk log = new produk();
        log.setExtendedState(JFrame.MAXIMIZED_BOTH);
        log.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        kategori log = new kategori ();
        log.setExtendedState(JFrame.MAXIMIZED_BOTH);
        log.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton23ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        supplier log = new supplier();
        log.setExtendedState(JFrame.MAXIMIZED_BOTH);
        log.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton24ActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        user log = new user();
        log.setExtendedState(JFrame.MAXIMIZED_BOTH);
        log.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        login log = new login();
        log.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton26ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        customer log = new customer();
        log.setExtendedState(JFrame.MAXIMIZED_BOTH);
        log.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton27ActionPerformed

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
        kasir log = new kasir();
        log.setExtendedState(JFrame.MAXIMIZED_BOTH);
        log.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton28ActionPerformed

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
            java.util.logging.Logger.getLogger(stokin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(stokin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(stokin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(stokin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new stokin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbkode_barang;
    private javax.swing.JComboBox<String> cmbsupplier;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblstokin_id;
    private javax.swing.JTable tbstok_in;
    private javax.swing.JTextField txtharga;
    private javax.swing.JTextField txtjumlah;
    private javax.swing.JTextArea txtketerangan;
    private javax.swing.JTextField txtnama_barang;
    private javax.swing.JTextField txtstok;
    private javax.swing.JTextField txttanggal_masuk;
    private javax.swing.JTextField txtunit;
    // End of variables declaration//GEN-END:variables
}
