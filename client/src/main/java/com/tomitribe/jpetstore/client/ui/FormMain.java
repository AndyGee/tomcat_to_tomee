/*
 *
 *  * Licensed to the Apache Software Foundation (ASF) under one or more
 *  * contributor license agreements.  See the NOTICE file distributed with
 *  * this work for additional information regarding copyright ownership.
 *  * The ASF licenses this file to You under the Apache License, Version 2.0
 *  * (the "License"); you may not use this file except in compliance with
 *  * the License.  You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *  Unless required by applicable law or agreed to in writing, software
 *  *  distributed under the License is distributed on an "AS IS" BASIS,
 *  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  See the License for the specific language governing permissions and
 *  *  limitations under the License.
 *
 */
package com.tomitribe.jpetstore.client.ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.tomitribe.ee.rest.ComplexType;
import com.tomitribe.jpetstore.client.ejb.ClientEJB;
import com.tomitribe.jpetstore.client.rs.ClientRs;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.util.concurrent.atomic.AtomicInteger;

public class FormMain implements ActionListener {

    private static final AtomicInteger integer = new AtomicInteger(1);

    private JPanel p_main;
    private JPanel p_top;
    private JPanel p_bottom;
    private JEditorPane txt_view;
    private JButton bt_complex;
    private JTextField txt_host;
    private JButton bt_ejb;

    public FormMain() {
        $$$setupUI$$$();
        bt_complex.addActionListener(this);
        bt_ejb.addActionListener(this);
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        if (bt_complex.equals(e.getSource())) {

            final URI uri = URI.create(txt_host.getText());

            bt_complex.setEnabled(false);

            //Asynchronous fire and forget
            new ClientRs().setUri(uri).callComplexType(this);
        } else if (bt_ejb.equals(e.getSource())) {

            bt_complex.setEnabled(false);

            //Asynchronous fire and forget
            new ClientEJB().callEJB(this);
        }
    }

    /**
     * Called from the thread started in the {@link com.tomitribe.jpetstore.client.ejb.ClientEJB#callEJB(com.tomitribe.jpetstore.client.ui.FormMain)} method
     *
     * @param ct ComplexType
     */
    public void setComplexType(final ComplexType ct) {

        /**
         * REST STEP 6 - Nothing to do with REST here, but a nice example
         * of ensuring cross thread safety on returning my complex object.
         */

        final FormMain fm = FormMain.this;

        final Runnable r = new Runnable() {
            @Override
            public void run() {
                fm.safeSetComplexType(ct);
            }
        };

        if (SwingUtilities.isEventDispatchThread()) {
            r.run();
        } else {
            SwingUtilities.invokeLater(r);
        }
    }

    private void safeSetComplexType(final ComplexType ct) {
        try {

            /**
             * REST STEP 7 - Use the REST supplied object as you wish.
             * Just going to display it here in our form for you to see.
             *
             * This is the end of the REST trail, have fun with it!
             *
             * To test this just fire up the web-application in TomEE - After
             * the sever has started fire up the client-application.
             */
            final Document document = this.txt_view.getDocument();
            document.insertString(0, "\n", null);
            document.insertString(0, integer.getAndIncrement() + " - " + ct.getName() + " : " + ct.getDescription(), new SimpleAttributeSet());

        } catch (final BadLocationException e) {
            e.printStackTrace();
        } finally {
            bt_complex.setEnabled(true);
        }
    }

    public void setEjbResult(final int i) {
        final FormMain fm = FormMain.this;

        final Runnable r = new Runnable() {
            @Override
            public void run() {
                fm.safeSetEjbResult(i);
            }
        };

        if (SwingUtilities.isEventDispatchThread()) {
            r.run();
        } else {
            SwingUtilities.invokeLater(r);
        }
    }

    public void safeSetEjbResult(final int i) {
        try {

            /**
             * EJB STEP ? - Use the EJB supplied value as you wish.
             * Just going to display it here in our form for you to see.
             *
             * This is the end of the EJB trail, have fun with it!
             */
            final Document document = this.txt_view.getDocument();
            document.insertString(0, "\n", null);
            document.insertString(0, "EJB result - " + i, new SimpleAttributeSet());

        } catch (final BadLocationException e) {
            e.printStackTrace();
        } finally {
            bt_complex.setEnabled(true);
        }
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        p_main = new JPanel();
        p_main.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        p_top = new JPanel();
        p_top.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        p_main.add(p_top, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        bt_complex = new JButton();
        bt_complex.setText("Complex REST Call");
        bt_complex.setToolTipText("Asynchronous call to retrieve a REST call");
        p_top.add(bt_complex, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txt_host = new JTextField();
        txt_host.setText("http://localhost:8080");
        p_top.add(txt_host, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        p_bottom = new JPanel();
        p_bottom.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        p_main.add(p_bottom, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setHorizontalScrollBarPolicy(31);
        scrollPane1.setVerticalScrollBarPolicy(22);
        p_bottom.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        txt_view = new JEditorPane();
        txt_view.setText("[Make sure the server is running first, then click on a button to see the result]");
        scrollPane1.setViewportView(txt_view);
        bt_ejb = new JButton();
        bt_ejb.setText("Remote EJB Call");
        bt_ejb.setToolTipText("Asynchronous call to retrieve an EJB call");
        p_main.add(bt_ejb, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return p_main;
    }
}
