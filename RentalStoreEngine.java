package package1;

/**
 * The RentalStoreEngine manages a list of DVDs, which represents
 * the DVDs, and Games, that are currently rented by customers.
 * 
 * @author Cameron O., John B., Eric V.
 * @version 1.0
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.text.*;
import javax.xml.parsers.*;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RentalStoreEngine extends AbstractListModel {

	private ArrayList<DVD> listDVDs;

	/**
	 * Instantiates a RentalStoreEngine. The engine manages an ArrayList of DVD
	 * objects.
	 */
	public RentalStoreEngine() {
		listDVDs = new ArrayList<DVD>();
	}

	/**
	 * Implements the AbstractListModel method required. The DVD object at the
	 * specified index is returned.
	 * 
	 * @param i
	 *            the index requested
	 * @return the DVD object at that index
	 */
	public Object getElementAt(int i) {
		return listDVDs.get(i);
	}

	/**
	 * This method returns the size of the ArrayList of DVDs.
	 * 
	 * @return the size of the ArrayList of DVDs
	 */
	public int getSize() {
		return listDVDs.size();
	}

	/**
	 * This method adds a DVD to the ArrayList of DVDs. Notifies the list that a
	 * DVD has been added.
	 * 
	 * @param unit
	 *            the DVD to add.
	 */
	public void addDVD(DVD unit) {
		listDVDs.add(unit);
		fireIntervalAdded(this, listDVDs.size() - 1, listDVDs.size() - 1);
	}

	/**
	 * This method deletes, or returns, the selected DVD from the ArrayList of
	 * DVDs. An appropriate message is displayed, with a dollar amount,
	 * depending on if the user is returning a DVD or a Game, and if they are
	 * late in returning said DVD or Game. This also notifies the list that a
	 * DVD has been removed.
	 * 
	 * @param i
	 *            the index that points the the DVD that the user wishes to
	 *            return
	 */
	public void deleteDVD(int i) {
		if (listDVDs.get(i).getClass().getSimpleName().equals("DVD")) {
			GregorianCalendar now = new GregorianCalendar();
			if (now.compareTo(listDVDs.get(i).getDueBack()) > 0) {
				JOptionPane
						.showMessageDialog(null, "Thanks for returning "
								+ listDVDs.get(i).getTitle() + ", "
								+ listDVDs.get(i).getNameOfRenter()
								+ ". You owe $2.00");
			} else {
				JOptionPane
						.showMessageDialog(null, "Thanks for returning "
								+ listDVDs.get(i).getTitle() + ", "
								+ listDVDs.get(i).getNameOfRenter()
								+ ". You owe $1.20");
			}
		} else {
			GregorianCalendar now = new GregorianCalendar();
			if (now.compareTo(listDVDs.get(i).getDueBack()) > 0) {
				JOptionPane.showMessageDialog(null, "Thanks for returning "
						+ listDVDs.get(i).getTitle() + ", "
						+ listDVDs.get(i).getNameOfRenter()
						+ ". You owe $10.00");
			} else {
				JOptionPane
						.showMessageDialog(null, "Thanks for returning "
								+ listDVDs.get(i).getTitle() + ", "
								+ listDVDs.get(i).getNameOfRenter()
								+ ". You owe $5.00");
			}
		}

		listDVDs.remove(i);
		fireIntervalRemoved(this, 0, listDVDs.size());
	}

	/**
	 * This method adds a Game object to the ArrayList of DVDs. Notifies the
	 * list that a DVD has been added.
	 * 
	 * @param unit
	 *            the Game to be added to the list, or the game to be rented.
	 */
	public void addGame(Game unit) {
		listDVDs.add(unit);
		fireIntervalAdded(this, listDVDs.size() - 1, listDVDs.size() - 1);
	}

	/**
	 * Saves the contents of the ArrayList to a text file. Uses a JFileChooser
	 * to accomplish this task.
	 */
	public void saveText() {
		SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yyyy");
		JFileChooser chooser = new JFileChooser();
		int status = chooser.showSaveDialog(null);
		if (status != JFileChooser.APPROVE_OPTION)
			JOptionPane.showMessageDialog(null, "The file has not been saved.");
		else {
			File filename = chooser.getSelectedFile();
			try {
				PrintWriter out = new PrintWriter(new BufferedWriter(
						new FileWriter(filename)));
				for (int i = 0; i < listDVDs.size(); i++) {
					if (listDVDs.get(i).getClass().getSimpleName()
							.equals("DVD")) {
						out.println(listDVDs.get(i).getClass().getSimpleName());
						out.println(listDVDs.get(i).getNameOfRenter());
						out.println(listDVDs.get(i).getTitle());
						out.println(fmt.format(listDVDs.get(i).getBought()
								.getTime()));
						out.println(fmt.format(listDVDs.get(i).getDueBack()
								.getTime()));
					} else {
						out.println(listDVDs.get(i).getClass().getSimpleName());
						out.println(listDVDs.get(i).getNameOfRenter());
						out.println(listDVDs.get(i).getTitle());
						out.println(fmt.format(listDVDs.get(i).getBought()
								.getTime()));
						out.println(fmt.format(listDVDs.get(i).getDueBack()
								.getTime()));
						out.println(((Game) listDVDs.get(i)).getPlayer());
					}

				}

				out.close();
			} catch (IOException error) {
				System.out.println("There was an error in writing the file.");
			}
		}
	}

	/**
	 * Loads the contents of an ArrayList from a text file. Uses a JFileChooser
	 * to accomplish this task.
	 */
	public void loadText() {
		SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yyyy");
		JFileChooser chooser = new JFileChooser();
		int status = chooser.showOpenDialog(null);
		if (status != JFileChooser.APPROVE_OPTION)
			JOptionPane.showMessageDialog(null, "No file has been loaded.");
		else {
			ArrayList<DVD> loadList = new ArrayList<DVD>();
			String line = "";
			File file = chooser.getSelectedFile();
			try {
				Scanner scan = new Scanner(file);
				while (scan.hasNextLine()) {
					line = scan.nextLine();
					if (line.equals("DVD")) {
						GregorianCalendar rented = new GregorianCalendar();
						GregorianCalendar due = new GregorianCalendar();
						String name = scan.nextLine();
						String title = scan.nextLine();
						String sBought = scan.nextLine();
						String sDueBack = scan.nextLine();
						rented.setTime(fmt.parse(sBought));
						due.setTime(fmt.parse(sDueBack));
						DVD loadUnit = new DVD(title, name, rented, due);
						loadList.add(loadUnit);
					} else {
						GregorianCalendar rented = new GregorianCalendar();
						GregorianCalendar due = new GregorianCalendar();
						String name = scan.nextLine();
						String title = scan.nextLine();
						String sBought = scan.nextLine();
						String sDueBack = scan.nextLine();
						String player = scan.nextLine();
						rented.setTime(fmt.parse(sBought));
						due.setTime(fmt.parse(sDueBack));
						Game loadUnit = new Game(title, name, rented, due,
								PlayerType.valueOf(player));
						loadList.add(loadUnit);
					}
				}
				listDVDs = loadList;
				fireContentsChanged(this, 0, listDVDs.size() - 1);
				scan.close();
			} catch (ParseException error) {
				JOptionPane.showMessageDialog(null,
						"Incorrect date format found");
			} catch (FileNotFoundException error) {
				JOptionPane.showMessageDialog(null, "File not found.");
			} catch (NoSuchElementException error) {
				JOptionPane.showMessageDialog(null,
						"There was an error reading the file.");
			}
		}
	}

	/**
	 * Saves the contents of the ArrayList to a serialized file. Uses a
	 * JFileChooser to accomplish this task.
	 */
	public void saveSerial() {
		JFileChooser chooser = new JFileChooser();
		int status = chooser.showSaveDialog(null);
		if (status != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, "No file has been saved.");
		} else {
			File filename = chooser.getSelectedFile();
			try {
				FileOutputStream fos = new FileOutputStream(filename);
				ObjectOutputStream os = new ObjectOutputStream(fos);
				os.writeObject(listDVDs);
				os.close();
			} catch (FileNotFoundException error) {
				JOptionPane.showMessageDialog(null, "File not found.");
			} catch (NoSuchElementException error) {
				JOptionPane.showMessageDialog(null,
						"There was an error saving the file.");
			} catch (IOException error) {
				JOptionPane.showMessageDialog(null,
						"There was an error processing the save");
			}
		}

	}

	/**
	 * Loads the contents of an ArrayList from a serialized file. Uses a
	 * JFileChooser to accomplish this task.
	 */
	public void openSerial() {
		JFileChooser chooser = new JFileChooser();
		int status = chooser.showOpenDialog(null);
		if (status != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, "No file has been loaded.");
		} else {
			File filename = chooser.getSelectedFile();
			try {
				FileInputStream fis = new FileInputStream(filename);
				ObjectInputStream is = new ObjectInputStream(fis);
				listDVDs = (ArrayList<DVD>) is.readObject();
				is.close();
			} catch (FileNotFoundException error) {
				JOptionPane.showMessageDialog(null, "File not found.");
			} catch (NoSuchElementException error) {
				JOptionPane.showMessageDialog(null,
						"There was an error reading the file.");
			} catch (IOException error) {
				JOptionPane.showMessageDialog(null,
						"There was an error processing the file");
			} catch (ClassNotFoundException error) {
				JOptionPane.showMessageDialog(null, "Class not found.");
			} catch (RuntimeException error) {
				JOptionPane.showMessageDialog(null, "Invalid file used");
			}
		}

		fireContentsChanged(this, 0, listDVDs.size() - 1);
	}

	/**
	 * Allows the user to search the ArrayList for DVDs that contain a phrase in
	 * the title of the DVD. Displays an appropriate message if no DVDs are
	 * found as matches, or the DVDs that are found.
	 */
	public void searchTitle() {
		String search = JOptionPane
				.showInputDialog("Search the titles of rented DVDs/Games: ");
		ArrayList<DVD> titles = new ArrayList<DVD>();
		if (search != null) {
			for (DVD d : listDVDs) {
				if (d.getTitle().toLowerCase().contains(search.toLowerCase())) {
					titles.add(d);
				}

			}

			if (titles.size() == 0) {
				JOptionPane.showMessageDialog(null, "No titles contain \""
						+ search + "\".");
			} else {
				String result = "";
				for (DVD d : titles) {
					result += (d + "\n");
				}
				JOptionPane.showMessageDialog(null, result);
			}
		}
	}

	/**
	 * Allows the user to search the ArrayList for DVDs that have a due date
	 * past the date they enter. Displays an appropriate message if no DVDs are
	 * found as matches, or the DVDs that are found.
	 */
	public void searchDueDate() {
		String search = JOptionPane
				.showInputDialog("Search the due dates of rented DVDs/Games: ");
		SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yyyy");
		GregorianCalendar searched = new GregorianCalendar();
		if (search != null) {
			try {
				searched.setTime(fmt.parse(search));
			} catch (ParseException ex) {
				JOptionPane.showMessageDialog(null,
						"Input a date in the format MM/dd/yyyy");
			} catch (RuntimeException ex) {
				JOptionPane.showMessageDialog(null,
						"Incorrect inputs, please try again.");
			}

			ArrayList<DVD> titles = new ArrayList<DVD>();
			for (DVD d : listDVDs) {
				if (d.getDueBack().compareTo(searched) > 0) {
					titles.add(d);
				}
			}

			if (titles.size() == 0) {
				JOptionPane.showMessageDialog(null, "No titles are due past "
						+ search + ".");
			} else {
				String result = "";
				for (DVD d : titles) {
					result += (d + "\n");
				}
				JOptionPane.showMessageDialog(null, result);
			}
		}

	}

	/**
	 * Sorts the list of DVDs rented by their due date (earliest to latest)
	 */
	public void sortDueDate() {
		Collections.sort(listDVDs);
		fireContentsChanged(this, 0, listDVDs.size() - 1);
	}

	/**
	 * Sorts the list of DVDs rented by their title (lexicographically)
	 */
	public void sortTitle() {
		Collections.sort(listDVDs, new TitleComparator());
		fireContentsChanged(this, 0, listDVDs.size() - 1);
	}
	
	/**
	 * Sorts the list of DVDs rented based on the renters name (lexicographically)
	 */
	public void sortRenter(){
		Collections.sort(listDVDs, new NameComparator());
		fireContentsChanged(this, 0, listDVDs.size() -1);
	}

	/**
	 * Saves the contents of an ArrayList to a XML file. Uses a JFileChooser to
	 * accomplish this task.
	 */
	public void saveXML() {
		SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yyyy");
		JFileChooser chooser = new JFileChooser();
		int status = chooser.showSaveDialog(null);
		if (status != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, "No file has been saved.");
		} else {
			try {
				File filename = chooser.getSelectedFile();
				DocumentBuilderFactory docFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.newDocument();
				Element rootElement = doc.createElement("List");
				doc.appendChild(rootElement);
				for (DVD d : listDVDs) {
					if (d.getClass().getSimpleName().equals("DVD")) {
						Element unit = doc
								.createElement(d.getClass().getSimpleName());
						rootElement.appendChild(unit);
						Element name = doc.createElement("Renter");
						name.appendChild(doc.createTextNode(d.getNameOfRenter()));
						unit.appendChild(name);
						Element title = doc.createElement("Title");
						title.appendChild(doc.createTextNode(d.getTitle()));
						unit.appendChild(title);
						Element rented = doc.createElement("Rented");
						rented.appendChild(doc.createTextNode(fmt.format(d
								.getBought().getTime())));
						unit.appendChild(rented);
						Element due = doc.createElement("Due");
						due.appendChild(doc.createTextNode(fmt.format(d
								.getDueBack().getTime())));
						unit.appendChild(due);
					} else {
						Element unit = doc
								.createElement(d.getClass().getSimpleName());
						rootElement.appendChild(unit);
						Element name = doc.createElement("Renter");
						name.appendChild(doc.createTextNode(d.getNameOfRenter()));
						unit.appendChild(name);
						Element title = doc.createElement("Title");
						title.appendChild(doc.createTextNode(d.getTitle()));
						unit.appendChild(title);
						Element rented = doc.createElement("Rented");
						rented.appendChild(doc.createTextNode(fmt.format(d
								.getBought().getTime())));
						unit.appendChild(rented);
						Element due = doc.createElement("Due");
						due.appendChild(doc.createTextNode(fmt.format(d
								.getDueBack().getTime())));
						unit.appendChild(due);
						Element player = doc.createElement("Player");
						player.appendChild(doc.createTextNode(((Game) d)
								.getPlayer().name()));
						unit.appendChild(player);
					}

					TransformerFactory transformerFactory = TransformerFactory
							.newInstance();
					Transformer transformer = transformerFactory
							.newTransformer();
					DOMSource source = new DOMSource(doc);
					StreamResult result = new StreamResult(filename);
					transformer.transform(source, result);
				}
			} catch (NoSuchElementException error) {
				JOptionPane.showMessageDialog(null,
						"There was an error saving the file.");
			} catch (ParserConfigurationException error) {
				JOptionPane.showMessageDialog(null,
						"There was an error parsing the data.");
			} catch (TransformerConfigurationException error) {
				JOptionPane.showMessageDialog(null,
						"There was an error configuring the file");
			} catch (TransformerException error) {
				JOptionPane.showMessageDialog(null,
						"There was an error transforming the file to XML");
			}

		}
	}

	/**
	 * Loads the contents of an ArrayList from a XML file. Uses a JFileChooser
	 * to accomplish this task.
	 */
	public void loadXML() {
		SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yyyy");
		ArrayList<DVD> loadList = new ArrayList<DVD>();
		JFileChooser chooser = new JFileChooser();
		int status = chooser.showOpenDialog(null);
		if (status != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, "No file has been loaded.");
		} else {
			try {
				File filename = chooser.getSelectedFile();
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(filename);
				NodeList dList = doc.getElementsByTagName("DVD");
				NodeList gList = doc.getElementsByTagName("Game");

				for (int i = 0; i < dList.getLength(); i++) {
					Node nNode = dList.item(i);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						GregorianCalendar b = new GregorianCalendar();
						GregorianCalendar d = new GregorianCalendar();
						Element eElement = (Element) nNode;
						String name = getTagValue("Renter", eElement);
						String title = getTagValue("Title", eElement);
						b.setTime(fmt.parse(getTagValue("Rented", eElement)));
						d.setTime(fmt.parse(getTagValue("Due", eElement)));
						DVD addUnit = new DVD(title, name, b, d);
						loadList.add(addUnit);
					}
				}

				for (int i = 0; i < gList.getLength(); i++) {
					Node nNode = gList.item(i);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						GregorianCalendar b = new GregorianCalendar();
						GregorianCalendar d = new GregorianCalendar();
						Element eElement = (Element) nNode;
						String name = getTagValue("Renter", eElement);
						String title = getTagValue("Title", eElement);
						b.setTime(fmt.parse(getTagValue("Rented", eElement)));
						d.setTime(fmt.parse(getTagValue("Due", eElement)));
						PlayerType player = PlayerType.valueOf(getTagValue(
								"Player", eElement));
						Game addUnit = new Game(title, name, b, d, player);
						loadList.add(addUnit);
					}
				}

				listDVDs = loadList;
				fireContentsChanged(this, 0, listDVDs.size() - 1);

			} catch (RuntimeException error) {
				JOptionPane.showMessageDialog(null,
						"There was an error loading the file");
			} catch (ParserConfigurationException error) {
				JOptionPane.showMessageDialog(null,
						"There was an error parsing the data.");
			} catch (Exception error) {
				JOptionPane.showMessageDialog(null,
						"There was an error loading the data.");
			}
		}
	}

	/**
	 * A private helper method to aid the loading of an ArrayList of DVDs from
	 * an XML file.
	 * 
	 * @param sTag
	 *            the tag of the piece of information
	 * @param eElement
	 *            the current element that is being observed
	 * @return a string that represents what is between the designated tags
	 */
	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
				.getChildNodes();
		Node nValue = (Node) nlList.item(0);
		return nValue.getNodeValue();
	}
}





