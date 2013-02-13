package package1;

/**
 * The RentalStoreGUI represents the interface that the user interacts
 * with. It manages a RentalStoreEngine and two custom dialogs.
 * 
 * @author Cameron O., John B., Eric V.
 * @version 1.0
 */

import java.awt.event.*;
import javax.swing.*;

public class RentalStoreGUI extends JFrame implements ActionListener {

	private JMenuBar menuBar;
	private JMenu file, action;
	private JMenuItem openSerial, saveSerial, rDVD, rGame, exit, returnDVD;
	private JMenuItem openText, saveText, searchTitle, searchDueDate;
	private JMenuItem sortDueDate, sortTitle, sortRenter, saveXML, openXML;
	private RentalStoreEngine engine;
	private RentDVDDialog rentDVD;
	private RentGameDialog rentGame;
	private JList list;

	/**
	 * Instantiates the RentalStoreGUI with appropriate menus and also creates
	 * the RentDVDDialog and RentGameDialog objects, but sets them to invisible
	 * at first.
	 */
	public RentalStoreGUI() {
		engine = new RentalStoreEngine();
		list = new JList(engine);

		menuBar = new JMenuBar();
		file = new JMenu("File");
		openSerial = new JMenuItem("Open Serial...");
		saveSerial = new JMenuItem("Save as Serial...");
		exit = new JMenuItem("Exit");
		returnDVD = new JMenuItem("Return Item...");
		openText = new JMenuItem("Open Text...");
		saveText = new JMenuItem("Save as Text...");
		searchTitle = new JMenuItem("Search by Title...");
		searchDueDate = new JMenuItem("Search by Due Date...");
		sortDueDate = new JMenuItem("Sort by Due Date");
		sortTitle = new JMenuItem("Sort by Title");
		sortRenter = new JMenuItem("Sort by Renter Name");
		saveXML = new JMenuItem("Save as XML...");
		openXML = new JMenuItem("Open XML...");

		file.add(openSerial);
		file.add(saveSerial);
		file.addSeparator();
		file.add(openText);
		file.add(saveText);
		file.addSeparator();
		file.add(openXML);
		file.add(saveXML);
		file.addSeparator();
		file.add(exit);
		action = new JMenu("Action");
		rDVD = new JMenuItem("Rent DVD");
		rGame = new JMenuItem("Rent Game");
		action.add(rDVD);
		action.add(rGame);
		action.addSeparator();
		action.add(searchTitle);
		action.add(searchDueDate);
		action.addSeparator();
		action.add(sortDueDate);
		action.add(sortTitle);
		action.add(sortRenter);
		action.addSeparator();
		action.add(returnDVD);
		menuBar.add(file);
		menuBar.add(action);
		this.setJMenuBar(menuBar);
		JScrollPane sPane = new JScrollPane(list,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(sPane);
		rentDVD = new RentDVDDialog(this);
		rentGame = new RentGameDialog(this);
		rentDVD.setVisible(false);
		rentGame.setVisible(false);

		rDVD.addActionListener(this);
		rGame.addActionListener(this);
		searchTitle.addActionListener(this);
		searchDueDate.addActionListener(this);
		sortDueDate.addActionListener(this);
		sortTitle.addActionListener(this);
		sortRenter.addActionListener(this);
		returnDVD.addActionListener(this);
		exit.addActionListener(this);
		openSerial.addActionListener(this);
		saveSerial.addActionListener(this);
		openText.addActionListener(this);
		saveText.addActionListener(this);
		openXML.addActionListener(this);
		saveXML.addActionListener(this);

	}

	/**
	 * This method returns the RentalStoreEngine object of the GUI
	 * 
	 * @return the engine in which the ArrayList<DVD> is held
	 */
	public RentalStoreEngine getEngine() {
		return engine;
	}

	/**
	 * This method handles the possible events that come from clicking on menu
	 * buttons in the GUI. Appropriate methods are called for each different
	 * button. Some actions require that the engine be used, while others do
	 * not.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exit) {
			System.exit(0);
		}

		if (e.getSource() == rDVD) {
			rentDVD.setVisible(true);
		}

		if (e.getSource() == rGame) {
			rentGame.setVisible(true);
		}

		if (e.getSource() == returnDVD) {
			try {
				engine.deleteDVD(list.getSelectedIndex());
			} catch (RuntimeException ex) {
				JOptionPane.showMessageDialog(null,
						"Please make a selection to return.");
			}
		}

		if (e.getSource() == openText) {
			engine.loadText();
		}

		if (e.getSource() == saveText) {
			engine.saveText();
		}

		if (e.getSource() == openSerial) {
			engine.openSerial();
		}

		if (e.getSource() == saveSerial) {
			engine.saveSerial();
		}

		if (e.getSource() == searchTitle) {
			engine.searchTitle();
		}

		if (e.getSource() == searchDueDate) {
			engine.searchDueDate();
		}

		if (e.getSource() == sortDueDate) {
			engine.sortDueDate();
		}

		if (e.getSource() == sortTitle) {
			engine.sortTitle();
		}

		if (e.getSource() == sortRenter) {
			engine.sortRenter();
		}

		if (e.getSource() == saveXML) {
			engine.saveXML();
		}

		if (e.getSource() == openXML) {
			engine.loadXML();
		}

	}

	/**
	 * The main method to run the GUI
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		RentalStoreGUI main = new RentalStoreGUI();
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.pack();
		main.setSize(800, 400);
		main.setVisible(true);

	}

}





