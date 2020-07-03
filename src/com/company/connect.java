package com.company;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Random;

public class connect extends JFrame {
    String addr_1, addr_2, addr_3, addr_4;
    JLabel l_address;
    HSSFSheet party_sheet, sell_sheet, vasuli_sheet;
    HSSFWorkbook party_workbook, sell_workbook, vasuli_workbook;
    JTextField t_address_1, t_address_2, t_address_3, t_address_4;
    private static ServerSocket server;
    Thread thread;
    OutputStream out;
    InputStream in;
    //convert ObjectInputStream object to String
    Socket socket;

    public connect() {
        try {
            get_address();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        setLayout(null);
        setVisible(true);
        setSize(500, 350);
        getContentPane().setBackground(new Color(rdm(), rdm(), rdm()));

        //header
        JPanel heading = new JPanel();
        heading.setBounds(0, 0, 500, 70);
        JLabel head = new JLabel("Connect");
        head.setFont(new Font("Monospaced", Font.BOLD, 40));
        head.setSize(100, 50);
        heading.setBackground(new java.awt.Color(rdm(), rdm(), rdm()));
        head.setForeground(new java.awt.Color(rdm(), rdm(), rdm()));
        heading.add(head);

        //Body Panel
        JPanel body = new JPanel();
        body.setLayout(null);
        body.setBounds(0, 80, 500, 200);
        body.setBackground(new java.awt.Color(rdm(), rdm(), rdm()));

        l_address = new JLabel("Create Network to continue");
        l_address.setBounds(50, 50, 150, 30);
        body.add(l_address);

        t_address_1 = new JTextField(addr_1);
        t_address_1.setBounds(200, 50, 30, 30);
        body.add(t_address_1);

        t_address_2 = new JTextField(addr_2);
        t_address_2.setBounds(235, 50, 30, 30);
        body.add(t_address_2);

        t_address_3 = new JTextField(addr_3);
        t_address_3.setBounds(270, 50, 30, 30);
        body.add(t_address_3);

        t_address_4 = new JTextField(addr_4);
        t_address_4.setBounds(305, 50, 30, 30);
        body.add(t_address_4);

        JButton b_create_network = new JButton("Create Network");
        b_create_network.setBounds(50, 100, 100, 50);
        b_create_network.addActionListener(f -> caught_create_server());
        body.add(b_create_network);

        JButton b_connect = new JButton("Connect");
        b_connect.setBounds(200, 100, 100, 50);
        b_connect.addActionListener(f -> listen_controller());
        body.add(b_connect);

        add(body);
        add(heading);
        //Thread thread = new Thread().setContextClassLoader(
    }

    private int rdm() {
        Random random = new Random();
        return random.nextInt(255);
    }

    private void listen_controller() {
        thread.start();
        int i = 0;
        while (i < 60) {
            try {
                Thread.sleep(1000);
                i++;
                System.out.println(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        thread.stop();
    }

    private void listen_connection() throws IOException {
        System.out.println("Waiting for the client request");
        //creating socket and waiting for client connection
        socket = server.accept();
        System.out.println(socket + " connection made");
        //read from socket to ObjectInputStream object
        in = socket.getInputStream();
        //convert ObjectInputStream object to String
        //create ObjectOutputStream object
        out = socket.getOutputStream();
        read();
        send();
    }

    private void caught_create_server() {
        try {
            create_server();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame("message"), "Error Occurred.. please check IP ..\nif problem persists please contact customer care");
        }
    }

    private void create_server() throws IOException {
        //create the socket server object
        Inet4Address inet4Address = (Inet4Address) Inet4Address.getByAddress("connect", (new byte[]{(byte) Integer.parseInt(t_address_1.getText()), (byte) Integer.parseInt(t_address_2.getText()), (byte) Integer.parseInt(t_address_3.getText()), (byte) Integer.parseInt(t_address_4.getText())}));
        server = new ServerSocket();
        //socket server port on which it will listen
        int port = 1447;
        SocketAddress endPoint = new InetSocketAddress(inet4Address, port);
        server.bind(endPoint);
        l_address.setText(server.getInetAddress().toString());
        //keep listens indefinitely until receives 'exit' call or program terminates
        thread = new Thread(() -> {
            try {
                listen_connection();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(new JFrame("message"), "No connection was made");
            }

        });
    }

    private void send() {
        DataOutputStream outputStream = new DataOutputStream(this.out);
        try {
            sendInfo(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("msg NOT sent");
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            send();
        }
        send2();
    }

    private void send2() {
        DataOutputStream outputStream = new DataOutputStream(this.out);
        try {
            send_party(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("msg NOT sent");
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            send2();
        }
    }

    private void send_party(DataOutputStream output) throws IOException {
        int num_of_parties = party_sheet.getLastRowNum() + 1;
        output.writeInt(num_of_parties);
        for (int i = 1; i < num_of_parties; i++) {
            Row row = party_sheet.getRow(i);
            output.writeUTF(row.getCell(1).getStringCellValue());
            output.writeUTF(row.getCell(7).getStringCellValue());
        }
        output.writeUTF("");
    }

    private void read() {
        try {
            DataInputStream input = new DataInputStream(in);
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream("vasuli.xls"));
            HSSFSheet sheet = workbook.getSheet("vasuli");
            int rows = input.readInt(), i = 0;
            String p_name;
            String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
            while (i < rows) {
                p_name = input.readUTF();
                if (p_name.equals("")) break;
                HSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
                row.createCell(0).setCellValue(p_name);
                row.createCell(1).setCellValue(date);
                row.createCell(2).setCellValue(input.readInt());
                i++;
            }
            workbook.write(new FileOutputStream("vasuli.xls"));
        } catch (IOException e) {
            System.out.println("msg NOT recvd");
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            read();
        }
    }

    private int calculate_back(String p_name, String date) {
        int back, opening_bal = 0, i = 0;
        HSSFRow row;
        row = party_sheet.getRow(i);
        while (i <= party_sheet.getLastRowNum()) {
            if (p_name.equals(row.getCell(1).getStringCellValue())) {
                opening_bal = (int) row.getCell(2).getNumericCellValue();
                break;
            }
            i++;
            row = party_sheet.getRow(i);
        }
        back = opening_bal;
        i = 0;
        int last_row = sell_sheet.getLastRowNum();
        row = sell_sheet.getRow(i);
        int cell_no;
        while (i <= last_row && !row.getCell(0).getStringCellValue().equals(date)) {
            if (row.getCell(1).getStringCellValue().equals(p_name)) {
                cell_no = 3;
                while (cell_no <= row.getLastCellNum()) {
                    //back = back + Integer.parseInt(row.getCell(cell_no).getStringCellValue());
                    back = back + ((int) row.getCell(cell_no).getNumericCellValue());
                    cell_no = cell_no + 2;
                }
            }
            i++;
            row = sell_sheet.getRow(i);
        }

        i = 0;
        row = vasuli_sheet.getRow(i);
        while (/*!row.getCell(1).getStringCellValue().equals(date) &&*/ i <= vasuli_sheet.getLastRowNum()) {
            if (row.getCell(0).getStringCellValue().equals(p_name)) {
                back = back - ((int) row.getCell(2).getNumericCellValue());
            }
            i++;
            row = vasuli_sheet.getRow(i);
        }
        return back;
    }


    private void sendInfo(DataOutputStream output) throws IOException {
        try {
            party_workbook = new HSSFWorkbook(new FileInputStream("party.xls"));
            sell_workbook = new HSSFWorkbook(new FileInputStream("sell.xls"));
            vasuli_workbook = new HSSFWorkbook(new FileInputStream("vasuli.xls"));
            sell_sheet = sell_workbook.getSheet("sell");
            vasuli_sheet = vasuli_workbook.getSheet("vasuli");
            party_sheet = party_workbook.getSheet("party");
        } catch (IOException e) {
            e.printStackTrace();
        }
        int num_of_parties = party_sheet.getLastRowNum() + 1;
        output.writeInt(num_of_parties);
        String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        int row = sell_sheet.getLastRowNum();
        HSSFRow _row = sell_sheet.getRow(row);
        ArrayList<String> names_done = new ArrayList<>();
        String p_name;
        int k;
        while (row > 0 && !_row.getCell(0).getStringCellValue().equals(date)) {
            row--;
            _row = sell_sheet.getRow(row);
        }
        while (_row.getCell(0).getStringCellValue().equals(date) && row > 0) {
            p_name = _row.getCell(1).getStringCellValue();
            names_done.add(p_name);
            output.writeUTF(p_name);
            output.writeInt(calculate_back(p_name, date));
            output.writeInt(_row.getLastCellNum() / 2);
            k = 2;
            while (k < _row.getLastCellNum()) {
                output.writeUTF(_row.getCell(k).getStringCellValue());
                k++;
                output.writeInt((int) _row.getCell(k).getNumericCellValue());
                k++;
            }
            row--;
            _row = sell_sheet.getRow(row);
        }
        int q = 1, back;
        while (q <= party_sheet.getLastRowNum()) {
            _row = party_sheet.getRow(q);
            p_name = _row.getCell(1).getStringCellValue();
            System.out.println(p_name);
            if (!names_done.contains(p_name) && (_row.getCell(4).getNumericCellValue() == 0 ||
                    _row.getCell(4).getNumericCellValue() == 3 ||
                    _row.getCell(4).getNumericCellValue() == 1)) {
                back = calculate_back(p_name, date);
                if (!p_name.equals("delete") && !p_name.equals("") && !(back == 0)) {
                    output.writeUTF(p_name);
                    output.writeInt(back);
                    output.writeInt(0);
                }

            }
            q++;
        }
        System.out.println("i am ending this all");
        output.writeUTF("");
    }

    @SuppressWarnings("rawtypes")
    private void get_address() throws SocketException {
        Enumeration eaa = NetworkInterface.getNetworkInterfaces();
        while (eaa.hasMoreElements()) {
            NetworkInterface n = (NetworkInterface) eaa.nextElement();
            Enumeration ee = n.getInetAddresses();
            while (ee.hasMoreElements()) {
                try {
                    Inet4Address i = (Inet4Address) ee.nextElement();
                    if (!i.isLoopbackAddress()) {
                        try {
                            String[] addr = i.getHostAddress().split("\\.", 4);
                            addr_4 = addr[3];
                            if (!addr_4.equals("1")) {
                                addr_1 = addr[0];
                                addr_2 = addr[1];
                                addr_3 = addr[2];
                            }

                        } catch (ArrayIndexOutOfBoundsException ia) {//just catch
                        }
                    }
                } catch (ClassCastException c) {
                    //ipv6 eliminated
                }
            }
        }
    }
}