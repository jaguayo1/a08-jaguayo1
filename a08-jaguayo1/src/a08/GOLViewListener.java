package a08;

public interface GOLViewListener {

	void handleReset();
	void handleCellClicked(int x, int y);
	void handleResize(int newSize);
	void handleNextStep();
	void handleRandomFill();
	void handleThresholdChange(int lowBirth, int highBirth, int lowSurvival, int highSurvival);
	void handleTorus(boolean isEnabled);
}
