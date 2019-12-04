package a08;

public interface IGOLModel {
	
	void initialize(int rows, int cols);
	boolean isAlive(int x, int y);
	void nextStep();
	void resetGame();
	void fillRandom();
	void toggleCell(int x, int y);
	void addObserver(IGOLObserver o);
	void removeObserver(IGOLObserver o);
	int getRowNumber();
	int getColumnNumber();
	void setThreshold(int lowBirth, int highBirth, int lowSurvival, int highSurvival);
	void setTorus(boolean isEnabled);
	boolean getTorus();
}
