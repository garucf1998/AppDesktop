package GUI;

import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.awt.*;

import javax.swing.*;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.toedter.calendar.JDateChooser;

import DAO.BenhNhanDAO;
import DAO.TaiKhoanDAO;
import Entity.BenhNhan;
import Entity.NhanVien;
import Entity.TaiKhoan;


public class GUIThongTinBenhNhan extends JFrame implements MouseListener,ActionListener{

	/** 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComponent menuBar;
	private JMenu mnFile;
	private JMenu mnEdit;
	private JMenu mnHelp;
	private JTextField txthoTen;
	private JTextField txtcmnd;
	private JButton btnthem;
	private JButton btnsua;
	private JButton btnluu;
	private JButton btnhuy;
	private JTextField txttim;
	private JPanel Jpanel_2;
	private JRadioButton radnam;
	private JRadioButton radnu;
	private JLabel lblsDT;
	private JTextField txtsDT;
	private JLabel lbldiaChi;
	private JTextField txtdiaChi;
	private JPanel Jpanel;
	private JLabel lblngaySinh;
	private JDateChooser txtngaySinh;
	private DefaultTableModel datamodel; 
	private JPanel panel;
	private JScrollPane scrollPane;
	private JTable table;
	private JTextField txtemail;
	
	private JTextField textField;
	private JComboBox comboBox;
	private JTextField txtTaiThoan;

	private NhanVien mNhanVien;
	private TaiKhoan mTaiKhoan;
	private BenhNhanDAO control;
	/**
	 * Create the frame.
	 */
	public GUIThongTinBenhNhan(TaiKhoan taikhoan,NhanVien nhanvien) {
		this.mNhanVien=nhanvien;
		this.mTaiKhoan=taikhoan;
		control=new BenhNhanDAO();
		setTitle("Qu???n l?? th??ng tin b???nh nh??n");
		setIconImage(Toolkit.getDefaultToolkit().getImage("logo.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1177, 700);
		setLocationRelativeTo(null);
		
		
		menuBar = new JMenuBar();
		menuBar.setBackground(SystemColor.menu);
		setJMenuBar((JMenuBar) menuBar);
		
		mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit ");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		mnFile.add(mntmExit);
		
		mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbltitle = new JLabel("Th??ng Tin B???nh Nh??n");
		lbltitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbltitle.setFont(new Font("Times New Roman", Font.BOLD, 32));
		lbltitle.setBounds(366, 11, 365, 45);
		contentPane.add(lbltitle);
		
		Jpanel_2 = new JPanel();
		Jpanel_2.setBackground(new Color(95, 158, 160));
		Jpanel_2.setBorder(BorderFactory.createTitledBorder("Ch???c n??ng"));
		Jpanel_2.setBounds(10, 438, 1104, 163);
		contentPane.add(Jpanel_2);
		Jpanel_2.setLayout(null);
		
		btnhuy = new JButton("Quay L???i");
		btnhuy.setBackground(new Color(102, 205, 170));
		btnhuy.setIcon(new ImageIcon("Login-out-icon.png"));
		btnhuy.setBounds(924, 95, 155, 57);
		Jpanel_2.add(btnhuy);
		btnhuy.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"H??? t??n", "Ch???ng minh", "S??? ??i???n tho???i"}));
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox.setBounds(705, 95, 155, 57);
		Jpanel_2.add(comboBox);
		
