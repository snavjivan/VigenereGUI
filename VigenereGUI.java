import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.Scanner;

public class VigenereGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private Vigenere vigenere;

	private JTextField keyText;
	private JTextField clearTextIn;
	private JLabel encodedTextOut;
	private JTextField codedTextIn;
	private JLabel decodedTextOut;
	
	VigenereGUI() {
		super("Vigenere Encoding/Decoding");
		setLayout(new BorderLayout());

		JPanel textPanel = new JPanel(new GridLayout(15, 1));

		textPanel.add(new JLabel());
		textPanel.add(createCenteredLabel("Made by Sarthak"));
		textPanel.add(createCenteredLabel("Enter the Key here:"));
		keyText = new JTextField(50);
		keyText.getDocument().addDocumentListener(new KeyListener());
		textPanel.add(keyText);
		textPanel.add(new JLabel());
		
		textPanel.add(createCenteredLabel("Enter text to encode here:"));
		clearTextIn = new JTextField(50);
		clearTextIn.getDocument().addDocumentListener(new ClearListener());
		textPanel.add(clearTextIn);
		textPanel.add(createCenteredLabel("Coded Text:"));
		encodedTextOut = createOutputLabel();
		textPanel.add(encodedTextOut);

		textPanel.add(new JLabel());
		textPanel.add(createCenteredLabel("Enter text to decode here:"));
		codedTextIn = new JTextField(50);
		codedTextIn.getDocument().addDocumentListener(new CodedListener());
		textPanel.add(codedTextIn);
		textPanel.add(createCenteredLabel("Decoded text:"));
		decodedTextOut = createOutputLabel();
		textPanel.add(decodedTextOut);
		textPanel.add(new JLabel());

		add(textPanel, BorderLayout.CENTER);

		vigenere = new Vigenere("");

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocation(30, 30);
		pack();
		setVisible(true);
	}

	private JLabel createCenteredLabel(String text) {
		JLabel label = new JLabel(text);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.BLUE);
		return label;
	}
	
	private JLabel createOutputLabel() {
		JLabel label = new JLabel();
		label.setOpaque(true);
		label.setBackground(Color.WHITE);
		label.setBorder(BorderFactory.createLineBorder(Color.RED));
		return label;
	}
	
	private void updateKeyString() {
		vigenere.setKey(keyText.getText());
		updateCodedString();
		updateDecodedString();
	}
	
	private void updateCodedString() {
		String line = clearTextIn.getText();
		encodedTextOut.setText(vigenere.encode(line));
	}

	private void updateDecodedString() {
		String line = codedTextIn.getText();
		decodedTextOut.setText(vigenere.decode(line));
	}
		
	public static void main(String[] args) {
		new VigenereGUI();
	}

	class CodedListener implements DocumentListener {

		@Override
		public void changedUpdate(DocumentEvent arg0) {
			updateDecodedString();
		}
		@Override
		public void insertUpdate(DocumentEvent arg0) {
			updateDecodedString();
		}
		@Override
		public void removeUpdate(DocumentEvent arg0) {
			updateDecodedString();
		}
	}

	class ClearListener implements DocumentListener {
		@Override
		public void changedUpdate(DocumentEvent arg0) {
			updateCodedString();
		}
		@Override
		public void insertUpdate(DocumentEvent arg0) {
			updateCodedString();
		}
		@Override
		public void removeUpdate(DocumentEvent arg0) {
			updateCodedString();
		}
	}
	
	class KeyListener implements DocumentListener {
		@Override
		public void changedUpdate(DocumentEvent arg0) {
			updateKeyString();
		}
		@Override
		public void insertUpdate(DocumentEvent arg0) {
			updateKeyString();
		}
		@Override
		public void removeUpdate(DocumentEvent arg0) {
			updateKeyString();
		}

	}

class Vigenere {

	private String key;

	public Vigenere(String key) {
		setKey(key);
	}
	
	public void setKey(String key) {
		if(key == null) {
			this.key = "";
			return;
		}
		
		char[] digit = key.toUpperCase().toCharArray();
		StringBuilder sb = new StringBuilder(digit.length);
		for(char c : digit) {
			if(c >= 'A' && c <= 'Z')
				sb.append(c);
		}
		
		this.key = sb.toString();
	}

	public String encode(String clear) {
		if(clear == null)
			return "";
		if(key.length() == 0)
			return clear.toUpperCase();

		char[] digit = clear.toLowerCase().toCharArray();
		
		String longKey = key;
		while(longKey.length() < clear.length())
			longKey += key;
		
		for(int i = 0; i < digit.length; i++) {
			if(digit[i] < 'a' || digit[i] > 'z') 
				continue;
			
			char offset = longKey.charAt(i);
			int nbShift = offset - 'A';
			digit[i] = Character.toUpperCase(digit[i]);
			digit[i] += nbShift;
			if(digit[i] > 'Z') {
				digit[i] -= 'Z';
				digit[i] += ('A' - 1);			
			}
		}
		return new String(digit);
	}
	
	public String decode(String coded) {
		if(coded == null)
			return "";
		if(key.length() == 0)
			return coded.toLowerCase();

		char[] digit = coded.toUpperCase().toCharArray();
		String longKey = key;
		while(longKey.length() < coded.length())
			longKey += key;
		
		for(int i = 0; i < digit.length; i++) {
			if(digit[i] < 'A' || digit[i] > 'Z') 
				continue;
			
			char offset = longKey.charAt(i);
			int nbShift = offset - 'A';
			digit[i] = Character.toLowerCase(digit[i]);
			digit[i] -= nbShift;
			if(digit[i] < 'a') {
				digit[i] += 'z';
				digit[i] -= ('a' - 1);			
			}

		}
		return new String(digit);
	}
	
	
}



}
