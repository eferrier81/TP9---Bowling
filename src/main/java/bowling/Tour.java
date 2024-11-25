package bowling;

import java.util.List;

public class Tour {
	private int quillesPremierLancer = -1;
	private int quillesDeuxiemeLancer = -1;
	private boolean estBonus = false;

	public Tour() {}

	public Tour(boolean estBonus) {
		this.estBonus = estBonus;
	}

	public void enregistrerLancer(int quillesTombees) {
		if (quillesPremierLancer == -1) {
			quillesPremierLancer = quillesTombees;
		} else if (quillesDeuxiemeLancer == -1) {
			quillesDeuxiemeLancer = quillesTombees;
		} else {
			throw new IllegalStateException("Les deux lancers ont déjà été enregistrés pour ce tour.");
		}
	}

	public boolean estStrike() {
		return quillesPremierLancer == 10;
	}

	public boolean estSpare() {
		return quillesPremierLancer != -1 && quillesDeuxiemeLancer != -1
			&& (quillesPremierLancer + quillesDeuxiemeLancer == 10);
	}

	public boolean estTermine() {
		return estStrike() || (quillesPremierLancer != -1 && quillesDeuxiemeLancer != -1);
	}

	public int calculerScore(int indexTour, List<Tour> tours) {
		int score = quillesPremierLancer + quillesDeuxiemeLancer;

		if (estStrike()) {
			// Bonus : deux lancers suivants
			score += quillesProchainesLancers(indexTour, tours, 2);
		} else if (estSpare()) {
			// Bonus : un lancer suivant
			score += quillesProchainesLancers(indexTour, tours, 1);
		}
		return score;
	}

	private int quillesProchainesLancers(int indexTour, List<Tour> tours, int nbLancers) {
		int quilles = 0;
		int lancersRestants = nbLancers;

		for (int i = indexTour + 1; i < tours.size() && lancersRestants > 0; i++) {
			Tour prochainTour = tours.get(i);
			if (prochainTour.quillesPremierLancer != -1) {
				quilles += prochainTour.quillesPremierLancer;
				lancersRestants--;
			}
			if (lancersRestants > 0 && prochainTour.quillesDeuxiemeLancer != -1) {
				quilles += prochainTour.quillesDeuxiemeLancer;
				lancersRestants--;
			}
		}
		return quilles;
	}

	public int getQuillesPremierLancer() {
		return quillesPremierLancer;
	}
}
