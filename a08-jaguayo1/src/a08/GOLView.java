package a08;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GOLView extends JPanel implements ActionListener, SpotListener, ChangeListener {

	private JSpotBoard _board;
	private Boolean _isTorusEnabled;
	private JSlider _size_slider;
	JButton _torus_button;
//	private JSlider _speed_slider;
//	private GridLayout _grid;
	private List<GOLViewListener> _listeners;

	public GOLView() {
		setBorder(BorderFactory.createEtchedBorder());
		setLayout(new BorderLayout());

		// Make another JPanel to store buttons
		JPanel button_panel = new JPanel();

		// Reset button
		JButton clear_button = new JButton("Reset");
		button_panel.add(clear_button);
		clear_button.setActionCommand("Reset");
		clear_button.addActionListener(this);

		// Next button
		JButton next_button = new JButton("Next");
		button_panel.add(next_button);
		next_button.setActionCommand("Next");
		next_button.addActionListener(this);

		// Random button
		JButton random_button = new JButton("Random");
		button_panel.add(random_button);
		random_button.setActionCommand("Random");
		random_button.addActionListener(this);

		// Torus mode button
		_torus_button = new JButton("Torus OFF");
		button_panel.add(_torus_button);
		_torus_button.setActionCommand("Torus");
		_torus_button.addActionListener(this);

		// Threshold settings button
		JButton threshold_button = new JButton("Set Threshold");
		button_panel.add(threshold_button);
		threshold_button.setActionCommand("Threshold");
		threshold_button.addActionListener(this);

		// Slider for size
		_size_slider = new JSlider(10, 100, 10);
		_size_slider.setPaintTicks(true);
		_size_slider.setSnapToTicks(false);
		_size_slider.setPaintLabels(false);
		_size_slider.setMajorTickSpacing(10);
		button_panel.add(new JLabel("Grid Size: "));
		button_panel.add(_size_slider);
		_size_slider.addChangeListener(this);

		// Start/stop button
//		JButton start_button = new JButton("Start/Stop");
//		button_panel.add(start_button);
//		start_button.setActionCommand("Start");
//		start_button.addActionListener(this);
//
//		// Slider for speed
//		_speed_slider = new JSlider(1, 100, 1);
//		_speed_slider.setPaintTicks(true);
//		_speed_slider.setSnapToTicks(false);
//		_speed_slider.setPaintLabels(false);
//		_speed_slider.setMajorTickSpacing(10);
//		button_panel.add(new JLabel("Speed: "));
//		button_panel.add(_speed_slider);
//		_speed_slider.addChangeListener(this);

		// Add the button panel to the JPanel
		add(button_panel, BorderLayout.SOUTH);

		// Create board
		setBoard(10);

		_listeners = new ArrayList<GOLViewListener>();
		_isTorusEnabled = false;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case ("Reset"):
			System.out.println("Clicked Reset!");
			notifyReset();
			break;
		case ("Next"):
			notifyNextStep();
			break;
//		case ("Start"):
		case ("Random"):
			notifyRandomFill();
			break;
		case ("Torus"):
			toggleTorus();
			notifyTorus(_isTorusEnabled);
			break;
		case ("Threshold"):
			int[] newTh = JOptionPaneMultiInput.showThresholdDialog();
			if (newTh != null) {
				notifyThreshold(newTh[0], newTh[1], newTh[2], newTh[3]);
			}
		}

	}

	// notifies for resize only when user stops dragging slider.
	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		if (!source.getValueIsAdjusting()) {
			int dim = _size_slider.getValue();
			notifyResize(dim);
		}
	}

	@Override
	public void spotClicked(Spot spot) {
		// TODO Auto-generated method stub
		System.out.println("Spot clicked! " + spot.getCoordString());
		notifyCellClicked(spot.getSpotX(), spot.getSpotY());
		// paintCell(spot.getSpotX(), spot.getSpotY());

	}

	@Override
	public void spotEntered(Spot spot) {
		// TODO Auto-generated method stub

	}

	@Override
	public void spotExited(Spot spot) {
		// TODO Auto-generated method stub

	}

	public void reset() {
		for (Spot s : _board) {
			s.clearSpot();
		}
	}

	public void resize(int dim) {
		remove(_board);
		setBoard(dim);
	}

	public void paintCell(int x, int y) {
		Spot spot = _board.getSpotAt(x, y);
		spot.setSpotColor(Color.GREEN);
		spot.setBackground(Color.GREEN);
		spot.toggleSpot();
	}

	public void setTorus(boolean status) {
		if (_isTorusEnabled) {
			_torus_button.setText("Torus ON");
		} else {
			_torus_button.setText("Torus OFF");
		}
	}

	public void addListener(GOLViewListener l) {
		_listeners.add(l);
	}

	public void removeListener(GOLViewListener l) {
		_listeners.remove(l);
	}

	void notifyReset() {
		for (GOLViewListener l : _listeners) {
			l.handleReset();
		}
	};

	void notifyCellClicked(int x, int y) {
		for (GOLViewListener l : _listeners) {
			l.handleCellClicked(x, y);
		}
	};

	void notifyResize(int newSize) {
		for (GOLViewListener l : _listeners) {
			l.handleResize(newSize);
		}
	};

	void notifyNextStep() {
		for (GOLViewListener l : _listeners) {
			l.handleNextStep();
		}
	};

	void notifyRandomFill() {
		for (GOLViewListener l : _listeners) {
			l.handleRandomFill();
		}
	};

	void notifyThreshold(int lowBirth, int highBirth, int lowSurvival, int highSurvival) {
		for (GOLViewListener l : _listeners) {
			l.handleThresholdChange(lowBirth, highBirth, lowSurvival, highSurvival);
		}
	}

	void notifyTorus(boolean status) {
		for (GOLViewListener l : _listeners) {
			l.handleTorus(status);
		}
	}

	private void setBoard(int size) {
		System.out.println(size);
		_board = new JSpotBoard(size, size);
		add(_board, BorderLayout.CENTER);
		revalidate();
		repaint();
		_board.addSpotListener(this);
	}

	private void toggleTorus() {
		if (_isTorusEnabled) {
			_isTorusEnabled = false;
		} else {
			_isTorusEnabled = true;
		}
	}
}
