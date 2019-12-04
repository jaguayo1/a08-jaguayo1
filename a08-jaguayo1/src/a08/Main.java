package a08;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		GOLModel model = new GOLModel(10,10);
		GOLView view = new GOLView();
		GOLController controller = new GOLController(model, view);
		
		JFrame main_frame = new JFrame();
		main_frame.setTitle("The Game of Life");
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		main_frame.setContentPane(view);

		main_frame.pack();
		main_frame.setVisible(true);
	}

}