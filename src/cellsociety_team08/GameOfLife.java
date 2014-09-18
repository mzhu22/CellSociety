package cellsociety_team08;

public class GameOfLife extends RuleSet {

	private static final String GAME_OF_LIFE = "Game of Life";

	private static final State[] possibleStates = new State[] {
			new State("Alive", 0, null) };

	public GameOfLife(Object[] params) {
		super(GAME_OF_LIFE, possibleStates, params);
	}

	@Override
	public State getState(Cell[][] neighborhood) {

		int count = 0;
		Cell currCell = neighborhood[1][1];

		for (int i = 0; i < neighborhood.length; i++) {
			for (int j = 0; j < neighborhood[0].length; j++) {
				if (!(i == 1 && j == 1) && neighborhood[i][j] != null
						&& !neighborhood[i][j].isEmpty) {
					count++;
				}
			}
		}
		
		if (currCell.isEmpty && count == 3) {
			revive(currCell);
			return possibleStates[0];
		}

		if (count < 2)
			kill(currCell);
		else if (count > 3)
			kill(currCell);	
		else if (count == 2 || count == 3)
			return currCell.getState();
		
		return null; // returns null if cell is newly empty
	}

	private void kill(Cell cell) {
		cell.isEmpty = true;
	}
	
	private void revive(Cell cell) {
		cell.isEmpty = false;
	}

	@Override
	public int[] getLocation(Cell[][] neighborhood) {
		return neighborhood[1][1].getLocation();
	}

}
