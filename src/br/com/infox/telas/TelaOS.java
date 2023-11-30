/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.com.infox.telas;

import java.sql.*;
import br.com.infox.dal.moduloConexao;
import javax.swing.JOptionPane;

/**
 *
 * @author Lucas Montello
 */
public class TelaOS extends javax.swing.JInternalFrame {

    Connection conector = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    private String tipo;

    /**
     * Creates new form TelaOS
     */
    public TelaOS() {
        initComponents();
        conector = moduloConexao.conector();
    }

    private void pesquisar_cliente() {
        String sql = "SELECT * FROM clientes WHERE cpfCli=?";
        try {
            pst = conector.prepareStatement(sql);
            pst.setString(1, txtOsCli.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                txtCliId.setText(rs.getString(2));
                txtOsNome.setText(rs.getString(3));
                txtOsFone.setText(rs.getString(5));
            } else {
                JOptionPane.showMessageDialog(null, "Usuário não cadastrado!");
                txtCliId.setText(null);
                txtOsNome.setText(null);
                txtOsFone.setText(null);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void emitir_os() {
        String sql = "INSERT INTO os(tipo, situacao, equipamento, defeito, servico, tecnico, valor, idcli) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            pst = conector.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, cboOsSit.getSelectedItem().toString());
            pst.setString(3, txtOsEquip.getText());
            pst.setString(4, txtOsDef.getText());
            pst.setString(5, txtOsServi.getText());
            pst.setString(6, txtOsTec.getText());
            pst.setString(7, txtOsValor.getText().replace(",", "."));
            pst.setString(8, txtCliId.getText());

            if ((txtCliId.getText().isEmpty()) || (txtOsEquip.getText().isEmpty()) || (txtOsDef.getText().isEmpty()) || (cboOsSit.getSelectedItem().equals(" "))) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigátorios");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Os emitida com sucesso");

                    btnOsAdicionar.setEnabled(false);
                    btnOsPesquisar.setEnabled(true);
                    
                    
                    limpar();
                    
                    btnOsAdicionar.setEnabled(true);

                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void pesquisar_os() {
        String num_os = JOptionPane.showInputDialog("Número da Os");
        String sql = "SELECT os, date_format(data_os, '%d/%m/%Y - %H:%i'), tipo, situacao, equipamento, defeito, servico,tecnico, valor, idcli from os WHERE os= " + num_os;
        try {
            pst = conector.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                txtOs.setText(rs.getString(1));
                txtData.setText(rs.getString(2));
                String rbtTipo = rs.getString(3);
                if (rbtTipo.equals("OS")) {
                    rbtOs.setSelected(true);
                    tipo = "OS";
                } else {
                    rbtRs.setSelected(true);
                    tipo = "Orçamento";
                }
                cboOsSit.setSelectedItem(rs.getString(4));
                txtOsEquip.setText(rs.getString(5));
                txtOsDef.setText(rs.getString(6));
                txtOsServi.setText(rs.getString(7));
                txtOsTec.setText(rs.getString(8));
                txtOsValor.setText(rs.getString(9));
                txtCliId.setText(rs.getString(10));

                txtOsCli.setText(null);
                txtCliId.setText(null);
                txtOsNome.setText(null);
                txtOsFone.setText(null);

                btnOsAdicionar.setEnabled(true);
                btnOsPesquisar.setEnabled(true);
                btnCliPes.setEnabled(true);
                

                btnOsAtualizar.setEnabled(true);
                btnOsDeletar.setEnabled(true);
               
                
                btnOsAdicionar.setEnabled(false);
                btnOsPesquisar.setEnabled(false);
                btnCliPes.setEnabled(false);
                
                

            } else {
                JOptionPane.showMessageDialog(null, "Os não cadastrada!");
            }
        } catch (java.sql.SQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(null, "Os inválida!");
        } catch (Exception e2) {
            JOptionPane.showMessageDialog(null, "e2");
        }
    }

    private void alterar_os() {
        String sql = "UPDATE os SET tipo=?, situacao=?, equipamento=?, defeito=?, servico=?, tecnico=?, valor=? WHERE os=?";
        try {
            pst = conector.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, cboOsSit.getSelectedItem().toString());
            pst.setString(3, txtOsEquip.getText());
            pst.setString(4, txtOsDef.getText());
            pst.setString(5, txtOsServi.getText());
            pst.setString(6, txtOsTec.getText());
            pst.setString(7, txtOsValor.getText().replace(",", "."));
            pst.setString(8, txtOs.getText());

            if ((txtOsEquip.getText().isEmpty()) || (txtOsDef.getText().isEmpty()) || (cboOsSit.getSelectedItem().equals(" "))) {
                btnOsAdicionar.setEnabled(false);
                btnCliPes.setEnabled(false);
                ;
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigátorios");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Os alterada com sucesso");
                    limpar();
                    
                    btnCliPes.setEnabled(true);
                    btnOsAdicionar.setEnabled(true);
                    btnOsPesquisar.setEnabled(true);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void excluir_os() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir essa OS", "Atenão", JOptionPane.YES_NO_OPTION);

        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM os WHERE OS=?";
            try {
                pst = conector.prepareStatement(sql);
                pst.setString(1, txtOs.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "OS excluida com sucesso!");
                    limpar();
                    btnOsAdicionar.setEnabled(true);
                    btnCliPes.setEnabled(true);
                    btnOsPesquisar.setEnabled(true);

                } else {
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    private void limpar() {
        txtOs.setText(null);
        txtData.setText(null);
        txtOsCli.setText(null);
        txtOsNome.setText(null);
        txtOsFone.setText(null);
        cboOsSit.setSelectedItem(" ");
        txtCliId.setText(null);
        txtOsEquip.setText(null);
        txtOsDef.setText(null);
        txtOsServi.setText(null);
        txtOsTec.setText(null);
        txtOsValor.setText(null);

        btnOsAtualizar.setEnabled(false);
        btnOsDeletar.setEnabled(false);
        
        btnCliPes.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtOs = new javax.swing.JTextField();
        txtData = new javax.swing.JTextField();
        rbtRs = new javax.swing.JRadioButton();
        rbtOs = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        cboOsSit = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        btnCliPes = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCliId = new javax.swing.JTextField();
        txtOsNome = new javax.swing.JTextField();
        txtOsFone = new javax.swing.JTextField();
        txtOsCli = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtOsEquip = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtOsDef = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtOsServi = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtOsTec = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtOsValor = new javax.swing.JTextField();
        btnOsAdicionar = new javax.swing.JButton();
        btnOsPesquisar = new javax.swing.JButton();
        btnOsAtualizar = new javax.swing.JButton();
        btnOsDeletar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("OS");
        setPreferredSize(new java.awt.Dimension(640, 480));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Nº Os");

        jLabel2.setText("Data");

        txtOs.setEditable(false);

        txtData.setEditable(false);
        txtData.setFont(new java.awt.Font("Segoe UI", 1, 9)); // NOI18N
        txtData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtRs);
        rbtRs.setText("Orçamento");
        rbtRs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtRsActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtOs);
        rbtOs.setText("Ordem de Serviço");
        rbtOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(txtOs, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rbtRs)
                        .addGap(18, 18, 18)
                        .addComponent(rbtOs)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtRs)
                    .addComponent(rbtOs))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jLabel3.setText("*Situação");

        cboOsSit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Na bancada", "Entrega Ok", "Orçamento Reprovado", "Aguardando Aprovação", "Aguardando Peças", "Abandonado pelo Cliente", "Retornou" }));
        cboOsSit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboOsSitActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));
        jPanel2.setToolTipText("");

        btnCliPes.setText("Pesquisar");
        btnCliPes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCliPesActionPerformed(evt);
            }
        });

        jLabel4.setText("*Id");

        jLabel5.setText("Nome");

        jLabel6.setText("Fone");

        txtCliId.setEditable(false);

        txtOsCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOsCliActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(txtOsCli, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtOsNome)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCliPes)
                            .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 75, Short.MAX_VALUE))
                    .addComponent(txtOsFone))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCliPes)
                    .addComponent(txtOsCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtOsNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtOsFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel7.setText("*Equipamento");

        jLabel8.setText("*Defeito");

        txtOsDef.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOsDefActionPerformed(evt);
            }
        });

        jLabel9.setText("Serviço");

        jLabel10.setText("Técnico");

        jLabel11.setText("Valor Total");

        txtOsValor.setText("0");

        btnOsAdicionar.setText("Adicionar");
        btnOsAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsAdicionarActionPerformed(evt);
            }
        });

        btnOsPesquisar.setText("Buscar");
        btnOsPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsPesquisarActionPerformed(evt);
            }
        });

        btnOsAtualizar.setText("Atualizar");
        btnOsAtualizar.setEnabled(false);
        btnOsAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsAtualizarActionPerformed(evt);
            }
        });

        btnOsDeletar.setText("Deletar");
        btnOsDeletar.setEnabled(false);
        btnOsDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsDeletarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtOsServi, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtOsDef, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtOsEquip, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtOsTec, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtOsValor, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnOsAdicionar)
                        .addGap(18, 18, 18)
                        .addComponent(btnOsPesquisar)
                        .addGap(18, 18, 18)
                        .addComponent(btnOsAtualizar)
                        .addGap(18, 18, 18)
                        .addComponent(btnOsDeletar))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(94, 94, 94)
                                .addComponent(cboOsSit, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cboOsSit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtOsEquip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtOsDef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtOsServi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtOsTec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtOsValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOsAdicionar)
                    .addComponent(btnOsPesquisar)
                    .addComponent(btnOsAtualizar)
                    .addComponent(btnOsDeletar))
                .addContainerGap(81, Short.MAX_VALUE))
        );

        setBounds(0, 0, 640, 489);
    }// </editor-fold>//GEN-END:initComponents

    private void txtDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataActionPerformed

    private void cboOsSitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboOsSitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboOsSitActionPerformed

    private void btnCliPesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCliPesActionPerformed
        // TODO add your handling code here:
        pesquisar_cliente();
    }//GEN-LAST:event_btnCliPesActionPerformed

    private void txtOsDefActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOsDefActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOsDefActionPerformed

    private void txtOsCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOsCliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOsCliActionPerformed

    private void rbtRsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtRsActionPerformed
        // TODO add your handling code here:
        tipo = "orçamento";
    }//GEN-LAST:event_rbtRsActionPerformed

    private void rbtOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOsActionPerformed
        // TODO add your handling code here:
        tipo = "OS";
    }//GEN-LAST:event_rbtOsActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        rbtRs.setSelected(true);
        tipo = "orçamento";
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnOsAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsAdicionarActionPerformed
        // TODO add your handling code here:
        emitir_os();
    }//GEN-LAST:event_btnOsAdicionarActionPerformed

    private void btnOsPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsPesquisarActionPerformed
        // TODO add your handling code here:
        pesquisar_os();
    }//GEN-LAST:event_btnOsPesquisarActionPerformed

    private void btnOsAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsAtualizarActionPerformed
        // TODO add your handling code here:
        alterar_os();
    }//GEN-LAST:event_btnOsAtualizarActionPerformed

    private void btnOsDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsDeletarActionPerformed
        // TODO add your handling code here:
        excluir_os();
    }//GEN-LAST:event_btnOsDeletarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCliPes;
    private javax.swing.JButton btnOsAdicionar;
    private javax.swing.JButton btnOsAtualizar;
    private javax.swing.JButton btnOsDeletar;
    private javax.swing.JButton btnOsPesquisar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboOsSit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JRadioButton rbtOs;
    private javax.swing.JRadioButton rbtRs;
    private javax.swing.JTextField txtCliId;
    private javax.swing.JTextField txtData;
    private javax.swing.JTextField txtOs;
    private javax.swing.JTextField txtOsCli;
    private javax.swing.JTextField txtOsDef;
    private javax.swing.JTextField txtOsEquip;
    private javax.swing.JTextField txtOsFone;
    private javax.swing.JTextField txtOsNome;
    private javax.swing.JTextField txtOsServi;
    private javax.swing.JTextField txtOsTec;
    private javax.swing.JTextField txtOsValor;
    // End of variables declaration//GEN-END:variables
}
