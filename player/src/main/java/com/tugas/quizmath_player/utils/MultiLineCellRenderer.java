/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tugas.quizmath_player.utils;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;


/**
 *
 * @author syafiq
 */
// Renderer custom untuk teks supaya wrap
public class MultiLineCellRenderer extends JTextArea implements TableCellRenderer {
    public MultiLineCellRenderer() {
        setLineWrap(true);
        setWrapStyleWord(true);
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        setText(value == null ? "" : value.toString());

        // warna default
        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        } else {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }

        // auto-adjust tinggi baris sesuai isi teks
        setSize(table.getColumnModel().getColumn(column).getWidth(), Short.MAX_VALUE);
        int preferredHeight = getPreferredSize().height;
        if (table.getRowHeight(row) < preferredHeight) {
            table.setRowHeight(row, preferredHeight);
        }

        return this;
    }
}
