package libmansys;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
public class libapplication {

	private JFrame frame;
	private JTextField txtRegistrationBookTitle;
	private JTextField txtRevision;
	private JTextField txtAuthor;
	private JTextField txtPrice;
	private JTable table;
	private JTextField txtsearchBookTitle;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					libapplication window = new libapplication();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public libapplication() {
		initialize();
		Connect();
		table_load();
	}
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;

	public void Connect()
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/books","root","admin");
		}
		catch (ClassNotFoundException ex)
		{
			
		}
		catch (SQLException ex)
		{
			
		}
	}
	public void table_load()
	{
		try {
			pst = con.prepareStatement("select * from mybooks");
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	

	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 12));
		frame.setBounds(100, 100, 745, 565);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("BOOK STORE ");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(174, 29, 374, 37);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Resgistration", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(24, 97, 341, 260);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Book Title");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(40, 28, 76, 27);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Revision");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2.setBounds(40, 83, 76, 27);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Author");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_3.setBounds(40, 142, 76, 27);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Price");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_4.setBounds(40, 202, 76, 27);
		panel.add(lblNewLabel_4);
		
		txtRegistrationBookTitle = new JTextField();
		txtRegistrationBookTitle.setBounds(126, 32, 188, 23);
		panel.add(txtRegistrationBookTitle);
		txtRegistrationBookTitle.setColumns(10);
		
		txtRevision = new JTextField();
		txtRevision.setColumns(10);
		txtRevision.setBounds(126, 86, 188, 23);
		panel.add(txtRevision);
		
		txtAuthor = new JTextField();
		txtAuthor.setColumns(10);
		txtAuthor.setBounds(126, 145, 188, 23);
		panel.add(txtAuthor);
		
		txtPrice = new JTextField();
		txtPrice.setColumns(10);
		txtPrice.setBounds(126, 205, 188, 23);
		panel.add(txtPrice);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String bookName, Revision, Author, Price;
				
				bookName = txtRegistrationBookTitle.getText();
				Revision = txtRevision.getText();
				Author = txtAuthor.getText();
				Price = txtPrice.getText();
				
				try {
					pst = con.prepareStatement("insert into mybooks(name, Revision, Author, price) values (?,?,?,?)");
					pst.setString(1, bookName);
					pst.setString(2, Revision);
					pst.setString(3, Author);
					pst.setString(4, Price);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Data Saved");
					table_load();
					txtRegistrationBookTitle.setText("");
					txtRevision.setText("");
					txtAuthor.setText("");
					txtPrice.setText("");
					txtRegistrationBookTitle.requestFocus();
				}
				catch(SQLException ex){
					ex.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(34, 368, 89, 31);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				txtRegistrationBookTitle.setText("");
				txtRevision.setText("");
				txtAuthor.setText("");
				txtPrice.setText("");
			}
		});
		btnClear.setBounds(159, 368, 89, 31);
		frame.getContentPane().add(btnClear);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
			}
		});
		btnExit.setBounds(276, 368, 89, 31);
		frame.getContentPane().add(btnExit);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(401, 106, 318, 243);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel Search = new JPanel();
		Search.setBorder(new TitledBorder(null, "Search", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		Search.setBounds(24, 423, 341, 75);
		frame.getContentPane().add(Search);
		Search.setLayout(null);
		
		JLabel lblNewLabel_2_1 = new JLabel("Book Title");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2_1.setBounds(39, 24, 76, 27);
		Search.add(lblNewLabel_2_1);
		
		txtsearchBookTitle = new JTextField();
		txtsearchBookTitle.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					String id = txtsearchBookTitle.getText();
					pst = con.prepareStatement("select name,Revision,Author,price from mybooks where id = ?");
					pst.setString(1, id);
					ResultSet rs = pst.executeQuery();
					
					if(rs.next()==true)
					{
						String name = rs.getString(1);
						String Revision = rs.getString(2);
						String Author = rs.getString(3);
						String price = rs.getString(4);
						
						
						txtRegistrationBookTitle.setText(name);
						txtRevision.setText(Revision);
						txtAuthor.setText(Author);
						txtPrice.setText(price);
					}
					else
					{
						txtRegistrationBookTitle.setText("");
						txtRevision.setText("");
						txtAuthor.setText("");
						txtPrice.setText("");
					}
				}
				catch (SQLException ex) {
					
				}
				
				
				
				
				
				
			}
		});
		txtsearchBookTitle.setColumns(10);
		txtsearchBookTitle.setBounds(125, 27, 188, 23);
		Search.add(txtsearchBookTitle);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				String bookName, Revision, Author, Price, searchtxt;
				
				bookName = txtRegistrationBookTitle.getText();
				Revision = txtRevision.getText();
				Author = txtAuthor.getText();
				Price = txtPrice.getText();
				searchtxt = txtsearchBookTitle.getText();
				
				try {
					pst = con.prepareStatement("update mybooks set name = ?, Revision = ?, Author = ?, price = ? where id = ?");
					pst.setString(1, bookName);
					pst.setString(2, Revision);
					pst.setString(3, Author);
					pst.setString(4, Price);
					pst.setString(5, searchtxt);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Update complete");
					table_load();
					txtRegistrationBookTitle.setText("");
					txtRevision.setText("");
					txtAuthor.setText("");
					txtPrice.setText("");
					txtRegistrationBookTitle.requestFocus();
				}
				catch(SQLException ex){
					ex.printStackTrace();
				}
				
				
			}
		});
		btnUpdate.setBounds(433, 368, 89, 31);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String searchtxt;
	
				searchtxt = txtsearchBookTitle.getText();
				
				try {
					pst = con.prepareStatement("delete from mybooks where id = ?");
					
					pst.setString(1, searchtxt);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Deleted");
					table_load();
					txtRegistrationBookTitle.setText("");
					txtRevision.setText("");
					txtAuthor.setText("");
					txtPrice.setText("");
					txtRegistrationBookTitle.requestFocus();
				}
				catch(SQLException ex){
					ex.printStackTrace();
				}
			}
		});
		btnDelete.setBounds(590, 368, 89, 31);
		frame.getContentPane().add(btnDelete);
	}
}
