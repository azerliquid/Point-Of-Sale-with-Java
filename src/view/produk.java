package view;

import config.koneksi;
import java.awt.HeadlessException;
import java.sql.Connection;
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
public class produk extends javax.swing.JFrame {
    
    String id, kode_barang, nama, kategori, unit, harga;
    koneksi koneksi = new koneksi();
    private DefaultTableModel model;

    /**
     * Creates new form produk
     */
    public produk() {
        initComponents();
        tampilItem();
        tampil_comboKategori();
        tampil_comboUnit();
    }
    
    public void tampil_comboKategori()
    {
        
        try {
            Connection connection = koneksi.SambungKeDatabase();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM kategori";
            ResultSet rs = statement.executeQuery(  sql);
            while (rs.next()) {
            cmbkategori.addItem(rs.getString("nama_kategori"));
            }
        rs.last();
        int jumlahdata = rs.getRow();
        rs.first();
        } catch (Exception e) {
        }
    }
    
    public void tampil_comboUnit()
    {
        
        try {
            Connection connection = koneksi.SambungKeDatabase();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM unit";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
            cmbunit.addItem(rs.getString("nama_unit"));
            }
        rs.last();
        int jumlahdata = rs.getRow();
        rs.first();
        } catch (Exception e) {
        }
    }
    
    public void tampilItem(){
        Object[]baris = {"Id","Kode Barang","Nama Barang", "Kategori", "Unit", "Harga", "Stok"};
        model = new DefaultTableModel(baris,0);
        tbItem.setModel(model);
        String sql = "select * from Item";
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
                String kategori = hasil.getString("category_id");
                String unit = hasil.getString("unit_id");
                String harga = hasil.getString("harga");
                String stok = hasil.getString("stok");
                Object[]data = {id,kode_barang, nama, kategori, unit, harga, stok};
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
            int stok = 0;
            String sql = "insert into item (kode_barang, nama, category_id, unit_id, harga, stok)values('"+txtkode.getText()+"','"+txtnama.getText()+"','"+cmbkategori.getSelectedItem()+"','"+cmbunit.getSelectedItem()+"','"+txtharga.getText()+"','"+stok+"')";
            statement.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Data Berhasil disimpan");
        } catch (SQLException |HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void edit(){
        lblitem_id.setText(id);
        txtkode.setText(kode_barang);
        txtnama.setText(nama);
        txtharga.setText(harga);
        cmbkategori.setSelectedItem(kategori);
        cmbunit.setSelectedItem(unit);
    }

    public void eksekusiedit(){
        try {
            Connection connection = koneksi.SambungKeDatabase();
            Statement statement = connection.createStatement();
            String sql = "UPDATE item SET kode_barang='"+txtkode.getText()+"',nama='"+txtnama.getText()+"',category_id='"+cmbkategori.getSelectedItem()+"',unit_id='"+cmbunit.getSelectedItem()+"',harga='"+txtharga.getText()+"'WHERE item_id='"+id+"'";
            statement.execute(sql);
            JOptionPane.showMessageDialog(null, "Data Berhasil diubah");
        } catch (SQLException |HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Gagal merubah");
        }
    }
    
    public void hapus(){
        Connection con;
        PreparedStatement st;
        int baris=tbItem.getSelectedRow();
        if(baris>=0){
            int jawab=JOptionPane.showConfirmDialog(rootPane, "Apakah yakin untuk menghapus data ini ?",null,JOptionPane.YES_NO_OPTION);
            if(jawab==0)
            {
                try {
                String hapus =tbItem.getValueAt(baris,0).toString();
                String sql="DELETE FROM item WHERE item_id='"+hapus+"'";
                con = koneksi.SambungKeDatabase();
                st = con.prepareStatement(sql);
                st.execute();
                    JOptionPane.showMessageDialog(rootPane, "Data Berhasil Dihapus");
                    tampilItem();
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

    private void showData(String key){ 
        model.getDataVector().removeAllElements();
        String where = "";     
        if(!key.isEmpty()){    
            where += "WHERE item_id LIKE '%"+key+"%' "  
                    + "OR kode_barang LIKE '%"+key+"%' "     
                    + "OR nama LIKE '%"+key+"%' "  
                    + "OR category_id LIKE '%"+key+"%' "      
                    + "OR unit_id LIKE '%"+key+"%'"
                    + "OR harga LIKE '%"+key+"%'"
                    + "OR stok LIKE '%"+key+"%'";
        } 
         String sql = "SELECT * FROM item "+where;    
         Connection conn;      
         Statement st;        
         ResultSet rs;   
         int baris = 0; 
          try {    
              conn = koneksi.SambungKeDatabase();
               st = conn.createStatement();  
               rs = st.executeQuery(sql);      
          while (rs.next()) {               
                   Object id = rs.getInt(1);
                   Object kode_barang = rs.getString(2);  
                   Object nama = rs.getString(3);   
                   Object category_id = rs.getString(4);  
                   Object unit_id = rs.getString(5);  
                   Object harga = rs.getInt(6);
                   Object stok = rs.getInt(7);
                   Object[] data = {id, kode_barang, nama, category_id, unit_id, harga, stok};   
                   model.insertRow(baris, data);
                   baris++;    
          } 
           st.close();       
           conn.close();        
           tbItem.revalidate();     
           tbItem.repaint();     
        } catch (SQLException e) { 
            System.err.println("showData(): "+e.getMessage());   
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
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbItem = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtkode = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtnama = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cmbkategori = new javax.swing.JComboBox<>();
        cmbunit = new javax.swing.JComboBox<>();
        txtharga = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        lblitem_id = new javax.swing.JLabel();
        txtcari = new javax.swing.JTextField();
        jButton15 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Leelawadee", 1, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Data Master Produk/Item");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(434, 434, 434)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(420, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Leelawadee", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Data Produk");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(594, 11, 369, 43));

        jLabel2.setFont(new java.awt.Font("Leelawadee", 1, 36)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Form Tambah Produk");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 37, 369, 43));

        tbItem.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tbItem);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(419, 94, 717, -1));

        jLabel3.setText("Kode Produk");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 108, -1, -1));

        txtkode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtkodeActionPerformed(evt);
            }
        });
        jPanel3.add(txtkode, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 128, 143, 32));

        jLabel4.setText("Nama Produk");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 178, -1, -1));

        txtnama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnamaActionPerformed(evt);
            }
        });
        jPanel3.add(txtnama, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 198, 313, 32));

        jLabel5.setText("Kategori");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 248, -1, -1));

        jLabel6.setText("Unit");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(233, 248, -1, -1));

        cmbkategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih Kategori --" }));
        cmbkategori.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbkategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbkategoriActionPerformed(evt);
            }
        });
        jPanel3.add(cmbkategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 268, 143, 32));

        cmbunit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih Unit --" }));
        cmbunit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel3.add(cmbunit, new org.netbeans.lib.awtextra.AbsoluteConstraints(233, 268, 138, 32));

        txtharga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txthargaActionPerformed(evt);
            }
        });
        jPanel3.add(txtharga, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 338, 143, 32));

        jLabel7.setText("Harga");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 318, -1, -1));

        jButton1.setText("Tambah");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 404, 86, 37));

        jButton2.setText("Update");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(233, 404, 86, 37));

        jButton3.setText("Hapus");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(796, 562, 86, 37));

        jButton4.setText("Edit");
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(692, 562, 86, 37));

        lblitem_id.setFont(new java.awt.Font("Tahoma", 0, 3)); // NOI18N
        jPanel3.add(lblitem_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(211, 128, -1, -1));

        txtcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcariActionPerformed(evt);
            }
        });
        txtcari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcariKeyTyped(evt);
            }
        });
        jPanel3.add(txtcari, new org.netbeans.lib.awtextra.AbsoluteConstraints(909, 60, 164, 28));

        jButton15.setText("CARI");
        jPanel3.add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(1079, 60, -1, 28));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(216, 101, 1150, 640));

        jPanel4.setBackground(java.awt.Color.lightGray);
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton6.setBackground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Dashboard");
        jButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 160, 48));

        jButton7.setBackground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Stok In");
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 160, 48));

        jButton8.setBackground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Stok Out");
        jButton8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 160, 48));

        jButton9.setBackground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Produk");
        jButton9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 160, 48));

        jButton10.setBackground(new java.awt.Color(255, 255, 255));
        jButton10.setText("Kategori & Unit");
        jButton10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, 160, 48));

        jButton13.setBackground(new java.awt.Color(255, 255, 255));
        jButton13.setText("Supplier");
        jButton13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 460, 160, 48));

        jButton11.setBackground(new java.awt.Color(255, 255, 255));
        jButton11.setText("Karyawan");
        jButton11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 510, 160, 48));

        jButton12.setBackground(new java.awt.Color(255, 255, 255));
        jButton12.setText("Log out");
        jButton12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 590, 100, 40));

        jButton14.setBackground(new java.awt.Color(255, 255, 255));
        jButton14.setText("Customer");
        jButton14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 410, 160, 48));

        jButton16.setBackground(new java.awt.Color(255, 255, 255));
        jButton16.setText("Kasir");
        jButton16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 160, 48));

        jLabel9.setFont(new java.awt.Font("Leelawadee", 1, 36)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("POS");
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 160, 60));
        jPanel4.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 210, 10));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(-8, 71, 220, 670));

        getContentPane().add(jPanel1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtkodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtkodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtkodeActionPerformed

    private void txtnamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnamaActionPerformed

    private void txthargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txthargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txthargaActionPerformed

    private void cmbkategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbkategoriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbkategoriActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        tambah();
        lblitem_id.setText("");
        txtnama.setText("");
        txtkode.setText("");
        txtharga.setText("");
        cmbkategori.setSelectedIndex(0);
        cmbunit.setSelectedIndex(0);
        tampilItem();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int ubahdata = tbItem.getSelectedRow();
        if (ubahdata >= 0 ) {
            id = tbItem.getValueAt(ubahdata,0).toString();
            kode_barang = tbItem.getValueAt(ubahdata,1).toString();
            nama = tbItem.getValueAt(ubahdata,2).toString();
            kategori = tbItem.getValueAt(ubahdata,3).toString();    
            unit = tbItem.getValueAt(ubahdata, 4).toString();
            harga = tbItem.getValueAt(ubahdata, 5).toString();
            edit();
        }else{
            JOptionPane.showMessageDialog(rootPane, "Silahkan pilih data yang akan diubah");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        eksekusiedit();
        lblitem_id.setText("");
        txtnama.setText("");
        txtkode.setText("");
        txtharga.setText("");
        cmbkategori.setSelectedIndex(0);
        cmbunit.setSelectedIndex(0);
        tampilItem();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        hapus();
    }//GEN-LAST:event_jButton3ActionPerformed

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

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        kategori log = new kategori ();
        log.setExtendedState(JFrame.MAXIMIZED_BOTH);
        log.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        supplier log = new supplier();
        log.setExtendedState(JFrame.MAXIMIZED_BOTH);
        log.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        user log = new user();
        log.setExtendedState(JFrame.MAXIMIZED_BOTH);
        log.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed

    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        customer log = new customer();
        log.setExtendedState(JFrame.MAXIMIZED_BOTH);
        log.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void txtcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariActionPerformed

    private void txtcariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyTyped
        String Key = txtcari.getText();
        showData(Key);
    }//GEN-LAST:event_txtcariKeyTyped

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        kasir log = new kasir();
        log.setExtendedState(JFrame.MAXIMIZED_BOTH);
        log.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        menu log = new menu ();
        log.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

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
            java.util.logging.Logger.getLogger(produk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(produk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(produk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(produk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new produk().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbkategori;
    private javax.swing.JComboBox<String> cmbunit;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
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
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblitem_id;
    private javax.swing.JTable tbItem;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextField txtharga;
    private javax.swing.JTextField txtkode;
    private javax.swing.JTextField txtnama;
    // End of variables declaration//GEN-END:variables
}
