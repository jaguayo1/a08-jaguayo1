package a08;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

public class GOLModel implements IGOLModel {

	private boolean[][] game_state;
	private int numberRows = 0;
	private int numberColumns = 0;
	private int lowBirthThreshold = 3;
	private int highBirthThreshold = 3;
	private int lowSurviveThreshold = 2;
	private int highSurviveThreshold = 3;
	private boolean torusEnabled = false;

	private List<IGOLObserver> model_observers;

	public GOLModel(int numRows, int numCols) {
		// Create Observer list
		model_observers = new ArrayList<IGOLObserver>();
		initialize(numRows, numCols);
	}

	@Override
	public void initialize(int rows, int cols) {
		numberRows = rows;
		numberColumns = cols;
		// Calling initialize always resets the board
		game_state = new boolean[rows][cols];
		notifyObservers();
	}

	@Override
	public boolean isAlive(int x, int y) {
		// Throws exception if out of bounds
		return game_state[x][y];
	}

	@Override
	public void nextStep() {
		boolean next_state[][] = new boolean[numberRows][numberColumns];
		for (int i = 0; i < numberRows; i++) {
			for (int j = 0; j < numberColumns; j++) {
				int aliveNeighbors = getNumAliveNeighbors(i, j);
				if (game_state[i][j]) {
					/*
					 * A living cell survives if the number of neighboring live
					 * cells is greater than or equal to 2 (low survive
					 * threshold) and less than or equal to 3 (high survive
					 * threshold)
					 */
					next_state[i][j] = aliveNeighbors >= lowSurviveThreshold && aliveNeighbors <= highSurviveThreshold;
				} else {
					/*
					 * A dead cell to life if the number of neighboring live
					 * cells is greater than or equal to 2 (low birth threshold)
					 * and less than or equal to 3 (high birth threshold) and
					 * otherwise stays dead.
					 */
					next_state[i][j] = aliveNeighbors >= lowBirthThreshold && aliveNeighbors <= highBirthThreshold;
				}
			}
		}
		game_state = next_state;
		notifyObservers();
	}

	private int getNumAliveNeighbors(int x, int y) {
		int count = 0;
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				int newx = x + dx;
				int newy = y + dy;
				if (newx == x && newy == y) {
					continue;
				}
				// Check out of bounds.
				// Assume x and y are in bounds
				if (newx < 0 || newx >= numberRows || newy < 0 || newy >= numberColumns) {
					if(!torusEnabled) continue;
					if(newx == -1)
						newx = numberRows - 1;
					else if(newx == numberRows)
						newx = 0;
					
					if(newy == -1)
						newy = numberColumns - 1;
					else if(newy == numberColumns)
						newy = 0;
				}
				if (isAlive(newx, newy)) {
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public void resetGame() {
		initialize(numberRows, numberColumns);
		notifyObservers();
	}

	@Override
	public void fillRandom() {
		for (int i = 0; i < numberRows; i++) {
			for (int j = 0; j < numberColumns; j++) {
				double rand = Math.random();
				game_state[i][j] = rand < 0.5;
			}
		}
		notifyObservers();
	}

	@Override
	public void toggleCell(int x, int y) {
		// Throws exception if out of bounds
		game_state[x][y] = !game_state[x][y];
		notifyObservers();
	}

	@Override
	public void addObserver(IGOLObserver o) {
		model_observers.add(o);
	}

	@Override
	public void removeObserver(IGOLObserver o) {
		model_observers.remove(o);
	}

	private void notifyObservers() {
		for (IGOLObserver o : model_observers) {
			o.update(this);
		}
	}

	@Override
	public int getRowNumber() {
		return numberRows;
	}

	@Override
	public int getColumnNumber() {
		return numberColumns;
	}

	@Override
	public void setThreshold(int lowBirth, int highBirth, int lowSurvival, int highSurvival) {
		lowBirthThreshold = lowBirth;
		highBirthThreshold = highBirth;
		lowSurviveThreshold = lowSurvival;
		highSurviveThreshold = highSurvival;
		notifyObservers();
	}

	@Override
	public void setTorus(boolean isEnabled) {
		torusEnabled = isEnabled;
		notifyObservers();
	}

	@Override
	public boolean getTorus() {
		return torusEnabled;
	}

}
