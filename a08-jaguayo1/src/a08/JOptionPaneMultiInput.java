package a08;

//Taken from StackOverflow.
import javax.swing.*;

public class JOptionPaneMultiInput {
	public static int[] showThresholdDialog() {
		JTextField lowBirthField = new JTextField(5);
		JTextField highBirthField = new JTextField(5);
		JTextField lowSurvivalField = new JTextField(5);
		JTextField highSurvivalField = new JTextField(5);

		JPanel myPanel = new JPanel();
		myPanel.add(new JLabel("Low birth threshold:"));
		myPanel.add(lowBirthField);
		myPanel.add(Box.createHorizontalStrut(15)); // a spacer
		myPanel.add(new JLabel("High birth threshold:"));
		myPanel.add(highBirthField);
		myPanel.add(Box.createHorizontalStrut(15));
		myPanel.add(new JLabel("Low survival threshold:"));
		myPanel.add(lowSurvivalField);
		myPanel.add(Box.createHorizontalStrut(15));
		myPanel.add(new JLabel("High survival threshold:"));
		myPanel.add(highSurvivalField);

		int result = JOptionPane.showConfirmDialog(null, myPanel, "Threshold setting", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			try {
				int lowBirth = Integer.parseInt(lowBirthField.getText());
				int highBirth = Integer.parseInt(highBirthField.getText());
				int lowSurvival = Integer.parseInt(lowSurvivalField.getText());
				int highSurvival = Integer.parseInt(highSurvivalField.getText());

				if (lowBirth <= highBirth && lowBirth >= 0 && lowSurvival <= highSurvival && lowSurvival >= 0) {
					return new int[] {lowBirth, highBirth, lowSurvival, highSurvival};
				}
				throw new Exception();
			} catch (Exception e) {
				showThresholdDialog();
			}
		}
		return null;
	}
}