		txttim = new JTextField();
		txttim.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String selection=(String)comboBox.getSelectedItem();
				if(selection.trim().equals("H??? t??n"))
				{
					removeTable();
					String gioitinh="";
					List<BenhNhan> listbn = control.SearchName(txttim.getText());;
					
					for(BenhNhan bn:listbn)
						if(String.valueOf(bn.getId())!=null)
					{
						if(bn.isGioiTinh()){
							gioitinh="Nam";
						}
						else
							gioitinh="N???";
						
						String[] rowdata = { String.valueOf(bn.getId()),bn.getTen(),gioitinh,bn.getSoDienThoai(),bn.getCmnd(),bn.getDiaChi(),control.doichuoitungay(bn.getNgaySinh()),bn.getEmail()};
						datamodel.addRow(rowdata);
					}	
				}
				if(selection.trim().equals("Ch???ng minh"))
					{
					removeTable();
					String gioitinh="";
					List<BenhNhan> listbn = control.SearchCMND(txttim.getText());;
					
					for(BenhNhan bn:listbn)
					if(String.valueOf(bn.getId())!=null)
					{
						if(bn.isGioiTinh()){
							gioitinh="Nam";
						}
						else
							gioitinh="N???";
						
						String[] rowdata = {String.valueOf(bn.getId()).toString(),bn.getTen(),gioitinh,bn.getSoDienThoai(),bn.getCmnd(),bn.getDiaChi(),control.doichuoitungay(bn.getNgaySinh()),bn.getEmail()};
						datamodel.addRow(rowdata);
					}
					}
				if(selection.trim().equals("S??? ??i???n tho???i"))
					{
					removeTable();
					String gioitinh="";
					List<BenhNhan> listbn = control.SearchSDT(txttim.getText());;
					
					for(BenhNhan bn:listbn)
					if(String.valueOf(bn.getId()).toString()!=null)
					{
						if(bn.isGioiTinh()){
							gioitinh="Nam";
						}
						else
							gioitinh="N???";
						
						String[] rowdata = {String.valueOf(bn.getId()).toString(),bn.getTen(),gioitinh,bn.getSoDienThoai(),bn.getCmnd(),bn.getDiaChi(),control.doichuoitungay(bn.getNgaySinh()),bn.getEmail()};
						datamodel.addRow(rowdata);
					}
					}
			
			}
		});
		
				
		txttim.setBounds(20, 108, 646, 34);
		Jpanel_2.add(txttim);
		txttim.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txttim.setColumns(10);
				
		btnluu = new JButton("L??u");
		btnluu.setBackground(new Color(102, 205, 170));
		btnluu.setIcon(new ImageIcon("luu.png"));
		btnluu.setBounds(380, 23, 155, 57);
		Jpanel_2.add(btnluu);
		btnluu.setFont(new Font("Tahoma", Font.PLAIN, 14));
				
		btnsua = new JButton("S???a");
		btnsua.setBackground(new Color(102, 205, 170));
		btnsua.setIcon(new ImageIcon("sua.png"));
		btnsua.setBounds(200, 23, 155, 57);
		Jpanel_2.add(btnsua);
		btnsua.setFont(new Font("Tahoma", Font.PLAIN, 14));
				
	
				
		btnthem = new JButton("Th??m");
		btnthem.setBackground(new Color(102, 205, 170));
		btnthem.setIcon(new ImageIcon("add.png"));
		btnthem.setBounds(20, 23, 155, 57);
		Jpanel_2.add(btnthem);
		btnthem.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		String[]headers = {"M??","H??? t??n","Gi???i t??nh",  "S??? ??i???n tho???i", "S??? ch???ng minh",  "?????a ch???" ,"Ng??y sinh","Email"};
		datamodel = new DefaultTableModel(headers,0);
		contentPane.add(scrollPane= new JScrollPane(table = new JTable(datamodel)));
		scrollPane.setBounds(28, 291, 1060, 118);
		table.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane.setBackground(SystemColor.scrollbar);
		
		Jpanel = new JPanel();
		Jpanel.setBackground(new Color(95, 158, 160));
		Jpanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Th??ng tin b???nh nh??n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		Jpanel.setBounds(10, 51, 1104, 204);
		contentPane.add(Jpanel);
		Jpanel.setLayout(null);
		
		JLabel lblEmail = new JLabel("Email :");

		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEmail.setBounds(33, 126, 86, 20);
		Jpanel.add(lblEmail);
		
		txtemail = new JTextField();
		txtemail.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtemail.setColumns(10);
		txtemail.setUI(new HintTextFieldUI("					Nh???p email . VD: duyvien159@gmail.com", true, Color.GRAY));
		txtemail.setBounds(152, 126, 268, 20);
		Jpanel.add(txtemail);
		
	
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField.setColumns(10);
		textField.setVisible(false);
		textField.setBounds(152, 157, 268, 20);
		Jpanel.add(textField);
		
		lblsDT = new JLabel("S??? ??i???n tho???i:");
		lblsDT.setBounds(33, 95, 86, 20);
		Jpanel.add(lblsDT);
		lblsDT.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		txtsDT = new JTextField();
		txtsDT.setBounds(152, 95, 268, 20);
		Jpanel.add(txtsDT);
		txtsDT.setUI(new HintTextFieldUI("					Nh???p s??? ??i???n tho???i kh??ch h??ng. VD: 0399972888", true, Color.GRAY));
		txtsDT.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtsDT.setColumns(10);
		
		JLabel lblgioiTinh = new JLabel("Gi???i t??nh:");
		lblgioiTinh.setBounds(33, 61, 86, 20);
		Jpanel.add(lblgioiTinh);
		lblgioiTinh.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblhoTen = new JLabel("H??? t??n: ");
		lblhoTen.setBounds(33, 31, 86, 20);
		Jpanel.add(lblhoTen);
		lblhoTen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		txthoTen = new JTextField();
		txthoTen.setBounds(152, 32, 268, 20);
		Jpanel.add(txthoTen);
		txthoTen.setUI(new HintTextFieldUI("					Nh???p h??? t??n kh??ch h??ng. VD: Nguy???n V??n B", true, Color.GRAY));
		txthoTen.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txthoTen.setColumns(10);
		
		
		radnam = new JRadioButton("Nam");
		radnam.setBounds(152, 61, 59, 23);
		Jpanel.add(radnam);
		radnam.setBackground(SystemColor.inactiveCaptionBorder);
		radnam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(radnam.isSelected())
				{
					radnu.setSelected(false);
				}
				
			}
		});
		radnam.setFont(new Font("Tahoma", Font.PLAIN, 11));
		radnam.setSelected(true);
		
		
		
		radnu = new JRadioButton("N???");
		radnu.setBounds(228, 61, 70, 23);
		Jpanel.add(radnu);
		radnu.setBackground(SystemColor.inactiveCaptionBorder);
		radnu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(radnu.isSelected())
				{
					radnam.setSelected(false);
				}
				
			}
		});
		radnu.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblcmnd = new JLabel("Ch???ng minh nh??n d??n:");
		lblcmnd.setBounds(621, 35, 135, 16);
		Jpanel.add(lblcmnd);
		lblcmnd.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		lblngaySinh = new JLabel("Ng??y sinh:");
		lblngaySinh.setBounds(621, 95, 86, 20);
		Jpanel.add(lblngaySinh);
		lblngaySinh.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		txtngaySinh = new JDateChooser();
		txtngaySinh.setBounds(768, 95, 268, 20);
		Jpanel.add(txtngaySinh);
		txtngaySinh.getCalendarButton().setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtngaySinh.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtngaySinh.setDateFormatString("yyyy-MM-dd");
		
		txtdiaChi = new JTextField();
		txtdiaChi.setBounds(768, 65, 268, 20);
		Jpanel.add(txtdiaChi);
		txtdiaChi.setUI(new HintTextFieldUI("					Nh???p ?????a ch??? kh??ch h??ng. VD: B??nh Thu???n", true, Color.GRAY));
		txtdiaChi.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtdiaChi.setColumns(10);
		
		txtcmnd = new JTextField();
		txtcmnd.setBounds(768, 35, 268, 20);
		Jpanel.add(txtcmnd);
		txtcmnd.setUI(new HintTextFieldUI("					Nh???p s??? ch???ng minh nh??n d??n. VD: 261464277", true, Color.GRAY));
		txtcmnd.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtcmnd.setColumns(10);
		
		lbldiaChi = new JLabel("?????a ch???:");
		lbldiaChi.setBounds(621, 65, 86, 20);
		Jpanel.add(lbldiaChi);
		lbldiaChi.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblcmnd_1 = new JLabel("T??i Kho???n :");
		lblcmnd_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblcmnd_1.setBounds(621, 126, 135, 16);
		Jpanel.add(lblcmnd_1);
		
		txtTaiThoan = new JTextField();
		txtTaiThoan.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtTaiThoan.setColumns(10);
		txtTaiThoan.setBounds(768, 126, 268, 20);
		Jpanel.add(txtTaiThoan);
		radnu.addActionListener(this);
		radnam.addActionListener(this);
		
		panel = new JPanel();
		panel.setBackground(new Color(95, 158, 160));
		panel.setBorder(new TitledBorder(null, "Danh s??ch b???nh nh??n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 266, 1106, 161);
		contentPane.add(panel);
		panel.setLayout(null);
		table.addMouseListener(this);
		btnsua.addActionListener(this);
		btnluu.addActionListener(this);
		btnthem.addActionListener(this);
		btnhuy.addActionListener(this);
		
		SetEnableEditText(false);
		
		btnluu.setEnabled(false);
		btnsua.setEnabled(false);
		
		updateTableData();
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		BenhNhanDAO benhNhanController=new BenhNhanDAO();
		Object o=e.getSource();
		if(o==btnhuy)
		{
			dispose();
			GUIChucNang ft= new GUIChucNang(mTaiKhoan,mNhanVien);
			ft.setVisible(true);
		}
		else if (o==btnthem)
		{
			btnluu.setEnabled(true);
			if(btnthem.getText().equals("Th??m"))
			{
				SetEnableEditText(true);
				btnsua.setEnabled(false);
				btnthem.setIcon(new ImageIcon("xoa.png"));
				btnthem.setText("Hu???");
			}
		else
				
			{
				SetEnableEditText(false);
				btnluu.setEnabled(false);
				xoarong();
				btnthem.setIcon(new ImageIcon("add.png"));
				btnthem.setText("Th??m");				
			}
			if(btnsua.getText().equals("Xong"))
			{
				btnsua.setText("S???a");
				btnsua.setEnabled(false);
			}
			
		}
		else if (o.equals(btnluu))
			
		{
			if(Check())
			{
				int responseCode=0;	
				BenhNhan bn=new BenhNhan();
				AddBenhNhan(bn);
				TaiKhoan tk=new TaiKhoan();
				tk.setUsername(txtTaiThoan.getText());
				tk.setPassword("123456");
				TaiKhoanDAO taikhoanController=new TaiKhoanDAO();
				try {
					tk.setRole(benhNhanController.GetOneRole((long) 5));
					
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				if(!(control.KiemTraTaiKhoan(tk)))
				{
					try {
						responseCode=taikhoanController.POSTTaiKhoan(tk);
						if(responseCode==200)
						{
							try {
								bn.setTaiKhoan(tk);
								responseCode=benhNhanController.POSTBenhNhan(bn);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							if(responseCode==200)
							{
								
								SetEnableEditText(false);
								
								btnthem.setEnabled(true);
								btnthem.setIcon(new ImageIcon("add.png"));
								btnthem.setText("Th??m");
								btnluu.setEnabled(false);
								
								removeTable();
								updateTableData();
								
								JOptionPane.showMessageDialog(table,"B???n v???a th??m m???i th??ng tin 1 b???nh nh??n !","Ch?? ??",JOptionPane.CLOSED_OPTION);
								
							}
							else
							{
								try {
									int ketqua=taikhoanController.DeleteTaiKhoan(txtTaiThoan.getText());
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								JOptionPane.showMessageDialog(table,"B???n v???a th??m th???t b???i b???nh nh??n !","Ch?? ??",JOptionPane.CLOSED_OPTION);
								
							}
						}
						else 
							JOptionPane.showMessageDialog(table,"B???n v???a th??m th???t b???i t??i kho???n !","Ch?? ??",JOptionPane.CLOSED_OPTION);
						
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else 
				{
					JOptionPane.showMessageDialog(table,"T??i kho???n b???n t???o b??? tr??ng !","Ch?? ??",JOptionPane.CLOSED_OPTION);
					txtTaiThoan.requestFocus();
				}
				
			}
			
			
		}
		
		else if(o.equals(btnsua))
		{
			if	(btnsua.getText().equals("S???a"))
			{
				SetEnableEditText(true);
				btnsua.setText("Xong");
				btnthem.setEnabled(true);
				btnthem.setText("H???y");
				btnthem.setIcon(new ImageIcon("xoa.png"));
				btnluu.setEnabled(false);
			}
			else
			{
					if(Check()) {
						int code=0;
						
					
						boolean gioitinh = true;
						if (radnu.isSelected()) gioitinh= false;
						BenhNhan bn=new BenhNhan();
						AddBenhNhan(bn);
						int row = table.getSelectedRow();
						long id=  Long.parseLong((String) datamodel.getValueAt(row, 0));
						bn.setId(id);
						if(row>=0){
							try {
								code = benhNhanController.PUTBenhNhan(bn);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						if(code==200)
						{
							btnsua.setText("S???a");
							SetEnableEditText(false);
							
							btnsua.setEnabled(false);
							btnthem.setEnabled(true);
							btnthem.setIcon(new ImageIcon("add.png"));
							btnthem.setText("Th??m");
							xoarong();
							
							removeTable();
							updateTableData();
							
							JOptionPane.showMessageDialog(table,"B???n v???a c???p nh???t th??ng tin 1 b???nh nh??n !","Ch?? ??",JOptionPane.CLOSED_OPTION);
							
						}
					}
					
			}
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int row = table.getSelectedRow();
		btnsua.setEnabled(true);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		BenhNhanDAO control=new BenhNhanDAO();
		
		try {
			date = df.parse(table.getValueAt(row, 6).toString());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		textField.setText(table.getValueAt(row, 0).toString());
		txthoTen.setText(table.getValueAt(row, 1).toString());
		if(table.getValueAt(row, 2).toString().equals("Nam"))
		{
			radnam.setSelected(true);
			radnu.setSelected(false);
		}
		else
		{
			radnam.setSelected(false);
			radnu.setSelected(true);
		}
		
		txtsDT.setText(table.getValueAt(row, 3).toString());
		txtcmnd.setText(table.getValueAt(row, 4).toString());
		txtdiaChi.setText(table.getValueAt(row, 5).toString());
		txtngaySinh.setDate(date );
		txtemail.setText(table.getValueAt(row, 7).toString());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void updateTableData() 
	{
		// TODO Auto-generated method stub
		ArrayList<BenhNhan>list=new ArrayList<>();
		try {
			list.addAll(control.GetAllBenhNhan());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(list.size()!=0)
		{
			for (BenhNhan bn : list) {
				
				String gioitinh="";
				if(bn.isGioiTinh()){
					gioitinh="Nam";
				}
				else
					gioitinh="N???";
				String[] rowdata = {String.valueOf(bn.getId()).toString(),bn.getTen(),gioitinh,bn.getSoDienThoai(),bn.getCmnd(),bn.getDiaChi(),control.doichuoitungay(bn.getNgaySinh()),bn.getEmail()};
				datamodel.addRow(rowdata);
			}
		}
	}
	public void removeTable() {
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setRowCount(0);
	}

	private void SetEnableEditText(boolean trangthai) {
		
		if(!trangthai)
		{
			
			
			txthoTen.setEditable(false);
			radnam.setEnabled(false);
			radnu.setEnabled(false);
			txtsDT.setEditable(false);
			txtcmnd.setEditable(false);
			txtdiaChi.setEditable(false);
			txtngaySinh.setEnabled(false);
			txtemail.setEnabled(false);
			txtTaiThoan.setEnabled(false);
			
			txthoTen.setBackground(SystemColor.info);
			txtcmnd.setBackground(SystemColor.info);
			txtsDT.setBackground(SystemColor.info);
			txtdiaChi.setBackground(SystemColor.info);
			txtemail.setBackground(SystemColor.info);
			txtTaiThoan.setBackground(SystemColor.info);
			
		}
		else
		{
			txthoTen.setEditable(true);
			radnam.setEnabled(true);
			radnu.setEnabled(true);
			txtsDT.setEditable(true);
			txtcmnd.setEditable(true);
			txtdiaChi.setEditable(true);
			txtngaySinh.setEnabled(true);
			txtemail.setEnabled(true);
			txtTaiThoan.setEnabled(true);
			
			txthoTen.setBackground(SystemColor.text);
			txtcmnd.setBackground(SystemColor.text);
			txtsDT.setBackground(SystemColor.text);
			txtdiaChi.setBackground(SystemColor.text);
			txtemail.setBackground(SystemColor.text);
			txtTaiThoan.setBackground(SystemColor.text);
		}
		
	}
	public void xoarong() {
		txthoTen.setText("");
		radnam.setSelected(true);
		radnu.setSelected(false);
		txtsDT.setText("");
		txtcmnd.setText("");
		txtdiaChi.setText("");
		txtngaySinh.setDate(null);
		txtemail.setText("");
		txtTaiThoan.setText("");
	}
	
	public boolean Check() {
		if(txthoTen.getText().equals("")) 
			{
				JOptionPane.showConfirmDialog(this, "H??? t??n b???n nh???p v??o ???? tr???ng. M???i nh???p l???i !","Ch?? ??",JOptionPane.CLOSED_OPTION);
				txthoTen.requestFocus();
				return false;
			}
		if(!radnam.isSelected()&&!radnu.isSelected())
		{
			JOptionPane.showConfirmDialog(this, "B???n ch??a ch???n gi???i t??nh !","Ch?? ??",JOptionPane.CLOSED_OPTION);
			txthoTen.requestFocus();
			return false;
		}
			
		if(txtsDT.getText().equals("")) 
			{
				JOptionPane.showConfirmDialog(this, "B???n ch??a nh???p s??? ??i???n tho???i !","Ch?? ??",JOptionPane.CLOSED_OPTION);
				txtsDT.requestFocus();
				return false;
			}
//		if(txtcmnd.getText().equals("")) 
//		{
//			JOptionPane.showConfirmDialog(this, "B???n ch??a nh???p s??? ch???ng minh nhan d??n !","Ch?? ??",JOptionPane.CLOSED_OPTION);
//			txtcmnd.requestFocus();
//			return false;
//		}
		if(txtdiaChi.getText().equals("")) {
			JOptionPane.showConfirmDialog(this, "B???n ch??a nh???p ?????a ch??? !","Ch?? ??",JOptionPane.CLOSED_OPTION);
			txtdiaChi.requestFocus();
			return false;
		}
		if(txtngaySinh.getDate()==null) {
			JOptionPane.showConfirmDialog(this, "B???n ch??a ch???n ng??y sinh !","Ch?? ??",JOptionPane.CLOSED_OPTION);
			txtngaySinh.requestFocus();
			return false;
		
		}
		// th???i gian hi???n h??nh
		Date date1 = new Date();
		if(txtngaySinh.getDate().compareTo(date1)>0) {
			JOptionPane.showConfirmDialog(this, "Ng??y sinh c???a b???n nh???p ???? l???n h??n ng??y hi???n h??nh. Xin ch???n l???i ng??y sinh !","Ch?? ??",JOptionPane.CLOSED_OPTION);
			txtngaySinh.requestFocus();
			return false;
		
		}
		
		
	// kiem tra so dien thoai
			try 
			{	
				if(Long.parseLong(txtsDT.getText())<0){
					JOptionPane.showMessageDialog(table,"S??? ??i???n tho???i b???n v???a nh???p l?? s??? ??m. Vui l??ng nh???p l???i !","Ch?? ??",JOptionPane.CLOSED_OPTION);
					txtsDT.requestFocus();
					txtsDT.selectAll();
					return false;}
				if(!control.CheckSdt(txtsDT.getText())) {
					JOptionPane.showConfirmDialog(this, "S??? ??i???n tho???i b???n v???a nh???p c?? k?? t??? kh??ng ph???i l?? s??? ho???c kh??ng ph???i 10 ch??? s??? ! ","Ch?? ??",JOptionPane.CLOSED_OPTION);
					txtsDT.requestFocus();
					txtsDT.selectAll();
				return false;
			}
			} catch (Exception e) 
				{
					// TODO: handle exception
					JOptionPane.showMessageDialog(table,"S??? ??i???n tho???i b???n nh???p kh??ng h???p l??? !","Ch?? ??",JOptionPane.CLOSED_OPTION);
					txtsDT.requestFocus();
					txtsDT.selectAll();
					return false;
				}
			try 
			{	
				if(Long.parseLong(txtcmnd.getText())<0){
					JOptionPane.showMessageDialog(table,"S??? Ch???ng minh kh??ng ???????c ??m. Vui l??ng nh???p l???i !","Ch?? ??",JOptionPane.CLOSED_OPTION);
					txtcmnd.requestFocus();
					txtcmnd.selectAll();
					return false;}
				if(!control.CheckCmnd(txtcmnd.getText())) {
					JOptionPane.showConfirmDialog(this, "S??? Ch???ng minh nh???p v??o c?? k?? t??? kh??ng ph???i l?? s??? ho???c kh??ng ph???i 9 ch??? s???. Vui l??ng nh???p l???i s??? ch???ng minh ! ","Ch?? ??",JOptionPane.CLOSED_OPTION);
					txtcmnd.requestFocus();
					txtcmnd.selectAll();
				return false;
			}
			} catch (Exception e) 
				{
					// TODO: handle exception
					JOptionPane.showMessageDialog(table,"S??? Ch???ng minh b???n nh???p kh??ng h???p l??? !","Ch?? ??",JOptionPane.CLOSED_OPTION);
					txtcmnd.requestFocus();
					txtcmnd.selectAll();
					return false;
				}
			try 
			{	
				if(!control.CheckEmail(txtemail.getText())) {
					JOptionPane.showConfirmDialog(this, "B???n v???a nh???p email kh??ng h???p l???. Email c?? d???ng : anystring@anystring.anystring ! ","Ch?? ??",JOptionPane.CLOSED_OPTION);
					txtemail.requestFocus();
					txtemail.selectAll();
				return false;
			}
			} catch (Exception e) 
				{
					// TODO: handle exception
					JOptionPane.showMessageDialog(table,"email b???n nh???p kh??ng h???p l??? !","Ch?? ??",JOptionPane.CLOSED_OPTION);
					txtemail.requestFocus();
					txtemail.selectAll();
					return false;
				}
		return true;	
		
	}
	
	
	public void AddBenhNhan(BenhNhan bn) {
				
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = df.format(txtngaySinh.getDate());
		boolean gioitinh =true ;
		if (radnam.isSelected()) gioitinh = true;
		if (radnu.isSelected()) gioitinh= false;
		
		bn.setTen(txthoTen.getText());
		bn.setDiaChi(txtdiaChi.getText());
		bn.setGioiTinh(gioitinh);
		bn.setNgaySinh(control.doingaytuchuoi(strDate));
		bn.setSoDienThoai(txtsDT.getText());
		bn.setEmail(txtemail.getText());
		bn.setCmnd(txtcmnd.getText());
	}
	
}
