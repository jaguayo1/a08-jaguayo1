package a08;

public class GOLController implements GOLViewListener, IGOLObserver {
	IGOLModel _model;
	GOLView _view;
	
	public GOLController(IGOLModel model, GOLView view) {
		_model = model;
		_view = view;
		_model.addObserver(this);
		_view.addListener(this);
		
	}

	@Override
	public void update(IGOLModel model) {
		// TODO Auto-generated method stub
		int dim = model.getColumnNumber();
		_view.resize(dim);
		// Assume it's a square
		for(int x = 0; x < dim; x++)
		{
			for(int y = 0; y < dim; y++)
			{
				if(model.isAlive(x, y))
				{
					_view.paintCell(x, y);
				}
			}
		}
		_view.setTorus(model.getTorus());
	}

	@Override
	public void handleReset() {
		_model.resetGame();
		
	}

	@Override
	public void handleCellClicked(int x, int y) {
		_model.toggleCell(x, y);
		
	}

	@Override
	public void handleResize(int newSize) {
		_model.initialize(newSize, newSize);
	}

	@Override
	public void handleNextStep() {
		_model.nextStep();
		
	}

	@Override
	public void handleRandomFill() {
		_model.fillRandom();
		
	}

	@Override
	public void handleThresholdChange(int lowBirth, int highBirth, int lowSurvival, int highSurvival) {
		_model.setThreshold(lowBirth, highBirth, lowSurvival, highSurvival);
		
	}

	@Override
	public void handleTorus(boolean isEnabled) {
		_model.setTorus(isEnabled);
	}
}
