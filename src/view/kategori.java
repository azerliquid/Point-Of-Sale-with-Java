/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import config.koneksi;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Reza Azmi
 */
public class kategori extends javax.swing.JFrame {
    String id_kategori, kategori, id_unit, unit;
    koneksi koneksi = new koneksi();
    
    private DefaultTableModel modelKategori;
    private DefaultTableModel modelUnit;

    /**
     * Creates new form kategori
     */
    public kategori() {
        initComponents();
        tampilKategori();
        tampilUnit();
    }
    
    public void tambahKategori(){
        try {
            Connection connection = koneksi.SambungKeDatabase();
            Statement statement = connection.createStatement();
            String sql = "insert into kategori (nama_kategori)values('"+txtkategori.getText()+"')";
            statement.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Data Kategori Berhasil disimpan");
        } catch (SQLException |HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void tambahUnit(){
        try {
            Connection connection = koneksi.SambungKeDatabase();
            Statement statement = connection.createStatement();
            String sql = "insert into unit (nama_unit) values('"+txtunit.getText()+"')";
            statement.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Data Unit Berhasil disimpan");
        } catch (SQLException |HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void tampilKategori(){
        Object[]baris = {"Id","Nama Kategori"};
        modelKategori = new DefaultTableModel(baris,0);
        tbkategori.setModel(modelKategori);
        String sql = "select * from kategori";
        Connection con;
        Statement st;
        try {
            con = koneksi.SambungKeDatabase();
            st = con.createStatement();
            ResultSet hasil = st.executeQuery(sql);
            while (hasil.next()){
                String id = hasil.getString("id_kategori");
                String nama = hasil.getString("nama_kategori");
                Object[]data = {id, nama};
                modelKategori.addRow(data);    
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "menampilkan data gagal", "informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void tampilUnit(){
        Object[]baris = {"Id","Nama Unit"};
        modelUnit = new DefaultTableModel(baris,0);
        tbunit.setModel(modelUnit);
        String sql = "select * from unit";
        Connection con;
        Statement st;
        try {
            con = koneksi.SambungKeDatabase();
            st = con.createStatement();
            ResultSet hasil = st.executeQuery(sql);
            while (hasil.next()){
                String id = hasil.getString("id_unit");
                String nama = hasil.getString("nama_unit");
                Object[]data = {id, nama};
                modelUnit.addRow(data);    
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "menampilkan data gagal", "informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void editKategori(){
        lblkategori.setText(id_kategori);
        txtkategori.setText(kategori);
    }
    
    public void eksekusieditKategori(){
        try {
            Connection connection = koneksi.SambungKeDatabase();
            Statement statement = connection.createStatement();
            String sql = "UPDATE kategori SET nama_kategori='"+txtkategori.getText()+"'WHERE id_kategori='"+id_kategori+"'";
            statement.execute(sql);
            JOptionPane.showMessageDialog(null, "Data Kategori Berhasil diubah");
        } catch (SQLException |HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Gagal merubah");
        }
    }
    
    public void editUnit(){
        lblunit.setText(id_unit);
        txtunit.setText(unit);
    }
    
    public void eksekusieditUnit(){
        try {
            Connection connection = koneksi.SambungKeDatabase();
            Statement statement = connection.createStatement();
            String sql = "UPDATE unit SET nama_unit='"+txtunit.getText()+"'WHERE id_unit='"+id_unit+"'";
            statement.execute(sql);
            JOptionPane.showMessageDialog(null, "Data Kategori Berhasil diubah");
        } catch (SQLException |HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Gagal merubah");
        }
    }
    
    public void hapusKategori(){
        Connection con;
        PreparedStatement st;
        int baris=tbkategori.getSelectedRow();
        if(baris>=0){
            int jawab=JOptionPane.showConfirmDialog(rootPane, "Apakah yakin untuk menghapus data ini ?",null,JOptionPane.YES_NO_OPTION);
            if(jawab==0)
            {
                try {
                String hapus =tbkategori.getValueAt(baris,0).toString();
                String sql="DELETE FROM kategori WHERE id_kategori='"+hapus+"'";
                con = koneksi.SambungKeDatabase();
                st = con.prepareStatement(sql);
                st.execute();
                    JOptionPane.showMessageDialog(rootPane, "Data Berhasil Dihapus");
                    tampilKategori();
                } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, "Gagal menghapus data");
                }
            }else{
                JOptionPane.showMessageDialog(rootPane, "Data tidak dihapus");
            }
        }else{
            JOptionPane.showMessageDialog(rootPane, "Silahkan pilih data yang akan dihapus");
            }
    }
    
    public void hapusUnit(){
        Connection con;
        PreparedStatement st;
        int baris=tbunit.getSelectedRow();
        if(baris>=0){
            int jawab=JOptionPane.showConfirmDialog(rootPane, "Apakah yakin untuk menghapus data ini ?",null,JOptionPane.YES_NO_OPTION);
            if(jawab==0)
            {
                try {
                String hapus =tbunit.getValueAt(baris,0).toString();
                String sql="DELETE FROM unit WHERE id_unit='"+hapus+"'";
                con = koneksi.SambungKeDatabase();
                st = con.prepareStatement(sql);
                st.execute();
                    JOptionPane.showMessageDialog(rootPane, "Data Berhasil Dihapus");
                    tampilUnit();
                } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, "Gagal menghapus data");
                }
            }else{
                JOptionPane.showMessageDialog(rootPane, "Data tidak dihapus");
            }
        }else{
            JOptionPane.showMessageDialog(rootPane, "Silahkan pilih data yang akan dihapus");
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
        txtkategori = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbkategori = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtunit = new javax.swing.JTextField();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbunit = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblkategori = new javax.swing.JLabel();
        lblunit = new javax.swing.JLabel();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
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
        jLabel7.setText("Data Master Kategori & Unit");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(434, 434, 434)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(433, Short.MAX_VALUE))
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

        txtkategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtkategoriActionPerformed(evt);
            }
        });
        jPanel4.add(txtkategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 530, 283, 42));

        jLabel1.setFont(new java.awt.Font("Leelawadee", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Data Kategori");
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, 283, 51));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Nama Kategori");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 500, 147, 28));

        jButton10.setText("Tambah");
        jButton10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 590, 99, 44));

        jButton11.setText("Update");
        jButton11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 590, 99, 44));

        tbkategori.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tbkategori);

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, 475, 282));

        jLabel3.setFont(new java.awt.Font("Leelawadee", 1, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Form Tambah Unit");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 440, 283, 51));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Nama Unit");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 500, 147, 28));

        txtunit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtunitActionPerformed(evt);
            }
        });
        jPanel4.add(txtunit, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 530, 283, 42));

        jButton12.setText("Tambah");
        jButton12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 590, 99, 44));

        jButton13.setText("Update");
        jButton13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 590, 99, 44));

        tbunit.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tbunit);

        jPanel4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 90, 475, 282));

        jLabel5.setFont(new java.awt.Font("Leelawadee", 1, 24)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Form Tambah Kategori");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 440, 283, 51));

        jLabel6.setFont(new java.awt.Font("Leelawadee", 1, 24)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Data Unit");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 30, 283, 51));
        jPanel4.add(lblkategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(448, 494, -1, -1));
        jPanel4.add(lblunit, new org.netbeans.lib.awtextra.AbsoluteConstraints(1035, 494, -1, -1));

        jButton14.setText("Edit");
        jButton14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 380, 103, 37));

        jButton15.setText("Hapus");
        jButton15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 380, 103, 37));

        jButton16.setText("Hapus");
        jButton16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton16, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 380, 103, 37));

        jButton17.setText("Edit");
        jButton17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton17, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 380, 103, 37));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(207, 81, 1160, 660));

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

    private void txtkategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtkategoriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtkategoriActionPerformed

    private void txtunitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtunitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtunitActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        tambahKategori();
        lblkategori.setText("");
        txtkategori.setText("");
        tampilKategori();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        tambahUnit();
        lblunit.setText("");
        txtunit.setText("");
        tampilUnit();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        int ubahdata = tbkategori.getSelectedRow();
        if (ubahdata >= 0 ) {
            id_kategori = tbkategori.getValueAt(ubahdata,0).toString();
            kategori = tbkategori.getValueAt(ubahdata,1).toString();
            editKategori();
        }else{
            JOptionPane.showMessageDialog(rootPane, "Silahkan pilih data yang akan diubah");
        }
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        eksekusieditKategori();
        lblkategori.setText("");
        txtkategori.setText("");
        tampilKategori();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        int ubahdata = tbunit.getSelectedRow();
        if (ubahdata >= 0 ) {
            id_unit = tbunit.getValueAt(ubahdata,0).toString();
            unit = tbunit.getValueAt(ubahdata,1).toString();
            editUnit();
        }else{
            JOptionPane.showMessageDialog(rootPane, "Silahkan pilih data yang akan diubah");
        }
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        eksekusieditUnit();
        lblunit.setText("");
        txtunit.setText("");
        tampilUnit();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        hapusUnit();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        hapusKategori();
    }//GEN-LAST:event_jButton15ActionPerformed

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
            java.util.logging.Logger.getLogger(kategori.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(kategori.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(kategori.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(kategori.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new kategori().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblkategori;
    private javax.swing.JLabel lblunit;
    private javax.swing.JTable tbkategori;
    private javax.swing.JTable tbunit;
    private javax.swing.JTextField txtkategori;
    private javax.swing.JTextField txtunit;
    // End of variables declaration//GEN-END:variables
}
